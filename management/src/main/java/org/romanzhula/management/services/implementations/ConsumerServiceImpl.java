package org.romanzhula.management.services.implementations;


import lombok.extern.log4j.Log4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.romanzhula.management.services.ConsumerService;
import org.romanzhula.management.services.ProducerService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Log4j
@Service
public class ConsumerServiceImpl implements ConsumerService {

//    private static final Logger log = LogManager.getLogger(ConsumerServiceImpl.class);

    private final ProducerService producerService;

    public ConsumerServiceImpl(ProducerService producerService) {
        this.producerService = producerService;
    }

    @Override
    @RabbitListener(queues = "${rabbitmq.queue.name.text}")
    public void consumeTextMessageUpdate(Update update) {
        log.debug("Manager: Text msg is received successfully.");

        Message message = update.getMessage();
        SendMessage sendMessage = new SendMessage();

        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText("Hello from MANAGEMENT!");

        producerService.produceAnswer(sendMessage);
    }

    @Override
    @RabbitListener(queues = "${rabbitmq.queue.name.document}")
    public void consumeDocumentMessageUpdate(Update update) {
        log.debug("Manager: Document msg is received successfully.");
    }

    @Override
    @RabbitListener(queues = "${rabbitmq.queue.name.photo}")
    public void consumePhotoMessageUpdate(Update update) {
        log.debug("Manager: Photo msg is received successfully.");
    }

}
