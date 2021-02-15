package com.bjenst;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class getWeather {

    final private String token = "b5fa45edace010a474bb534c3fadf142";

    public String getWeather(String city) throws IOException {
        String result;
        String urlS = "http://api.openweathermap.org/data/2.5/weather";
        urlS = urlS + "?q=" + city + "&units=metric&lang=ru&appid=" + token;
        URL url = new URL(urlS);

        URLConnection con = url.openConnection();
        InputStream is = con.getInputStream();

        String line = new BufferedReader(new InputStreamReader(is)).readLine();
        JSONObject jsonObj = new JSONObject(line);

        JSONObject temp = jsonObj.getJSONObject("main");
        result = "temp = " + temp.getDouble("temp") + "\n"
                + "feels_like = " + temp.getInt("feels_like") + "\n";
        return result;
    }
}
