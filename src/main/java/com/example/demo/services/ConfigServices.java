package com.example.demo.services;

import com.example.demo.models.Change;
import com.example.demo.models.Config;
import com.example.demo.repositories.ConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigServices {
    final private ConfigRepository repository;

    @Autowired
    public ConfigServices(ConfigRepository repository) {
        this.repository = repository;
    }

    public Change getNewChange(String etag, String id) {
        if (repository.noUpdatesAvailable(id, etag)) {
            return null;
        } else {
            Config config = repository.getConfig(id);
            return new Change(config.key, config.value);
        }
    }

    public String getLatestEtag(String id) {
        return repository.getLatestEtag(id);
    }

    public void addConfig(Config config) {
        String id = config.client + "/" + config.version;
        repository.addConfig(id, config);
    }
}
