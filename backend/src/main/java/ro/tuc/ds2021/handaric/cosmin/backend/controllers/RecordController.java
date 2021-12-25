package ro.tuc.ds2021.handaric.cosmin.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2021.handaric.cosmin.backend.dtos.sensor.RecordDTO;
import ro.tuc.ds2021.handaric.cosmin.backend.dtos.sensor.RecordReportDTO;
import ro.tuc.ds2021.handaric.cosmin.backend.services.RecordService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/records")
public class RecordController {

    private final RecordService recordService;

    @Autowired
    public RecordController(RecordService recordService) {
        this.recordService = recordService;
    }

    @GetMapping
    public ResponseEntity<List<List<RecordReportDTO>>> reportRecords(@RequestParam UUID idClient, @RequestParam String dateReport) {
        return ResponseEntity.ok(recordService.fetchRecordsByDay(idClient, dateReport));
    }

    @PostMapping()
    ResponseEntity<UUID> addNewRecord(@RequestParam UUID idSensor, @RequestBody RecordDTO newRecord) {
        return ResponseEntity.ok(recordService.addNewRecord(idSensor, newRecord));
    }
}
