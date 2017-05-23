package me.tre.ai.function.listener;

import com.google.common.primitives.Ints;
import me.tre.ai.Constants;
import me.tre.ai.function.Questions;
import me.tre.ai.function.event.EventHandler;
import me.tre.ai.function.event.EventPriority;
import me.tre.ai.function.event.events.AnswerEvent;
import me.tre.ai.function.event.events.QuestionEvent;
import me.tre.ai.util.LocationUtil;
import me.tre.ai.util.ResponceUtil;

public class WeatherListener {


    @EventHandler(priority = EventPriority.FIRST)
    public void onQuestion(QuestionEvent event){

        String question = event.getQuestion().replace("?", "").toLowerCase();


        if(question.contains("weather") || question.contains("degree") || question.contains("temperature") || question.contains("rain") || question.contains("humidity")){
            if(Constants.getAi().getProfile().getZipCode() == null){
                Constants.getAi().getAnswer().ask(Questions.ZIP);
                event.setCancelled(true);
                return;
            }

            // We need to get the date.
            if(question.contains("today") || question.contains("now")){
                ResponceUtil.sendMessageResponce(getWeather());
                event.setCancelled(true);
                return;
            }
            if(question.contains("tomorrow")){

            }
            if(question.contains("future")){

            }
        }else {
            return;
        }

    }

    @EventHandler(priority = EventPriority.FINAL)
    public void onAnswer(AnswerEvent event){
        String answer = event.getAnswer();
        switch (event.getQuestion()){
            case ZIP: {
                Integer integer = Ints.tryParse(answer);
                if(integer == null){
                    Constants.getAi().getAnswer().ask(Questions.ZIP);
                    return;
                }
                if(!LocationUtil.checkZip(integer)){
                    Constants.getAi().getAnswer().ask(Questions.ZIP);
                    return;
                }
                Constants.getAi().getProfile().setZipCode(integer);
                ResponceUtil.sendStupidMessage("Thanks, now I can help you so much more.");

            }
        }
    }

    private String getWeather(){
        //https://weather.com/weather/today/l/72801:4:US
        return "Sometiung";
    }
}
