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
    namespace               = string
    region                  = string
    network                 = string
    subnetwork              = string
    ip_range_pods_name      = string
    ip_range_services_name  = string
    service_account_json    = string
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

data "google_client_config" "default" {}

provider "kubernetes" {
  host                   = "https://${module.gke.endpoint}"
  token                  = data.google_client_config.default.access_token
  cluster_ca_certificate = base64decode(module.gke.ca_certificate)
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
  grant_registry_access    = true
  remove_default_node_pool = false
  create_service_account   = false
  node_pools_oauth_scopes = {
    all = [
      "https://www.googleapis.com/auth/logging.write",
      "https://www.googleapis.com/auth/monitoring",
      "https://www.googleapis.com/auth/cloud-platform",
    ]
  }
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

resource "kubernetes_namespace" "namespace" {
  metadata  {
    name = "spanner-test"
  }
}

resource "kubernetes_secret" "service_account" {
  metadata {
    name      = "sa-key"
    namespace = kubernetes_namespace.namespace.metadata.0.name
  }
  data = {
    "key.json" = file("${var.gke_config.service_account_json}")
  }
}

resource "kubernetes_config_map" "configmap_jmeter_load_test" {
  metadata {
    labels = {
      "app" = "influxdb-jmeter"
    }
    name = "jmeter-load-test"
    namespace = kubernetes_namespace.namespace.metadata.0.name
  }

  data = {
    "load_test" = <<-EOT
      #!/bin/bash
      #Script created to invoke jmeter test script with the slave POD IP addresses
      #Script should be run like: ./load_test "path to the test script in jmx format"
      /jmeter/apache-jmeter-*/bin/jmeter -n -t "$@" -Dserver.rmi.ssl.disable=true -R `getent ahostsv4 jmeter-slaves-svc | cut -d' ' -f1 | sort -u | awk -v ORS=, '{print $1}' | sed 's/,$//'`

      EOT
  }
}

resource "kubernetes_config_map" "configmap_influxdb_config" {
  metadata {
    labels = {
      "app" = "influxdb-jmeter"
    }
    name = "influxdb-config"
    namespace = kubernetes_namespace.namespace.metadata.0.name
  }
  data = {
    "influxdb.conf" = <<-EOT
      [meta]
        dir = "/var/lib/influxdb/meta"
      [data]
        dir = "/var/lib/influxdb/data"
        engine = "tsm1"
        wal-dir = "/var/lib/influxdb/wal"
      # Configure the graphite api
      [[graphite]]
      enabled = true
      bind-address = ":2003" # If not set, is actually set to bind-address.
      database = "jmeter"  # store graphite data in this database

      EOT
  }
}

resource "kubernetes_config_map" "configmap_grafana_config" {
  metadata {
    labels = {
      "app" = "jmeter-grafana"
    }
    name = "grafana-config"
    namespace = kubernetes_namespace.namespace.metadata.0.name
  }
  data = {
    "influxdb-datasource.yml" = file("influxdb-datasource.yml")
    "grafana-dashboard-provider.yml" = file("grafana-dashboard-provider.yml")
    "grafana-dashboard.json" = file("grafana-dashboard.json")
  }
}

resource "kubernetes_deployment" "jmeter-master" {
  metadata {
    name = "jmeter-master"
    namespace =  kubernetes_namespace.namespace.metadata.0.name
    labels = {
      jmeter_mode = "master"
    }
  }

  spec {
    replicas = 1
    selector {
      match_labels = {
        jmeter_mode = "master"
      }
    }
    template {
      metadata {
        labels = {
          jmeter_mode = "master"
        }
      }
      spec {
        container {
          image = "gcr.io/span-cloud-testing/jmeter-master:latest"
          name  = "jmmaster"
          image_pull_policy = "Always"
          command = [ "/bin/bash", "-c", "--" ]
          args = [ "while true; do sleep 30; done;" ]
          volume_mount {
            mount_path = "/load_test"
            name       = "loadtest"
            sub_path = "load_test"
          }
          volume_mount {
            mount_path = "/var/secrets/google"
            name       = "google-cloud-key"
          }
          port {
            container_port = 60000
          }
          env {
            name = "GOOGLE_APPLICATION_CREDENTIALS"
            value = "/var/secrets/google/key.json"
          }
        }
        volume {
          name = "loadtest"
          config_map {
            name = "jmeter-load-test"
            items {
              key  = "load_test"
              path = "load_test"
              mode = "0755"
            }
          }
        }
        volume {
          name = "google-cloud-key"
          secret {
            secret_name = "sa-key"
          }
        }
      }
    }
  }
}

resource "kubernetes_deployment" "jmeter-slave" {
  metadata {
    name = "jmeter-slaves"
    namespace =  kubernetes_namespace.namespace.metadata.0.name
    labels = {
      jmeter_mode = "slave"
    }
  }
  spec {
    replicas = 2
    selector {
      match_labels = {
        jmeter_mode = "slave"
      }
    }
    template {
      metadata {
        labels = {
          jmeter_mode = "slave"
        }
      }
      spec {
        container {
          image = "gcr.io/span-cloud-testing/jmeter-slave:latest"
          name  = "jmslave"
          image_pull_policy = "Always"
          volume_mount {
            mount_path = "/var/secrets/google"
            name       = "google-cloud-key"
          }
          port {
            container_port = 1099
          }
          port {
            container_port = 50000
          }
          resources {
            limits = {
              cpu    = "200m"
              memory = "2Gi"
            }
            requests = {
              cpu    = "100m"
              memory = "1Gi"
            }
          }
          env {
            name = "GOOGLE_APPLICATION_CREDENTIALS"
            value = "/var/secrets/google/key.json"
          }
        }
        volume {
          name = "google-cloud-key"
          secret {
            secret_name = "sa-key"
          }
        }
      }
    }
  }
}

resource "kubernetes_deployment" "influxdb" {
  metadata {
    labels = {
      app = "influxdb-jmeter"
    }
    name = "influxdb-jmeter"
    namespace =  kubernetes_namespace.namespace.metadata.0.name
  }
  spec {
    replicas = 1
    selector {
      match_labels = {
        app = "influxdb-jmeter"
      }
    }
    template {
      metadata {
        labels = {
          app = "influxdb-jmeter"
        }
      }
      spec {
        container {
          image           = "influxdb:1.8.3"
          image_pull_policy = "Always"
          name            = "influxdb"
          volume_mount {
            mount_path = "/etc/influxdb"
            name       = "config-volume"
          }
          port {
            container_port = 8083
            name = "influx"
          }
          port {
            container_port = 8086
            name = "api"
          }
          port {
            container_port = 2003
            name = "graphite"
          }
          env {
            name = "INFLUXDB_DB"
            value = "jmeter"
          }
        }
        volume {
          name = "config-volume"
          config_map {
            name = "influxdb-config"
          }
        }
      }
    }
  }
}

resource "kubernetes_deployment" "jmeter-grafana" {
  metadata {
    labels = {
      app = "jmeter-grafana"
    }
    name = "jmeter-grafana"
    namespace =  kubernetes_namespace.namespace.metadata.0.name
  }
  spec {
    replicas = 1
    selector {
      match_labels = {
        app = "jmeter-grafana"
      }
    }
    template {
      metadata {
        labels            = {
          app = "jmeter-grafana"
        }
      }
      spec {
        container {
          env {
            name  = "GF_AUTH_BASIC_ENABLED"
            value = "true"
          }
          env {
            name  = "GF_USERS_ALLOW_ORG_CREATE"
            value = "true"
          }
          env {
            name  = "GF_AUTH_ANONYMOUS_ENABLED"
            value = "true"
          }
          env {
            name  = "GF_AUTH_ANONYMOUS_ORG_ROLE"
            value = "Admin"
          }
          env {
            name  = "GF_SERVER_ROOT_URL"
            value = "/"
          }
          image           = "grafana/grafana:5.2.0"
          image_pull_policy = "Always"
          name            = "grafana"
          port {
            container_port = 3000
            protocol = "TCP"
          }
          volume_mount {
            mount_path = "/etc/grafana/provisioning/datasources/influxdb-datasource.yml"
            name       = "grafana-config"
            read_only = true
            sub_path = "influxdb-datasource.yml"
          }
          volume_mount {
            mount_path = "/etc/grafana/provisioning/dashboards/grafana-dashboard-provider.yml"
            name       = "grafana-config"
            read_only = true
            sub_path = "grafana-dashboard-provider.yml"
          }
          volume_mount {
            mount_path = "/var/lib/grafana/dashboards/grafana-dashboard.json"
            name       = "grafana-config"
            read_only = true
            sub_path = "grafana-dashboard.json"
          }
        }
        volume {
          name = "grafana-config"
          config_map {
            name = "grafana-config"
          }
        }
      }
    }
  }
}

resource "kubernetes_service" "service_jmeter_slave" {
  metadata {
    labels = {
      "jmeter_mode" = "slave"
    }
    name = "jmeter-slaves-svc"
    namespace = kubernetes_namespace.namespace.metadata.0.name
  }
  spec  {
    cluster_ip = "None"
    port {
      name = "first"
      port = 1099
      protocol = "TCP"
      target_port = 1099
    }
    port {
      name = "second"
      port = 50000
      protocol = "TCP"
      target_port = 50000
    }
    selector = {
      "jmeter_mode" = "slave"
    }
    type = "ClusterIP"
  }
}

resource "kubernetes_service" "service_influxdb" {
  metadata  {
    labels = {
      "app" = "influxdb-jmeter"
    }
    name = "jmeter-influxdb"
    namespace =  kubernetes_namespace.namespace.metadata.0.name
  }
  spec {
    port {
      name = "http"
      port = 8083
      target_port = 8083
    }
    port {
      name = "api"
      port = 8086
      target_port = 8086
    }
    port {
      name = "graphite"
      port = 2003
      target_port = 2003
    }
    selector = {
      "app" = "influxdb-jmeter"
    }
  }
}

resource "kubernetes_service" "service_jmeter_grafana" {
  metadata {
    labels = {
      "app" = "jmeter-grafana"
    }
    name = "jmeter-grafana"
    namespace =  kubernetes_namespace.namespace.metadata.0.name
  }
  spec {
    port {
      node_port = 31458
      port = 3000
      protocol = "TCP"
      target_port = 3000
    }
    selector = {
      "app" = "jmeter-grafana"
    }
    type = "NodePort"
  }
}