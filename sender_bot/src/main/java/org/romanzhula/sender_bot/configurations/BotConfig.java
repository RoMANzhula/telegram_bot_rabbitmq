package org.romanzhula.sender_bot.configurations;

import lombok.extern.log4j.Log4j;
import org.romanzhula.sender_bot.telegram.BotSettings;
import org.romanzhula.sender_bot.telegram.GeneralBot;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;


@Log4j
@Configuration
public class BotConfig {

    private final GeneralBot generalBot;
    private final BotSettings botSettings;

    public BotConfig(GeneralBot generalBot, BotSettings botSettings) {
        this.generalBot = generalBot;
        this.botSettings = botSettings;
    }

    @Bean
    public TelegramBotsApi telegramBotsApi() {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(generalBot, createWebhook());
            return botsApi;
        } catch (TelegramApiException e) {
            log.error("Error initializing TelegramBotsApi: " + e.getMessage());
            throw new RuntimeException("Failed to initialize bot API", e);
        }
    }

    private SetWebhook createWebhook() {
        return SetWebhook.builder()
                .url(botSettings.getBotUri())
                .build()
        ;
    }

}
