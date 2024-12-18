package org.romanzhula.mail_dispatcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan("org.romanzhula*")
@EntityScan("org.romanzhula.*")
@SpringBootApplication
public class MailDispatcherApplication {

    public static void main(String[] args) {
        SpringApplication.run(MailDispatcherApplication.class, args);
    }

}
