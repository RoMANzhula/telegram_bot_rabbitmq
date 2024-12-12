package org.romanzhula.sender_bot.telegram;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BotSettings {

    @Value("${telegram.bot.name}")
    private String botName;

    @Value("${telegram.bot.token}")
    private String botToken;

    @Value("${telegram.bot.uri}")
    private String botUri;


    public String getBotName() {
        return botName;
    }

    public String getBotToken() {
        return botToken;
    }

    public String getBotUri() {
        return botUri;
    }

}
