package ro.tuc.ds2021.handaric.cosmin.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2021.handaric.cosmin.backend.dtos.sensor.SensorDTO;
import ro.tuc.ds2021.handaric.cosmin.backend.dtos.sensor.SensorDetailsDTO;
import ro.tuc.ds2021.handaric.cosmin.backend.dtos.sensor.SensorInfoDTO;
import ro.tuc.ds2021.handaric.cosmin.backend.services.SensorService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/sensors")
public class SensorController {

    private final SensorService sensorService;

    @Autowired
    public SensorController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @GetMapping("/all/details")
    ResponseEntity<List<SensorDetailsDTO>> getAllDetails() {
        return ResponseEntity.ok(sensorService.getDetailsForAllSensor());
    }

    @PostMapping("/add/{idDevice}")
    ResponseEntity<UUID> addSensor(@PathVariable UUID idDevice, @RequestBody SensorInfoDTO sensor) {
        return ResponseEntity.ok(sensorService.addNewSensor(idDevice, sensor));
    }

    @PutMapping("/update")
    public ResponseEntity<UUID> updateSensor(@RequestBody SensorDTO newUpdatedSensor) {
        return ResponseEntity.ok(sensorService.updateSensor(newUpdatedSensor));
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Object> deleteSensor(@RequestParam UUID id) {
        sensorService.deleteSensor(id);
        return ResponseEntity.ok().build();
    }
}
