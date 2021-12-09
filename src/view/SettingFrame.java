package view;

import components.ChessGridComponent;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Callable;

public class SettingFrame extends JFrame {

    public Container container=this.getContentPane();

    public SettingFrame(){
        this.setTitle("Setting");
        this.setLayout(null);
        this.container.setBackground(Color.WHITE);
        this.setBounds(300,300,380,260);
        this.setVisible(false);
        this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
    }

    public void init(){

        JLabel jLabel1 = new JLabel("Volume:");
        JLabel jLabel2 = new JLabel("Sound:");
        JLabel jLabel3 = new JLabel("Style:");

        JSlider jSlider1 = new JSlider(-60206,60206,0);
        JSlider jSlider2 = new JSlider(-60206,60206,0);

        myChangeListener myChangeListener = new myChangeListener();

        jSlider1.setBounds(60,10,300,50);
        jSlider2.setBounds(60,50,300,50);
        jSlider1.setPaintTicks(true);
        jSlider2.setPaintTicks(true);
        jSlider1.setPaintLabels(true);
        jSlider2.setPaintLabels(true);
        jSlider1.addChangeListener(myChangeListener);



        jLabel1.setBounds(5,10,60,50);
        jLabel2.setBounds(5,50,60,50);
        jLabel3.setBounds(5,90,60,50);
        jLabel1.setForeground(Color.black);
        jLabel1.setVisible(true);
        jLabel2.setVisible(true);
        jLabel3.setVisible(true);

        JComboBox styleChooser = new JComboBox<String>();
        styleChooser.addItem("Default");
        styleChooser.addItem("Sea");
        styleChooser.addItem("Grass");
        styleChooser.addItem("Flower");
        styleChooser.addItem("Paper");
        styleChooser.addItem("NullPointerException");


        styleChooser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (styleChooser.getSelectedItem().equals("Sea")){
                    ChessBoardPanel.boardStyle=BoardStyle.BLUESEA;
                }else if (styleChooser.getSelectedItem().equals("Grass")){
                    ChessBoardPanel.boardStyle=BoardStyle.GREENGRASS;
                }else if (styleChooser.getSelectedItem().equals("Flower")){
                    ChessBoardPanel.boardStyle=BoardStyle.FLOWERCLOTH;
                }else if (styleChooser.getSelectedItem().equals("Paper")){
                    ChessBoardPanel.boardStyle=BoardStyle.WHITESKETCH;
                }else if (styleChooser.getSelectedItem().equals("NullPointerException")){
                    ChessBoardPanel.boardStyle=BoardStyle.REDSCARED;
                }else {
                    ChessBoardPanel.boardStyle=BoardStyle.DEFAULT;
                }
            }
        });
        styleChooser.setBounds(65,90,290,50);


        this.container.add(styleChooser);
        this.container.add(jSlider1);
        this.container.add(jSlider2);
        this.add(jLabel2);
        this.add(jLabel1);
        this.add(jLabel3);
    }
        //维修方法
    public static void main(String[] args) {
        new SettingFrame().init();
    }

    private class myChangeListener implements ChangeListener {

        public myChangeListener() {
        }

        @Override
        public void stateChanged(ChangeEvent e) {
            JSlider slider=(JSlider)e.getSource();
            if(slider.getValue()==-60206)
                MainMenu.clip.stop();
            if(slider.getValue()!=-60206){
                MainMenu.clip.start();
            MainMenu.gainControl.setValue(slider.getValue()*0.0001f);
            }
        }
    }
}
