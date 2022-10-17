#!/usr/bin/env bash


working_dir=`pwd`

echo "Running perf test..."
echo "Setting up k8s in new namespace "
./scripts/cluster_create.sh
echo "Done setting up k8s"
#Get namesapce variable
tenant=`awk '{print $NF}' $working_dir/tenant_export`
echo "Creating google service account secretMap..."
kubectl create secret -n $tenant generic sa-key --from-file=key.json=/Users/irahul/cloud-spanner-poc-validation/sa.json
echo "Done creating service account..."
sleep 5
echo "Setup Grafana datasource..."
./scripts/dashboard.sh
echo "Port forward grafana on local..."
kubectl port-forward -n $tenant $(kubectl get po -n $tenant | grep jmeter-grafana | awk '{print $1}') 3001:3000