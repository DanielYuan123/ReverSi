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
import java.io.IOException;
import java.sql.*;


public class EnterClientNameFrame extends JDialog {
    private Player BlackPlayer;
    private Player WhitePlayer;
    private JTextField BlackTextField;
    private JTextField WhiteTextField;
    private JLabel kiddingLabel;


    //构造器，用于设置窗口大小，布局为绝对布局；
    public EnterClientNameFrame(){
        this.setTitle("Enter player information");
        this.setBounds(300,180,300,250);
        this.setLayout(null);
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

        //设置
        BlackTextField.setSize(100,20);
        WhiteTextField.setSize(100,20);
        BlackTextField.setLocation(140,20);
        WhiteTextField.setLocation(140,60);

        start_game.setBounds(30,120,100,60);
        back.setBounds(170,120,100,60);

        MyStartBtnListener myStartBtnListener = new MyStartBtnListener(this);
        start_game.addActionListener(myStartBtnListener);

        MyBackBtnListener myBackBtnListener = new MyBackBtnListener(this);
        back.addActionListener(myBackBtnListener);

        kiddingLabel = new JLabel("You are kidding.");

        kiddingLabel.setSize(150,30);
        kiddingLabel.setLocation(100,80);
        kiddingLabel.setForeground(Color.RED);
        kiddingLabel.setVisible(false);

        this.add(kiddingLabel);
        this.add(BlackPlayerLabel);
        this.add(BlackTextField);
        this.add(WhitePlayerLabel);
        this.add(WhiteTextField);
        this.add(start_game);
        this.add(back);

        this.setVisible(true);

    }


    private class MyBackBtnListener implements ActionListener {
        private EnterClientNameFrame enterClientNameFrame;

        public MyBackBtnListener(EnterClientNameFrame enterClientNameFrame) {
            this.enterClientNameFrame = enterClientNameFrame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            enterClientNameFrame.setVisible(false);
        }
    }

    private class MyStartBtnListener implements ActionListener {

        private EnterClientNameFrame enterClientNameFrame;

        public MyStartBtnListener(EnterClientNameFrame enterClientNameFrame) {
            this.enterClientNameFrame = enterClientNameFrame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            WhitePlayer = new Player(enterClientNameFrame.WhiteTextField.getText());
            BlackPlayer = new Player(enterClientNameFrame.BlackTextField.getText());

            if(WhitePlayer.getName().equals(BlackPlayer.getName())){
                kiddingLabel.setVisible(true);
                return;
            }


            for(Player formerPlayer:Player.getPlayerList()){
                if(formerPlayer.getName().equals(WhiteTextField.getText())){
                    WhitePlayer=formerPlayer;
                }
                if(formerPlayer.getName().equals(BlackTextField.getText())){
                    BlackPlayer=formerPlayer;
                }
            }
            try {
                Class.forName("org.sqlite.JDBC");

                Statement statement = DataManage.getStatement();

                Connection connection = DriverManager.getConnection("jdbc:sqlite:lib/player.db","Daniel","123qweasd");

                String sql5 = "SELECT MAX (CAST(UserNumber as int)) FROM USER;";
                PreparedStatement preparedStatement1 =connection.prepareStatement(sql5);
                ResultSet resultSet1 = preparedStatement1.executeQuery();

                int num = resultSet1.getInt(1);

                String sql = "SELECT UserNumber FROM USER WHERE UserName = ?;";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1,WhitePlayer.getName());
                ResultSet whiteresultSet = preparedStatement.executeQuery();

                if(!whiteresultSet.next()){
                    String sql1 = "INSERT INTO USER VALUES ( ?, ?,0,0);";
                    PreparedStatement preparedStatement3 = connection.prepareStatement(sql1);
                    preparedStatement3.setString(1,WhitePlayer.getName());
                    preparedStatement3.setString(2,String.valueOf(num+1));
                    preparedStatement3.executeUpdate();
                }

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
                //get current max No.
                connection.close();

            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }


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
        }
    }

    public void pvcInit(){

        JLabel chooseColorLabel = new JLabel("Choose your color:");
        chooseColorLabel.setSize(240,80);
        chooseColorLabel.setLocation(80,0);
        chooseColorLabel.setForeground(Color.MAGENTA);

        JRadioButton radioBlackButton = new JRadioButton("Black");
        JRadioButton radioWhiteButton = new JRadioButton("White");

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
        JButton main_menu = new JButton("Main Menu");

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

    }

    private class MyStartPvcBtnListener implements ActionListener{
        private EnterClientNameFrame enterClientNameFrame;
        private MyChoosePlayerListener myChoosePlayerListener;
        private JTextField enterNameTextField;
        private JLabel tipColorLabel;
        private JLabel tipLabel;


        public MyStartPvcBtnListener(EnterClientNameFrame enterClientNameFrame,MyChoosePlayerListener myChoosePlayerListener,JTextField enterNameTextField,JLabel tipColorLabel,JLabel tipLabel){

            this.enterClientNameFrame=enterClientNameFrame;
            this.myChoosePlayerListener=myChoosePlayerListener;
            this.enterNameTextField=enterNameTextField;
            this.tipColorLabel=tipColorLabel;
            this.tipLabel=tipLabel;

        }

        @Override
        public void actionPerformed(ActionEvent e) {

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
                Class.forName("org.sqlite.JDBC");

                Connection connection = DriverManager.getConnection("jdbc:sqlite:lib/player.db","Daniel","123qweasd");
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
        }
    }


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

    public static void main(String[] args) {
        new EnterClientNameFrame().pvcInit();
    }

}
