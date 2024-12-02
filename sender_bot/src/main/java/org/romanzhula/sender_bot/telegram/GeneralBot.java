package org.romanzhula.sender_bot.telegram;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Log4j
@Component
public class GeneralBot extends TelegramLongPollingBot {

    private final BotSettings botSettings;

    public GeneralBot(BotSettings botSettings) {
        super(botSettings.getBotToken());
        this.botSettings = botSettings;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            var message = update.getMessage();
            var chatId = message.getChatId();
            log.debug("chatId_" + chatId + ": " + message.getText());
        } else {
            log.debug("Received update: " + update);
        }
    }

    @Override
    public String getBotUsername() {
        return botSettings.getBotName();
    }

}
