package com.example.traffic.traffic_analysis_api.service;

import com.example.traffic.traffic_analysis_api.model.LiveTrafficEntity;
import com.example.traffic.traffic_analysis_api.repository.LiveTrafficRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class LiveTrafficService {

    private final LiveTrafficRepository liveTrafficRepository;

    public Optional<LiveTrafficEntity> getLatest(UUID segmentId) {
        var latest = liveTrafficRepository.findLatestBySegmentId(segmentId);
        if (latest.isEmpty()) {
            log.warn("Aucune donnée temps réel trouvée pour le segment {}", segmentId);
        }
        return latest.stream().findFirst();
    }

    public double getSpeedOrDefault(UUID segmentId, double defaultSpeed) {
        return getLatest(segmentId)
                .map(data -> {
                    double speed = data.getAverageSpeed() != null ? data.getAverageSpeed() : 0;
                    return speed > 0 ? speed : defaultSpeed;
                })
                .orElse(defaultSpeed);
    }

    public double getCongestionFactorOrDefault(UUID segmentId, double defaultFactor) {
        return getLatest(segmentId)
                .map(data -> {
                    Integer level = data.getCongestionLevel();
                    return switch (level != null ? level : -1) {
                        case 0 -> 1.0;
                        case 1 -> 1.2;
                        case 2 -> 1.5;
                        case 3 -> 2.0;
                        default -> defaultFactor;
                    };
                })
                .orElse(defaultFactor);
    }

    public double getWeatherFactorOrDefault(UUID segmentId, double defaultFactor) {
        return getLatest(segmentId)
                .map(data -> {
                    String weather = data.getWeatherConditions();
                    return switch (weather != null ? weather : "") {
                        case "rain" -> 1.2;
                        case "heavy_rain", "storm" -> 1.5;
                        case "fog", "wind" -> 1.3;
                        default -> 1.0;
                    };
                })
                .orElse(defaultFactor);
    }

    public double getEstimatedTravelTimeOrCompute(UUID segmentId, double lengthMeters, double fallbackSpeedKmh) {
        return getLatest(segmentId)
                .map(data -> {
                    Double time = data.getEstimatedTravelTime();
                    if (time != null && time > 0) return time;
                    Double speed = data.getAverageSpeed();
                    return speed != null && speed > 0
                            ? lengthMeters / (speed * 1000.0 / 3600.0)
                            : lengthMeters / (fallbackSpeedKmh * 1000.0 / 3600.0);
                })
                .orElse(lengthMeters / (fallbackSpeedKmh * 1000.0 / 3600.0));
    }
}
