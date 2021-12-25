package views;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.Measurement;
import utils.MeasurementReader;
import utils.MessageProducer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import com.rabbitmq.client.Channel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SensorView extends JFrame implements ActionListener {

    private final static int WINDOW_WIDTH = 350;
    private final static int WINDOW_HEIGHT = 170;

    private JButton pauseResumeBtn;
    private JButton stopBtn;
    private JLabel labelDate;

    private Integer measurementValue;
    private Timer timer;
    private Date currentReadTime;
    private boolean isPause;

    private MeasurementReader measurementReader;
    private MessageProducer messageProducer;

    public SensorView(String idSensor, String date, Integer measurementValue, MessageProducer messageProducer) throws IOException, ParseException {
        setTitle(idSensor);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setResizable(false);
        JLabel label = new JLabel();

        // date
        this.labelDate = new JLabel();
        labelDate.setBounds(55, 10, 300, 30);
        label.add(labelDate);

        // button pause/resume
        this.pauseResumeBtn = new JButton("Pause Simulation");
        this.pauseResumeBtn.setBounds(20, 50, 300, 30);
        this.pauseResumeBtn.addActionListener(this);
        label.add(this.pauseResumeBtn);

        // button stop
        this.stopBtn = new JButton("Stop Simulation");
        this.stopBtn.setBounds(20, 90, 300, 30);
        this.stopBtn.addActionListener(this);
        label.add(this.stopBtn);

        add(label);
        setVisible(true);

        // window close listener
        addWindowListener (new WindowAdapter() {
            public void windowClosing (WindowEvent e) {
                stopSimulate();
            }
        });

        this.measurementValue = measurementValue;
        this.measurementReader = new MeasurementReader(idSensor);
        this.messageProducer = messageProducer;
        this.initSimulate(date);
        this.readMeasurementValue();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(pauseResumeBtn)) {
            if (this.isPause) {
                this.pauseResumeBtn.setText("Pause Simulation");
                this.resumeSimulate();
            } else {
                this.pauseResumeBtn.setText("Resume Simulation");
                this.pauseSimulate();
            }
        }
        if (e.getSource().equals(stopBtn)) {
            this.stopSimulate();
        }
    }

    private void readMeasurementValue() {
        this.measurementReader.readMeasurement(this.currentReadTime);
        List<Measurement> measurementList = this.measurementReader.getMeasurements();
        Measurement lastMeasurement = measurementList.get(measurementList.size() - 1);
        this.sendMessage(lastMeasurement);
        this.labelDate.setText("Last Read: " + this.currentReadTime.toString());
    }

    private void initSimulate(String dateTxt) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        this.currentReadTime = formatter.parse(dateTxt);
        this.timer = new Timer(this.measurementValue * 1000, e -> {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currentReadTime);
            calendar.add(Calendar.HOUR_OF_DAY, 1);
            currentReadTime = calendar.getTime();
            readMeasurementValue();
        });
        this.timer.start();
    }

    private void pauseSimulate() {
        this.isPause = true;
        this.timer.stop();
    }

    private void resumeSimulate() {
        this.isPause = false;
        this.timer.start();
    }

    private void stopSimulate() {
        this.timer.stop();
        dispose();
    }

    private void sendMessage(Measurement currentMeasurement) {
        Channel channel = this.messageProducer.getChannel();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String currentMeasurementJSON = objectMapper.writeValueAsString(currentMeasurement);
            channel.basicPublish("", MessageProducer.getQueueName(), null, currentMeasurementJSON.getBytes());
            System.out.println("[V] Sent: " + currentMeasurement.getTimestamp()
                    + ", " +  currentMeasurement.getSensor_id() + ", " + currentMeasurement.getMeasurement_value());
        } catch (IOException e) {
            System.out.println("[X] Failed: " + currentMeasurement.getTimestamp()
                    + ", " +  currentMeasurement.getSensor_id() + ", " + currentMeasurement.getMeasurement_value());
        }
    }

}
