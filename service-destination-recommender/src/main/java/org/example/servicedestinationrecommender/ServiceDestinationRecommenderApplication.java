package org.example.servicedestinationrecommender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application.properties")
public class ServiceDestinationRecommenderApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceDestinationRecommenderApplication.class, args);
	}

}
