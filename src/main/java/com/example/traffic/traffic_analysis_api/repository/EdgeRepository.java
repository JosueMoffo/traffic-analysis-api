package com.example.traffic.traffic_analysis_api.repository;

import com.example.traffic.traffic_analysis_api.model.EdgeEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.UUID;

public interface EdgeRepository extends CassandraRepository<EdgeEntity, UUID> {}
