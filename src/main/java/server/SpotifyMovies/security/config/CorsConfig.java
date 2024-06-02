package server.SpotifyMovies.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/moviesVault/followers/**")
                .allowedOrigins("http://localhost:3002", "http://localhost:3001", "http://localhost:3000", "http://localhost:3003")
                .allowedMethods("GET", "POST", "DELETE");
    }
}

