package com.example.traffic.traffic_analysis_api.repository;

import com.example.traffic.traffic_analysis_api.model.NodeEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface NodeRepository extends CassandraRepository<NodeEntity, Long> {}
