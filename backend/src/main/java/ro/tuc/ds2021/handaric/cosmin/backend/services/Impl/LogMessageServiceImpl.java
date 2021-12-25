package ro.tuc.ds2021.handaric.cosmin.backend.services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2021.handaric.cosmin.backend.controllers.handlers.exceptions.NotFoundResourceException;
import ro.tuc.ds2021.handaric.cosmin.backend.dtos.mappers.LogMessageMapper;
import ro.tuc.ds2021.handaric.cosmin.backend.dtos.sensor.LogMessageDTO;
import ro.tuc.ds2021.handaric.cosmin.backend.entities.sensor.LogMessage;
import ro.tuc.ds2021.handaric.cosmin.backend.entities.sensor.Record;
import ro.tuc.ds2021.handaric.cosmin.backend.entities.sensor.Sensor;
import ro.tuc.ds2021.handaric.cosmin.backend.entities.user.Client;
import ro.tuc.ds2021.handaric.cosmin.backend.repositories.sensor.LogMessageRepository;
import ro.tuc.ds2021.handaric.cosmin.backend.repositories.sensor.RecordRepository;
import ro.tuc.ds2021.handaric.cosmin.backend.repositories.user.ClientRepository;
import ro.tuc.ds2021.handaric.cosmin.backend.services.LogMessageService;
import ro.tuc.ds2021.handaric.cosmin.backend.utils.Utils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LogMessageServiceImpl implements LogMessageService {

    private final LogMessageRepository logMessageRepository;
    private final RecordRepository recordRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public LogMessageServiceImpl(LogMessageRepository logMessageRepository, RecordRepository recordRepository, ClientRepository clientRepository) {
        this.logMessageRepository = logMessageRepository;
        this.recordRepository = recordRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    @Transactional
    public LogMessageDTO chekIfMeasurementExceed(UUID idLastRecordInserted) {
        Record lastRecordInserted = recordRepository.findById(idLastRecordInserted).orElse(null);
        if (lastRecordInserted == null) {
            return null;
        }
        Sensor sensor = lastRecordInserted.getSensor();
        Record lastRecordBeforeLastInsert = this.recordRepository.findLastRecordForSensorBeforeLastInsert(sensor);
        if (lastRecordBeforeLastInsert == null) {
            return null;
        }
        double value = lastRecordInserted.getEnergyConsumption() - lastRecordBeforeLastInsert.getEnergyConsumption();
        double time = (lastRecordInserted.getTimestamp().getTime() - lastRecordBeforeLastInsert.getTimestamp().getTime()) / (60.0 * 60.0 * 1000.0);
        double peak = value / time;

        if (peak < sensor.getMaxValue()) {
            return null;
        }

        LogMessage logMessage = LogMessage.builder()
                .isRead(false)
                .timestamp(lastRecordInserted.getTimestamp())
                .record(lastRecordInserted)
                .client(sensor.getDevice().getClient())
                .build();
        LogMessage logMessageSaved = this.logMessageRepository.save(logMessage);

        lastRecordInserted.setLogMessage(logMessageSaved);
        recordRepository.save(lastRecordInserted);

        return LogMessageMapper.mapModelToDto(logMessageSaved);
    }

    @Override
    @Transactional
    public List<LogMessageDTO> getAllForClient(UUID idUser, Integer noOfPage, Integer itemsPerPage) {
        Client client = this.clientRepository.findById(idUser).orElseThrow(this::userNotFound);
        return this.fetchMessageByPage(client, noOfPage, itemsPerPage, false).stream()
                .map(LogMessageMapper::mapModelToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<LogMessageDTO> getAllUnreadForClient(UUID idUser, Integer noOfPage, Integer itemsPerPage) {
        Client client = this.clientRepository.findById(idUser).orElseThrow(this::userNotFound);
        return this.fetchMessageByPage(client, noOfPage, itemsPerPage, true).stream()
                .map(LogMessageMapper::mapModelToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Integer checkLogsNo(UUID idUser) {
        Client client = this.clientRepository.findById(idUser).orElseThrow(this::userNotFound);
        return this.fetchMessageByPage(client, null, null, true).size();
    }

    @Override
    @Transactional
    public UUID readLog(UUID id) {
        LogMessage logMessage = this.logMessageRepository.findById(id).orElse(null);
        if (logMessage == null) {
            String message ="Message not found!";
            List<String> errors = new ArrayList<>();
            errors.add("error.log-message.read-failed");
            errors.add("error.log-message.message-not-found");
            throw  new NotFoundResourceException(message, errors);
        }

        logMessage.setIsRead(true);
        LogMessage logMessageSaved = this.logMessageRepository.save(logMessage);

        return logMessageSaved.getId();
    }

    private List<LogMessage> fetchMessageByPage(Client client, Integer noOfPage, Integer itemsPerPage, boolean onlyUnread) {
        List<LogMessage> logMessages;

        if (itemsPerPage == null) {
            logMessages = new ArrayList<>(onlyUnread ?
                    logMessageRepository.getAllUnreadByClient(client) :
                    logMessageRepository.getAllByClient(client));
        } else if (noOfPage == null || noOfPage <= 1) {
            logMessages = onlyUnread ?
                    logMessageRepository.getAllUnreadByClientForFirstPage(client, itemsPerPage) :
                    logMessageRepository.getAllByClientForFirstPage(client, itemsPerPage);
        } else {
            int offset = Utils.computeOffset(noOfPage, itemsPerPage);
            logMessages = onlyUnread ?
                    logMessageRepository.getAllUnreadByClientForPage(client, offset, itemsPerPage) :
                    logMessageRepository.getAllByClientForPage(client, offset, itemsPerPage);
        }

        return logMessages;
    }

    private NotFoundResourceException userNotFound() {
        String message ="User not found!";
        List<String> errors = new ArrayList<>();
        errors.add("error.log-message.fetch-failed");
        errors.add("error.log-message.user-not-found");
        return new NotFoundResourceException(message, errors);
    }
}
