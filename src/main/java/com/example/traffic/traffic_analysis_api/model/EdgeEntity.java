package com.example.traffic.traffic_analysis_api.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Getter
@Setter
@Table("edges")
public class EdgeEntity {

    @PrimaryKey
    private UUID id; // généré aléatoirement pour chaque ligne

    private long osmid;
    private long u;
    private long v;
    private double length;
    private String name;
    private String highway;
    private boolean oneway;
    private String maxspeed;
    private String junction;

    private double estimatedTravelTime; // en secondes ou minutes
    private String timeProfile; // matin, soir, nuit, etc.


}
