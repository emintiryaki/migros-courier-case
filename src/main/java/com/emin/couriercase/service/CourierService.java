package com.emin.couriercase.service;

import com.emin.couriercase.common.ProjectCommonService;
import com.emin.couriercase.model.StoreModel;
import com.emin.couriercase.model.dto.CourierLocationRequestDto;
import com.emin.couriercase.entity.CourierStoreMatch;
import com.emin.couriercase.repository.CourierStoreMatchRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import com.emin.couriercase.repository.CourierRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.emin.couriercase.entity.Courier;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourierService {
    private final CourierRepository courierRepository;
    private final ProjectCommonService commonService;
    private final CourierStoreMatchRepository storeMatchRepository;

    public List<Courier> getAllCouriers() {
        return courierRepository.findAll();
    }

    @Transactional
    public List<String> changeCourierLocation(List<CourierLocationRequestDto> requestDtos) throws IOException {
        List<String> logMessages = new ArrayList<>();
        List<StoreModel> storeList = commonService.readJsonFile();
        requestDtos.forEach(courierLocation -> {
            Double courierLocationLat = courierLocation.getLat();
            Double courierLocationLng = courierLocation.getLng();
            LocalDateTime courierLocationTime = courierLocation.getTime();
            Courier courier = courierRepository.findByCourierId(courierLocation.getCourierId())
                    .map(courier1 -> {
                        Double totalDistanceTraveled = courier1.getTotalDistance() + commonService
                                .calculateDistanceBetweenTwoPoints(courier1.getLastLat(), courier1.getLastLng(),
                                        courierLocationLat, courierLocationLng);
                        courier1.setLastLat(courierLocationLat);
                        courier1.setLastLng(courierLocationLng);
                        courier1.setTotalDistance(totalDistanceTraveled);
                        courier1.setLastTime(courierLocationTime);
                        return courier1;
                    })
                    .orElseGet(() -> Courier.builder()
                            .courierId(courierLocation.getCourierId())
                            .lastTime(courierLocationTime)
                            .lastLng(courierLocationLng)
                            .lastLat(courierLocationLat)
                            .totalDistance(0.0)
                            .build());
            courierRepository.save(courier);
            storeList.forEach(store -> {
                Double distance = commonService.calculateDistanceBetweenTwoPoints(store.getLat(), store.getLng(),
                        courierLocationLat, courierLocationLng);
                if (distance <= 100) {
                    CourierStoreMatch match;
                    Optional<CourierStoreMatch> optionalMatch = storeMatchRepository
                            .findByStoreNameAndCourierId(store.getName(), courierLocation.getCourierId());
                    if (optionalMatch.isPresent()) {
                        match = optionalMatch.get();
                        if (commonService.secondsDifference(courierLocationTime, match.getLastTime()) > 60) {
                            match.setLastTime(courierLocationTime);
                            logMessages.add(generateLog(courierLocation.getCourierId(), distance, store.getName()));
                            storeMatchRepository.save(match);
                        }
                    } else {
                        match = CourierStoreMatch.builder()
                                .courierId(courierLocation.getCourierId())
                                .storeName(store.getName())
                                .lastTime(courierLocationTime)
                                .build();
                        logMessages.add(generateLog(courierLocation.getCourierId(), distance, store.getName()));
                        storeMatchRepository.save(match);
                    }
                }
            });
        });
        return logMessages;
    }

    @Transactional
    public Double getTotalTravelDistance(Integer courierId) {
        return courierRepository.findByCourierId(courierId)
                .orElseThrow(() -> new EntityNotFoundException("courier not found")).getTotalDistance();
    }

    private String generateLog(Integer courierId, Double distance, String storeName) {
        String message = String.format("Courier with id number %s is about %.2f meters close to the store named %s.",
                courierId, distance, storeName);
        log.info(message);
        return message;
    }
}
