package com.anupriya.parkinglot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;

@Configuration
@ConfigurationProperties(prefix = "parking.admin")
public class AdminProperties {
    private Set<String> emails = new HashSet<>();

    public Set<String> getEmails() { return emails; }
    public void setEmails(Set<String> emails) { this.emails = emails; }
}

