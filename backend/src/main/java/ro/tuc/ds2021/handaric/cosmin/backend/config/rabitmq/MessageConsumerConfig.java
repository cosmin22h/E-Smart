package ro.tuc.ds2021.handaric.cosmin.backend.config.rabitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class MessageConsumerConfig {

    @Value("${rabbitmq.uri}")
    private String uri;
    @Value("${rabbitmq.queue}")
    private String queueName;
    @Value("${rabbitmq.durable}")
    private String durable;
    @Value("${rabbitmq.exclusive}")
    private String exclusive;
    @Value("${rabbitmq.auto-delete}")
    private String autoDelete;

    @Bean(name = "rabbitMqChannel")
    @Scope("singleton")
    public Channel getChannel() {
        ConnectionFactory factory = new ConnectionFactory();
        Channel channel = null;
        try {
            factory.setUri(this.uri);
            factory.setConnectionTimeout(30000);
            Connection connection = factory.newConnection();
            channel = connection.createChannel();
            channel.queueDeclare(this.queueName,
                    Boolean.valueOf(this.durable),
                    Boolean.valueOf(this.exclusive),
                    Boolean.valueOf(this.autoDelete), null);

            return channel;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return channel;
    }
}
