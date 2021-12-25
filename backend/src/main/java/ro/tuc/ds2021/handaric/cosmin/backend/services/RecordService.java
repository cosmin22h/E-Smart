package ro.tuc.ds2021.handaric.cosmin.backend.services;

import org.springframework.stereotype.Component;
import ro.tuc.ds2021.handaric.cosmin.backend.dtos.sensor.RecordDTO;
import ro.tuc.ds2021.handaric.cosmin.backend.dtos.sensor.RecordReportDTO;

import java.util.List;
import java.util.UUID;

@Component
public interface RecordService {
    UUID addNewRecord(UUID idSensor, RecordDTO newRecord);
    List<List<RecordReportDTO>> fetchRecordsByDay(UUID idClient, String dateReport);
}
