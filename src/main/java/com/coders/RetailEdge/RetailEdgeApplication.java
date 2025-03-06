package com.coders.RetailEdge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class RetailEdgeApplication {

	public static void main(String[] args) {
		SpringApplication.run(RetailEdgeApplication.class, args);
	}

}
