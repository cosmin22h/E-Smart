package utils;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SensorReader {

    private static final String FILE_PATH = "data/sensors.csv";

    public static List<String> readSensors() {
        List<String> ids = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(FILE_PATH));
            br.readLine();
            String id;
            while((id = br.readLine()) != null) {
                id = id.replace("\"", "");
                ids.add(id);
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Sensors CSV doesn't exists!");
        }

        return ids;
    }


}
