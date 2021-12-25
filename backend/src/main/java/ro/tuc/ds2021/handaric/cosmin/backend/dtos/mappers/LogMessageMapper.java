package ro.tuc.ds2021.handaric.cosmin.backend.dtos.mappers;

import ro.tuc.ds2021.handaric.cosmin.backend.dtos.device.DeviceDTO;
import ro.tuc.ds2021.handaric.cosmin.backend.dtos.sensor.LogMessageDTO;
import ro.tuc.ds2021.handaric.cosmin.backend.dtos.sensor.RecordDTO;
import ro.tuc.ds2021.handaric.cosmin.backend.entities.sensor.LogMessage;

public class LogMessageMapper {

    public static LogMessageDTO mapModelToDto(LogMessage logMessage) {
        RecordDTO recordDTO = RecordMapper.mapModelToDto(logMessage.getRecord());
        DeviceDTO deviceDTO = DeviceMapper.mapModelToDto(logMessage.getRecord().getSensor().getDevice());
        return LogMessageDTO.builder()
                .id(logMessage.getId())
                .isRead(logMessage.getIsRead())
                .timestamp(logMessage.getTimestamp())
                .record(recordDTO)
                .device(deviceDTO)
                .build();
    }
}
