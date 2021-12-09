package view;

import components.ChessGridComponent;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class SettingFrame extends JFrame {

    public Container container=this.getContentPane();

    public SettingFrame(){
        this.setTitle("Setting");
        this.setLayout(null);
        this.container.setBackground(Color.WHITE);
        this.setBounds(300,300,380,130);
        this.setVisible(false);
        this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
    }

    public void init(){

        JLabel jLabel1 = new JLabel("Volume:");
        JLabel jLabel2 = new JLabel("Sound:");

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
        jLabel1.setForeground(Color.black);
        jLabel1.setVisible(true);
        jLabel2.setVisible(true);


        this.container.add(jSlider1);
        this.container.add(jSlider2);
        this.add(jLabel2);
        this.add(jLabel1);

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
