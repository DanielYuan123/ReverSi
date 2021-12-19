package Data;

import java.sql.*;
import java.util.Scanner;

public class DataManage {
    private static String url = "jdbc:sqlite:lib/player.db";
    private static String name = "Daniel";
    private static String code = "123qweasd";
    private static Statement statement;
    private static PreparedStatement preparedStatement;
    private static ResultSet resultSet;
    
    
    public static ResultSet getResultSet() {
        return resultSet;
    }
    
    public static Statement getStatement() {
        return statement;
    }
    
    public static void main(String[] args) {
        
        try {
            Class.forName("org.sqlite.JDBC");
            
            
            String url = "jdbc:sqlite:lib/player.db";
            String name;
            String code;
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter Administer' name:");
            name = scanner.next();
            System.out.print("Enter code:");
            code = scanner.next();
            Connection connection = DriverManager.getConnection(url, name, code);
            if (name.equals("Daniel") && code.equals("123qweasd")) {
                System.out.println("Succeed in.");
                System.out.println("1 for create new Table named USER\n2 for check current USER Table\n3 for delete current USER Table");
                int UserCommand = scanner.nextInt();
    
                Statement statement = connection.createStatement();
    
                String sql = "";
                switch (UserCommand) {
                    case 1:
                        sql = "CREATE TABLE USER( UserName MESSAGE_TEXT,UserNumber INT,UserWinTime INT,UserGameTime INT);";
                        statement.executeUpdate(sql);
                        break;
                    case 2:
                        sql = "SELECT * FROM USER";
                        ResultSet resultSet = statement.executeQuery(sql);
                        while (resultSet.next()) {
                            for (int i = 1; i < 5; i++) {
                                System.out.printf("%-20s", resultSet.getString(i));
                            }
                            System.out.println();
                        }
                        break;
                    case 3:
                        sql = "DROP TABLE USER;";
                        statement.executeUpdate(sql);
                        break;
                    default:
                        System.out.println("Wrong command.");
                }
    
            } else {
                System.out.println("Error.");
                System.exit(1);
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Wrong message.");
            e.printStackTrace();
        }
    }
}
