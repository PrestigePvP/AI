package me.tre.ai.util;

import lombok.experimental.UtilityClass;

import java.util.Calendar;

@UtilityClass
public class GreetingsUtil {

    public String getGreeting(){
        Calendar calendar = Calendar.getInstance(); // creates a new calendar instance
        int hour = calendar.get(Calendar.HOUR_OF_DAY); // gets hour in 24h format
        // We need to forget day and we just get time of day.
        if(hour > 4 && hour < 12){
            return "Morning";
        }

        if(hour >= 12 && hour < 18){
            return "Afternoon";
        }

        if(hour >= 18 && hour < 2){
            return "Night";
        }

        return null;
    }
}
