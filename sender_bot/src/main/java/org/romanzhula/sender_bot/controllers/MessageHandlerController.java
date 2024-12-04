package org.romanzhula.sender_bot.controllers;

import lombok.extern.log4j.Log4j;
import org.romanzhula.sender_bot.configurations.RabbitConfig;
import org.romanzhula.sender_bot.services.UpdateProducer;
import org.romanzhula.sender_bot.telegram.GeneralBot;
import org.romanzhula.sender_bot.utils.DataHandlerUtils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;


@Log4j
@Component
public class MessageHandlerController {

    private GeneralBot generalBot;

    private final DataHandlerUtils textMessage;
    private final UpdateProducer updateProducer;
    private final RabbitConfig rabbitConfig;


    public MessageHandlerController(
            DataHandlerUtils textMessage,
            UpdateProducer updateProducer,
            RabbitConfig rabbitConfig
    ) {
        this.textMessage = textMessage;
        this.updateProducer = updateProducer;
        this.rabbitConfig = rabbitConfig;
    }


    public void registerBot(GeneralBot generalBot) {
        this.generalBot = generalBot;
    }

    public void processUpdate(Update update) {
        if (update == null) {
            log.error("ERROR: received update is NULL!");
            return;
        }

        if (update.getMessage() != null) {
            processMessagesByType(update);
        } else {
            log.error("Received message has unsupported type: " + update);
            throw new NullPointerException("Received message has unsupported type: " + update);
        }

    }

    private void processMessagesByType(Update update) {
        var message = update.getMessage();

        if (message.getText() != null) {
            processMessageTextType(update);
        } else if (message.getPhoto() != null) {
            processMessagePhotoType(update);
        } else if (message.getDocument() != null) {
            processMessageDocumentType(update);
        } else {
            processUnsupportedMessageType(update);
        }
    }

    private void processMessageTextType(Update update) {
        updateProducer.produce(rabbitConfig.getQueueNameText(), update);
    }

    private void processMessagePhotoType(Update update) {
        updateProducer.produce(rabbitConfig.getQueueNamePhoto(), update);
        processWaitingUploadData(update);
    }

    private void processMessageDocumentType(Update update) {
        updateProducer.produce(rabbitConfig.getQueueNameDocument(), update);
        processWaitingUploadData(update);
    }

    private void processUnsupportedMessageType(Update update) {
        String text = "Unsupported message type. Please try again.";

        var sendMessage = textMessage.sendTextMessage(update, text);

        setAnswerViewForClient(sendMessage);
    }

    private void processWaitingUploadData(Update update) {
        String text = "FIle received. Loading...";

        var sendMessage = textMessage.sendTextMessage(update, text);

        setAnswerViewForClient(sendMessage);
    }

    public void setAnswerViewForClient(SendMessage sendMessage) {
        generalBot.sendAnswerMessage(sendMessage);
    }

}
