package com.example.traffic.traffic_analysis_api.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Getter
@Setter
@Table("live_traffic")
public class LiveTrafficEntity {

    @PrimaryKey
    private LiveTrafficKey id;

    private Double averageSpeed;
    private Integer congestionLevel;
    private String weatherConditions;
    private String timeSlot;
    private Double estimatedTravelTime;
}
