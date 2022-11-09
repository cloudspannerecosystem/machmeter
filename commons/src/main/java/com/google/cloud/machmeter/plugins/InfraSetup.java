package com.google.cloud.machmeter.plugins;

import com.google.cloud.machmeter.model.GKEConfig;
import com.google.cloud.machmeter.model.MachmeterConfig;
import com.google.common.io.Resources;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class InfraSetup implements PluginInterface {

    String content = "// Copyright 2022 Google LLC\n" +
            "//\n" +
            "// Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
            "// you may not use this file except in compliance with the License.\n" +
            "// You may obtain a copy of the License at\n" +
            "//\n" +
            "//    https://www.apache.org/licenses/LICENSE-2.0\n" +
            "//\n" +
            "// Unless required by applicable law or agreed to in writing, software\n" +
            "// distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
            "// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
            "// See the License for the specific language governing permissions and\n" +
            "// limitations under the License.\n" +
            "\n" +
            "variable \"gcp_project\" {\n" +
            "  type = string\n" +
            "}\n" +
            "#\n" +
            "#variable \"gke_config\" {\n" +
            "#  type = object({\n" +
            "#    cluster_name            = string\n" +
            "#    region                  = string\n" +
            "#    network                 = string\n" +
            "#    subnetwork              = string\n" +
            "#    ip_range_pods_name      = string\n" +
            "#    ip_range_services_name  = string\n" +
            "#  })\n" +
            "#  description = \"The configuration specifications for the GKE cluster\"\n" +
            "#}\n" +
            "\n" +
            "variable \"spanner_config\" {\n" +
            "  type = object({\n" +
            "    instance_name     = string\n" +
            "    database_name     = string\n" +
            "    configuration     = string\n" +
            "    display_name      = string\n" +
            "    processing_units  = number\n" +
            "    environment       = string\n" +
            "  })\n" +
            "  description = \"The configuration specifications for the Spanner instance\"\n" +
            "\n" +
            "  validation {\n" +
            "    condition     = length(var.spanner_config.display_name) >= 4 && length(var.spanner_config.display_name) <= \"30\"\n" +
            "    error_message = \"Display name must be between 4-30 characters long.\"\n" +
            "  }\n" +
            "\n" +
            "  validation {\n" +
            "    condition     = (var.spanner_config.processing_units <= 1000) && (var.spanner_config.processing_units%100) == 0\n" +
            "    error_message = \"Processing units must be 1000 or less, and be a multiple of 100.\"\n" +
            "  }\n" +
            "}\n" +
            "\n" +
            "provider \"google\" {\n" +
            "  project = var.gcp_project\n" +
            "}\n" +
            "#\n" +
            "#module \"gke_auth\" {\n" +
            "#  source = \"terraform-google-modules/kubernetes-engine/google//modules/auth\"\n" +
            "#  depends_on   = [module.gke]\n" +
            "#  project_id   = var.gcp_project\n" +
            "#  location     = module.gke.location\n" +
            "#  cluster_name = module.gke.name\n" +
            "#}\n" +
            "#\n" +
            "#resource \"local_file\" \"kubeconfig\" {\n" +
            "#  content  = module.gke_auth.kubeconfig_raw\n" +
            "#  filename = \"kubeconfig-${var.spanner_config.instance_name}\"\n" +
            "#}\n" +
            "\n" +
            "\n" +
            "#module \"gcp-network\" {\n" +
            "#  source       = \"terraform-google-modules/network/google\"\n" +
            "#  project_id   = var.gcp_project\n" +
            "#  network_name = \"gke-network\"\n" +
            "#  subnets = [\n" +
            "#    {\n" +
            "#      subnet_name   = \"${var.gke_config.subnetwork}\"\n" +
            "#      subnet_ip     = \"10.10.0.0/16\"\n" +
            "#      subnet_region = var.gke_config.region\n" +
            "#    },\n" +
            "#  ]\n" +
            "#  secondary_ranges = {\n" +
            "#    \"${var.gke_config.subnetwork}\" = [\n" +
            "#      {\n" +
            "#        range_name    = var.gke_config.ip_range_pods_name\n" +
            "#        ip_cidr_range = \"10.20.0.0/16\"\n" +
            "#      },\n" +
            "#      {\n" +
            "#        range_name    = var.gke_config.ip_range_services_name\n" +
            "#        ip_cidr_range = \"10.30.0.0/16\"\n" +
            "#      },\n" +
            "#    ]\n" +
            "#  }\n" +
            "#}\n" +
            "\n" +
            "#module \"gke\" {\n" +
            "#  source                 = \"terraform-google-modules/kubernetes-engine/google//modules/private-cluster\"\n" +
            "#  project_id             = var.gcp_project\n" +
            "#  name                   = \"${var.gke_config.cluster_name}\"\n" +
            "#  regional               = true\n" +
            "#  region                 = var.gke_config.region\n" +
            "#  network                = module.gcp-network.network_name\n" +
            "#  subnetwork             = module.gcp-network.subnets_names[0]\n" +
            "#  ip_range_pods          = var.gke_config.ip_range_pods_name\n" +
            "#  ip_range_services      = var.gke_config.ip_range_services_name\n" +
            "#  node_pools = [\n" +
            "#    {\n" +
            "#      name                      = \"node-pool\"\n" +
            "#      machine_type              = \"e2-medium\"\n" +
            "#      node_locations            = \"us-central1-c\"\n" +
            "#      min_count                 = 3\n" +
            "#      max_count                 = 3\n" +
            "#      disk_size_gb              = 30\n" +
            "#    },\n" +
            "#  ]\n" +
            "#}\n" +
            "\n" +
            "resource \"google_spanner_instance\" \"instance\" {\n" +
            "  name             = var.spanner_config.instance_name  # << be careful changing this in production\n" +
            "  config           = var.spanner_config.configuration\n" +
            "  display_name     = var.spanner_config.display_name\n" +
            "  processing_units = var.spanner_config.processing_units\n" +
            "  labels           = { \"env\" = var.spanner_config.environment }\n" +
            "}\n" +
            "\n" +
            "resource \"google_spanner_database\" \"database\" {\n" +
            "  instance = google_spanner_instance.instance.name\n" +
            "  name     = var.spanner_config.database_name\n" +
            "  deletion_protection = false\n" +
            "  version_retention_period = \"1h\"\n" +
            "}";
    private static final Logger logger = Logger.getLogger(InfraSetup.class.getName());

    @Override
    public String getName() {
        return "infraSetup";
    }

    @Override
    public void execute(MachmeterConfig config) {
//        File f = new File(System.getProperty("user.dir")+"/machmeter/main.tf");
//        if(!f.getParentFile().exists()){
//            f.getParentFile().mkdirs();
//        }
//        if(!f.exists()){
//            try {
//                f.createNewFile();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        Charset utf8 = StandardCharsets.UTF_8;
//        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(f), utf8)) {
//            writer.write(content);
//        } catch (IOException e) {}
//        File f= new File(Resources.getResource("main.tf").getPath());
//        System.out.println(f.getParent());
//        String terraformCommand = String.format("terraform -chdir=%s plan -var=gcp_project=%s", f.getParent(),"span-cloud-testing");
        String terraformCommand = String.format("terraform plan -var=gcp_project=%s", "span-cloud-testing");
//
//        List<String> terraformCommand = new ArrayList<>();
//        terraformCommand.add("terraform");
//        terraformCommand.add("-chdir="+System.getProperty("user.dir")+"/machmeter");
//        terraformCommand.add("plan");
//        terraformCommand.add("-var='gcp_project=span-cloud-testing'");
//        terraformCommand.add("-var='spanner_config={\"instance_name\":\"spanner-load-test\",\"database_name\":\"finance-ledger\"" +
//                ",\"configuration\":\"regional-us-central1\",\"display_name\":\"jmeter finance-ledger instance\"" +
//                ",\"processing_units\":1000,\"environment\":\"testing\"" +
//                "}'");

//        List<String> terraformCommand2 = new ArrayList<>();
//        terraformCommand2.add("terraform");
//        terraformCommand2.add("plan");
//        terraformCommand2.add("-var='gcp_project=\"span-cloud-testing\"'");
//        terraformCommand2.add("-var='spanner_config={\"instance_name\":\"spanner-load-test\",\"database_name\":\"finance-ledger\"" +
//                ",\"configuration\":\"regional-us-central1\",\"display_name\":\"jmeter finance-ledger instance\"" +
//                ",\"processing_units\":1000,\"environment\":\"testing\"" +
//                "}'");
//
//        List<String> terraformCommand3 = new ArrayList<>();
//        terraformCommand3.add("terraform");
//        terraformCommand3.add("apply");
//        terraformCommand3.add("--auto-approve");
        try {
            logger.log(Level.INFO, "Executing terraform chdir. {0}", terraformCommand.toString());
            run(terraformCommand);
//            logger.log(Level.INFO, "Executing terraform plan. {0}", terraformCommand2.toString());
//            run(terraformCommand2);
//            logger.log(Level.INFO, "Executing terraform apply. {0}", terraformCommand3.toString());
//            run(terraformCommand3);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void run(String executeCommand) throws Exception{
        ProcessBuilder processBuilder = new ProcessBuilder(executeCommand.split(" "));
        processBuilder.inheritIO();
        processBuilder.directory(new File("machmeter_output/terraform"));
        Process process = processBuilder.start();
        StringBuilder output = new StringBuilder();
        BufferedReader reader
                = new BufferedReader(new InputStreamReader(
                process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line + "\n");
        }

        int exitVal = process.waitFor();
        if (exitVal == 0) {
            System.out.println("Success!");
            System.out.println(output);
            System.exit(0);
        } else {
            System.out.println("Fail!");
            System.out.println(output);
            System.exit(1);
        }
    }
}
