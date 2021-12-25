package ro.tuc.ds2021.handaric.cosmin.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2021.handaric.cosmin.backend.dtos.device.DeviceDTO;
import ro.tuc.ds2021.handaric.cosmin.backend.dtos.device.DeviceInfoDTO;
import ro.tuc.ds2021.handaric.cosmin.backend.services.DeviceService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {

    private final DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping()
    public ResponseEntity<List<DeviceDTO>> fetchDevices(@RequestParam(required = false) Integer noOfPage, @RequestParam(required = false) Integer itemsPerPage) {
        return ResponseEntity.ok(deviceService.fetchDevices(noOfPage, itemsPerPage));
    }

    @GetMapping("/client")
    public ResponseEntity<List<DeviceDTO>> fetchDevicesForClient(@RequestParam UUID idClient, @RequestParam(required = false) Integer noOfPage, @RequestParam(required = false) Integer itemsPerPage) {
        return ResponseEntity.ok(deviceService.fetchClientDevices(idClient, noOfPage, itemsPerPage));
    }

    @GetMapping("/device")
    public ResponseEntity<DeviceDTO> fetchDevice(@RequestParam UUID id) {
        return ResponseEntity.ok(deviceService.fetchDevice(id));
    }

    @PostMapping("/add")
    public ResponseEntity<UUID> addNewDevice(@RequestBody DeviceInfoDTO newDevice) {
        return ResponseEntity.ok(deviceService.addNewDevice(newDevice));
    }

    @PutMapping("/update")
    public ResponseEntity<UUID> updateDevice(@RequestBody DeviceDTO device) {
        return ResponseEntity.ok(deviceService.updateDevice(device));
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Object> deleteDevice(@RequestParam  UUID id) {
        deviceService.deleteDevice(id);
        return ResponseEntity.ok().build();
    }
}
