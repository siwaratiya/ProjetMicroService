package tn.esprit.apigateway.Config;


import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {


    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder,  FilterAuthentificate filterAuthentificate) {
        return builder.routes()
                .route("internship-service", r -> r.path("/biochar/internship-service/**")
                        .filters(f -> f.filter(filterAuthentificate.apply( new FilterAuthentificate.Config())))
                        .uri("lb://internship-service"))
                .route("appointment-service", r -> r.path("/biochar/appointment-service/**").uri("lb://appointment-service"))
                .route("analyse-service", r -> r.path("/biochar/Analyse-service/**").uri("lb://analyse-service"))
                .route("stock-service", r -> r.path("/biochar/stock-service/**").uri("lb://stock-service"))
                .route("commande-service", r -> r.path("/biochar/commande-service/**").uri("lb://commande-service"))
                .route("hr-service", r -> r.path("/biochar/hr-service/**").uri("lb://hr-service"))
                .route("user-service", r -> r.path("/biochar/user-service/**").uri("lb://user-service"))
                .route("discovery-server", r -> r.path("/eureka/web").filters(f -> f.setPath("/")).uri("http://localhost:8761"))
                .route("discovery-server-static", r -> r.path("/eureka/**") .uri("http://localhost:8761"))
                .build();
    }

}


