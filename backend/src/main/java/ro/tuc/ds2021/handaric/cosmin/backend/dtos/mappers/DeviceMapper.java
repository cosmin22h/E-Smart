package ro.tuc.ds2021.handaric.cosmin.backend.dtos.mappers;

import ro.tuc.ds2021.handaric.cosmin.backend.dtos.device.DeviceDTO;
import ro.tuc.ds2021.handaric.cosmin.backend.dtos.device.DeviceInfoDTO;
import ro.tuc.ds2021.handaric.cosmin.backend.dtos.sensor.SensorDTO;
import ro.tuc.ds2021.handaric.cosmin.backend.dtos.user.ClientDTO;
import ro.tuc.ds2021.handaric.cosmin.backend.entities.device.Device;
import ro.tuc.ds2021.handaric.cosmin.backend.entities.sensor.Sensor;

public class DeviceMapper {

    public static Device mapInfoDtoToModel(DeviceInfoDTO deviceInfoDTO) {
        Sensor sensor = SensorMapper.mapInfoDtoToModel(deviceInfoDTO.getSensor());
        return Device.builder()
                .description(deviceInfoDTO.getDescription())
                .location(deviceInfoDTO.getLocation())
                .sensor(sensor)
                .build();
    }

    public static DeviceDTO mapModelToDto(Device device) {
        ClientDTO clientDTO = UserMapper.mapClientModelToDto(device.getClient());
        SensorDTO sensorDTO = null;
        Sensor sensor = device.getSensor();
        if (sensor != null) {
            sensorDTO = SensorMapper.mapModelToDto(sensor);

        }
        return DeviceDTO.builder()
                .id(device.getId())
                .description(device.getDescription())
                .location(device.getLocation())
                .maxEnergyConsumption(device.getMaxEnergyConsumption())
                .averageEnergyConsumption(device.getAverageEnergyConsumption())
                .disabled(device.isDisabled())
                .sensor(sensorDTO)
                .client(clientDTO)
                .build();
    }
}
