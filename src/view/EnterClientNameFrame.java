package view;

import Data.DataManage;
import PlayerInfo.Player;
import model.ChessPiece;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.*;

public class EnterClientNameFrame extends JDialog {
    private Player BlackPlayer;
    private Player WhitePlayer;
    private JTextField BlackTextField;
    private JTextField WhiteTextField;
    private JLabel kiddingLabel;
    private MyWindowCloseListener myWindowCloseListener = new MyWindowCloseListener(this);
    public static boolean EnterFrameIsOn = false;


    //构造器，用于设置窗口大小，布局为绝对布局；
    public EnterClientNameFrame(){
        this.setTitle("Enter player information");
        this.setBounds(300,180,300,250);
        this.setLayout(null);
        this.addWindowListener(myWindowCloseListener);
        this.setResizable(false);
    }

    //初始化方法；在new后调用；
    public void init(){

        //新建两个JLabel用于提醒用户填写对应玩家姓名；
        JLabel BlackPlayerLabel = new JLabel("BlackPlayer:");
        JLabel WhitePlayerLabel = new JLabel("WhitePlayer:");

        //设置Label的大小和位置；
        BlackPlayerLabel.setSize(80,20);
        WhitePlayerLabel.setSize(80,20);
        BlackPlayerLabel.setLocation(50,20);
        WhitePlayerLabel.setLocation(50,60);

        //new 两个按钮，分别用来开始游戏与返回主菜单；
        JButton start_game = new JButton("Start Game");
        JButton back = new JButton("Back");

        //设置按钮大小；
        start_game.setSize(120,80);
        back.setSize(120,80);

        //添加两个文本框，用于输入姓名；
        BlackTextField = new JTextField("NO SPACE",10);
        WhiteTextField = new JTextField("NO SPACE",10);

        BlackTextField.setSize(100,20);
        WhiteTextField.setSize(100,20);
        BlackTextField.setLocation(140,20);
        WhiteTextField.setLocation(140,60);

        start_game.setBounds(30,120,100,60);
        back.setBounds(170,120,100,60);

        //添加startBtn的事件监听；
        MyStartBtnListener myStartBtnListener = new MyStartBtnListener(this);
        start_game.addActionListener(myStartBtnListener);

        //添加backBtn的事件监听；
        MyBackBtnListener myBackBtnListener = new MyBackBtnListener(this);
        back.addActionListener(myBackBtnListener);

        //添加提示JLabel用于提醒用户不能输入相同用户名；
        kiddingLabel = new JLabel("You are kidding.");
        kiddingLabel.setSize(150,30);
        kiddingLabel.setLocation(100,80);
        kiddingLabel.setForeground(Color.RED);
        kiddingLabel.setVisible(false);

        //添加所有swing组件；
        this.add(kiddingLabel);
        this.add(BlackPlayerLabel);
        this.add(BlackTextField);
        this.add(WhitePlayerLabel);
        this.add(WhiteTextField);
        this.add(start_game);
        this.add(back);

        //设置可见性为true；
        this.setVisible(true);
        EnterClientNameFrame.EnterFrameIsOn=true;
    }




    //若选择的游戏模式为PVC，则启用pvc的初始化方法；
    public void pvcInit(){

        JLabel chooseColorLabel = new JLabel("Choose your color:");
        chooseColorLabel.setSize(240,80);
        chooseColorLabel.setLocation(80,0);
        chooseColorLabel.setForeground(Color.MAGENTA);

        JRadioButton radioBlackButton = new JRadioButton("Black");
        JRadioButton radioWhiteButton = new JRadioButton("White");

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(radioBlackButton);
        buttonGroup.add(radioWhiteButton);

        radioBlackButton.setBounds(40,60,90,20);
        radioWhiteButton.setBounds(170,60,90,20);

        JLabel tipNameLabel = new JLabel("Your name:");
        tipNameLabel.setBounds(20,120,80,20);

        JTextField enterNameTextField = new JTextField("");
        enterNameTextField.setBounds(100,120,180,20);

        MyChoosePlayerListener myChoosePlayerListener = new MyChoosePlayerListener();

        radioBlackButton.addActionListener(myChoosePlayerListener);
        radioWhiteButton.addActionListener(myChoosePlayerListener);

        JButton start_game = new JButton("Start Game");
        JButton main_menu = new JButton("Back");

        JLabel tipLabel = new JLabel("Enter your name!");
        tipLabel.setBounds(80,80,200,60);
        tipLabel.setForeground(Color.RED);
        tipLabel.setVisible(false);

        JLabel tipColorLabel = new JLabel("Choose your color!");
        tipLabel.setBounds(80,80,200,60);
        tipLabel.setForeground(Color.RED);
        tipLabel.setVisible(false);

        start_game.setBounds(20,150,100,50);
        main_menu.setBounds(180,150,100,50);

        MyBackBtnListener myBackBtnListener = new MyBackBtnListener(this);
        main_menu.addActionListener(myBackBtnListener);

        MyStartPvcBtnListener myStartPvcBtnListener = new MyStartPvcBtnListener(this,myChoosePlayerListener,enterNameTextField,tipColorLabel,tipLabel);
        start_game.addActionListener(myStartPvcBtnListener);

        this.add(start_game);
        this.add(main_menu);
        this.add(chooseColorLabel);
        this.add(radioBlackButton);
        this.add(radioWhiteButton);
        this.add(tipNameLabel);
        this.add(enterNameTextField);
        this.add(tipLabel);
        this.add(tipColorLabel);

        this.setVisible(true);
        EnterClientNameFrame.EnterFrameIsOn=true;
    }

    //编写PVC特殊的开始按钮事件监听；
    private class MyStartPvcBtnListener implements ActionListener{
        private EnterClientNameFrame enterClientNameFrame;
        private MyChoosePlayerListener myChoosePlayerListener;
        private JTextField enterNameTextField;
        private JLabel tipColorLabel;
        private JLabel tipLabel;

        //获取当前的用户框架等对象；
        public MyStartPvcBtnListener(EnterClientNameFrame enterClientNameFrame,MyChoosePlayerListener myChoosePlayerListener,JTextField enterNameTextField,JLabel tipColorLabel,JLabel tipLabel){

            this.enterClientNameFrame=enterClientNameFrame;
            this.myChoosePlayerListener=myChoosePlayerListener;
            this.enterNameTextField=enterNameTextField;
            this.tipColorLabel=tipColorLabel;
            this.tipLabel=tipLabel;

        }

        @Override
        public void actionPerformed(ActionEvent e) {

            //根据选择的颜色，创建对应的Player对象；
            if(myChoosePlayerListener.Player.equals("White")){
                WhitePlayer = new Player(enterNameTextField.getText());
            }else if(myChoosePlayerListener.Player.equals("Black")){
                BlackPlayer = new Player(enterNameTextField.getText());
            }else{
                tipColorLabel.setVisible(true);
            }

            if(enterNameTextField.getText()==null){
                tipLabel.setVisible(true);
                return;
            }


            try {
                //注册驱动，创建连接；
                Class.forName("org.sqlite.JDBC");
                Connection connection = DriverManager.getConnection("jdbc:sqlite:lib/player.db","Daniel","123qweasd");

                //查重方法参见PVP模式；
                String sql1 = "SELECT MAX (CAST(UserNumber as int)) FROM USER;";
                PreparedStatement preparedStatement = connection.prepareStatement(sql1);
                ResultSet resultSet = preparedStatement.executeQuery();

                int Index = 0;

                while(resultSet.next()){
                    Index = resultSet.getInt(1);
                }

                String sql = "SELECT UserNumber FROM USER WHERE UserName = ?;";
                PreparedStatement preparedStatement1 = connection.prepareStatement(sql);
                preparedStatement1.setString(1,enterNameTextField.getText());
                ResultSet nameResultSet = preparedStatement1.executeQuery();

                if(!nameResultSet.next()){
                    if(myChoosePlayerListener.Player.equals("White")){
                        String sql2 = "INSERT INTO USER VALUES ( ?, ?, 0, 0);";
                        PreparedStatement preparedStatement3 = connection.prepareStatement(sql2);
                        preparedStatement3.setString(1,WhitePlayer.getName());
                        preparedStatement3.setString(2,String.valueOf(Index+1));
                        preparedStatement3.executeUpdate();
                    }else if(myChoosePlayerListener.Player.equals("Black")){
                        String sql2 = "INSERT INTO USER VALUES ( ?, ?, 0, 0);";
                        PreparedStatement preparedStatement3 = connection.prepareStatement(sql2);
                        preparedStatement3.setString(1,BlackPlayer.getName());
                        preparedStatement3.setString(2,String.valueOf(Index+1));
                        preparedStatement3.executeUpdate();
                    }
                }

                connection.close();

            } catch (ClassNotFoundException | SQLException ex) {
                ex.printStackTrace();
            }


            if(myChoosePlayerListener.Player.equals("White")){
                WhitePlayer = new Player(enterNameTextField.getText());
                GameFrame.AIModeIsOn=true;
                try {
                    new GameFrame(800,null,WhitePlayer);
                    GameFrame.controller.setPvcPlayer(ChessPiece.WHITE);
                    enterClientNameFrame.setVisible(false);

                } catch (UnsupportedAudioFileException ex) {
                    ex.printStackTrace();
                } catch (LineUnavailableException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }


            }else if(myChoosePlayerListener.Player.equals("Black")){
                BlackPlayer = new Player(enterNameTextField.getText());
                GameFrame.AIModeIsOn=true;

                try {
                    new GameFrame(800,BlackPlayer,null);
                    GameFrame.controller.setPvcPlayer(ChessPiece.BLACK);
                    enterClientNameFrame.setVisible(false);
                } catch (UnsupportedAudioFileException ex) {
                    ex.printStackTrace();
                } catch (LineUnavailableException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
            EnterClientNameFrame.EnterFrameIsOn=false;
        }
    }

    //编写PVC所需的颜色选择监听类；
    private class MyChoosePlayerListener implements ActionListener{
        private String Player = null;

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equals("Black")){
                this.Player="Black";
            } else if (e.getActionCommand().equals("White")){
                this.Player="White";
            }
        }
    }


    //编写窗口关闭类；
    private class MyWindowCloseListener extends WindowAdapter{
        EnterClientNameFrame enterClientNameFrame;
        public MyWindowCloseListener(EnterClientNameFrame enterClientNameFrame){
            this.enterClientNameFrame=enterClientNameFrame;
        }

        @Override
        public void windowClosing(WindowEvent e) {
            enterClientNameFrame.setVisible(false);
            EnterClientNameFrame.EnterFrameIsOn=false;
        }
    }

    //新建开始游戏的内部类；
    private class MyStartBtnListener implements ActionListener {
        private EnterClientNameFrame enterClientNameFrame;
        public MyStartBtnListener(EnterClientNameFrame enterClientNameFrame) {
            this.enterClientNameFrame = enterClientNameFrame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            //获得两个输入框中的名字；
            WhitePlayer = new Player(enterClientNameFrame.WhiteTextField.getText());
            BlackPlayer = new Player(enterClientNameFrame.BlackTextField.getText());

            //如果名字相同，弹出提示框；
            if(WhitePlayer.getName().equals(BlackPlayer.getName())){
                kiddingLabel.setVisible(true);
                return;
            }

            try {

                //注册sqlite驱动，创立JDBC连接；
                Class.forName("org.sqlite.JDBC");
                Statement statement = DataManage.getStatement();
                Connection connection = DriverManager.getConnection("jdbc:sqlite:lib/player.db","Daniel","123qweasd");

                //sql语句，用于获得总行数；
                String sql5 = "SELECT MAX (CAST(UserNumber as int)) FROM USER;";
                PreparedStatement preparedStatement1 =connection.prepareStatement(sql5);
                ResultSet resultSet1 = preparedStatement1.executeQuery();
                int num = resultSet1.getInt(1);

                //查找已有名单中是否有被输入的姓名；
                String sql = "SELECT UserNumber FROM USER WHERE UserName = ?;";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1,WhitePlayer.getName());
                ResultSet whiteresultSet = preparedStatement.executeQuery();

                //如果结果集为空（未找到该名字），新建用户；
                if(!whiteresultSet.next()){
                    String sql1 = "INSERT INTO USER VALUES ( ?, ?,0,0);";
                    PreparedStatement preparedStatement3 = connection.prepareStatement(sql1);
                    preparedStatement3.setString(1,WhitePlayer.getName());
                    preparedStatement3.setString(2,String.valueOf(num+1));
                    preparedStatement3.executeUpdate();
                }

                //重复上述操作，录入两人信息；
                String sql3 = "SELECT MAX (CAST(UserNumber as int)) FROM USER;";
                PreparedStatement preparedStatement4 = connection.prepareStatement(sql3);
                ResultSet resultSet2 = preparedStatement4.executeQuery();

                num = resultSet2.getInt(1);


                String sql4 = "SELECT UserNumber FROM USER WHERE UserName = ?;";
                PreparedStatement preparedStatement2 = connection.prepareStatement(sql4);
                preparedStatement2.setString(1,BlackPlayer.getName());
                ResultSet blackesultSet = preparedStatement2.executeQuery();

                if(!blackesultSet.next()){
                    String sql2 = "INSERT INTO USER VALUES (?,?,0,0);";
                    PreparedStatement preparedStatement5 = connection.prepareStatement(sql2);
                    preparedStatement5.setString(1,BlackPlayer.getName());
                    preparedStatement5.setString(2,String.valueOf(num+1));
                    preparedStatement5.executeUpdate();
                }
                connection.close();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            //新建GameFrame游戏框架；
            try {
                new GameFrame(800,BlackPlayer,WhitePlayer).initializePlayers(WhitePlayer,BlackPlayer);
            } catch (UnsupportedAudioFileException ex) {
                ex.printStackTrace();
            } catch (LineUnavailableException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            enterClientNameFrame.setVisible(false);
            EnterClientNameFrame.EnterFrameIsOn=false;
        }
    }

    //编写返回按钮的事件监听类；
    private class MyBackBtnListener implements ActionListener {
        private EnterClientNameFrame enterClientNameFrame;
        public MyBackBtnListener(EnterClientNameFrame enterClientNameFrame) {
            this.enterClientNameFrame = enterClientNameFrame;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            enterClientNameFrame.setVisible(false);
            EnterClientNameFrame.EnterFrameIsOn=false;
        }
    }

    //维修函数；
    public static void main(String[] args) {
        new EnterClientNameFrame().pvcInit();
    }
}
