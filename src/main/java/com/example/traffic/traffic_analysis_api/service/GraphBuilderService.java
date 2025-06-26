package com.example.traffic.traffic_analysis_api.service;

import com.example.traffic.traffic_analysis_api.model.EdgeEntity;
import com.example.traffic.traffic_analysis_api.model.NodeEntity;
import com.example.traffic.traffic_analysis_api.repository.EdgeRepository;
import com.example.traffic.traffic_analysis_api.repository.NodeRepository;
import lombok.Getter;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GraphBuilderService {

    @Autowired private NodeRepository nodeRepository;
    @Autowired private EdgeRepository edgeRepository;
    @Autowired private LiveTrafficService liveTrafficService;

    @Getter
    private DefaultDirectedWeightedGraph<Long, DefaultWeightedEdge> graphDistance;

    @Getter
    private DefaultDirectedWeightedGraph<Long, DefaultWeightedEdge> graphTime;

    // === Graphe pondéré par distance ===
    public void buildGraphDistance() {
        graphDistance = new DefaultDirectedWeightedGraph<>(DefaultWeightedEdge.class);

        List<NodeEntity> nodes = nodeRepository.findAll();
        for (NodeEntity node : nodes) {
            graphDistance.addVertex(node.getOsmid());
        }

        List<EdgeEntity> edges = edgeRepository.findAll();
        for (EdgeEntity edge : edges) {
            Long source = edge.getU();
            Long target = edge.getV();

            if (graphDistance.containsVertex(source) && graphDistance.containsVertex(target)) {
                DefaultWeightedEdge e = graphDistance.addEdge(source, target);
                if (e != null) {
                    graphDistance.setEdgeWeight(e, edge.getLength());
                }

                if (!edge.isOneway()) {
                    DefaultWeightedEdge rev = graphDistance.addEdge(target, source);
                    if (rev != null) {
                        graphDistance.setEdgeWeight(rev, edge.getLength());
                    }
                }
            }
        }

        System.out.println(STR."Graphe (distance) construit avec \{graphDistance.vertexSet().size()} sommets et \{graphDistance.edgeSet().size()} arêtes.");
    }

    // === Graphe pondéré par temps estimé dynamique ===
    public void buildGraphTime() {
        graphTime = new DefaultDirectedWeightedGraph<>(DefaultWeightedEdge.class);

        List<NodeEntity> nodes = nodeRepository.findAll();
        for (NodeEntity node : nodes) {
            graphTime.addVertex(node.getOsmid());
        }

        List<EdgeEntity> edges = edgeRepository.findAll();
        for (EdgeEntity edge : edges) {
            Long source = edge.getU();
            Long target = edge.getV();

            if (graphTime.containsVertex(source) && graphTime.containsVertex(target)) {

                double estimatedTime = estimateTravelTime(edge);

                DefaultWeightedEdge e = graphTime.addEdge(source, target);
                if (e != null) {
                    graphTime.setEdgeWeight(e, estimatedTime);
                }

                if (!edge.isOneway()) {
                    DefaultWeightedEdge rev = graphTime.addEdge(target, source);
                    if (rev != null) {
                        graphTime.setEdgeWeight(rev, estimatedTime);
                    }
                }
                System.out.println(STR."Segment \{source} → \{target} = \{estimatedTime} s");

            }

        }

        System.out.println(STR."Graphe (temps estimé) construit avec \{graphTime.vertexSet().size()} sommets et \{graphTime.edgeSet().size()} arêtes.");
    }

    // === Dijkstra (distance) ===
    public List<Long> findShortestPath(long sourceId, long targetId) {
        if (graphDistance == null) buildGraphDistance();

        if (!graphDistance.containsVertex(sourceId) || !graphDistance.containsVertex(targetId)) {
            throw new IllegalArgumentException("Source ou cible introuvable dans le graphe (distance).");
        }

        var dijkstra = new DijkstraShortestPath<>(graphDistance);
        var path = dijkstra.getPath(sourceId, targetId);
        return path != null ? path.getVertexList() : List.of();
    }

    // === Dijkstra (temps estimé) ===
    public List<Long> findOptimalPath(long sourceId, long targetId) {

        if (graphTime == null || graphTime.vertexSet().isEmpty()) {
            System.out.println("Graphe temps estimé vide : déclenchement de build");
            buildGraphTime();
        }

        if (!graphTime.containsVertex(sourceId) || !graphTime.containsVertex(targetId)) {
            throw new IllegalArgumentException("Source ou cible introuvable dans le graphe (temps).");
        }

        var dijkstra = new DijkstraShortestPath<>(graphTime);
        var path = dijkstra.getPath(sourceId, targetId);
        System.out.println(STR."Recherche chemin optimal de \{sourceId} à \{targetId}");

        return path != null ? path.getVertexList() : List.of();
    }

    // === Export GeoJSON ===
    public String exportPathAsGeoJson(List<Long> pathNodeIds) {
        JSONArray coordinates = new JSONArray();

        for (Long id : pathNodeIds) {
            NodeEntity node = nodeRepository.findById(id).orElse(null);
            if (node != null) {
                JSONArray coord = new JSONArray();
                coord.put(node.getX()); // Longitude
                coord.put(node.getY()); // Latitude
                coordinates.put(coord);
            }
        }

        // Construction d’un objet GeoJSON de type Feature
        JSONObject lineFeature = new JSONObject();
        lineFeature.put("type", "Feature");

        JSONObject geometry = new JSONObject();
        geometry.put("type", "LineString");
        geometry.put("coordinates", coordinates);

        lineFeature.put("geometry", geometry);

        JSONObject properties = new JSONObject();
        properties.put("name", "Trajet optimal");
        lineFeature.put("properties", properties);

        // FeatureCollection finale
        JSONObject featureCollection = new JSONObject();
        featureCollection.put("type", "FeatureCollection");
        featureCollection.put("features", new JSONArray().put(lineFeature));

        return featureCollection.toString(2); // indentation
    }

    // === Estimation dynamique du temps de trajet ===
    private double estimateTravelTime(EdgeEntity edge) {
        double lengthMeters = edge.getLength();

        // Données dynamiques depuis live_traffic (sinon valeur par défaut)
        double defaultSpeed = parseMaxSpeed(edge.getMaxspeed(), 30.0); // km/h
        double speed = liveTrafficService.getSpeedOrDefault(edge.getId(), defaultSpeed);
        double congestion = liveTrafficService.getCongestionFactorOrDefault(edge.getId(), 1.0);
        double weather = liveTrafficService.getWeatherFactorOrDefault(edge.getId(), 1.0);

        // temps brut en secondes
        double baseTimeSec = lengthMeters / (speed * 1000.0 / 3600.0);
        return baseTimeSec * congestion * weather;
    }

    // === Extraction de vitesse max numérique ===
    private double parseMaxSpeed(String speedStr, double defaultSpeed) {
        if (speedStr == null) return defaultSpeed;
        try {
            return Double.parseDouble(speedStr.replaceAll("[^0-9]", ""));
        } catch (Exception e) {
            return defaultSpeed;
        }
    }
}
