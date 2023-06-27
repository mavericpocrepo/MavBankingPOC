package com.mav.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication

public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

	@Bean
	public WebClient.Builder webClientBuilder() {
		return WebClient.builder();
	}
	@Bean
	public RestTemplate restTemplate()
	{
		return new RestTemplate();
	}

//	@Bean
//	public CorsWebFilter corsWebFilter()
//	{
//		CorsConfiguration corsConfig = new CorsConfiguration();
//		corsConfig.addAllowedOrigin("*");
//		corsConfig.addAllowedHeader("*");
//		corsConfig.addAllowedMethod("*");
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		source.registerCorsConfiguration("/**",corsConfig);
//		return new CorsWebFilter(source);
//	}

}
