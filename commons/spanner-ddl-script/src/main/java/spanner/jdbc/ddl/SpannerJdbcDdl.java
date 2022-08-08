package spanner.jdbc.ddl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SpannerJdbcDdl {


  public static void main(String[] arg) throws SQLException, FileNotFoundException {
    executeSqlFile();
  }

  /**
   * Executing this will require setting the environment variable GOOGLE_APPLICATION_CREDENTIALS
   * to the service accounts credential path.
   * @throws SQLException
   * @throws FileNotFoundException
   */
  static void executeSqlFile() throws SQLException, FileNotFoundException {
    String projectId = "span-cloud-testing";
    String instanceId = "asapha-test";
    String databaseId = "pratick-test-google";
    String sqlFile = "/Users/pratick/benchmarking-poc/queries.sql";
    executeSqlFile(projectId, instanceId, databaseId, sqlFile);
  }


  /**
   * Executing this will require setting the environment variable GOOGLE_APPLICATION_CREDENTIALS
   * to the service accounts credential path.
   * @throws SQLException
   * @throws FileNotFoundException
   */
  static void executeSqlFile(String projectId, String instanceId, String databaseId, String sqlFile)
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
