package org.romanzhula.management.services;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface ProducerService {

    void produceAnswer(SendMessage sendMessage);

}
