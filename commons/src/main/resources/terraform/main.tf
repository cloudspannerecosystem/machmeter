// Copyright 2022 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

variable "gcp_project" {
  type = string
}

variable "gke_config" {
  type = object({
    cluster_name            = string
    region                  = string
    network                 = string
    subnetwork              = string
    ip_range_pods_name      = string
    ip_range_services_name  = string
  })
  description = "The configuration specifications for the GKE cluster"
}

variable "spanner_config" {
  type = object({
    instance_name     = string
    database_name     = string
    configuration     = string
    display_name      = string
    processing_units  = number
    environment       = string
  })
  description = "The configuration specifications for the Spanner instance"

  validation {
    condition     = length(var.spanner_config.display_name) >= 4 && length(var.spanner_config.display_name) <= "30"
    error_message = "Display name must be between 4-30 characters long."
  }

  validation {
    condition     = (var.spanner_config.processing_units <= 1000) && (var.spanner_config.processing_units%100) == 0
    error_message = "Processing units must be 1000 or less, and be a multiple of 100."
  }
}

provider "google" {
  project = var.gcp_project
}

module "gke_auth" {
  source = "terraform-google-modules/kubernetes-engine/google//modules/auth"
  depends_on   = [module.gke]
  project_id   = var.gcp_project
  location     = module.gke.location
  cluster_name = module.gke.name
}

resource "local_file" "kubeconfig" {
  content  = module.gke_auth.kubeconfig_raw
  filename = "kubeconfig-${var.spanner_config.instance_name}"
}


module "gcp-network" {
  source       = "terraform-google-modules/network/google"
  project_id   = var.gcp_project
  network_name = "gke-network"
  subnets = [
    {
      subnet_name   = "${var.gke_config.subnetwork}"
      subnet_ip     = "10.10.0.0/16"
      subnet_region = var.gke_config.region
    },
  ]
  secondary_ranges = {
    "${var.gke_config.subnetwork}" = [
      {
        range_name    = var.gke_config.ip_range_pods_name
        ip_cidr_range = "10.20.0.0/16"
      },
      {
        range_name    = var.gke_config.ip_range_services_name
        ip_cidr_range = "10.30.0.0/16"
      },
    ]
  }
}

module "gke" {
  source                 = "terraform-google-modules/kubernetes-engine/google//modules/private-cluster"
  project_id             = var.gcp_project
  name                   = "${var.gke_config.cluster_name}"
  regional               = true
  region                 = var.gke_config.region
  network                = module.gcp-network.network_name
  subnetwork             = module.gcp-network.subnets_names[0]
  ip_range_pods          = var.gke_config.ip_range_pods_name
  ip_range_services      = var.gke_config.ip_range_services_name
  node_pools = [
    {
      name                      = "node-pool"
      machine_type              = "e2-medium"
      node_locations            = "us-central1-c"
      min_count                 = 3
      max_count                 = 3
      disk_size_gb              = 30
    },
  ]
}

resource "google_spanner_instance" "instance" {
  name             = var.spanner_config.instance_name  # << be careful changing this in production
  config           = var.spanner_config.configuration
  display_name     = var.spanner_config.display_name
  processing_units = var.spanner_config.processing_units
  labels           = { "env" = var.spanner_config.environment }
}

resource "google_spanner_database" "database" {
  instance = google_spanner_instance.instance.name
  name     = var.spanner_config.database_name
  deletion_protection = false
  version_retention_period = "1h"
}