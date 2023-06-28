package com.chaoyang.example.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Collections;

/**
 * 跨域配置
 *
 * @author 韩朝阳
 * @since 2023/3/16
 */
@Configuration
// TODO 待测试
//@ConditionalOnProperty(prefix = "spring.profiles", name = "active", havingValue = "dev | test | demo")
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.setAllowedOriginPatterns(Collections.singletonList("*"));
        corsConfiguration.addAllowedHeader(CorsConfiguration.ALL);
        corsConfiguration.addAllowedMethod(CorsConfiguration.ALL);
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();

        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);

        return new CorsFilter(urlBasedCorsConfigurationSource);
    }

}