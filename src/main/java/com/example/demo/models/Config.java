package com.example.demo.models;

import javax.validation.constraints.NotNull;

public class Config {
    @NotNull(message = "Client property is missing")
    public String client;
    @NotNull(message = "Version property is missing")
    public String version;
    @NotNull(message = "Key property is missing")
    public String key;
    @NotNull(message = "Value property is missing")
    public String value;

    public Config() {}

    public Config(String client, String version, String key, String value) {
        this.client = client;
        this.version = version;
        this.key = key;
        this.value = value;
    }
}
