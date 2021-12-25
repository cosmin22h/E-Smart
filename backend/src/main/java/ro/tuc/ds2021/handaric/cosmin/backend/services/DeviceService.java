package ro.tuc.ds2021.handaric.cosmin.backend.services;

import org.springframework.stereotype.Component;
import ro.tuc.ds2021.handaric.cosmin.backend.dtos.device.DeviceDTO;
import ro.tuc.ds2021.handaric.cosmin.backend.dtos.device.DeviceInfoDTO;

import java.util.List;
import java.util.UUID;

@Component
public interface DeviceService {
    List<DeviceDTO> fetchDevices(Integer noOfPage, Integer itemsPerPage);
    List<DeviceDTO> fetchClientDevices(UUID idUser, Integer noOfPage, Integer itemsPerPage);
    DeviceDTO fetchDevice(UUID id);
    UUID addNewDevice(DeviceInfoDTO deviceInfoDTO);
    UUID updateDevice(DeviceDTO newUpdatedDevice);
    void deleteDevice(UUID id);
}
