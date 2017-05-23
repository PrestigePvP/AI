package me.tre.ai.function.listener;

import me.tre.ai.Constants;
import me.tre.ai.function.Questions;
import me.tre.ai.function.event.EventHandler;
import me.tre.ai.function.event.EventPriority;
import me.tre.ai.function.event.events.QuestionEvent;
import me.tre.ai.util.ResponceUtil;

import javax.swing.*;
import java.util.Calendar;
import java.util.regex.Pattern;

public class SimpleQuestionListener {

    @EventHandler(priority = EventPriority.FINAL)
    public void onQuestion(QuestionEvent event) {
        // Check the case.
        String question = event.getQuestion().toLowerCase();
        question = question.replace("?", "");

        // A question
        // Pertains to a name, either mine or theirs
        if (question.contains("name")) {
            if (question.contains("your")) {
                if (question.contains("mean")) {
                    ResponceUtil.sendStupidMessage("My name means " + Constants.getMEANING() + " made by " + Constants.getCREATOR());
                    return;
                }
                String responce = "My name is " + Constants.getNAME();
                if (Constants.getAi().getProfile().getName() == null) {
                    responce = responce + ", whats yours?";
                }
                if (ResponceUtil.sendMessageResponce(responce) == JOptionPane.OK_OPTION) {
                    Constants.getAi().getAnswer().ask(Questions.NAME);
                    return;
                }
            } else if (question.contains("my")) {
                if (Constants.getAi().getProfile().getName() == null) {
                    if (question.contains("what")) {
                        if (ResponceUtil.sendMessageResponce("I don't know your name, why don't you tell me!") == JOptionPane.OK_OPTION) {
                            Constants.getAi().getAnswer().ask(Questions.NAME);
                            return;
                        }
                    } else {
                        question = question.replace("name", "").replace("my", "").replace("is", "");
                        if (ResponceUtil.sendMessageResponce("Your name is " + question + "?") == JOptionPane.OK_OPTION) {
                            Constants.getAi().getProfile().setName(question);
                            ResponceUtil.sendStupidMessage("I thought so, just confirming.");
                            return;
                        } else {
                            Constants.getAi().getAnswer().ask(Questions.NAME);
                            return;
                        }
                    }
                } else {
                    ResponceUtil.sendMessageResponce("You're " + Constants.getAi().getProfile().getName() + ", or atleast thats what you told me!");
                    return;
                }
            } else {
                return;
            }
        }
        if (question.contains("time")) {
            if (question.endsWith("it") || question.endsWith("now")) {
                ResponceUtil.sendStupidMessage("It is currently " + Calendar.getInstance().getTime());
                return;
            }
            if (question.contains("in")) {
                ResponceUtil.sendStupidMessage("I'm still learning, sorry. I don't know.");
                return;
            }
        }
        if (!event.isCancelled())
            Constants.getAi().getQuestion().sendFrame("I don't understand, mind asking again " + (Constants.getAi().getProfile().getName() == null ? "?" : Constants.getAi().getProfile().getName() + '?'));
    }
}
