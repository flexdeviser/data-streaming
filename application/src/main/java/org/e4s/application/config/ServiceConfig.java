package org.e4s.application.config;

import org.e4s.stream.ScriptService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ServiceConfig {
    @Bean
    public ScriptService scriptService() {
        return new ScriptService();
    }

}
