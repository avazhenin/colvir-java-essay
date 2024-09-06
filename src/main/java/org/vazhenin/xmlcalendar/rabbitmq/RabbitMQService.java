package org.vazhenin.xmlcalendar.rabbitmq;

import org.jboss.logging.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vazhenin.xmlcalendar.configuration.RabbitMQConfig;

@Service
public class RabbitMQService {
    Logger log = Logger.getLogger(RabbitMQService.class);
    @Autowired
    RabbitTemplate rabbitTemplate;

    public void send(String msg) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, msg);
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void receiveMessage(String message) {
        log.infof("Received Message: %s", message);
    }

}
