package view;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


//设置界面类；
public class SettingFrame extends JFrame {
    //获取JFrame的容器；
    public Container container = this.getContentPane();

    //无参构造，确认窗口关闭形式，背景颜色，位置与大小；
    public SettingFrame() {
        this.setTitle("Setting");
        this.setLayout(null);
        this.container.setBackground(Color.WHITE);
        this.setBounds(300, 300, 380, 260);
        this.setVisible(false);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
    }

    //初始化函数；
    public void init() {
        //new 三个JLabel用于告知用户调节的内容；
        JLabel jLabel1 = new JLabel("Volume:");
        JLabel jLabel2 = new JLabel("Sound:");
        JLabel jLabel3 = new JLabel("Style:");

        // new 两个滑动框，用于调节音量，最小值与最大值为audioclip所能调节的上下限；
        JSlider jSlider1 = new JSlider(-60206, 60206, 0);
        JSlider jSlider2 = new JSlider(-60206, 60206, 0);

        // new 一个音量调节监听器；
        myChangeListener myChangeListener = new myChangeListener();

        //设置调节框的位置和大小，添加变化监听事件；
        jSlider1.setBounds(60, 10, 300, 50);
        jSlider2.setBounds(60, 50, 300, 50);
        jSlider1.setPaintTicks(true);
        jSlider2.setPaintTicks(true);
        jSlider1.setPaintLabels(true);
        jSlider2.setPaintLabels(true);
        jSlider1.addChangeListener(myChangeListener);
        
        //设置提示JLabel的位置和大小；
        jLabel1.setBounds(5, 10, 60, 50);
        jLabel2.setBounds(5, 50, 60, 50);
        jLabel3.setBounds(5, 90, 60, 50);
        jLabel1.setForeground(Color.black);
        jLabel1.setVisible(true);
        jLabel2.setVisible(true);
        jLabel3.setVisible(true);

        //new 一个下拉框，用于选择game style；
        JComboBox styleChooser = new JComboBox<String>();
        styleChooser.addItem("Default");
        styleChooser.addItem("Sea");
        styleChooser.addItem("Grass");
        styleChooser.addItem("Flower");
        styleChooser.addItem("Paper");
        styleChooser.addItem("NullPointerException");
        styleChooser.setBounds(65, 90, 290, 50);
        
        //添加下拉框的事件监听器；
        styleChooser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //根据用户所选择的style设置GameFrame的GameStyle；
                if (styleChooser.getSelectedItem().equals("Sea")) {
                    ChessBoardPanel.boardStyle = BoardStyle.BLUESEA;
                } else if (styleChooser.getSelectedItem().equals("Grass")) {
                    ChessBoardPanel.boardStyle = BoardStyle.GREENGRASS;
                } else if (styleChooser.getSelectedItem().equals("Flower")) {
                    ChessBoardPanel.boardStyle = BoardStyle.FLOWERCLOTH;
                } else if (styleChooser.getSelectedItem().equals("Paper")) {
                    ChessBoardPanel.boardStyle = BoardStyle.WHITESKETCH;
                } else if (styleChooser.getSelectedItem().equals("NullPointerException")) {
                    ChessBoardPanel.boardStyle = BoardStyle.REDSCARED;
                } else {
                    ChessBoardPanel.boardStyle = BoardStyle.DEFAULT;
                }
            }
        });
        //添加所有组件；
        this.container.add(styleChooser);
        this.container.add(jSlider1);
        this.container.add(jSlider2);
        this.add(jLabel2);
        this.add(jLabel1);
        this.add(jLabel3);
    }
    
    //创建实现了变化监听的内部类，该类的对象用于充当音量调节框的变化监听器；
    private class myChangeListener implements ChangeListener {
        
        public myChangeListener() {
        }

        //重写状态改变方法；
        @Override
        public void stateChanged(ChangeEvent e) {
            //获得JSlider当前所在的位置，根据所在位置运用setValue方法调节声音大小；
            JSlider slider = (JSlider) e.getSource();
            if (slider.getValue() == -60206)
                MainMenu.clip.stop();
            if (slider.getValue() != -60206) {
                MainMenu.clip.start();
                MainMenu.gainControl.setValue(slider.getValue() * 0.0001f);
            }
        }
    }
    
    //维修方法
    public static void main(String[] args) {
        new SettingFrame().init();
    }
}
