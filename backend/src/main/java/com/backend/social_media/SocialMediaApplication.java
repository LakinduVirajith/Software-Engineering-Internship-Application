package com.backend.social_media;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SocialMediaApplication {

	@Value("${server.port}")
	private String PORT;

	public static void main(String[] args) {
		SpringApplication.run(SocialMediaApplication.class, args);
	}

	@GetMapping
	public String home() {
		return "Social Media App is up and running on port " + PORT;
	}
}
