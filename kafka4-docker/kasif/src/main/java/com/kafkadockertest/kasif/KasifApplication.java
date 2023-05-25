package com.kafkadockertest.kasif;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KasifApplication {

	public static void main(String[] args) {

		System.err.println("Lets starts");
		SpringApplication.run(KasifApplication.class, args);
	}

}
