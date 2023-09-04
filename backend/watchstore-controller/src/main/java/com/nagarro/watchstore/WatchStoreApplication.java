package com.nagarro.watchstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

/**
 * The main class for the Watch Store application.
 * This class contains the entry point for running the application.
 * 
 * @author vishaldeswal, yogesh
 */
@SpringBootApplication
@ComponentScan("com.nagarro.watchstore")
@EnableJpaAuditing
@OpenAPIDefinition(info=@Info(
		title="Watch Store API",
		description="Watch Store api details",
		termsOfService="https://www.nagarro-es.com/wp-content/uploads/2021/02/Service-Licence-Conditions_v6_202003_en.pdf",
		version="1.0",
		contact=@Contact(
				email="info@nagarro.com",
				name="Nagarro SE",
				url="https://www.nagarro.com"
				),
		license=@License(
				name="Nagarro",
				url="https://www.nagarro.com"
				)
		))
public class WatchStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(WatchStoreApplication.class, args);
	}
}
