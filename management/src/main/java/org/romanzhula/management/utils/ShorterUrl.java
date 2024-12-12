package org.romanzhula.management.utils;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class ShorterUrl {

    public String getShortenedUrl(String originalUrl) {
        try {
            String tinyUrlApi = "http://tinyurl.com/api-create.php?url=" + originalUrl;
            URL url = new URL(tinyUrlApi);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } catch (Exception e) {
            return originalUrl;
        }
    }

}
