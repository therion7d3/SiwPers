package it.uniroma3.siw.siwpers.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Accetta da tutte le origini
                .allowedOrigins("http://localhost:4200")  // Origine consentita (indirizzo di Angular)
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Metodi HTTP consentiti
                .allowCredentials(true);  // Consentire i cookie, se applicabile
    }
}
