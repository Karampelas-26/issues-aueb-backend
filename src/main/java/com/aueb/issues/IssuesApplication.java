package com.aueb.issues;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class IssuesApplication {

	public static void main(String[] args) {
		SpringApplication.run(IssuesApplication.class, args);
	}

}
