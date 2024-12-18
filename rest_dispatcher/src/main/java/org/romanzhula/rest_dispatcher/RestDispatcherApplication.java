package org.romanzhula.rest_dispatcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan("org.romanzhula")
@EnableJpaRepositories("org.romanzhula")
@EntityScan("org.romanzhula")
@SpringBootApplication
public class RestDispatcherApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestDispatcherApplication.class, args);
    }

}
