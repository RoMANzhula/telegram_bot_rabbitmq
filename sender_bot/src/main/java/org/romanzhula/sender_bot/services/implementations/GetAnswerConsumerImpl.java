package org.romanzhula.sender_bot.services.implementations;

import org.romanzhula.sender_bot.controllers.MessageHandlerController;
import org.romanzhula.sender_bot.services.GetAnswerConsumer;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
public class GetAnswerConsumerImpl implements GetAnswerConsumer {

    private final MessageHandlerController messageHandlerController;


    public GetAnswerConsumerImpl(MessageHandlerController messageHandlerController) {
        this.messageHandlerController = messageHandlerController;
    }


    @Override
    @RabbitListener(queues = "${rabbitmq.queue.name.answer}")
    public void consume(SendMessage sendMessage) {
        messageHandlerController.setAnswerViewForClient(sendMessage);
    }

}
