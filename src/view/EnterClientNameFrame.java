package view;
import Data.DataManage;
import PlayerInfo.Player;

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


    public EnterClientNameFrame(){
        this.setTitle("Enter player information");
        this.setBounds(300,180,300,250);
        this.setLayout(null);
    }

    public void init(){
        JLabel BlackPlayerLabel = new JLabel("BlackPlayer:");
        JLabel WhitePlayerLabel = new JLabel("WhitePlayer:");

        BlackPlayerLabel.setSize(80,20);
        WhitePlayerLabel.setSize(80,20);
        BlackPlayerLabel.setLocation(50,20);
        WhitePlayerLabel.setLocation(50,60);


        JTextField enter_name1 = new JTextField("Enter name");
        JTextField enter_name2 = new JTextField("Enter name");

        JButton start_game = new JButton("Start Game");
        JButton back = new JButton("Back");

        start_game.setSize(120,80);
        back.setSize(120,80);

        BlackTextField = new JTextField(10);
        WhiteTextField = new JTextField(10);

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


        this.add(BlackPlayerLabel);
        this.add(BlackTextField);
        this.add(WhitePlayerLabel);
        this.add(WhiteTextField);
        this.add(start_game);
        this.add(back);

        this.setVisible(true);

    }

    private void checkFile(String name){

            try {
                Class.forName("org.sqlite.JDBC");

                Connection connection=DataManage.getConnection();
                Statement statement=DataManage.getStatement();
                ResultSet resultSet=DataManage.getResultSet();

                String sql = "SELECT UserName FORM UserTable";

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

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
                //getallnum;
                String sql5 = "SELECT MAX (CAST(UserNumber as int)) FROM USER;";
                PreparedStatement preparedStatement1 =connection.prepareStatement(sql5);
                ResultSet resultSet1 = preparedStatement1.executeQuery();

                int num = resultSet1.getInt(1);//resultSet1.getString(1);

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

    public static void main(String[] args) {
        new EnterClientNameFrame().init();
    }

}
