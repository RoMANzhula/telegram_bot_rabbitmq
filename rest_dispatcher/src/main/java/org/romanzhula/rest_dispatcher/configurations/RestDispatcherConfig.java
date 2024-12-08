package org.romanzhula.rest_dispatcher.configurations;

import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;


@Configuration
public class RestDispatcherConfig {

    @Value("${hashids.salt}")
    private String salt;

    @Bean
    public Hashids hashids() {
        int minHashLength = 10;

        return new Hashids(salt, minHashLength);
    }

}
