package me.tre.ai.function;

import lombok.Getter;
import me.tre.ai.Constants;
import me.tre.ai.function.event.EventManager;
import me.tre.ai.function.event.events.ProgramFunctionEvent;
import me.tre.ai.function.frames.AnswerFrame;
import me.tre.ai.function.frames.InputFrame;
import me.tre.ai.function.learning.Profile;
import me.tre.ai.function.listener.SimpleAnswerListener;
import me.tre.ai.function.listener.SimpleQuestionListener;
import me.tre.ai.function.listener.WeatherListener;

public class AI {

    @Getter private InputFrame question;
    @Getter private AnswerFrame answer;
    @Getter private EventManager eventManager;
    @Getter private Profile profile;

    public AI(){
        Constants.setAi(this);
        profile = new Profile();
        onProgramStart();
    }

    public void onProgramStart(){
        question = new InputFrame();
        answer = new AnswerFrame();
        eventManager = new EventManager();

        eventManager.call(new ProgramFunctionEvent(ProgramFunctionEvent.ProgramStatus.START));
        question.sendFrame();
        registerListener();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            eventManager.call(new ProgramFunctionEvent(ProgramFunctionEvent.ProgramStatus.END));
           // Constants.setAi(null);
        }));
    }

    private void registerListener(){
        eventManager.register(new SimpleQuestionListener());
        eventManager.register(new SimpleAnswerListener());
        eventManager.register(new WeatherListener());
    }

}
