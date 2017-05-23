package me.tre.ai.function.frames;

import me.tre.ai.Constants;
import me.tre.ai.function.Questions;
import me.tre.ai.function.event.events.AnswerEvent;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AnswerFrame extends JFrame {

    public void ask(Questions question){
        setTitle(question.getQuestion());

        setResizable(false);
        setLocationRelativeTo(null);

        setAlwaysOnTop(true);

        setSize(500, 300);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        // Memorizing AI Ready for You

        JTextField area = new JTextField("Enter answer here!");
        area.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() != KeyEvent.VK_ENTER)
                    return;
                dispose();
                Constants.getAi().getEventManager().call(new AnswerEvent(question, area.getText()));
            }
        });

        JPanel panel = new JPanel();
        panel.add(area);

        add(panel);

        setVisible(true);
    }

}
