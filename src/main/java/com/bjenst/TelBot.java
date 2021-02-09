package com.bjenst;

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
    ReplyKeyboardMarkup keyb = new ReplyKeyboardMarkup();
    @Override
    public void onUpdateReceived(Update e) {
        Message msg = e.getMessage();// Это нам понадобится
        String txt = msg.getText();

        System.out.println(txt);

//        if(!txt.isEmpty()){
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
                sendMsg(msg, "Введите город:");
                Scanner in = new Scanner(System.in);
                city = in.nextLine();
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

    private void sendMsg(Message msg, String text) {
        SendMessage s = new SendMessage();
//        System.out.println(msg.getFrom().getUserName());
        s.setChatId(msg.getChatId()); // Боту может писать не один человек, и поэтому чтобы отправить сообщение, грубо говоря нужно узнать куда его отправлять
        s.setText(text);
        // Создаем клавиуатуру
        s.setReplyMarkup(keyb);
        keyb.setSelective(true);
        keyb.setResizeKeyboard(true);
        keyb.setOneTimeKeyboard(true);

        // Создаем список строк клавиатуры
        List<KeyboardRow> keyboard = new ArrayList<>();

        // Первая строчка клавиатуры
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        // Добавляем кнопки в первую строчку клавиатуры
        keyboardFirstRow.add("узнать свой телеграм логин");
        keyboardFirstRow.add("weather");

//        // Вторая строчка клавиатуры
//        KeyboardRow keyboardSecondRow = new KeyboardRow();
//        // Добавляем кнопки во вторую строчку клавиатуры
//        keyboardSecondRow.add("Команда 3");
//        keyboardSecondRow.add("Команда 4");

        // Добавляем все строчки клавиатуры в список
        keyboard.add(keyboardFirstRow);
//        keyboard.add(keyboardSecondRow);
        // и устанваливаем этот список нашей клавиатуре
        keyb.setKeyboard(keyboard);

        try { //Чтобы не крашнулась программа при вылете Exception
            execute(s);
        } catch (TelegramApiException e){
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "bjens_bot";
    }

    @Override
    public String getBotToken() {
        return "788825648:AAFwgXplFncCtcm1oJeRtJpKLTkeaiF_Frg";
    }
}

