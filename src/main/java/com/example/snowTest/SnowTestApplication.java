package com.example.snowTest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SnowTestApplication {
	static {
		System.setProperty("java.awt.headless", "false");
	}
	public static void main(String[] args) {
		SpringApplication.run(SnowTestApplication.class, args);
	}

}
