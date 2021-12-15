package view;


import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

public class RankFrame extends JFrame {
    private Container container;

    public RankFrame (){
        this.setBounds(500,300,580,250);
        this.container=this.getContentPane();
        this.setLayout(null);
    }



    public void init(){
        //RankPanel.setBounds(10,50,480,440);

        JPanel jPanel = new JPanel();
        jPanel.setBounds(0,0,580,460);


        DefaultListModel UserModel = new DefaultListModel();

        Vector<String> UserInfo = new Vector<>();

        JList<String> RankList = new JList<String>(UserInfo);
        RankList.setVisibleRowCount(12);
        JScrollPane RankPane = new JScrollPane(RankList,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        RankList.setVisible(true);


        UserInfo.add(String.format("%-20s %-10s %-10s %-10s","User name","UserNo.","User win time","User play time"));

        try {
            Class.forName("org.sqlite.JDBC");

            Connection connection = DriverManager.getConnection("jdbc:sqlite:lib/player.db","Daniel","123qweasd");

            Statement statement = connection.createStatement();

            String sql = "SELECT * FROM USER ORDER BY UserWinTime desc;";

            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()){
                String name = resultSet.getString(1);
                int no = resultSet.getInt(2);
                int wintime = resultSet.getInt(3);
                int playtime = resultSet.getInt(4);
                UserInfo.add(String.format("%-20s %-10d %-10d %-10d",name,no,wintime,playtime));
            }

            statement.close();
            resultSet.close();
            connection.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        RankList.setListData(UserInfo);

        MyWindowCLoseAdapter myWindowCLoseAdapter = new MyWindowCLoseAdapter(this);

        this.addWindowListener(myWindowCLoseAdapter);

        //container.add(RankList,BorderLayout.CENTER);
        jPanel.add(RankPane);
        container.add(jPanel);

        //container.add(RankPane,BorderLayout.EAST);

        this.setVisible(true);

    }

    private class MyWindowCLoseAdapter extends WindowAdapter{
        private RankFrame rankFrame;
        public MyWindowCLoseAdapter(RankFrame rankFrame){
            this.rankFrame=rankFrame;
        }

        public void windowClosing(WindowEvent e) {
            rankFrame.setVisible(false);
        }
    }

    public static void main(String[] args) {
        new RankFrame().init();

    }


}
