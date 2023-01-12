package com.kc.labs.connections;

public class CustomDependencyResult {

    private String service;
    private String status;

    private String currentCode;

    private String expectedCode;
    private String url;

    public CustomDependencyResult(String service, String status, String currentCode, String expectedCode, String url) {
        this.service = service;
        this.status = status;
        this.currentCode = currentCode;
        this.expectedCode = expectedCode;
        this.url = url;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCurrentCode() {
        return currentCode;
    }

    public void setCurrentCode(String currentCode) {
        this.currentCode = currentCode;
    }

    public String getExpectedCode() {
        return expectedCode;
    }

    public void setExpectedCode(String expectedCode) {
        this.expectedCode = expectedCode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
