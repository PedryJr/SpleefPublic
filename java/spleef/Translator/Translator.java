package spleef.Translator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


public class Translator {
    // TODO: If you have your own Premium account credentials, put them down here:

    /**
     * Sends out a WhatsApp message via WhatsMate WA Gateway.
     */
    public String translate(String fromLang, String toLang, String text) throws Exception {


        // TODO: Translate:


        String urlStr = "https://script.google.com/macros/s/AKfycbwmvxHmo2paS2J1aSQDrmHAiDX0lDHYTA3WKJFvODzcu_vU4vTmhgY7Hi6PX_NZyy0/exec" +
                "?q=" + URLEncoder.encode(text, "UTF-8") +
                "&target=" + toLang +
                "&source=" + fromLang;
        URL url = new URL(urlStr);
        StringBuilder response = new StringBuilder();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        if(toLang.equals("ru") || toLang.equals("uk")){
            return new String(response.toString().getBytes(), StandardCharsets.UTF_8);
        }
            in.close();
            return response.toString();
    }

}
