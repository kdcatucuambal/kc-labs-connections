package com.kc.labs.connections;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.HttpURLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public abstract class HealthCheckLogicExecutor {

    private static final String SELECT_TEST_QUERY = "SELECT 1";

    private static final String URL = "jdbc:mysql://localhost:3306/cobis";
    private static final String USER = "root";
    private static final String PASSWORD = "123456789";

    protected HashMap<String, CustomDependency> dependencies;

    protected HashMap<String, CustomDependencyResult> results;
    public HealthCheckLogicExecutor() {
        dependencies = new HashMap<>();
        results = new HashMap<>();
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

    public void executeCheckDependencies() {

        //RDS
        boolean rdsStatus = this.isRDSHealthy();
        results.put("rds", new CustomDependencyResult(
                "rds", rdsStatus ? "UP" : "DOWN", "200", "200", "JDBC"));

        if (dependencies.size() != 0) {
            for (Map.Entry<String, CustomDependency> entry : dependencies.entrySet()) {
                boolean status = this.isCustomDependencyHealthy(entry.getValue());
                results.put(entry.getKey(),
                        new CustomDependencyResult(entry.getKey(),
                                status ? "UP" : "DOWN",
                                "200",
                                entry.getValue().getExpectedResult(),
                                entry.getValue().getResourceUrl()));
            }
        }
    }


    private boolean isCustomDependencyHealthy(CustomDependency dependency) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new java.net
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

    public String getResults() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(results);
    }

}
