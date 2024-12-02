package org.romanzhula.sender_bot.configurations;

import lombok.extern.log4j.Log4j;
import org.romanzhula.sender_bot.telegram.GeneralBot;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;


@Log4j
@Configuration
public class BotConfig {

    private final GeneralBot generalBot;

    public BotConfig(GeneralBot generalBot) {
        this.generalBot = generalBot;
    }

    @Bean
    public TelegramBotsApi telegramBotsApi() {

        TelegramBotsApi botsApi;

        try {
            botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(generalBot);
        } catch (TelegramApiException e) {
            log.error("Error: {}", e);
            return null;
        }

        return botsApi;

    }

}
