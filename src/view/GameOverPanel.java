package view;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class GameOverPanel extends JPanel {

    private JLabel resultLabel;

    private GameFrame gameFrame;

    private ImageIcon blackImageIcon = new ImageIcon("Image/blackPiece.png");

    private ImageIcon whiteImageIcon = new ImageIcon("Image/whitePiece.png");



    public GameOverPanel(GameFrame gameFrame)  {

        this.setLayout(null);
        this.setBackground(Color.white);

        this.setSize(800,800);
        this.gameFrame=gameFrame;

        JButton restartButton = new JButton("Restart");
        JButton mainMenuButton = new JButton("Main menu");

        JLabel titleLabel = new JLabel();

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
                try {
                    new GameFrame(800);
                } catch (UnsupportedAudioFileException ex) {
                    ex.printStackTrace();
                } catch (LineUnavailableException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        Image blackImage = blackImageIcon.getImage();

        blackImage = blackImage.getScaledInstance(158,150,Image.SCALE_AREA_AVERAGING);

        blackImageIcon = new ImageIcon(blackImage);

        Image whiteImage = whiteImageIcon.getImage();

        whiteImage = whiteImage.getScaledInstance(165,150,Image.SCALE_AREA_AVERAGING);

        whiteImageIcon = new ImageIcon(whiteImage);

        mainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                GameFrame.getBoardPanelsList().clear();
                GameFrame.stepNum=0;

                gameFrame.setVisible(false);
            }
        });

        //4.8177

        titleLabel.setBounds(160,0,482,100);


        ImageIcon imageIcon=new ImageIcon("Image/img.png");

        Image image = imageIcon.getImage();

        image = image.getScaledInstance(482,100,Image.SCALE_AREA_AVERAGING);

        imageIcon = new ImageIcon(image);

        titleLabel.setIcon(imageIcon);

        this.setBackground(new Color(201,233,202));
        this.add(titleLabel);
        this.add(restartButton);
        this.add(mainMenuButton);

        this.setVisible(false);
    }

    public void init(){
        JLabel resultLabel = new JLabel();

        if(GameFrame.controller.getBlackScore()>GameFrame.controller.getWhiteScore()){
            resultLabel.setBounds(321,150,158,150);
            resultLabel.setIcon(blackImageIcon);

        }
        else if (GameFrame.controller.getBlackScore()<GameFrame.controller.getWhiteScore()){
            resultLabel.setBounds(318,150,165,150);
            resultLabel.setIcon(whiteImageIcon);
        }
        else {
            resultLabel.setFont(new Font("Black",Font.ITALIC,35));
            resultLabel.setText("Nobody");
            resultLabel.setForeground(Color.blue);
        }

        this.add(resultLabel);
        this.setVisible(true);

        gameFrame.clearAllBtn();

    }

    @Override
    public void print(Graphics g) {

            g.setColor(Color.WHITE);

            g.fillOval(365,150,50,50);

    }

    public static void main(String[] args) {

    }
}
