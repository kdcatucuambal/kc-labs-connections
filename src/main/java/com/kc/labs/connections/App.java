package com.kc.labs.connections;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.HttpURLConnection;
import java.util.HashMap;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        LambdaHandler lambdaHandler = new LambdaHandler();
        lambdaHandler.executeCheckDependencies();
        try {
            System.out.println(lambdaHandler.getResults());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}