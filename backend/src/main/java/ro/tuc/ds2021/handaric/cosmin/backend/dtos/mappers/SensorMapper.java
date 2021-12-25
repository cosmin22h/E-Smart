package ro.tuc.ds2021.handaric.cosmin.backend.dtos.mappers;

import ro.tuc.ds2021.handaric.cosmin.backend.dtos.sensor.RecordDTO;
import ro.tuc.ds2021.handaric.cosmin.backend.dtos.sensor.SensorDTO;
import ro.tuc.ds2021.handaric.cosmin.backend.dtos.sensor.SensorDetailsDTO;
import ro.tuc.ds2021.handaric.cosmin.backend.dtos.sensor.SensorInfoDTO;
import ro.tuc.ds2021.handaric.cosmin.backend.entities.sensor.Sensor;

import java.util.ArrayList;
import java.util.List;

public class SensorMapper {

   public static Sensor mapInfoDtoToModel(SensorInfoDTO sensorInfoDTO) {
       return Sensor.builder()
               .description(sensorInfoDTO.getDescription())
               .maxValue(sensorInfoDTO.getMaxValue())
               .build();
   }

   public static SensorDTO mapModelToDto(Sensor sensor) {
       List<RecordDTO> records = new ArrayList<>();
       sensor.getRecords().forEach(record -> records.add(RecordMapper.mapModelToDto(record)));
       return SensorDTO.builder()
               .id(sensor.getId())
               .description(sensor.getDescription())
               .maxValue(sensor.getMaxValue())
               .records(records)
               .build();
   }

   public static SensorDetailsDTO mapModelToDetailsDto(Sensor sensor) {
       return SensorDetailsDTO.builder()
               .id(sensor.getId())
               .deviceDescription(sensor.getDevice().getDescription())
               .clientUsername(sensor.getDevice().getClient().getUsername())
               .build();
   }
}
