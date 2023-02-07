/*
 * Copyright 2022 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.cloud.machmeter.helpers;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class ShellExecutor {

  public void run(String executeCommand, String directory) throws Exception {
    ProcessBuilder processBuilder = new ProcessBuilder("/bin/sh", "-c", executeCommand);
    processBuilder.inheritIO();
    processBuilder.directory(new File(directory));
    Process process = processBuilder.start();
    StringBuilder output = new StringBuilder();
    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
    String line;
    while ((line = reader.readLine()) != null) {
      output.append(line).append("\n");
    }

    int exitVal = process.waitFor();
    if (exitVal == 0) {
      System.out.println("Success!");
      System.out.println(output);
    } else {
      System.out.println("Fail!");
      System.out.println(output);
      System.exit(1);
    }
  }
}
