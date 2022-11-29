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
