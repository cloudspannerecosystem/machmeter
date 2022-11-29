package com.google.cloud.machmeter.helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

/** A helper to copy resources from a JAR file into a directory. */
public final class ResourceHandler {
  private static final String MACHMETER_ASSETS = "%s/machmeter-assets.txt";

  public void copyResourceDirectory(final String sourceFolder, final Path targetDirPath) {
    try (BufferedReader listFile =
        new BufferedReader(
            new InputStreamReader(
                Objects.requireNonNull(
                    getClass()
                        .getClassLoader()
                        .getResourceAsStream(String.format(MACHMETER_ASSETS, sourceFolder))),
                StandardCharsets.UTF_8))) {
      String line;
      while ((line = listFile.readLine()) != null) {
        String assetResource = String.format("%s/%s", sourceFolder, line);
        System.out.println("resource: " + assetResource);
        Path assetFile = targetDirPath.resolve(assetResource);
        Files.createDirectories(assetFile.getParent());
        try (InputStream asset = getClass().getClassLoader().getResourceAsStream(assetResource)) {
          Files.copy(asset, assetFile, StandardCopyOption.REPLACE_EXISTING);
        }
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
