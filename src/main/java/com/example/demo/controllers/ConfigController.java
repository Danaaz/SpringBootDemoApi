package com.example.demo.controllers;

import com.example.demo.models.Change;
import com.example.demo.models.Config;
import com.example.demo.services.ConfigServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/config")
public class ConfigController {
    final private ConfigServices service;

    @Autowired
    public ConfigController(ConfigServices service) {
        this.service = service;
    }

    @PostMapping("/")
    public ResponseEntity addClient(@Valid @RequestBody Config config) {
        service.addConfig(config);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{client}/{version}")
    public ResponseEntity helloWorld(
            @PathVariable("client") String client,
            @PathVariable("version") String version,
            @RequestHeader(value = "If-None-Match", required = false) String etag) {

        String id = client + "/" + version;
        Change newChange = service.getNewChange(etag, id);
        if (newChange == null) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        } else {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .eTag(service.getLatestEtag(id))
                    .body(newChange);
        }
    }
}
