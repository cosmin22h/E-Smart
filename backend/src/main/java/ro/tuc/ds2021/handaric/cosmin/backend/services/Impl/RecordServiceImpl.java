package ro.tuc.ds2021.handaric.cosmin.backend.services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2021.handaric.cosmin.backend.controllers.handlers.exceptions.NotFoundResourceException;
import ro.tuc.ds2021.handaric.cosmin.backend.dtos.sensor.RecordDTO;
import ro.tuc.ds2021.handaric.cosmin.backend.dtos.sensor.RecordReportDTO;
import ro.tuc.ds2021.handaric.cosmin.backend.entities.device.Device;
import ro.tuc.ds2021.handaric.cosmin.backend.entities.sensor.Record;
import ro.tuc.ds2021.handaric.cosmin.backend.entities.sensor.Sensor;
import ro.tuc.ds2021.handaric.cosmin.backend.entities.user.Client;
import ro.tuc.ds2021.handaric.cosmin.backend.repositories.sensor.RecordRepository;
import ro.tuc.ds2021.handaric.cosmin.backend.repositories.sensor.SensorRepository;
import ro.tuc.ds2021.handaric.cosmin.backend.repositories.user.ClientRepository;
import ro.tuc.ds2021.handaric.cosmin.backend.services.RecordService;
import ro.tuc.ds2021.handaric.cosmin.backend.utils.Utils;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class RecordServiceImpl implements RecordService {

    private final RecordRepository recordRepository;
    private final SensorRepository sensorRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public RecordServiceImpl(RecordRepository recordRepository, SensorRepository sensorRepository, ClientRepository clientRepository) {
        this.recordRepository = recordRepository;
        this.sensorRepository = sensorRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    @Transactional
    public UUID addNewRecord(UUID idSensor, RecordDTO newRecord) {
        Sensor sensorRecord = sensorRepository.findById(idSensor).orElse(null);
        if (sensorRecord == null) {
            String message = "Sensor not found!";
            List<String> errors = new ArrayList<>();
            errors.add("error.record.sensor-not-found");
            errors.add("error.record.cant-add-record");

            throw new NotFoundResourceException(message, errors);
        }
        Record recordToSave = Record.builder()
                .energyConsumption(newRecord.getEnergyConsumption())
                .timestamp(newRecord.getTimestamp())
                .sensor(sensorRecord)
                .build();

        Record recordSaved = recordRepository.save(recordToSave);

        List<Record> recordsSensor = sensorRecord.getRecords();
        recordsSensor.add(recordSaved);
        sensorRecord.setRecords(recordsSensor);
        sensorRepository.save(sensorRecord);

        Device deviceToUpdate = sensorRecord.getDevice();
        Double newAvgValue = updateDeviceAvgConsumption(recordsSensor.size() - 1, deviceToUpdate.getAverageEnergyConsumption(), recordSaved.getEnergyConsumption());
        deviceToUpdate.setAverageEnergyConsumption(newAvgValue);
        if (deviceToUpdate.getMaxEnergyConsumption() < newRecord.getEnergyConsumption()) {
            deviceToUpdate.setMaxEnergyConsumption(newRecord.getEnergyConsumption());
        }

        return recordSaved.getId();
    }

    @Override
    @Transactional
    public List<List<RecordReportDTO>> fetchRecordsByDay(UUID idClient, String dateReport) {
        Client client = clientRepository.findById(idClient).orElse(null);
        if (client == null) {
            String message = "Client not found!";
            List<String> errors = new ArrayList<>();
            errors.add("error.record.client-not-found");
            errors.add("error.record.cant-fetch-report");

            throw new NotFoundResourceException(message, errors);
        }

        List<List<RecordReportDTO>> reports = new ArrayList<>();
        for (Device device: client.getDevices()) {
            if (device.getSensor() == null) {
                continue;
            }
            List<RecordReportDTO> records = new ArrayList<>();
            for (Record record: device.getSensor().getRecords()) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                if (sdf.format(record.getTimestamp()).equals(dateReport)) {
                    records.add(RecordReportDTO.builder()
                                    .id(record.getId())
                                    .deviceDescription(device.getDescription())
                                    .timestamp(record.getTimestamp())
                                    .energyConsumption(record.getEnergyConsumption())
                                    .build());
                }
            }
            if (records.size() > 0) {
                reports.add(Utils.sortRecords(records));
            } else {
                records.add(RecordReportDTO.builder()
                        .deviceDescription(device.getDescription())
                        .build());
                reports.add(records);
            }
        }
        return reports;
    }

    private Double updateDeviceAvgConsumption(int oldNoOfRecords, Double oldAvgValue, Double newRecordValue) {
        if (oldAvgValue == 0) {
            return newRecordValue;
        }

        return (oldAvgValue * oldNoOfRecords + newRecordValue) / (oldNoOfRecords + 1);
    }
}
