package com.example.traffic.traffic_analysis_api.model;

import lombok.*;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import static org.springframework.data.cassandra.core.cql.PrimaryKeyType.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyClass
public class LiveTrafficKey implements Serializable {

    @PrimaryKeyColumn(name = "segment_id", type = PARTITIONED)
    private UUID segmentId;

    @PrimaryKeyColumn(name = "timestamp", type = CLUSTERED, ordinal = 0)
    private Date timestamp;
}
