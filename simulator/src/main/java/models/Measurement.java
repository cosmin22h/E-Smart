package models;

import java.util.Date;

public class Measurement {
    private Date timestamp;
    private String sensor_id;
    private Double measurement_value;

    public Measurement(Date timestamp, String sensor_id, Double measurement_value) {
        this.timestamp = timestamp;
        this.sensor_id = sensor_id;
        this.measurement_value = measurement_value;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getSensor_id() {
        return sensor_id;
    }

    public Double getMeasurement_value() {
        return measurement_value;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setSensor_id(String sensor_id) {
        this.sensor_id = sensor_id;
    }

    public void setMeasurement_value(Double measurement_value) {
        this.measurement_value = measurement_value;
    }
}
