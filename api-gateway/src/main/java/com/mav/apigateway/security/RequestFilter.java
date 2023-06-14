package com.mav.apigateway.security;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.mav.apigateway.dto.AuthRequest;
import jdk.nashorn.internal.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.ResolvableType;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

@Component
public class RequestFilter implements GlobalFilter,Ordered {
    @Autowired
    private WebClient.Builder webClientBuilder;
    private String email;
    private static final Logger logger = LoggerFactory.getLogger(RequestFilter.class);
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain)
    {
        String jwtToken = extractJwtToken(exchange.getRequest());
        System.out.println("JWT TOKEN "+jwtToken);
        try {
            Boolean isValidTokenMono = validateJwtToken(exchange, jwtToken);
            //validateJwtToken(jwtToken, exchange);
            if (isValidTokenMono)
                return chain.filter(exchange);
            else {
                return Mono.error(new RuntimeException("Invalid JWT token"));
            }
        }
        catch (Exception ex)
        {
            // System.out.println("Mono "+ex);
            return errorResponse(exchange, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }

    }

    private Mono<Void> errorResponse(ServerWebExchange exchange, HttpStatus status, String errorMessage) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        String errorBody = "{\"error\": \"" + errorMessage + "\"}";
        return response.writeWith(Mono.just(response.bufferFactory().wrap(errorBody.getBytes())));
    }

    private Boolean validateJwtToken(ServerWebExchange exchange,String jwtToken) {
        RestTemplate restTemplate = new RestTemplate();
        if (jwtToken != null)
        {
            String authUrl = "http://localhost:3000/api/auth/validateToken";
            System.out.println("validateJwtToken If Part "+jwtToken);
            try {
                ResponseEntity<?> responseEntity = restTemplate.postForEntity(authUrl + "?jwtToken=" + jwtToken, null, Boolean.class);
                System.out.println("validateJwtToken If Part " + responseEntity.getBody());
                return true;
            }
            catch (HttpStatusCodeException ex) {

                throw new RuntimeException(ex.getResponseBodyAsString(), ex);
            }


            //if (!responseEntity.getStatusCode().is2xxSuccessful()) {

            // return true;
        }

        else {
            AuthRequest authRequest = new AuthRequest();
            authRequest.setEmail(email);
            authRequest.setPassword("yash");
            String authUrl = "http://localhost:3000/api/auth/authenticate";
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(authUrl, authRequest, String.class);
            System.out.println("ValidateJwt "+responseEntity);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                String authResponse = responseEntity.getBody();
                // Process the authentication response here if needed
            } else {
                // Handle the error response from the authentication service
                // You can throw an exception or return false to indicate authentication failure
                return false;

            }
            return true;
        }
    }


//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        String jwtToken = extractJwtToken(exchange.getRequest());
//        //System.out.println("Yash "+jwtToken);
//        validateJwtToken(jwtToken);
//        return chain.filter(exchange);
//    }



    private String extractJwtToken(ServerHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        String authorizationHeader = headers.getFirst(HttpHeaders.AUTHORIZATION);
        email = headers.getFirst("email");
        System.out.println(email);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // Extract the token by removing the "Bearer " prefix
            String jwtToken = authorizationHeader.substring(7);
            return jwtToken;
        }

        // Token not found or invalid format
        return null;
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
