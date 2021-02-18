package com.bjenst;

import org.apache.log4j.Logger;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Bot extends TelegramLongPollingBot {

    private static final Logger log = Logger.getLogger(Bot.class);
    private final String botName;
    private final String token;

    public Bot(String botName, String token){
        this.botName = botName;
        this.token = token;
    }

    @Override
    public void onUpdateReceived(Update e) {
//        log.debug("new Update recieve");
        Message msg = e.getMessage();// Это нам понадобится
        Model model = new Model();

        if (msg != null && msg.hasText()) {
            switch (msg.getText()) {
                case "/start": {
                    sendMsg(msg, "для получения погоды введите название города.");
                    break;
                }
                case "узнать свой телеграм логин": {
                    String userName = msg.getFrom().getUserName();
                    log.info("get userName from " + userName);
                    sendMsg(msg, userName);
                    break;
                }

                default:
                    try {
                        sendMsg(msg, Weather.getWeather(msg.getText(), model, msg.getFrom().getUserName()));
                    } catch (IOException ioException) {
                        sendMsg(msg, "Город не найден");
                        log.info(msg.getFrom().getUserName());
                        log.info("Город не найден");
                    }
            }
        }
    }

    private void sendMsgButt(SendMessage message){
        ReplyKeyboardMarkup keyb = new ReplyKeyboardMarkup();

        // Создаем клавиуатуру
        message.setReplyMarkup(keyb);
        keyb.setSelective(true);
        keyb.setResizeKeyboard(true);
        keyb.setOneTimeKeyboard(true);
        // Создаем список строк клавиатуры
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();
        // Добавляем кнопки в первую строчку клавиатуры
        keyboardFirstRow.add(new KeyboardButton("узнать свой телеграм логин"));
//        keyboardFirstRow.add(new KeyboardButton("weather"));

        keyboard.add(keyboardFirstRow);
        keyb.setKeyboard(keyboard);
    }

    private void sendMsg(Message msg, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(msg.getChatId()); // Боту может писать не один человек, и поэтому чтобы отправить сообщение, грубо говоря нужно узнать куда его отправлять
        message.setText(text);

        try { //Чтобы не крашнулась программа при вылете Exception
            sendMsgButt(message);
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return token;
    }
}

