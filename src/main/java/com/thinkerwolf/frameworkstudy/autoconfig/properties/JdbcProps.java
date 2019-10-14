package com.thinkerwolf.frameworkstudy.autoconfig.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jdbc")
@PropertySource("jdbc.properties")
public class JdbcProps {

    private String driver;

    private String username;

    private String password;

    private String url;

    private String xaDataSource;

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getXaDataSource() {
        return xaDataSource;
    }

    public void setXaDataSource(String xaDataSource) {
        this.xaDataSource = xaDataSource;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
