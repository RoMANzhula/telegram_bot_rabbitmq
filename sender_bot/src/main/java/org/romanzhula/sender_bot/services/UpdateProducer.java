package org.romanzhula.sender_bot.services;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface UpdateProducer {

    void produce(String rabbitQueueName, Update update);

}
