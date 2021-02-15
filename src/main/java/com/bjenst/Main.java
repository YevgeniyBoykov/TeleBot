package com.bjenst;

import org.apache.log4j.Logger;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

public class Main {
    private static final Logger log = Logger.getLogger(Main.class);
    private static final String botName = "bjens_bot";
    private static final String token = "788825648:AAFwgXplFncCtcm1oJeRtJpKLTkeaiF_Frg";

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        Bot bot = new Bot(botName, token);
    }
}
