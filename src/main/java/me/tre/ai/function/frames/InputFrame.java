package me.tre.ai.function.frames;

import me.tre.ai.Constants;
import me.tre.ai.function.Questions;
import me.tre.ai.function.event.events.AnswerEvent;
import me.tre.ai.function.event.events.QuestionEvent;
import me.tre.ai.util.GreetingsUtil;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class InputFrame extends JFrame {

    public void sendFrame() {
        setTitle("Ask anything to M.A.R.Y");
        if(Constants.getAi().getProfile().getName() != null){
            setTitle((GreetingsUtil.getGreeting() == null ? "Hello" : "Good " + GreetingsUtil.getGreeting()) + ' ' + Constants.getAi().getProfile().getName() + ", how may I help you?");
        }


        setResizable(false);
        setLocationRelativeTo(null);

        setAlwaysOnTop(true);

        setSize(500, 300);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        JTextField area = new JTextField("Enter question here!");
        area.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() != KeyEvent.VK_ENTER)
                    return;

                dispose();
                Constants.getAi().getEventManager().call(new QuestionEvent(area.getText()));
            }
        });

        JPanel panel = new JPanel();
        panel.add(area);

        add(panel);

        setVisible(true);
    }

    public void sendFrame(String string) {
        setTitle(string);

        setResizable(false);
        setLocationRelativeTo(null);

        setAlwaysOnTop(true);

        setSize(500, 300);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        JTextField area = new JTextField("Enter question here!");
        area.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() != KeyEvent.VK_ENTER)
                    return;

                dispose();
                Constants.getAi().getEventManager().call(new QuestionEvent(area.getText()));
            }
        });

        JPanel panel = new JPanel();
        panel.add(area);

        add(panel);

        setVisible(true);
    }


}
