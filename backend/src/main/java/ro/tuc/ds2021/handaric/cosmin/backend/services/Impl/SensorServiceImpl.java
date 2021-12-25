package ro.tuc.ds2021.handaric.cosmin.backend.services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2021.handaric.cosmin.backend.controllers.handlers.exceptions.NotFoundResourceException;
import ro.tuc.ds2021.handaric.cosmin.backend.dtos.mappers.SensorMapper;
import ro.tuc.ds2021.handaric.cosmin.backend.dtos.sensor.SensorDTO;
import ro.tuc.ds2021.handaric.cosmin.backend.dtos.sensor.SensorDetailsDTO;
import ro.tuc.ds2021.handaric.cosmin.backend.dtos.sensor.SensorInfoDTO;
import ro.tuc.ds2021.handaric.cosmin.backend.entities.device.Device;
import ro.tuc.ds2021.handaric.cosmin.backend.entities.sensor.Sensor;
import ro.tuc.ds2021.handaric.cosmin.backend.repositories.device.DeviceRepository;
import ro.tuc.ds2021.handaric.cosmin.backend.repositories.sensor.SensorRepository;
import ro.tuc.ds2021.handaric.cosmin.backend.services.SensorService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class SensorServiceImpl implements SensorService {

    private final SensorRepository sensorRepository;
    private final DeviceRepository deviceRepository;

    @Autowired
    public SensorServiceImpl(SensorRepository sensorRepository, DeviceRepository deviceRepository) {
        this.sensorRepository = sensorRepository;
        this.deviceRepository = deviceRepository;
    }

    @Override
    @Transactional
    public List<SensorDetailsDTO> getDetailsForAllSensor() {
        List<SensorDetailsDTO> sensorDetailsDTOS = new ArrayList<>();
        sensorRepository.findAll().forEach(sensor -> sensorDetailsDTOS.add(SensorMapper.mapModelToDetailsDto(sensor)));

        return sensorDetailsDTOS;
    }

    @Override
    @Transactional
    public UUID addNewSensor(UUID idDevice, SensorInfoDTO newSensor) {
        Device device = deviceRepository.findById(idDevice).orElse(null);
        if (device == null) {
            String message = "Sensor not found!";
            List<String> errors = new ArrayList<>();
            errors.add("error.sensor.not-found");
            errors.add("error.sensor.cant-add");

            throw new NotFoundResourceException(message, errors);
        }
        Sensor sensorToAdd = Sensor.builder()
                .description(newSensor.getDescription())
                .maxValue(newSensor.getMaxValue())
                .build();
        Sensor sensorAdded = sensorRepository.save(sensorToAdd);
        device.setSensor(sensorAdded);
        device.setDisabled(false);
        deviceRepository.save(device);

        return sensorAdded.getId();
    }

    @Override
    @Transactional
    public UUID updateSensor(SensorDTO newUpdatedSensor) {
        Sensor sensorToUpdate = sensorRepository.findById(newUpdatedSensor.getId()).orElse(null);
        if (sensorToUpdate == null) {
            String message = "Sensor not found!";
            List<String> errors = new ArrayList<>();
            errors.add("error.sensor.not-found");
            errors.add("error.sensor.cant-update");

            throw new NotFoundResourceException(message, errors);
        }
        sensorToUpdate.setDescription(newUpdatedSensor.getDescription());
        sensorToUpdate.setMaxValue(newUpdatedSensor.getMaxValue());

        Sensor updatedSensor = sensorRepository.save(sensorToUpdate);

        return updatedSensor.getId();
    }

    @Override
    @Transactional
    public void deleteSensor(UUID id) {
        Sensor sensorToDelete = sensorRepository.findById(id).orElse(null);
        if (sensorToDelete == null) {
            String message = "Sensor not found!";
            List<String> errors = new ArrayList<>();
            errors.add("error.sensor.not-found");
            errors.add("error.sensor.cant-delete-sensor");

            throw new NotFoundResourceException(message, errors);
        }
        Device device = sensorToDelete.getDevice();
        device.setSensor(null);
        device.setDisabled(true);
        device.setAverageEnergyConsumption(0);
        device.setMaxEnergyConsumption(0);
        deviceRepository.save(device);
        sensorRepository.delete(sensorToDelete);
    }
}
