package com.zaitec.gs02;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Gs02Application {

	public static void main(String[] args) {
		SpringApplication.run(Gs02Application.class, args);
	}

}
