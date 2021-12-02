package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameOverPanel extends Panel {

    private JLabel resultLabel;

    private GameFrame gameFrame;

    public GameOverPanel(GameFrame gameFrame){

        this.setLayout(null);
        this.setBackground(Color.white);

        this.setSize(800,800);
        this.gameFrame=gameFrame;

        JButton restartButton = new JButton("Restart");
        JButton mainMenuButton = new JButton("Main menu");

        JLabel titleLabel = new JLabel("The Winner is:");

        restartButton.setBounds(100,600,250,100);
        mainMenuButton.setBounds(450,600,250,100);
        restartButton.setFont(new Font("nano",Font.ROMAN_BASELINE,20));
        mainMenuButton.setFont(new Font("yuku",Font.ROMAN_BASELINE,20));
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameFrame.setVisible(false);
                GameFrame.getBoardPanelsList().clear();
                GameFrame.stepNum=0;
                new GameFrame(800);
            }
        });

        mainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                GameFrame.getBoardPanelsList().clear();
                GameFrame.stepNum=0;

                gameFrame.setVisible(false);
            }
        });
        titleLabel.setBounds(200,50,800,100);
        titleLabel.setFont(new Font("Daniel",Font.ROMAN_BASELINE,40));
        titleLabel.setForeground(new Color(246, 88, 88));


        this.setBackground(Color.gray);
        this.add(titleLabel);
        this.add(restartButton);
        this.add(mainMenuButton);

        this.setVisible(false);
    }

    public void init(){
        JLabel resultLabel = new JLabel();
        resultLabel.setBounds(365,150,100,100);
        if(GameFrame.controller.getBlackScore()>GameFrame.controller.getWhiteScore()){
            resultLabel.setText("Black");
            resultLabel.setFont(new Font("Black",Font.ITALIC,35));
            resultLabel.setForeground(Color.BLACK);

        }
        else if (GameFrame.controller.getBlackScore()<GameFrame.controller.getWhiteScore()){
            resultLabel.setText("White");
            resultLabel.setFont(new Font("White",Font.ITALIC,35));
            resultLabel.setForeground(Color.WHITE);
        }
        else {
            resultLabel.setFont(new Font("Black",Font.ITALIC,35));
            resultLabel.setText("Nobody");
            resultLabel.setForeground(Color.blue);
        }

        this.add(resultLabel);
        this.setVisible(true);

    }
//GameFrame.controller.getBlackScore()>GameFrame.controller.getWhiteScore()
    @Override
    public void print(Graphics g) {
        super.print(g);
        if(true){

            g.setColor(Color.WHITE);
            g.fillOval(365,150,50,50);

        }
    }

    public static void main(String[] args) {

    }
}
