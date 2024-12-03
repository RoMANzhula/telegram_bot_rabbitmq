package org.romanzhula.sender_bot.utils;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;


@Component
public class DataHandlerUtils {

    public SendMessage sendTextMessage(
            Update update,
            String text
    ) {
        var message = update.getMessage();
        var sendMessage = new SendMessage();

        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(text);

        return sendMessage;
    }

}
