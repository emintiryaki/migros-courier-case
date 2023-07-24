package com.emin.couriercase.controller;

import com.emin.couriercase.entity.Courier;
import com.emin.couriercase.model.dto.CourierLocationRequestDto;
import com.emin.couriercase.service.CourierService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("courier")
@RequiredArgsConstructor
@Validated
public class CourierController {
    private final CourierService courierService;

    @GetMapping("get-all-couriers")
    @Operation(summary = "Get all courier")
    public ResponseEntity<List<Courier>> getAllCouriers() {
        return new ResponseEntity<>(courierService.getAllCouriers(), HttpStatus.OK);
    }

    @PostMapping("change-courier-location")
    @Operation(summary = "Change courier location and log if any courier matching any store.")
    public ResponseEntity<List<String>> changeCourierLocation(
            @RequestBody @Valid @NotEmpty(message = "Request not empty") List<CourierLocationRequestDto> requestDtos)
            throws IOException {
        return new ResponseEntity<>(courierService.changeCourierLocation(requestDtos), HttpStatus.OK);
    }

    @GetMapping("get-total-travel-distance/{courierId}")
    @Operation(summary = "Get the total travel distance of the courier based on the courier id.")
    public ResponseEntity<Double> getTotalTravelDistance(@PathVariable("courierId") Integer courierId) {
        return new ResponseEntity<>(courierService.getTotalTravelDistance(courierId), HttpStatus.OK);
    }
}
