package ro.tuc.ds2021.handaric.cosmin.backend.services;

import org.springframework.stereotype.Component;
import ro.tuc.ds2021.handaric.cosmin.backend.dtos.sensor.LogMessageDTO;

import java.util.List;
import java.util.UUID;

@Component
public interface LogMessageService {
    LogMessageDTO chekIfMeasurementExceed(UUID idLastRecordInserted);
    List<LogMessageDTO> getAllForClient(UUID idUser, Integer noOfPage, Integer itemsPerPage);
    List<LogMessageDTO> getAllUnreadForClient(UUID idUser, Integer noOfPage, Integer itemsPerPage);
    Integer checkLogsNo(UUID idUser);
    UUID readLog(UUID id);
}
