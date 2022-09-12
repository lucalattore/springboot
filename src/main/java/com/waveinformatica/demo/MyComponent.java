package com.waveinformatica.demo;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Primary
@Slf4j
@Profile("test")
public class MyComponent implements MyFirstInterface {

    @Value("${demo.gruppo.esempio}")
    private String test;

    @Value("${demo.gruppo.k:10}")
    private Integer k;

    @Value("${demo.gruppo.s:}")
    private String s;

    @PostConstruct
    void init() {
        log.info("Valore di test: {}", test);
        log.info("Valore di k: {}", k);
        log.info("Valore di s: {} blank: {}", s, StringUtils.isBlank(s));
    }

    @Scheduled(fixedDelayString = "${demo.job.period:5000}")
    void myFirstTask() {
        log.info("Eccomi!");
    }

    @Override
    public String getTitle() {
        return "Hello";
    }
}
