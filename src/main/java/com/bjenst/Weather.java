package com.bjenst;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class Weather {

    private static final Logger log = Logger.getLogger(Weather.class);

    public static String getWeather(String city, Model model, String user) throws IOException {
        String weatherToken = "b5fa45edace010a474bb534c3fadf142";
        String urlS = "http://api.openweathermap.org/data/2.5/weather" +
                 "?q=" + city + "&lang=ru&units=metric&appid=" + weatherToken;
        URL url = new URL(urlS);

        Scanner in = new Scanner((InputStream) url.getContent());
        StringBuilder result = new StringBuilder();
        while (in.hasNext()){
            result.append(in.nextLine());
        }

        JSONObject object = new JSONObject(result.toString());
        model.setName(object.getString("name"));
        model.setTemp(object.getJSONObject("main").getDouble("temp"));
        model.setFeels_like(object.getJSONObject("main").getDouble("feels_like"));
        double mBarIndex = 0.750063755419211;
        model.setPressure((int) Math.round(object.getJSONObject("main").getDouble("pressure") * mBarIndex));
        model.setHumidity(object.getJSONObject("main").getDouble("humidity"));

        JSONArray array = object.getJSONArray("weather");
        for(int i = 0; i < array.length(); i++){
            JSONObject obj = array.getJSONObject(i);
            model.setIcon(obj.getString("icon"));
            model.setMain(obj.getString("description"));
        }

        String weatherInfo = "Город: " + model.getName() + "\n" +
                "Температура: " + model.getTemp() + " град.\n" +
                "Ощущается как: " + model.getFeels_like() + " град.\n" +
                "Атм.давление: " + model.getPressure() + " мм. рт.с.\n" +
                "Влажность: " + model.getHumidity() + "%\n" +
                "Погода: " + model.getMain();
        log.info(user + " ask " + model.getName());
        log.info("answer: \n" + weatherInfo);
        return weatherInfo +
                "http://openweathermap.org/img/w/" + model.getIcon() + ".png";
    }
}
