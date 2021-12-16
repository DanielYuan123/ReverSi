package view;


import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RankFrame extends JFrame {
    private Container container;


    //排行榜构造器；
    public RankFrame (){
        this.setBounds(500,300,580,460);
        this.container=this.getContentPane();
        this.setLayout(null);
    }


    //排行榜初始化方法；
    public void init(){

        //new 一个用于存放滚动框的JPanel，设置其范围；
        JPanel jPanel = new JPanel();
        jPanel.setBounds(0,0,580,460);

        //声明一个行数变量，用于得到数据库输出；
        int Row=0;

        //数据库操作；
        try {
            //注册驱动，这里用的sqlite的驱动，该jar包在lib文件夹中；
            Class.forName("org.sqlite.JDBC");

            //建立连接；
            Connection connection1 = DriverManager.getConnection("jdbc:sqlite:lib/player.db","Daniel","123qweasd");

            //生成sql指令，并新建一个用于接收的结果集；该sql指令可以得到数据库当前总行数；
            Statement statement1 = connection1.createStatement();
            String sql = "SELECT COUNT(*)FROM USER;";
            ResultSet resultSet1 = statement1.executeQuery(sql);

            //若数据库不为空，将得到的总行数赋予Row变量；
            while(resultSet1.next()){
                Row = resultSet1.getInt(1);
            }
            //关闭连接；
            connection1.close();
            statement1.close();
            resultSet1.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        //新建用于构造JTable的columnName和rowValue字符串数组；
        String[] columnName = {"User name","UserNo.","User win time","User play time"};
        String[][] rowValue = new String[Row][4];

        //用新建的columnName和rowValue新建JTable；
        JTable frameTable = new JTable(rowValue,columnName);
        frameTable.setSize(560,430);

        //以frameTable为ViewPort， new 一个JScrollPane，用于滚动；
        JScrollPane RankPane = new JScrollPane(frameTable,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        frameTable.setVisible(true);

        //新建用于遍历数据库的List；
        List<String> UserInfo = new ArrayList<String>(5);

        //数据库操作；
        try {
            //注册驱动，创建连接；
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:lib/player.db","Daniel","123qweasd");

            //由连接生成用于操控数据库的statement；
            Statement statement = connection.createStatement();

            //写入sql指令；该指令用于按UserWinTime的降序得到数据库的每一行；新建接收用的结果集；
            String sql = "SELECT * FROM USER ORDER BY UserWinTime desc;";
            ResultSet resultSet = statement.executeQuery(sql);

            //当结果集不为空时，遍历；将得到的每一行放入ArrayList中；
            while(resultSet.next()){
                String name = resultSet.getString(1);
                int no = resultSet.getInt(2);
                int wintime = resultSet.getInt(3);
                int playtime = resultSet.getInt(4);
                UserInfo.add(String.format("%s %d %d %d",name,no,wintime,playtime));
            }

            //关闭连接；
            statement.close();
            resultSet.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        //将ArrayList中的每个字符串以空格为字节分开，用于得到每个人的姓名，编号，胜场和游玩次数；
        for(int i=0;i<Row;i++){
            String[] InfoSplite = UserInfo.get(i).split(" ");
            rowValue[i][0]=InfoSplite[0];
            rowValue[i][1]=InfoSplite[1];
            rowValue[i][2]=InfoSplite[2];
            rowValue[i][3]=InfoSplite[3];
        }

        //新建窗口适配器；
        MyWindowCLoseAdapter myWindowCLoseAdapter = new MyWindowCLoseAdapter(this);
        this.addWindowListener(myWindowCLoseAdapter);


        //将写好的JPanel放入container中；
        jPanel.add(RankPane);
        container.add(jPanel);

        //设置窗口为可见；
        this.setVisible(true);

    }

    //新建内部类，用于窗口关闭操作；
    private class MyWindowCLoseAdapter extends WindowAdapter{
        private RankFrame rankFrame;
        public MyWindowCLoseAdapter(RankFrame rankFrame){
            this.rankFrame=rankFrame;
        }

        public void windowClosing(WindowEvent e) {
            rankFrame.setVisible(false);
        }
    }

    //维修方法；
    public static void main(String[] args) {
        new RankFrame().init();

    }


}
