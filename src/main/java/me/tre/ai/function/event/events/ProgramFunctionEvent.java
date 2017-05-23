package me.tre.ai.function.event.events;

import lombok.Getter;
import me.tre.ai.function.event.Event;


public class ProgramFunctionEvent extends Event{

    @Getter private long time;
    @Getter private ProgramStatus newStatus;

    public ProgramFunctionEvent(ProgramStatus newStatus) {
        this.newStatus = newStatus;
        this.time = System.currentTimeMillis();
    }

    public enum ProgramStatus {
        START,
        END,
    }

}
