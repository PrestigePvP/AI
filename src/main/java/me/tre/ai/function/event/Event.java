package me.tre.ai.function.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public abstract class Event {

    @Getter private final boolean async;


    public Event(){
        async = false;
    }

}
