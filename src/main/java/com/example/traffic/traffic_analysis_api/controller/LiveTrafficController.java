package com.example.traffic.traffic_analysis_api.controller;

import com.example.traffic.traffic_analysis_api.service.LiveTrafficService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/live-traffic")
@RequiredArgsConstructor
public class LiveTrafficController {

    private final LiveTrafficService liveTrafficService;

    @GetMapping
    public ResponseEntity<?> getLiveTraffic(@RequestParam UUID segmentId) {
        return liveTrafficService.getLatest(segmentId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
