package org.romanzhula.management.configurations;

import jakarta.annotation.PostConstruct;
import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ManagementConfig {

    @Value("${hashids.salt}")
    private String salt;

    @Bean
    @Primary
    public Hashids hashids() {
        int minHashLength = 10;

        return new Hashids(salt, minHashLength);
    }

}
