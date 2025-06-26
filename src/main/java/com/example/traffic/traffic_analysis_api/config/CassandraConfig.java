package com.example.traffic.traffic_analysis_api.config;

import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CassandraConfig {

    @Bean
    public CqlSessionBuilderCustomizer sessionBuilderCustomizer() {
        return builder -> builder
                .withLocalDatacenter("datacenter1")
                .withKeyspace("traffic_keyspace");
    }
}
