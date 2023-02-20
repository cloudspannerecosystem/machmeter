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

package com.google.cloud.machmeter.ddl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SpannerJdbcDdl {

  /**
   * Executing this will require setting the environment variable GOOGLE_APPLICATION_CREDENTIALS to
   * the service accounts credential path.
   */
  public void executeSqlFile() throws SQLException, FileNotFoundException {
    String projectId = "span-cloud-testing";
    String instanceId = "alpha-test";
    String databaseId = "test-google";
    String sqlFile = "/Users/alpha/queries.sql";
    executeSqlFile(projectId, instanceId, databaseId, sqlFile);
  }

  /**
   * Executing this will require setting the environment variable GOOGLE_APPLICATION_CREDENTIALS to
   * the service accounts credential path.
   */
  public void executeSqlFile(String projectId, String instanceId, String databaseId, String sqlFile)
      throws SQLException, FileNotFoundException {
    String connectionUrl =
        String.format(
            "jdbc:cloudspanner:/projects/%s/instances/%s/databases/%s",
            projectId, instanceId, databaseId);
    try (Connection connection = DriverManager.getConnection(connectionUrl)) {
      ScriptRunner sr = new ScriptRunner(connection);
      Reader reader = new BufferedReader(new FileReader(sqlFile));
      sr.runScript(reader);
    }
  }
}
