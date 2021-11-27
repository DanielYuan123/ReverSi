package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.net.URL;

public class MainMenu extends JFrame {
    public MainMenu(){
        this.setTitle("GameMenu");
    }

    //初始化方法；
    public void init(){

        //获取容器；
        Container container=getContentPane();

        //设置背景颜色,边界和绝对布局
        container.setBackground( new Color(227, 192, 40, 255));
        this.setBounds(600,210,200,350);
        this.setLayout(null);

        //设置大小不可变；
        this.setResizable(false);

        //使用窗口事件的适配器，重写关闭方法，使关闭窗口时结束进程；
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("Game Quited.");
                System.exit(0);
            }
        });

        //从图片源获取图片，并将其导入至Icon类中；
        URL url=MainMenu.class.getResource("Icon.jpeg");
        ImageIcon Icon = new ImageIcon(url);
        URL url2=MainMenu.class.getResource("Titleimage.png");
        ImageIcon TitleIcon = new ImageIcon(url2);

        //获取该Icon的图片类，进行缩小适应；
        Image image=Icon.getImage();
        Image title=TitleIcon.getImage();
        image=image.getScaledInstance(80,80,Image.SCALE_AREA_AVERAGING);
        title=title.getScaledInstance(100,29,image.SCALE_AREA_AVERAGING);

        //将图标设置为修改后的图像；
        Icon=new ImageIcon(image);
        TitleIcon.setImage(title);

        //创建Icon承载体IconPanel，其类型为JLabel；
        JLabel IconPanel = new JLabel(Icon);
        JLabel TitlePanel = new JLabel(TitleIcon);
        TitlePanel.setLocation(50,100);
        TitlePanel.setSize(100,29);
        IconPanel.setSize(80,80);
        IconPanel.setLocation(60,10);
        IconPanel.setVisible(true);
        TitlePanel.setVisible(true);


        //获取JFrame容器，将Icon面板放入；
        container=getContentPane();
        container.add(IconPanel);
        container.add(TitlePanel);

        //new两个按钮，分别用来启动游戏和查看规则；
        JButton Btn1 = new JButton("Start Game");
        JButton Btn2 = new JButton("Rule");

        //设置两个按钮范围，添加按钮；
        Btn1.setBounds(50,140,100,40);
        Btn2.setBounds(50,190,100,40);
        Btn1.setBackground(Color.gray);
        Btn2.setBackground(Color.gray);
        this.add(Btn1);
        this.add(Btn2);

        //
        Btn1.setToolTipText("Start the game whose mode is what you choose.");
        Btn2.setToolTipText("To know how to play.");

        /*
        按钮一（开始游戏）的事件监听器；运用匿名类实现ActionListen中的方法，使按钮
        被激活后打开游戏窗口；
         */
        Btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameFrame mainFrame = new GameFrame(800);
                mainFrame.setVisible(true);
            }
        });

        /*
        完成按钮二（帮助）的事件监听，新建JFrame名为Rule，执行其初始化操作；
        */
        Btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RuleFrame("RULE").init();
            }
        });

        //设置MainMenu的可见性为true；
        this.setVisible(true);
    }
    //维修类；
    public static void main(String[] args) {
        new MainMenu().init();
    }
}
