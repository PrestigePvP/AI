package me.tre.ai.function.listener;

import me.tre.ai.Constants;
import me.tre.ai.function.Questions;
import me.tre.ai.function.event.EventHandler;
import me.tre.ai.function.event.EventPriority;
import me.tre.ai.function.event.events.AnswerEvent;
import me.tre.ai.util.ResponceUtil;

public class SimpleAnswerListener {


    @EventHandler(priority = EventPriority.FINAL)
    public void onAnswer(AnswerEvent event){
        String answer = event.getAnswer();
        switch (event.getQuestion()){
            case NAME: {
                String[] split = answer.split(" ");
                // Must be their name!
                if(split.length == 1){
                    Constants.getAi().getProfile().setName(split[0]);
                }else {
                    // Lets look for is, because then it'll be like "my name is Alex"
                    int foundIs = -1;
                    int search = 0;
                    for (String string : split){
                        if(foundIs == -1) {
                            if (string.equalsIgnoreCase("is")) {
                                foundIs = search;
                            }
                        }else {
                            if(search == foundIs){
                                // Meaning this should be the word after is
                                Constants.getAi().getProfile().setName(split[0]);
                            }
                        }
                        search++;
                    }
                }
                ResponceUtil.sendMessageResponce("Thanks " + (Constants.getAi().getProfile().getName() == null ? "but I really don't understand, mind answering again?" : Constants.getAi().getProfile().getName().equals(Constants.getCREATOR()) ? "master, you know I like to learn!" : Constants.getAi().getProfile().getName() + ", I love learning!"));
                if(Constants.getAi().getProfile().getName() == null){
                    Constants.getAi().getAnswer().ask(Questions.NAME);
                    return;
                }
                Constants.getAi().getQuestion().sendFrame("Anything else " + Constants.getAi().getProfile().getName());

            }
        }
    }
}
