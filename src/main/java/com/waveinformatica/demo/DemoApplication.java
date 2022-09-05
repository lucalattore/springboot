package com.waveinformatica.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
@Slf4j
public class DemoApplication {

	@PostConstruct
	void init() {
		log.debug("Starting!!!");
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
