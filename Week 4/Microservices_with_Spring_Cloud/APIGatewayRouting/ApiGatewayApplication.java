import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApiGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("product-service-route", r -> r
                .path("/api/products/**")
                .filters(f -> f.stripPrefix(1))
                .uri("lb://product-service"))
            .route("order-service-route", r -> r
                .path("/api/orders/**")
                .filters(f -> f.stripPrefix(1))
                .uri("lb://order-service"))
            .route("payment-service-route", r -> r
                .path("/api/payments/**")
                .filters(f -> f.stripPrefix(1))
                .uri("lb://payment-service"))
            .build();
    }
}
