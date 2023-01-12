package com.kc.labs.connections;

public class CustomDependency {

    private String expectedResult;
    private String resourceUrl;

    public CustomDependency(){}

    public CustomDependency(String expectedResult, String resourceUrl) {
        this.expectedResult = expectedResult;
        this.resourceUrl = resourceUrl;
    }

    public String getExpectedResult() {
        return expectedResult;
    }

    public void setExpectedResult(String expectedResult) {
        this.expectedResult = expectedResult;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    @Override
    public String toString() {
        return "CustomDependency{" +
                "expectedResult='" + expectedResult + '\'' +
                ", resourceUrl='" + resourceUrl + '\'' +
                '}';
    }
}
