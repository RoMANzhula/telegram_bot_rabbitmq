package org.romanzhula.management.services.implementations;

import org.romanzhula.management.models.MessageData;
import org.romanzhula.management.repositories.MessageDataRepository;
import org.romanzhula.management.services.ConsumerService;
import org.romanzhula.management.services.MessageHandleService;
import org.romanzhula.management.services.ProducerService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;


@Service
public class MessageHandleServiceImpl implements MessageHandleService {

    private final MessageDataRepository messageDataRepository;
    private final ProducerService producerService;

    public MessageHandleServiceImpl(
            MessageDataRepository messageDataRepository,
            ProducerService producerService
    ) {
        this.messageDataRepository = messageDataRepository;
        this.producerService = producerService;
    }

    @Override
    public void processTextMessage(Update update) {
        saveMessageData(update);

        Message message = update.getMessage();
        SendMessage sendMessage = new SendMessage();

        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText("Hello from MANAGEMENT!");

        producerService.produceAnswer(sendMessage);
    }

    private void saveMessageData(Update update) {
        var messageData = MessageData.builder()
                .event(update)
                .build()
        ;

        messageDataRepository.save(messageData);
    }

}
