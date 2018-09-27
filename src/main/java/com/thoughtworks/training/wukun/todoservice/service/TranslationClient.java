package com.thoughtworks.training.wukun.todoservice.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "translation-service", url = "http://localhost:9500")
public interface TranslationClient {
    @GetMapping("/translations/{query}")
    String translate(@PathVariable("query") String query);
}
