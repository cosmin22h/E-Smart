package ro.tuc.ds2021.handaric.cosmin.backend.dtos.device;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.tuc.ds2021.handaric.cosmin.backend.dtos.sensor.SensorInfoDTO;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class DeviceInfoDTO {
    private UUID idClient;
    private String description;
    private String location;
    private SensorInfoDTO sensor;
}
