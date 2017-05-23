package me.tre.ai.function.event.events;

import lombok.Getter;
import lombok.Setter;
import me.tre.ai.function.event.Cancellable;
import me.tre.ai.function.event.Event;

public class QuestionEvent extends Event implements Cancellable{

    @Getter private long time;
    @Getter String question;
    @Getter @Setter boolean cancelled;

    public QuestionEvent(String question){
        this.question = question;
        this.time = System.currentTimeMillis();
        cancelled = false;
    }


}
