package com.sjiwon.wantedconcurrency.global.config;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.MySQLContainer;

public class MySQLTestContainerConfiguration implements BeforeAllCallback {
    private MySQLContainer<?> mysql;

    @Override
    public void beforeAll(ExtensionContext context) {
        mysql = new MySQLContainer("mysql")
                .withDatabaseName("testdb")
                .withUsername("root")
                .withPassword("1234");
        mysql.start();
    }
}
