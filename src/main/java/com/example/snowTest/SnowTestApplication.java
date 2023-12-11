package com.example.snowTest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SnowTestApplication {
	static { /* too late ! */
		System.setProperty("java.awt.headless", "false");
		System.out.println(java.awt.GraphicsEnvironment.isHeadless());
		/* ---> prints false */
	}
	public static void main(String[] args) {
		SpringApplication.run(SnowTestApplication.class, args);
	}

}
