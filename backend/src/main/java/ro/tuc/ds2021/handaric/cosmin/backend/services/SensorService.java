package ro.tuc.ds2021.handaric.cosmin.backend.services;

import org.springframework.stereotype.Component;
import ro.tuc.ds2021.handaric.cosmin.backend.dtos.sensor.SensorDTO;
import ro.tuc.ds2021.handaric.cosmin.backend.dtos.sensor.SensorDetailsDTO;
import ro.tuc.ds2021.handaric.cosmin.backend.dtos.sensor.SensorInfoDTO;

import java.util.List;
import java.util.UUID;

@Component
public interface SensorService {
    List<SensorDetailsDTO> getDetailsForAllSensor();
    UUID addNewSensor(UUID idDevice, SensorInfoDTO newSensor);
    UUID updateSensor(SensorDTO newUpdatedSensor);
    void deleteSensor(UUID id);
}
