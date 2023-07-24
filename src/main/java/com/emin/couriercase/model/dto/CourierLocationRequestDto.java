package com.emin.couriercase.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@Builder
@AllArgsConstructor
public class CourierLocationRequestDto {
    @NotNull(message = "courier_id_not_null")
    private Integer courierId;
    @NotNull(message = "latitude_not_null")
    private Double lat;
    @NotNull(message = "longitude_not_null")
    private Double lng;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "time_not_null")
    private LocalDateTime time;
}
