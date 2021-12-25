package ro.tuc.ds2021.handaric.cosmin.backend.dtos.sensor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class SensorDetailsDTO {
    private UUID id;
    private String deviceDescription;
    private String clientUsername;
}
