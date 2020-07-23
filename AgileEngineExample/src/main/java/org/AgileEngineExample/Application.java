package org.AgileEngineExample;

import org.AgileEngineExample.service.ImageFetchingService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		ImageFetchingService.initialize();
		
		SpringApplication.run(Application.class, args);
	}		

}