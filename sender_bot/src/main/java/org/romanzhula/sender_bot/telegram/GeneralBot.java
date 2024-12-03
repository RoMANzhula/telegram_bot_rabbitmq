package org.romanzhula.sender_bot.telegram;

import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j;
import org.romanzhula.sender_bot.controllers.MessageHandlerController;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Log4j
@Component
public class GeneralBot extends TelegramLongPollingBot {

    private final BotSettings botSettings;
    private final MessageHandlerController messageHandlerController;

    public GeneralBot(
            BotSettings botSettings,
            MessageHandlerController messageHandlerController
    ) {
        super(botSettings.getBotToken());

        this.botSettings = botSettings;
        this.messageHandlerController = messageHandlerController;
    }

    @PostConstruct
    public void init() {
        messageHandlerController.registerBot(this);
    }

    @Override
    public void onUpdateReceived(Update update) {
//        if (update.hasMessage() && update.getMessage().hasText()) {
//            var message = update.getMessage();
//            var chatId = message.getChatId();
//            log.debug("chatId_" + chatId + ": " + message.getText());
//
//            var response = new SendMessage();
//            response.setChatId(chatId.toString());
//            response.setText("It's message from GeneralBot.class. Testing... answer to your message.");
//
//            sendAnswerMessage(response);
//
//        } else {
//            log.debug("Received update: " + update);
//        }

        messageHandlerController.processUpdate(update);

    }

    public void sendAnswerMessage(SendMessage message) {
        if (message != null) {
            try {
                execute(message);
            } catch (TelegramApiException exception) {
                log.error(exception);
            }
        }
    }

    @Override
    public String getBotUsername() {
        return botSettings.getBotName();
    }

}
