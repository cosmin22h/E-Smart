package ro.tuc.ds2021.handaric.cosmin.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Value("${client.react}")
    private String localDevReact;
    @Value("${client.nginx}")
    private String localDevNgInx;
    @Value("${client.heroku}")
    private String prodHeroku;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins(localDevReact, localDevNgInx, prodHeroku)
                        .allowedMethods("GET", "POST", "PUT", "DELETE");
            }
        };
    }
}