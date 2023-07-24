package com.emin.couriercase.common;

import com.emin.couriercase.model.StoreModel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectCommonService {
    public Double calculateDistanceBetweenTwoPoints(Double lat1, Double lng1, Double lat2, Double lng2) {
        final int r = 6371;

        double distanceLat = Math.toRadians(lat2 - lat1);
        double distanceLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(distanceLat / 2) * Math.sin(distanceLat / 2) +
                Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(lat1)) *
                        Math.sin(distanceLng / 2) * Math.sin(distanceLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return r * c * 1000;
    }

    public List<StoreModel> readJsonFile() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream inputStream = getClass().getResourceAsStream("/store.json");
        return objectMapper.readValue(inputStream, new TypeReference<List<StoreModel>>() {
        });
    }

    public Long secondsDifference(LocalDateTime firstTime, LocalDateTime secondTime) {
        Duration duration = Duration.between(firstTime, secondTime);
        return duration.abs().getSeconds();
    }
}
