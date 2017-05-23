package me.tre.ai.function.event.events;

import lombok.Getter;
import me.tre.ai.function.Questions;
import me.tre.ai.function.event.Event;

public class AnswerEvent extends Event{

    @Getter private long time;
    @Getter Questions question;
    @Getter String answer;

    public AnswerEvent(Questions question, String answer){
        this.question = question;
        this.answer = answer;
        this.time = System.currentTimeMillis();
    }
}
