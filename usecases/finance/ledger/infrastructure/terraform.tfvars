gcp_project = "span-cloud-testing"

gke_config = {
  cluster_name           = "jmeter-load-test"
  region                 = "us-central1"
  network                = "gke-network"
  subnetwork             = "gke-subnet"
  ip_range_pods_name     = "ip-range-pods"
  ip_range_services_name = "ip-range-services"
}

spanner_config = {
  instance_name     = "spanner-load-test"
  database_name     = "finance-ledger"
  configuration     = "regional-us-central1"
  display_name      = "jmeter finance-ledger instance"
  processing_units  = 1000
  environment       = "testing"
}