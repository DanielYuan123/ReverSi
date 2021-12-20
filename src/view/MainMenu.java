package view;

import javax.swing.*;
import javax.sound.sampled.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.File;

public class MainMenu extends JFrame {
    
    private SettingFrame settingFrame = new SettingFrame();
    
    private static JLabel HintChooseMode;
    private static JLabel HintGmaeStarted;
    public static AudioInputStream Mainstream;
    public static Clip clip;
    public static FloatControl gainControl;
    
    
    //获取容器；
    private Container container = this.getContentPane();
    
    //构造器，设置标题和关闭窗口；
    public MainMenu() {
        this.setTitle("GameMenu");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.settingFrame.init();
    }
    
    //初始化方法；
    public void init() {
    
        ImageIcon FrameIcon = new ImageIcon("Image/棋盘图标.png");
        this.setIconImage(FrameIcon.getImage());
    
        //设置背景颜色,边界和绝对布局
        container.setBackground(new Color(77, 218, 100));
        this.setBounds(600, 210, 200, 350);
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
        ImageIcon Icon = new ImageIcon("Image/Icon.jpeg");
        ImageIcon TitleIcon = new ImageIcon("Image/Titleimage.png");
    
        //获取该Icon的图片类，进行缩小适应；
        Image image = Icon.getImage();
        Image title = TitleIcon.getImage();
        image = image.getScaledInstance(80, 80, Image.SCALE_AREA_AVERAGING);
        title = title.getScaledInstance(100, 29, image.SCALE_AREA_AVERAGING);
    
        //将图标设置为修改后的图像；
        Icon = new ImageIcon(image);
        TitleIcon.setImage(title);
    
        //创建Icon承载体IconPanel，其类型为JLabel；
        JLabel IconPanel = new JLabel(Icon);
        JLabel TitlePanel = new JLabel(TitleIcon);
        TitlePanel.setLocation(50, 100);
        TitlePanel.setSize(100, 29);
        IconPanel.setSize(80, 80);
        IconPanel.setLocation(60, 10);
        IconPanel.setVisible(true);
        TitlePanel.setVisible(true);
    
    
        //将Icon面板放入；
        container.add(IconPanel);
        container.add(TitlePanel);
    
        //new三个按钮，分别用来启动游戏和查看规则以及调整音量；
        JButton Btn1 = new JButton("Start Game");
        JButton Btn2 = new JButton("Rule");
        JButton Btn3 = new JButton("Setting");
    
        //导入用于排行榜图片按钮的图片，调整其大小；
        ImageIcon rankBtnIcon = new ImageIcon("Image/RankFrameIcon.png");
        Image RankImage = rankBtnIcon.getImage();
        RankImage = RankImage.getScaledInstance(30, 30, Image.SCALE_AREA_AVERAGING);
        rankBtnIcon = new ImageIcon(RankImage);
    
        //new 一个排行榜图标按钮，设置其范围；
        JButton rankFrameBtn = new JButton(rankBtnIcon);
        rankFrameBtn.setVisible(true);
        rankFrameBtn.setBounds(160, 10, 30, 30);
        rankFrameBtn.setToolTipText("Check your rank here.");
    
        //为排行榜按钮写一个事件监听器，用户触发时代开RankFrame类并触发其初始化方法；
        rankFrameBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RankFrame().init();
            }
        });
    
        //将排行榜按钮添加至主菜单容器；
        container.add(rankFrameBtn);
    
    
        //new两个单选框，用于调整游戏模式；
        JRadioButton pvp = new JRadioButton("PVP");
        JRadioButton pvc = new JRadioButton("PVC");
    
        //new 一个自制监听器；
        Mylistener mylistener = new Mylistener(this);
    
        //完成单选框的事件监听；
        pvp.addActionListener(mylistener);
        pvc.addActionListener(mylistener);
    
        //new 一个提示框，用于提醒用户进行模式选取；
        MainMenu.HintChooseMode = new JLabel("*请选择游戏模式！");
        HintChooseMode.setVisible(false);
        HintChooseMode.setBounds(45, 210, 120, 20);
        container.add(HintChooseMode);
    
        //new 一个提示框，用于提醒用户游戏已经开始了；
        MainMenu.HintGmaeStarted = new JLabel("*游戏已经开始了！");
        HintGmaeStarted.setVisible(false);
        HintGmaeStarted.setBounds(45, 210, 120, 20);
        container.add(HintGmaeStarted);
    
        //设置单选框大小；
        pvp.setBounds(40, 190, 60, 20);
        pvc.setBounds(100, 190, 60, 20);
    
        //设置按钮组；
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(pvp);
        buttonGroup.add(pvc);
    
        //container添加单选框；
        container.add(pvp);
        container.add(pvc);
    
        //设置两个按钮范围，添加按钮；
        Btn1.setBounds(50, 140, 100, 40);
        Btn2.setBounds(50, 230, 100, 40);
        Btn3.setBounds(50, 270, 100, 40);
        Btn1.setBackground(Color.white);
        Btn2.setBackground(Color.white);
        Btn3.setBackground(Color.white);
        this.add(Btn1);
        this.add(Btn2);
        this.add(Btn3);
    
        //设置提示框；
        Btn1.setToolTipText("Start the game whose mode is what you choose.");
        Btn2.setToolTipText("To know how to play.");
        Btn3.setToolTipText("Set the volume of background music and sound.");

        /*
        按钮一（开始游戏）的事件监听器；运用匿名类实现ActionListen中的方法，使按钮
        被激活后打开游戏窗口；
         */
        Btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainMenu.HintChooseMode.setVisible(false);
                if (mylistener.Gamemode == 1) {
                    if (GameFrame.stepNum == 0 && !EnterClientNameFrame.EnterFrameIsOn) {
                        GameFrame.AIModeIsOn=false;
                        new EnterClientNameFrame().init();
                    } else {
                        MainMenu.HintGmaeStarted.setVisible(true);
                    }
                } else if (mylistener.Gamemode == -1) {
                    if (GameFrame.stepNum == 0 && !EnterClientNameFrame.EnterFrameIsOn){
                        GameFrame.AIModeIsOn=true;
                        new EnterClientNameFrame().pvcInit();
                    }
                    else {
                        MainMenu.HintGmaeStarted.setVisible(true);
                    }
                } else {
                    System.out.println("Please choose your gamemode.");
                    HintChooseMode.setVisible(true);
                }
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
    
        //完成按钮三事件监听；
        Btn3.addActionListener(mylistener);
    
        try {
            this.setMusic1();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    
        //播放音乐；（在playmusic函数中，输入参数为-1停止播放,为1开始播放）
        this.PlayMusic();
    
        //设置MainMenu的可见性为true；
        this.setVisible(true);
    }
    
    //设置音乐的方法；
    void setMusic1() throws IOException, LineUnavailableException, UnsupportedAudioFileException {
    
        this.Mainstream = AudioSystem.getAudioInputStream(new File("Music/1-05 Un Jour De Juillet.wav"));
        this.clip = AudioSystem.getClip();
        this.clip.open(this.Mainstream);
        this.gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
    
    }
    
    //播放音乐的方法；
    void PlayMusic() {
        this.clip.start();
        this.clip.loop(5000);
    }
    
    //维修函数；
    public static void main(String[] args) {
        new MainMenu().init();
    }
    
    
    //新建一个自制监听器的内部类；
    private class Mylistener implements ActionListener {
        private int Gamemode = 0;
        private MainMenu mainMenu;
        
        public Mylistener(MainMenu mainMenu) {
            this.mainMenu = mainMenu;
        }
        
        public int getGamemode() {
            return Gamemode;
        }
        
        //重写事件监听方法；
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("PVP")) {
                this.Gamemode = 1;
            } else if (e.getActionCommand().equals("PVC")) {
                this.Gamemode = -1;
            } else if (e.getActionCommand().equals("Setting")) {
                this.mainMenu.settingFrame.setVisible(true);
            }
        }
    }
}