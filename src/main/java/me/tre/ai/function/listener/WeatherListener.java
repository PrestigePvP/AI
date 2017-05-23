package me.tre.ai.function.listener;

import com.google.common.primitives.Ints;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.tre.ai.Constants;
import me.tre.ai.function.Questions;
import me.tre.ai.function.event.EventHandler;
import me.tre.ai.function.event.EventPriority;
import me.tre.ai.function.event.events.AnswerEvent;
import me.tre.ai.function.event.events.QuestionEvent;
import me.tre.ai.util.LocationUtil;
import me.tre.ai.util.ResponceUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherListener {


    @EventHandler(priority = EventPriority.FIRST)
    public void onQuestion(QuestionEvent event) {

        String question = event.getQuestion().replace("?", "").toLowerCase();


        if(question.contains("weather") || question.contains("degree") || question.contains("temperature") || question.contains("rain") || question.contains("humidity")){
            if(Constants.getAi().getProfile().getZipCode() == null){
                //TODO Replace w/ Automated system.
                Constants.getAi().getAnswer().ask(Questions.ZIP);
                event.setCancelled(true);
                return;
            }

            // We need to get the date.
            if (question.contains("today") || question.contains("now")) {
                ResponceUtil.sendMessageResponce(getWeather());
                event.setCancelled(true);
                return;
            }
            if (question.contains("tomorrow")) {

            }
            if (question.contains("future")) {

            }
        } else {
        }

    }


    //TODO Replace w/ the automated system.
    @EventHandler(priority = EventPriority.FINAL)
    public void onAnswer(AnswerEvent event) {
        String answer = event.getAnswer();
        switch (event.getQuestion()) {
            case ZIP: {
                Integer integer = Ints.tryParse(answer);
                if (integer == null) {
                    Constants.getAi().getAnswer().ask(Questions.ZIP);
                    return;
                }
                if (!LocationUtil.checkZip(integer)) {
                    Constants.getAi().getAnswer().ask(Questions.ZIP);
                    return;
                }
                Constants.getAi().getProfile().setZipCode(integer);
                ResponceUtil.sendStupidMessage("Thanks, now I can help you so much more.");

            }
        }
    }

    private String getWeather() {
        //TODO Get weather is json parse and make a nice gui using images.
        //WeatherUnderground API key: 894b5e63bc489d7c
        //https://www.wunderground.com/weather/api/d/docs
        return "In progress";
    }

    private String getIPAddress() throws IOException {
        //We cannot get the IP from the computer itself so we have to make a call to get it because InetAddress.getLocalHost() returns the local IP.
        URL whatismyip = new URL("http://checkip.amazonaws.com");
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
            return in.readLine();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Integer getZipCode() {
        int zip = 0;
        try {
            String ipadd = "http://freegeoip.net/json/" + getIPAddress();
            URL url = new URL(ipadd);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            JsonParser jsonParser = new JsonParser();
            JsonElement jsonElement = jsonParser.parse(new InputStreamReader((InputStream) urlConnection.getContent()));
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            zip = jsonObject.get("zip_code").getAsInt();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return zip;
    }
}