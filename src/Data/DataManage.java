package Data;
import javax.sql.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.*;

public class DataManage {
    private static String url = "jdbc:sqlite:lib/player.db";
    private static String name = "Daniel";
    private static String code = "123qweasd";
    private static Connection connection;
    private static Statement statement;

    static {
        try {
            connection = DriverManager.getConnection(url, name, code);
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static PreparedStatement preparedStatement;
    private static ResultSet resultSet;


    public static Connection getConnection(){
        return connection;
    }

    public static ResultSet getResultSet(){
        return resultSet;
    }

    public static Statement getStatement(){
        return statement;
    }

    public static void main(String[] args) {
        File file=new File("");
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            Class.forName("org.sqlite.JDBC");


            String url = "jdbc:sqlite:lib/player.db";
            String name = "Daniel";
            String code = "123qweasd";
            connection=DriverManager.getConnection(url,name,code);

            /*String sql = "CREATE TABLE USER " +
                    "( UserName MESSAGE_TEXT," +
                    "UserNumber INT," +
                    "UserWinTime INT," +
                    "UserGameTime INT);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            Statement statement= connection.createStatement();
            preparedStatement.executeUpdate();*/

            /*String sql1 = "INSERT INTO USER (UserName,UserNumber,UserWinTime,UserGameTime) VALUES ('Daniel',90,2,3);";//INSERT INTO USER (UserName,UserNumber,UserWinTime,UserGameTime) VALUES ('Daniel',90,2,3);
            PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
            preparedStatement1.executeUpdate();
            statement.close();
            connection.close();*/

            //SELECT * FROM USER;

            //DROP TABLE USER;

            //CREATE TABLE USER( UserName MESSAGE_TEXT,UserNumber INT,UserWinTime INT,UserGameTime INT);



            Statement statement = connection.createStatement();
            //String sql = "SELECT MAX(CAST(UserNumber as int)) FROM USER;";
            String sql = "CREATE TABLE USER( UserName MESSAGE_TEXT,UserNumber INT,UserWinTime INT,UserGameTime INT);";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                for (int i = 1; i < 5; i++) {
                    System.out.print(resultSet.getString(i)+"\t");
                }
                System.out.println();
            }

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Wrong");
            e.printStackTrace();
        }

    }
}
