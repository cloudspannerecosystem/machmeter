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
  ddl = [
    "CREATE TABLE user_bal_new ( UserId STRING(1024) NOT NULL, CSpent INT64, CEarn INT64, Balance INT64 NOT NULL, EngmntLvl STRING(1024), StreakDay STRING(1024), Source STRING(1024), CreationTime TIMESTAMP NOT NULL OPTIONS ( allow_commit_timestamp = true ), UpdationTime TIMESTAMP, ) PRIMARY KEY(UserId)",
    "CREATE TABLE user_txns_new ( UserId STRING(1024) NOT NULL, TransactionId STRING(1024) NOT NULL, Amount INT64 NOT NULL, TransactionType STRING(1024) NOT NULL, TransactionStatus STRING(1024) NOT NULL, RewardType STRING(1024) NOT NULL, TransactionSource STRING(1024), CreationTime TIMESTAMP NOT NULL OPTIONS ( allow_commit_timestamp = true ), ) PRIMARY KEY(UserId, TransactionId), INTERLEAVE IN PARENT user_bal_new ON DELETE CASCADE",
  ]
}