package ro.tuc.ds2021.handaric.cosmin.backend.dtos.device;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.tuc.ds2021.handaric.cosmin.backend.dtos.sensor.SensorDTO;
import ro.tuc.ds2021.handaric.cosmin.backend.dtos.user.ClientDTO;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class DeviceDTO {
    private UUID id;
    private String description;
    private String location;
    private Double maxEnergyConsumption;
    private Double averageEnergyConsumption;
    private Boolean disabled;
    private SensorDTO sensor;
    private ClientDTO client;
}
