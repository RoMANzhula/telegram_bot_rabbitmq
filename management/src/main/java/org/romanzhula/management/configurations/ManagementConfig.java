package org.romanzhula.management.configurations;

import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ManagementConfig {

    @Value("${hashids.salt}")
    private String salt;

    @Bean
    public Hashids getHashId() {
        int minHashLength = 10;

        return new Hashids(salt, minHashLength);
    }

}
