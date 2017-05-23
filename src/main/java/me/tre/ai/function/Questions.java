package me.tre.ai.function;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Questions {
    NAME("What is your name?"),
    ZIP("I need to know your location first, what is your Zip Code?"),
    What("I don't know what you mean");

    @Getter private String question;
}
