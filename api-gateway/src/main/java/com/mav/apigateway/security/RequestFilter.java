package com.mav.apigateway.security;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;



@Component
public class RequestFilter implements GlobalFilter,Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain)
    {
        ServerHttpRequest request = exchange.getRequest();
        String jwtToken = extractJwtToken(exchange.getRequest());
        String requestUrl = request.getURI().toString();
        System.out.println(requestUrl);
        if(jwtToken!=null) {
            try {
                Boolean isValidTokenMono = validateJwtToken(exchange, jwtToken);
                if (isValidTokenMono)
                    return chain.filter(exchange);
                else {
                    return Mono.error(new RuntimeException("Invalid JWT token"));
                }
            }
            catch (Exception ex)
            {
                System.out.println(ex);
                return errorResponse(exchange, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
            }

        }
        else
        {
            if(requestUrl.contains("/api/auth/authenticate")||requestUrl.contains("/api/users/register"))
            {
                return chain.filter(exchange);
            }
            else
                return errorResponse(exchange,HttpStatus.UNAUTHORIZED,"No JWT Token Found");
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
        String authUrl = "http://localhost:3000/api/auth/validateToken";
        try
            {
                ResponseEntity<?> responseEntity = restTemplate.postForEntity(authUrl + "?jwtToken=" + jwtToken, null, Boolean.class);
                System.out.println("validateJwtToken If Part " + responseEntity.getBody());
                return true;
            }
        catch (HttpStatusCodeException ex)
            {
                System.out.println(ex.getResponseBodyAsString()+" yash "+ex.getMessage());
                throw new RuntimeException(ex.getResponseBodyAsString(), ex);
            }
    }
    private String extractJwtToken(ServerHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        String authorizationHeader = headers.getFirst(HttpHeaders.AUTHORIZATION);
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
