package com.chewie;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
// API Doc available at http://localhost:8080/swagger-ui/index.html
public class ChewieApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChewieApplication.class, args);
	}

}
