package utils;

import models.Measurement;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MeasurementReader {

    private final static String BASE_PATH = "data/";

    private String sensor_id;
    private BufferedReader br;
    private List<Measurement> measurements;

    public MeasurementReader(String sensor_id) {
        this.sensor_id = sensor_id;
        try {
            this.br = new BufferedReader(new FileReader(BASE_PATH + sensor_id + ".csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Measurement CSV doesn't exists! (sensor: " + this.sensor_id +")");
        }
        this.measurements = new ArrayList<>();
    }

    public void readMeasurement(Date currentDate) {
        try {
            String readValue = br.readLine();
            if (readValue == null) {
                JOptionPane.showMessageDialog(null, "No more value to read! (sensor: " + this.sensor_id + ")");
            }
            Double measurementValue = Double.parseDouble(readValue);
            Measurement measurement = new Measurement(currentDate, this.sensor_id, measurementValue);
            this.measurements.add(measurement);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Measurement> getMeasurements() {
        return this.measurements;
    }

}
