package com.example.traffic.traffic_analysis_api.repository;

import com.example.traffic.traffic_analysis_api.model.LiveTrafficEntity;
import com.example.traffic.traffic_analysis_api.model.LiveTrafficKey;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LiveTrafficRepository extends CassandraRepository<LiveTrafficEntity, LiveTrafficKey> {

    @Query("SELECT * FROM live_traffic WHERE segment_id = ?0 ORDER BY timestamp DESC LIMIT 1")
    List<LiveTrafficEntity> findLatestBySegmentId(UUID segmentId);
}
