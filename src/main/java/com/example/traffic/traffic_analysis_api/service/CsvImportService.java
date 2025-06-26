package com.example.traffic.traffic_analysis_api.service;

import com.example.traffic.traffic_analysis_api.model.NodeEntity;
import com.example.traffic.traffic_analysis_api.model.EdgeEntity;
import com.example.traffic.traffic_analysis_api.repository.NodeRepository;
import com.example.traffic.traffic_analysis_api.repository.EdgeRepository;
import com.opencsv.CSVReader;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.UUID;

@Service
@Slf4j
public class CsvImportService {

    @Autowired private NodeRepository nodeRepository;
    @Autowired private EdgeRepository edgeRepository;

    private static final double DEFAULT_ESTIMATED_TRAVEL_TIME = 30.0; // valeur par défaut

    /**
    @PostConstruct
    public void importCsvFiles() {
        edgeRepository.deleteAll();
        nodeRepository.deleteAll();
        importNodes();
        importEdges();
    }
    */


    public void importNodes() {
        try (
                InputStream inputStream = getClass().getClassLoader().getResourceAsStream("static/yaounde_nodes.csv")
        ) {
            if (inputStream == null) {
                log.error("Fichier yaounde_nodes.csv introuvable.");
                return;
            }

            try (CSVReader reader = new CSVReader(new InputStreamReader(inputStream))) {
                String[] line;
                reader.readNext();

                while ((line = reader.readNext()) != null) {
                    NodeEntity node = new NodeEntity();
                    node.setOsmid(parseLongSafe(line, 0));
                    node.setX(parseDoubleSafe(line, 1, 0.0));
                    node.setY(parseDoubleSafe(line, 2, 0.0));
                    node.setName(getOrDefault(line, 3, "NAN"));
                    node.setRef(getOrDefault(line, 4, "NAN"));
                    node.setHighway(getOrDefault(line, 5, "NAN"));

                    nodeRepository.save(node);
                    log.info("import du noeud : " +node.getOsmid());
                }

                log.info("✅ Import des noeuds terminé.");
            }
        } catch (Exception e) {
            log.error("Erreur lors de l'import des noeuds : ", e);
        }
    }

    public void importEdges() {
        try (
                InputStream inputStream = getClass().getClassLoader().getResourceAsStream("static/yaounde_edges.csv")
        ) {
            if (inputStream == null) {
                log.error("Fichier yaounde_edges.csv introuvable.");
                return;
            }

            try (CSVReader reader = new CSVReader(new InputStreamReader(inputStream))) {
                String[] line;
                reader.readNext();

                while ((line = reader.readNext()) != null) {
                    EdgeEntity edge = new EdgeEntity();
                    edge.setId(UUID.randomUUID());
                    edge.setU(parseLongSafe(line, 0));
                    edge.setV(parseLongSafe(line, 1));
                    edge.setLength(parseDoubleSafe(line, 2, 1.0));

                    edge.setName(getOrDefault(line, 3, "NAN"));
                    edge.setHighway(getOrDefault(line, 4, "residential"));
                    edge.setOneway(parseBooleanSafe(line, 5, false));
                    edge.setOsmid(parseLongSafe(line, 6));
                    edge.setMaxspeed(getOrDefault(line, 7, "50"));
                    edge.setJunction(getOrDefault(line, 8, ""));

                    // assignation fixe de estimatedTravelTime
                    edge.setEstimatedTravelTime(DEFAULT_ESTIMATED_TRAVEL_TIME);
                    edge.setTimeProfile("default");

                    edgeRepository.save(edge);
                    log.info("import de l'arret : " + edge.getOsmid());
                }

                log.info("✅ Import des arêtes terminé.");
            }
        } catch (Exception e) {
            log.error("❌ Erreur lors de l'import des arêtes : ", e);
        }
    }

    // ===== Utilitaires =====

    private String getOrDefault(String[] line, int index, String defaultValue) {
        return (line.length > index && line[index] != null && !line[index].trim().isEmpty())
                ? line[index].trim()
                : defaultValue;
    }

    private double parseDoubleSafe(String[] line, int index, double defaultValue) {
        try {
            return Double.parseDouble(line[index].trim());
        } catch (Exception e) {
            return defaultValue;
        }
    }

    private long parseLongSafe(String[] line, int index) {
        try {
            return Long.parseLong(line[index].trim());
        } catch (Exception e) {
            return 0L;
        }
    }

    private boolean parseBooleanSafe(String[] line, int index, boolean defaultValue) {
        try {
            return Boolean.parseBoolean(line[index].trim());
        } catch (Exception e) {
            return defaultValue;
        }
    }
}
