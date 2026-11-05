package com.example.QuickserveEurekaServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class QuickserveEurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuickserveEurekaServerApplication.class, args);
	}

}
