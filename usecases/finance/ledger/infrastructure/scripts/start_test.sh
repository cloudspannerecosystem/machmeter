#!/usr/bin/env bash
# Copyright 2022 Google LLC
#
# Use of this source code is governed by a BSD-style
# license that can be found in the LICENSE file or at
# https://developers.google.com/open-source/licenses/bsd

#Script created to launch Jmeter tests directly from the current terminal without accessing the jmeter master pod.
#It requires that you supply the path to the jmx file
#After execution, test script jmx file may be deleted from the pod itself but not locally.

working_dir=`pwd`

#Get namesapce variable
tenant="$1"
if [ -z "$tenant" ]
then
  echo "Enter the name of the namespace where infrastructure is running."
  read tenant
fi

jmx="$2"
[ -n "$jmx" ] || read -p 'Enter path to the jmx file ' jmx

if [ ! -f "$jmx" ];
then
    echo "Test script file was not found in PATH"
    echo "Kindly check and input the correct file path"
    exit
fi

test_name="$(basename "$jmx")"

slave_pods=($(kubectl get po -n "$tenant" | grep jmeter-slave | awk '{print $1}'))
# for array iteration
slavesnum=${#slave_pods[@]}
printf "Number of slaves is %s\n" "${slavesnum}"

for csvfilefull in "$(dirname "$jmx")"/*.csv
  do
    for i in $(seq -f "%0${slavedigits}g" 0 $((slavesnum-1)))
      do
        printf "Copy %s on %s\n" "${csvfilefull}" "${slave_pods[i]}"
        kubectl -n "$tenant" cp "${csvfilefull}" "${slave_pods[i]}":/
      done
  done
#Get Master pod details

master_pod=`kubectl get po -n $tenant | grep jmeter-master | awk '{print $1}'`

kubectl cp "$jmx" -n $tenant "$master_pod:/$test_name"

## Echo Starting Jmeter load test

kubectl exec -ti -n $tenant $master_pod -- /bin/bash /load_test "$test_name"
