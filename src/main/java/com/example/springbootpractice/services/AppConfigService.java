package com.example.springbootpractice.services;

import com.example.springbootpractice.entity.AppConfig;
import com.example.springbootpractice.repository.AppConfigRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppConfigService {

    @Autowired
    AppConfigRepository appConfigRepository;

    public Map<String, String> appCache;

    @PostConstruct
    public void setAppCache(){
        appCache = new HashMap<>();
        List<AppConfig> all = appConfigRepository.findAll();
        for (AppConfig appConfig:all){
            appCache.put(appConfig.getKey(), appConfig.getValue());
        }

    }

}
