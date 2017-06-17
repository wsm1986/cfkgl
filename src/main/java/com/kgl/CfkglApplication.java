package com.kgl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CfkglApplication {

	public static void main(String[] args) {
		SpringApplication.run(CfkglApplication.class, args);
	}
}
