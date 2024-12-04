package org.romanzhula.management.services.implementations;


import lombok.extern.log4j.Log4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.romanzhula.management.services.ProducerService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;


@Log4j
@Service
public class ProducerServiceImpl implements ProducerService {

//    private static final Logger log = LogManager.getLogger(ProducerServiceImpl.class);

    @Value("${rabbitmq.queue.name.answer}")
    private String queueNameAnswer;

    private final RabbitTemplate rabbitTemplate;

    public ProducerServiceImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void produceAnswer(SendMessage sendMessage) {
        log.debug("Message was send: " + sendMessage.getText());
        rabbitTemplate.convertAndSend(queueNameAnswer, sendMessage);
    }

}
