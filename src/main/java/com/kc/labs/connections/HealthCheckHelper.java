package com.kc.labs.connections;

import java.net.HttpURLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class HealthCheckHelper {

    private static final String SELECT_TEST_QUERY = "SELECT 1";
    private static final String URL = "jdbc:mysql://localhost:3306/cobis";
    private static final String USER = "root";
    private static final String PASSWORD = "123456789";

    private HashMap<String, CustomDependency> dependencies;

    private HttpURLConnection connection = null;

    public HealthCheckHelper() {
    }

    public HealthCheckHelper(HashMap<String, CustomDependency> dependencies) {
        this.dependencies = dependencies;
    }

    private boolean isRDSHealthy() {
        try (
                Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                Statement stmt = connection.createStatement()
        ) {
            stmt.executeQuery(SELECT_TEST_QUERY);
            return true;
        } catch (SQLException e) {
            System.out.println("Error while connecting to RDS: " + e.getMessage());
            return false;
        }
    }

    public void executeCheckCustomDependencies() {

        for (Map.Entry<String, CustomDependency> entry : dependencies.entrySet()) {
            boolean status = this.isCustomDependencyHealthy(entry.getValue());
            System.out.println(status);
        }
    }


    private boolean isCustomDependencyHealthy(CustomDependency dependency) {
        try {
            connection = (HttpURLConnection) new java.net
                    .URL(dependency.getResourceUrl()).openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0;Win64) AppleWebkit/537.36 " +
                    "(KHTML, like Gecko) Chrome/87.0.4280.66 Safari/537.36");
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            return Integer.toString(responseCode).equals(dependency.getExpectedResult());
        } catch (Exception e) {
            return false;
        }
    }


}
