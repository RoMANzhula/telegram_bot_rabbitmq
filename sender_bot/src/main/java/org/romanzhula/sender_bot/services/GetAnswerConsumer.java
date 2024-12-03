package org.romanzhula.sender_bot.services;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface GetAnswerConsumer {

    void consume(SendMessage sendMessage);

}
