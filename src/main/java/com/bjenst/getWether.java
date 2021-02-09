package com.bjenst;

import com.fasterxml.jackson.core.JsonToken;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class getWether {

//    String wetherUrl = "http://api.openweathermap.org/data/2.5/weather?q=Kharkov&appid=b5fa45edace010a474bb534c3fadf142";
    final private String token = "b5fa45edace010a474bb534c3fadf142";

//    HttpClient client = new HttpClient();



//       String s = con.set setRequestMethod("GET");
//    con.setRequestProperty("Content-Type", "application/json");
//    con.setConnectTimeout(CONNECTION_TIMEOUT);
//    con.setReadTimeout(CONNECTION_TIMEOUT);

    public String getWeather(String city) throws IOException {
        String urlS = "http://api.openweathermap.org/data/2.5/weather";
        urlS = urlS + "?q=" + city + "&appid=" + token;
        URL url = new URL(urlS);

        URLConnection con = url.openConnection();

        InputStream is = con.getInputStream();

        StringBuilder result = new StringBuilder();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        String line;
//        line = rd.readLine();
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        System.out.print(result);
        rd.close();
        return String.valueOf(result);
    }

//    private final HttpRequest httpRequest = HttpRequestBuilder.create(httpClient).build();
//    HttpRequest httpRequest = HttpRequestBuilder.createGet(uri, String.class)
//            .responseDeserializer(ResponseDeserializer.ignorableDeserializer()).build();
//
//    public getWether() throws IOException {
//    }
//
//    public void send(){
//        ResponseHandler<String> response = httpRequest.execute(someParamsYouWant);
//        System.out.println(response.getStatusCode());
//        System.out.println(response.get()); //retuns response body
//    }
}
