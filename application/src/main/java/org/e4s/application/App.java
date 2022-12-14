package org.e4s.application;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args).close();
    }

    @Bean
    @Profile("default") // Don't run from test(s)
    public ApplicationRunner runner() {
        return args -> {
            System.out.println("Hit Enter to terminate...");
            System.in.read();
        };
    }

}
