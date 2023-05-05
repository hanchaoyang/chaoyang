package com.chaoyang.example.config;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Json配置
 *
 * @author 韩朝阳
 * @since 2023/5/5
 */
@Configuration
public class JsonConfig {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizer() {
        return builder -> builder.serializerByType(Long.class, ToStringSerializer.instance);
    }

}