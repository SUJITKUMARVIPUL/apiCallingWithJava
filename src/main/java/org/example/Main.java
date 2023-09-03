package org.example;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Main {
    public static void main(String[] args) throws IOException {
        URL url = null;
        HttpURLConnection connection = null;
        int responseCode = 0;
        String urlString = "https://api.nationalize.io/?name=nathaniel";


        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            System.out.println("problem in URL");
        }

        //connection

        try {
            connection = (HttpURLConnection) url.openConnection();
            responseCode = connection.getResponseCode();
        } catch (Exception e) {
            System.out.println("connection problem");
        }

        //extract information from the connection object:

        if (responseCode == 200) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder apiData = new StringBuilder();
            String readLine = null;

            while ((readLine = in.readLine()) != null) {
                apiData.append(readLine);
            }

            //
            try {
                in.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            System.out.println(apiData.toString());
            JSONObject jsonAPIResponse = new JSONObject(apiData.toString());
            System.out.println("Count = " + jsonAPIResponse.get("count"));
            System.out.println("Name = "+jsonAPIResponse.get("name"));

            JSONArray son = jsonAPIResponse.getJSONArray("country");
            for (Object s:son) {
                JSONObject j = new JSONObject(s.toString());
                System.out.println("Country Id  = " + j.get("country_id"));
                System.out.println("Probability  = " + j.get("probability"));
                System.out.println("-----------------");
            }
        } else
            System.out.println("API call could not be made!!!");


    }
}