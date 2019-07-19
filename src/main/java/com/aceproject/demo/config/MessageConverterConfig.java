package com.aceproject.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.aceproject.demo.converter.DateTimeModule;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class MessageConverterConfig {
    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        // 생성한 모듈을 등록해 준다.
        objectMapper.registerModule(new DateTimeModule());
        jsonConverter.setObjectMapper(objectMapper);

        return jsonConverter;
    }
}