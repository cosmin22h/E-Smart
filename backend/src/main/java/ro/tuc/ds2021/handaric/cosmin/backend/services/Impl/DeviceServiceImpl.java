package ro.tuc.ds2021.handaric.cosmin.backend.services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2021.handaric.cosmin.backend.controllers.handlers.exceptions.CustomException;
import ro.tuc.ds2021.handaric.cosmin.backend.controllers.handlers.exceptions.NotFoundResourceException;
import ro.tuc.ds2021.handaric.cosmin.backend.dtos.device.DeviceDTO;
import ro.tuc.ds2021.handaric.cosmin.backend.dtos.device.DeviceInfoDTO;
import ro.tuc.ds2021.handaric.cosmin.backend.dtos.mappers.DeviceMapper;
import ro.tuc.ds2021.handaric.cosmin.backend.dtos.mappers.UserMapper;
import ro.tuc.ds2021.handaric.cosmin.backend.entities.device.Device;
import ro.tuc.ds2021.handaric.cosmin.backend.entities.user.Client;
import ro.tuc.ds2021.handaric.cosmin.backend.repositories.device.DeviceRepository;
import ro.tuc.ds2021.handaric.cosmin.backend.repositories.user.ClientRepository;
import ro.tuc.ds2021.handaric.cosmin.backend.services.DeviceService;
import ro.tuc.ds2021.handaric.cosmin.backend.utils.Utils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository deviceRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public DeviceServiceImpl(DeviceRepository deviceRepository, ClientRepository clientRepository) {
        this.deviceRepository = deviceRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    @Transactional
    public List<DeviceDTO> fetchDevices(Integer noOfPage, Integer itemsPerPage) {
        List<Device> devices = fetchDevicesByPage(null, noOfPage, itemsPerPage);
        List<DeviceDTO> deviceDTOS = new ArrayList<>();
        devices.forEach(device -> deviceDTOS.add(DeviceMapper.mapModelToDto(device)));

        return deviceDTOS;
    }

    @Override
    @Transactional
    public List<DeviceDTO> fetchClientDevices(UUID idClient, Integer noOfPage, Integer itemsPerPage) throws CustomException {
        Client client = null;
        if (idClient != null) {
            client = clientRepository.findById(idClient).orElse(null);
            if (client == null) {
                String message = "User not found!";
                List<String> errors = new ArrayList<>();
                errors.add("error.device.user-not-found");
                errors.add("error.device.cant-fetch-devices");

                throw new NotFoundResourceException(message, errors);
            }
        }
        List<Device> devices = fetchDevicesByPage(client, noOfPage, itemsPerPage);
        List<DeviceDTO> deviceDTOS = new ArrayList<>();
        devices.forEach(device -> deviceDTOS.add(DeviceMapper.mapModelToDto(device)));

        return deviceDTOS;
    }

    @Override
    @Transactional
    public DeviceDTO fetchDevice(UUID id) {
        Device device = deviceRepository.findById(id).orElse(null);
        if (device == null) {
            String message = "Device not found!";
            List<String> errors = new ArrayList<>();
            errors.add("error.device.not-found");
            errors.add("error.device.cant-fetch-device");

            throw new NotFoundResourceException(message, errors);
        }

        return DeviceMapper.mapModelToDto(device);
    }

    @Override
    @Transactional
    public UUID addNewDevice(DeviceInfoDTO deviceInfoDTO) {
        Client client = clientRepository.findById(deviceInfoDTO.getIdClient()).orElse(null);
        if (client == null) {
            String message = "User not found!";
            List<String> errors = new ArrayList<>();
            errors.add("error.device.user-not-found");
            errors.add("error.device.cant-add-device");

            throw new NotFoundResourceException(message, errors);
        }
        Device newDevice = DeviceMapper.mapInfoDtoToModel(deviceInfoDTO);
        newDevice.setClient(client);
        Device deviceAdded = deviceRepository.save(newDevice);

        return deviceAdded.getId();
    }

    @Override
    @Transactional
    public UUID updateDevice(DeviceDTO newUpdatedDevice) {
        Device deviceToUpdate = deviceRepository.findById(newUpdatedDevice.getId()).orElse(null);
        if (deviceToUpdate == null) {
            String message = "Device not found!";
            List<String> errors = new ArrayList<>();
            errors.add("error.device.not-found");
            errors.add("error.device.cant-update-device");

            throw new NotFoundResourceException(message, errors);
        }

        Client oldClient = clientRepository.findById(deviceToUpdate.getClient().getId()).orElse(null);
        if (oldClient.getId() != newUpdatedDevice.getClient().getId()) {
            List<Device> devicesOldClient = oldClient.getDevices();
            devicesOldClient.remove(deviceToUpdate);
            clientRepository.save(oldClient);
            Client newClient = clientRepository.findById(newUpdatedDevice.getClient().getId()).orElse(null);
            deviceToUpdate.setClient(newClient);
        }

        deviceToUpdate.setDescription(newUpdatedDevice.getDescription());
        deviceToUpdate.setLocation(newUpdatedDevice.getLocation());

        Device updatedDevice = deviceRepository.save(deviceToUpdate);

        return updatedDevice.getId();
    }

    @Override
    @Transactional
    public void deleteDevice(UUID id) {
        Device device = deviceRepository.findById(id).orElse(null);
        if (device == null) {
            String message = "Device not found!";
            List<String> errors = new ArrayList<>();
            errors.add("error.device.not-found");
            errors.add("error.device.cant-delete-device");

            throw new NotFoundResourceException(message, errors);
        }

        deviceRepository.delete(device);
    }

    private List<Device> fetchDevicesByPage(Client client, Integer noOfPage, Integer itemsPerPage) {
        List<Device> devices;
        if (itemsPerPage == null) {
            if (client == null) {
                devices = new ArrayList<>(deviceRepository.findAll());
            } else {
                devices = deviceRepository.findAllByClient(client);
            }
        } else if (noOfPage == null || noOfPage <= 1) {
            if (client == null) {
                devices = deviceRepository.findForFirstPage(itemsPerPage);
            } else {
                devices = deviceRepository.findAllForFirstPageByClient(client, itemsPerPage);
            }
        } else {
            int offset = Utils.computeOffset(noOfPage, itemsPerPage);
            if (client == null) {
                devices = deviceRepository.findForPage(offset, itemsPerPage);
            } else {
                devices = deviceRepository.findAllForPageByClient(client, offset, itemsPerPage);
            }
        }

        return devices;
    }

}
