package com.myshop.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("com.myshop.repository")
@EntityScan("com.myshop.domain")
@SpringBootApplication
public class AppConfig {

	public static void main(String[] args) {
		SpringApplication.run(AppConfig.class, args);
	}

}
