package com.nikolai.network;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class NetworkApplication {

	public static void main(String[] args) {
		SpringApplication.run(NetworkApplication.class, args);
	}

}
