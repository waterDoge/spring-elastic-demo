package com.ata.elastic.config;

import com.ata.elastic.strategy.PasswordStrategies;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "config.sys")
public class ApplicationProperties {
    private String host;



    public void setUp() {

    }

    private PasswordStrategies passwordStrategy = PasswordStrategies.SHA_256;

    public PasswordStrategies getPasswordStrategy() {
        return passwordStrategy;
    }

    public void setPasswordStrategy(String name) {
        this.passwordStrategy = PasswordStrategies.valueOf(name);
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
