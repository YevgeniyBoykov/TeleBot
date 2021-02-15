package com.bjenst;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Bot extends TelegramLongPollingBot {

    private static final Logger log = Logger.getLogger(Bot.class);

    String userName;
    String token;

    public Bot (String userName, String token){
        this.userName = userName;
        this.token = token;
    }

    @Override
    public void onUpdateReceived(Update e) {
        log.debug("new Update recieve");
        Message msg = e.getMessage();// Это нам понадобится
        String txt = msg.getText();

        switch (txt){
            case "/start":{
                sendMsg(msg,"Привет! чего будем делать?");
                break;
            }
            case "узнать свой телеграм логин":{
                sendMsg(msg, msg.getFrom().getUserName());
                break;
            }
            case "weather": {
                String city;
//                sendMsg(msg, "Введите город:");
                city = "Kharkov";
//                Scanner in = new Scanner(System.in);
//                city = in.nextLine();
                try {
                    getWether d = new getWether();
                    sendMsg(msg, d.getWeather(city));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                break;
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
        keyboardFirstRow.add("узнать свой телеграм логин");
        keyboardFirstRow.add("weather");

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
        } catch (TelegramApiException e){
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return userName;
    }

    @Override
    public String getBotToken() {
        return token;
    }
}

