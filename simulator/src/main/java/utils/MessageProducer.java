package utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import javax.swing.*;

public class MessageProducer {

    // constants for rabbitmq configuration
    private final static String QUEUE_NAME = "measurements";
    private final static boolean DURABLE = false;    // RabbitMQ will never lose the queue if a crash occurs
    private final static boolean EXCLUSIVE = false;  // if queue only will be used by one connection
    private final static boolean AUT0_DELETE = false; // queue is deleted when last consumer unsubscribes

    private Channel channel;

    public MessageProducer(String uri) {
        initConnectionWithRabbitMQ(uri);
    }

    public Channel getChannel() {
        return channel;
    }

    public static String getQueueName() {
        return QUEUE_NAME;
    }

    private void initConnectionWithRabbitMQ(String uri) {
        ConnectionFactory factory = new ConnectionFactory();
        try {
            factory.setUri(uri);
            factory.setConnectionTimeout(30000);
            Connection connection = factory.newConnection();
            this.channel = connection.createChannel();
            this.channel.queueDeclare(QUEUE_NAME, DURABLE, EXCLUSIVE, AUT0_DELETE, null);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Connection with RabbitMQ failed!");
        }
    }
}
