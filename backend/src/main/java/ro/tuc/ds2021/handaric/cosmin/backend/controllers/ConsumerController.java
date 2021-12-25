package ro.tuc.ds2021.handaric.cosmin.backend.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import ro.tuc.ds2021.handaric.cosmin.backend.dtos.sensor.LogMessageDTO;
import ro.tuc.ds2021.handaric.cosmin.backend.dtos.sensor.RecordDTO;
import ro.tuc.ds2021.handaric.cosmin.backend.services.LogMessageService;
import ro.tuc.ds2021.handaric.cosmin.backend.services.RecordService;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@Controller
public class ConsumerController {

    private final static String BASE_NOTIFICATION_ENDPOINT = "/topic/socket/notification/measurement/exceed/user/";

    private final Channel rabbitMqChannel;
    private final RecordService recordService;
    private final LogMessageService logMessageService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Value("${rabbitmq.queue}")
    private String queueName;

    @Autowired
    public ConsumerController(RecordService recordService, ApplicationContext context, LogMessageService logMessageService, SimpMessagingTemplate simpMessagingTemplate) {
        rabbitMqChannel = (Channel) context.getBean("rabbitMqChannel");
        this.recordService = recordService;
        this.logMessageService = logMessageService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Bean
    @Scope("singleton")
    public void listenToRabbit() {
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode currentMeasurementJSON = objectMapper.readTree(message);
            UUID idSensor = UUID.fromString(currentMeasurementJSON.get("sensor_id").toString().replace("\"", ""));
            RecordDTO recordDTO = this.extractMeasurement(currentMeasurementJSON);
            UUID idLastRecordInserted = this.recordService.addNewRecord(idSensor, recordDTO);
            LogMessageDTO logMessageDTO = this.logMessageService.chekIfMeasurementExceed(idLastRecordInserted);
            if (logMessageDTO != null) {
                this.sendNotification(logMessageDTO);
            }
        };
        try {
            this.rabbitMqChannel.basicConsume(queueName, true, deliverCallback, consumerTag -> {});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private RecordDTO extractMeasurement(JsonNode currentMeasurementJSON) {
        Timestamp ts = new Timestamp(Long.parseLong(currentMeasurementJSON.get("timestamp").toString()));
        Date timestamp = new Date(ts.getTime());
        Double measurementValue = Double.valueOf(currentMeasurementJSON.get("measurement_value").toString());
        return RecordDTO.builder()
                .energyConsumption(measurementValue)
                .timestamp(timestamp)
                .build();
    }

    private void sendNotification(LogMessageDTO logMessageDTO) {
        UUID idClient = logMessageDTO.getDevice().getClient().getId();
        String messagePayload = "Your device's sensor (" +
                logMessageDTO.getDevice().getSensor().getDescription() +
                ") measured a value at " +
                logMessageDTO.getTimestamp().toString().substring(0, 19) +
                " that exceed the sensor's limit!";
        this.simpMessagingTemplate.convertAndSend(BASE_NOTIFICATION_ENDPOINT + idClient, messagePayload);
    }
}
