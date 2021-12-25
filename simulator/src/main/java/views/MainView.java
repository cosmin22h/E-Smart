package views;

import models.Host;
import utils.MessageProducer;
import utils.SensorReader;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MainView extends JFrame implements ActionListener {

    // GUI config
    private final static String WINDOW_TITLE = "Sensor Simulator";
    private final static int WINDOW_WIDTH = 220;
    private final static int WINDOW_HEIGHT = 150;

    // RabbitMQ config
    private final static String CLOUDAMQP_URI = "amqps://euitndrk:KsCrKJuRAn3cNSwJi4CAaXoD_kj3qFRV@kangaroo.rmq.cloudamqp.com/euitndrk";
    private final static String LOCAL_RABBITMQ_URI = "amqp://user:pass@localhost";

    private JComboBox<Object> dropdown;
    private List<Host> hosts = new ArrayList<>();
    private JButton confirmBtn;

    public MainView() {
        // default config
        setTitle(WINDOW_TITLE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        JLabel label = new JLabel();

        // dropdown
        JLabel dropdownLabel = new JLabel();
        dropdownLabel.setBounds(20, 0, 170, 30);
        dropdownLabel.setText("Select the RabbitMQ Host");
        label.add(dropdownLabel);
        hosts.add(new Host("Local", LOCAL_RABBITMQ_URI));
        hosts.add(new Host("Cloud", CLOUDAMQP_URI));
        Object[] hostsArray = this.hosts.toArray();
        this.dropdown = new JComboBox<>(hostsArray);
        this.dropdown.setBounds(20, 30, 170, 30);
        this.dropdown.addActionListener(this);
        label.add(this.dropdown);

        // button
        this.confirmBtn = new JButton("Confirm");
        this.confirmBtn.setBounds(20, 70, 170, 30);
        this.confirmBtn.addActionListener(this);
        label.add(this.confirmBtn);

        add(label);
        setVisible(true);
    }

    private void initConnectionWithRabbitMq(String uri) {
        List<String> sensors = SensorReader.readSensors();
        MessageProducer messageProducer = new MessageProducer(uri);
        this.dispose();
        new SimulationView(sensors, messageProducer);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this.confirmBtn)) {
            Host hostSelected = (Host) this.dropdown.getSelectedItem();
            this.initConnectionWithRabbitMq(hostSelected.getValue());
        }
    }
}
