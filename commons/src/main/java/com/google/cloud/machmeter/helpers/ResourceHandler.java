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
