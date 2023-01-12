package com.kc.labs.connections;

public class LambdaHandler extends HealthCheckLogicExecutor {


    public LambdaHandler() {
        super();
        this.dependencies.put("google", new CustomDependency("200", "https://www.google.com"));
        this.dependencies.put("yahoo", new CustomDependency("200", "https://www.yahoo.com"));

    }


}
