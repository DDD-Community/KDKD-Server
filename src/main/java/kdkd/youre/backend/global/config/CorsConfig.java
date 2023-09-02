package kdkd.youre.backend.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Value("chrome.extension.id")
    private String extensionId;

    @Bean
    public CorsFilter corsFilter() {

        CorsConfiguration configuration = new CorsConfiguration();
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        configuration.setAllowCredentials(true); // 내 서버가 응답을 할 때 json을 자바스크립트에서 처리할 수 있게 할지를 설정하는 것
        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.addAllowedOrigin("chrome-extension://" + extensionId);
        configuration.addAllowedHeader("*"); // 모든 header에 응답을 허용
        configuration.addAllowedMethod("*"); // 모든 post, get, put, delete, patch 요청을 허용

        source.registerCorsConfiguration("/**", configuration);
        return new CorsFilter(source);
    }
}
