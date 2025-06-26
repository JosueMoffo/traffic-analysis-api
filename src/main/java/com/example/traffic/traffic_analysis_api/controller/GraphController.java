package com.example.traffic.traffic_analysis_api.controller;

import com.example.traffic.traffic_analysis_api.service.GraphBuilderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/graph")
public class GraphController {

    @Autowired
    private GraphBuilderService graphBuilderService;

    // === Construction du graphe par distance ===
    @GetMapping("/build-distance")
    public ResponseEntity<String> buildGraphDistance() {
        graphBuilderService.buildGraphDistance();
        return ResponseEntity.ok("Graphe (distance) construit !");
    }

    // === Construction du graphe par temps estimé ===
    @GetMapping("/build-time")
    public ResponseEntity<String> buildGraphTime() {
        graphBuilderService.buildGraphTime();
        return ResponseEntity.ok("Graphe (temps estimé) construit !");
    }

    // === Chemin le plus court (distance) ===
    @GetMapping("/shortest-path")
    public ResponseEntity<?> getShortestPath(@RequestParam long from, @RequestParam long to) {
        try {
            List<Long> path = graphBuilderService.findShortestPath(from, to);
            return ResponseEntity.ok(path);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ============= Chemin optimal (temps estimé) ==============
    @GetMapping("/optimal-path")
    public ResponseEntity<?> getOptimalPath(@RequestParam long from, @RequestParam long to) {
        try {
            List<Long> path = graphBuilderService.findOptimalPath(from, to);
            return ResponseEntity.ok(path);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // === Export GeoJSON pour chemin par distance ===
    @GetMapping("/shortest-path/geojson")
    public ResponseEntity<String> getShortestPathGeoJson(
            @RequestParam long from,
            @RequestParam long to
    ) {
        try {
            List<Long> path = graphBuilderService.findShortestPath(from, to);
            String geojson = graphBuilderService.exportPathAsGeoJson(path);
            return ResponseEntity.ok()
                    .header("Content-Type", "application/json")
                    .body(geojson);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(STR."{\"error\": \"\{e.getMessage()}\"}");
        }
    }

    // === Export GeoJSON pour chemin par temps estimé ===
    @GetMapping("/optimal-path/geojson")
    public ResponseEntity<String> getOptimalPathGeoJson(
            @RequestParam long from,
            @RequestParam long to
    ) {
        try {
            List<Long> path = graphBuilderService.findOptimalPath(from, to);
            String geojson = graphBuilderService.exportPathAsGeoJson(path);
            return ResponseEntity.ok()
                    .header("Content-Type", "application/json")
                    .body(geojson);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(STR."{\"error\": \"\{e.getMessage()}\"}");
        }
    }
}
