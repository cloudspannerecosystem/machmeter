gcp_project = "span-cloud-testing"

spanner_config = {
  instance_name     = "spanner-load-test"
  database_name     = "finance-ledger"
  configuration     = "regional-us-central1"
  display_name      = "jmeter finance-ledger instance"
  processing_units  = 1000
  environment       = "testing"
}