package ro.tuc.ds2021.handaric.cosmin.backend.dtos.mappers;

import ro.tuc.ds2021.handaric.cosmin.backend.dtos.sensor.RecordDTO;
import ro.tuc.ds2021.handaric.cosmin.backend.dtos.sensor.RecordReportDTO;
import ro.tuc.ds2021.handaric.cosmin.backend.entities.sensor.Record;

public class RecordMapper {

    public static RecordDTO mapModelToDto(Record record) {
        return RecordDTO.builder()
                .id(record.getId())
                .timestamp(record.getTimestamp())
                .energyConsumption(record.getEnergyConsumption())
                .build();
    }

    public static RecordReportDTO mapModelToReportDto(Record record) {
        return RecordReportDTO.builder()
                .id(record.getId())
                .timestamp(record.getTimestamp())
                .energyConsumption(record.getEnergyConsumption())
                .deviceDescription(record.getSensor().getDevice().getDescription())
                .build();
    }
}
