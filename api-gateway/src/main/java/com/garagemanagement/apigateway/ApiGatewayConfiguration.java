package com.garagemanagement.apigateway;


import io.netty.resolver.DefaultAddressResolverGroup;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.netty.http.client.HttpClient;


@Configuration
public class ApiGatewayConfiguration {
    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder){
        // with this config, we can access service via load balancing
        return builder.routes().
//                route(p -> p.path("/accessory-service/**")
//                        .uri("lb://accessory-service")).
//                route(p -> p.path("/car-repair-service/**")
//                        .uri("lb://car-repair-service")).
//                route(p -> p.path("/user-service/**")
//                        .uri("lb://user-service")).
                build();
    }


}
