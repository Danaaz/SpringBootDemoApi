package com.example.demo.repositories;

import com.example.demo.models.Config;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ConfigRepository {
    private Map<String, Config> db = new HashMap<>();
    private Map<String, String> etags = new HashMap<>();

    public String getLatestEtag(String id) {
        return etags.get(id);
    }

    public boolean noUpdatesAvailable(String id, String etag) {
        String currentEtag = etags.get(id);
        return currentEtag == null || currentEtag.equals(etag);
    }

    public Config getConfig(String id) {
        return db.get(id);
    }

    public void addConfig(String id, Config config) {
        etags.put(id, generateNewEtag(id));
        db.put(id, config);
    }

    private String generateNewEtag(String id) {
        String old = etags.get(id);
        if (old == null) {
            return "W/1";
        } else {
            int newMod = Integer.parseInt(old.split("/")[1]) + 1;
            return "W/"+newMod;
        }
    }
}
