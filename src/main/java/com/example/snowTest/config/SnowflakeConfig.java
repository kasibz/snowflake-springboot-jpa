package com.example.snowTest.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class SnowflakeConfig {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @EventListener(ApplicationReadyEvent.class)
    public void initialize() {
        try {
            jdbcTemplate.execute("ALTER SESSION SET JDBC_QUERY_RESULT_FORMAT='JSON'");
        } catch (Exception e) {
            throw new RuntimeException("Failed to set JDBC_QUERY_RESULT_FORMAT", e);
        }
    }
}

