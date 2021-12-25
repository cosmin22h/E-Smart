package views;

import utils.MessageProducer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class SimulationView extends JFrame implements ActionListener {

    private final static String WINDOW_TITLE = "Sensor Simulator";
    private final static int WINDOW_WIDTH = 350;
    private final static int WINDOW_HEIGHT = 320;

    private JComboBox<Object> dropdown;
    private JSpinner spinner;
    private JButton startBtn;
    private JButton selectHostBtn;
    private JTextField txtInput;

    private MessageProducer messageProducer;

    public SimulationView(List<String> sensors, MessageProducer messageProducer) {
        // default config
        setTitle(WINDOW_TITLE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        JLabel label = new JLabel();

        // dropdown
        JLabel dropdownLabel = new JLabel();
        dropdownLabel.setBounds(20, 0, 300, 30);
        dropdownLabel.setText("Select a sensor");
        label.add(dropdownLabel);
        Object[] sensorsIds = sensors.toArray();
        this.dropdown = new JComboBox<>(sensorsIds);
        this.dropdown.setBounds(20, 30, 300, 30);
        this.dropdown.addActionListener(this);
        label.add(this.dropdown);

        // measurement interval
        JLabel measurementLabel = new JLabel();
        measurementLabel.setBounds(20, 60, 300, 30);
        measurementLabel.setText("Measurement interval (in seconds)");
        label.add(measurementLabel);
        this.spinner = new JSpinner(new SpinnerNumberModel(5, 1, 3600, 1));
        this.spinner.setBounds(20, 90, 300, 30);
        label.add(this.spinner);

        // input date
        JLabel dateLabel = new JLabel();
        dateLabel.setBounds(20, 120, 300, 30);
        dateLabel.setText("Select a start date (Format: dd-MM-yyyy hh:mm:ss)");
        label.add(dateLabel);
        this.txtInput = new JTextField("");
        this.txtInput.setBounds(20, 150, 300, 30);
        label.add(this.txtInput);

        // start button
        this.startBtn = new JButton("Start Simulation");
        this.startBtn.setBounds(20, 190, 300, 30);
        this.startBtn.addActionListener(this);
        label.add(this.startBtn);

        // host button
        this.selectHostBtn = new JButton("Select a host");
        this.selectHostBtn.setBounds(20, 230, 300, 30);
        this.selectHostBtn.addActionListener(this);
        label.add(this.selectHostBtn);

        add(label);
        setVisible(true);

        this.messageProducer = messageProducer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(this.startBtn)) {
            String selectedSensor = (String) dropdown.getSelectedItem();
            Integer measurementValue = Integer.parseInt(spinner.getValue().toString());
            try {
                new SensorView(selectedSensor, txtInput.getText(), measurementValue, messageProducer);
            } catch (IOException | ParseException ex) {
                ex.printStackTrace();
            }
        }
        if (e.getSource().equals(this.selectHostBtn)) {
            this.dispose();
            new MainView();
        }
    }
}
