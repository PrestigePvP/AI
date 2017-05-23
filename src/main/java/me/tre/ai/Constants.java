package me.tre.ai;

import lombok.Getter;
import lombok.Setter;
import me.tre.ai.function.AI;

public class Constants {


    @Getter @Setter private static AI ai;
    @Getter private static final String NAME;
    @Getter private static final String CREATOR;
    @Getter private static final String MEANING;

    static {
        NAME = "M.A.R.Y";
        CREATOR = "Tre";
        MEANING = "Memorizing AI Ready for You";
    }



    public static void main(String[] args){
        ai = new AI();
    }


}
