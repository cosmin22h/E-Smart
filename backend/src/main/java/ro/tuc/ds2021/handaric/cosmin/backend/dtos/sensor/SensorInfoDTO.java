package ro.tuc.ds2021.handaric.cosmin.backend.dtos.sensor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class SensorInfoDTO {
    private String description;
    private Double maxValue;
}
