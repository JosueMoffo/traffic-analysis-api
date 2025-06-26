package com.example.traffic.traffic_analysis_api.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Getter
@Setter
@Table(value = "nodes", keyspace = "traffic_keyspace")
public class NodeEntity {

    @PrimaryKey
    private Long osmid;

    private double x;
    private double y;
    private String name;
    private String ref;
    private String highway;

    // Getters & setters
}
