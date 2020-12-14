package com.apse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
//@EnableJpaRepositories(basePackages = {"com.example.demo.BookDao"})
//@EntityScan(basePackages = {"com.example.demo.Book"})
public class ApseApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApseApplication.class, args);
	}

}

