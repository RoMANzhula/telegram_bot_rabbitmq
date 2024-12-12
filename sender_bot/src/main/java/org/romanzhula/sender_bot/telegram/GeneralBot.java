package org.romanzhula.sender_bot.telegram;

import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j;
import org.romanzhula.sender_bot.controllers.MessageHandlerController;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Log4j
@Component
public class GeneralBot extends TelegramWebhookBot {

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
        setWebhookAsync();
    }

    private void setWebhookAsync() {
        try {
            SetWebhook webhook = SetWebhook.builder()
                    .url(botSettings.getBotUri())
                    .build();
            this.setWebhook(webhook);
        } catch (TelegramApiException e) {
            log.error("Failed to set webhook: " + e.getMessage());
        }
    }

    public void sendAnswerMessage(SendMessage message) {
        if (message == null) {
            log.warn("Attempted to send a null message");
            return;
        }
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Failed to send message: {}" + e.getMessage(), e);
        }
    }

    @Override
    public String getBotUsername() {
        return botSettings.getBotName();
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return null; // We do not use this method
    }

    @Override
    public String getBotPath() {
        return "/update";
    }

}
