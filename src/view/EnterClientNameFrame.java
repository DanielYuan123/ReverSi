package view;
import PlayerInfo.Player;

import javax.swing.*;
import java.awt.*;
import java.sql.*;


public class EnterClientNameFrame extends JDialog {
    private Player BlackPlayer;
    private Player WhitePlayer;


    public EnterClientNameFrame(Frame frame){
        this.setTitle("Enter player information");
        this.setBounds(300,180,300,300);
        this.setLayout(new FlowLayout());
    }

    private void init(){
        JLabel BlackPlayerLabel = new JLabel("BlackPlayer:");
        JLabel WhitePlayerLabel = new JLabel("WhitePlayer:");

        BlackPlayerLabel.setSize(50,100);
        WhitePlayerLabel.setSize(50,100);

        JTextField enter_name1 = new JTextField("Enter name");
        JTextField enter_name2 = new JTextField("Enter name");

        JButton start_game = new JButton("Start Game");
        JButton back = new JButton("Back");

        start_game.setSize(120,80);
        back.setSize(120,80);


    }

    public static void main(String[] args) {
        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }

}
