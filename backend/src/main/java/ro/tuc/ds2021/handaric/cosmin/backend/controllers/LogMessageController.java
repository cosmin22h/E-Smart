package ro.tuc.ds2021.handaric.cosmin.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2021.handaric.cosmin.backend.dtos.sensor.LogMessageDTO;
import ro.tuc.ds2021.handaric.cosmin.backend.services.LogMessageService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/logs")
public class LogMessageController {

    private final LogMessageService logMessageService;

    @Autowired
    public LogMessageController(LogMessageService logMessageService) {
        this.logMessageService = logMessageService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<LogMessageDTO>> getAllLogsForUser(@RequestParam("idClient") UUID idClient,
                                                                 @RequestParam(required = false) Integer noOfPage,
                                                                 @RequestParam(required = false) Integer itemsPerPage) {
        return ResponseEntity.ok(this.logMessageService.getAllForClient(idClient, noOfPage, itemsPerPage));
    }

    @GetMapping("/all/unread")
    public ResponseEntity<List<LogMessageDTO>> getAllUnreadLogsForUser(@RequestParam("idUser") UUID idUser,
                                                                       @RequestParam(required = false) Integer noOfPage,
                                                                       @RequestParam(required = false) Integer itemsPerPage) {
        return ResponseEntity.ok(this.logMessageService.getAllUnreadForClient(idUser, noOfPage, itemsPerPage));
    }

    @GetMapping("/unread/number")
    public ResponseEntity<String> getNoOfUnreadLogs(@RequestParam("idClient") UUID idClient) {
        return ResponseEntity.ok(this.logMessageService.checkLogsNo(idClient).toString());
    }

    @PutMapping("/read/{id}")
    public ResponseEntity<UUID> readMessage(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(this.logMessageService.readLog(id));
    }
}
