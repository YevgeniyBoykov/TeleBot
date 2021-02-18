package com.bjenst;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

public class Main {
    private static final String botName = "bjens_bot";
    private static final String token = "788825648:AAFwgXplFncCtcm1oJeRtJpKLTkeaiF_Frg";

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new Bot(botName, token));
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }

    }
}
