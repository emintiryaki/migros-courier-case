package com.emin.couriercase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication()
@OpenAPIDefinition(info = @Info(title = "Migros Courier API", description = "Migros Courier and Store Matching Case Project"))
public class CouriercaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(CouriercaseApplication.class, args);
	}

}