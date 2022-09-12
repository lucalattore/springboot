package com.waveinformatica.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@SpringBootApplication
@Slf4j
@RestController
@EnableScheduling
public class DemoApplication {

	@Autowired
	@Qualifier("secondoComponente")
	private MyFirstInterface x;

	@Autowired
	private ApplicationContext ctx;

	@PostConstruct
	void init() {
		log.debug("Starting!!!");
		MyFirstPrototype x = ctx.getBean(MyFirstPrototype.class);
		MyFirstPrototype y = ctx.getBean(MyFirstPrototype.class);

		x.setValue("uno");
		y.setValue("due");

		log.info("X = {}", x.getValue());
		log.info("Y = {}", y.getValue());
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@GetMapping("/hello")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("%s %s", x.getTitle(), name);
	}
}
