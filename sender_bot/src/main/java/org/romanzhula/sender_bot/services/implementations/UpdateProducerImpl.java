package org.romanzhula.sender_bot.services.implementations;

import lombok.extern.log4j.Log4j;
import org.romanzhula.sender_bot.services.UpdateProducer;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;


@Log4j
@Service
public class UpdateProducerImpl implements UpdateProducer {

    private final RabbitTemplate rabbitTemplate;

    public UpdateProducerImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void produce(String rabbitQueueName, Update update) {
        log.debug(update.getMessage().getText());

        rabbitTemplate.convertAndSend(rabbitQueueName, update);
    }

}
