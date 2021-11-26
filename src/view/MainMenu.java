package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainMenu extends Frame {
    public MainMenu(){
        this.setTitle("GameMenu");
    }

    public void init(){
        this.setBackground(Color.WHITE);
        this.setBounds(600,210,200,350);
        this.setLayout(null);
        this.setVisible(true);
        this.setResizable(false);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("Game Quited.");
                System.exit(0);
            }
        });

        Button Btn1 = new Button("Start Game");
        Button Btn2 = new Button("Rule");

        Btn1.setBounds(50,160,100,50);
        Btn2.setBounds(50,210+20,100,50);
        this.add(Btn1);
        this.add(Btn2);

        Btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameFrame mainFrame = new GameFrame(800);
                mainFrame.setVisible(true);
            }
        });
        Btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RuleFrame("RULE").init();
            }
        });
    }

    public static void main(String[] args) {
        new MainMenu().init();
    }
}
