package view;

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
import java.util.Collections;

public class GameOverPanel extends JPanel {
    
    private JLabel resultLabel;
    
    private GameFrame gameFrame;
    
    private ImageIcon blackImageIcon = new ImageIcon("Image/blackPiece.png");
    
    private ImageIcon whiteImageIcon = new ImageIcon("Image/whitePiece.png");

    private ImageIcon drawImageIcon = new ImageIcon("Image/Draw.png");
    
    //有参构造，获得当前游戏GameFrame；
    public GameOverPanel(GameFrame gameFrame) {
        //设置布局与背景；
        this.setLayout(null);
        this.setBackground(Color.white);

        //设置大小与GameFrame属性；
        this.setSize(800, 800);
        this.gameFrame = gameFrame;

        //添加两个按钮，用于重开游戏和返回主菜单；
        JButton restartButton = new JButton("Restart");
        JButton mainMenuButton = new JButton("Main menu");

        //新建提示Label，告知玩家胜利方；
        JLabel titleLabel = new JLabel();

        //设置按钮的范围和字号；
        restartButton.setBounds(100, 600, 250, 100);
        mainMenuButton.setBounds(450, 600, 250, 100);
        restartButton.setFont(new Font("nano", Font.ROMAN_BASELINE, 20));
        mainMenuButton.setFont(new Font("yuku", Font.ROMAN_BASELINE, 20));
        
        //设置restartBtn的事件监听，用于关闭当前GameFrame，还原GameFrame中的静态参数；
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameFrame.setVisible(false);
                ChessPiece people = GameFrame.controller.getPvcPlayer();
                GameFrame.getBoardPanelsList().clear();
                GameFrame.stepNum = 0;
                try {
                    new GameFrame(800, GameFrame.controller.getBlackPlayer(), GameFrame.controller.getWhitePlayer());
                } catch (UnsupportedAudioFileException ex) {
                    ex.printStackTrace();
                } catch (LineUnavailableException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                if(GameFrame.AIModeIsOn){
                    GameFrame.controller.setPvcPlayer(people);
                }
            }
        });

        //得到当前ImageIcon的Image类，调节其大小，使之适应大小；
        Image blackImage = blackImageIcon.getImage();
        blackImage = blackImage.getScaledInstance(158, 150, Image.SCALE_AREA_AVERAGING);
        blackImageIcon = new ImageIcon(blackImage);
        Image whiteImage = whiteImageIcon.getImage();
        whiteImage = whiteImage.getScaledInstance(165, 150, Image.SCALE_AREA_AVERAGING);
        whiteImageIcon = new ImageIcon(whiteImage);
        Image drawImage = drawImageIcon.getImage();
        drawImage = drawImage.getScaledInstance(150,150,Image.SCALE_AREA_AVERAGING);
        drawImageIcon = new ImageIcon(drawImage);


        //添加主菜单的事件监听；
        mainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
    
    
                GameFrame.getBoardPanelsList().clear();
                GameFrame.stepNum = 0;

                gameFrame.setVisible(false);
            }
        });
        
        
        titleLabel.setBounds(160, 0, 482, 100);
        
        
        ImageIcon imageIcon = new ImageIcon("Image/img.png");
        
        Image image = imageIcon.getImage();
        
        image = image.getScaledInstance(482, 100, Image.SCALE_AREA_AVERAGING);
        
        imageIcon = new ImageIcon(image);
        
        titleLabel.setIcon(imageIcon);
        
        this.setBackground(new Color(201, 233, 202));
        this.add(titleLabel);
        this.add(restartButton);
        this.add(mainMenuButton);
        
        this.setVisible(false);
    }

    //结束菜单初始化方法，用于显示胜利方与改变sqlite数据库中的数据；
    public void init() throws SQLException {
        
        
        JLabel resultLabel = new JLabel();
        
        if (GameFrame.controller.getBlackScore() > GameFrame.controller.getWhiteScore()) {
            resultLabel.setBounds(321, 150, 158, 150);
            resultLabel.setIcon(blackImageIcon);
            
        } else if (GameFrame.controller.getBlackScore() < GameFrame.controller.getWhiteScore()) {
            resultLabel.setBounds(318, 150, 165, 150);
            resultLabel.setIcon(whiteImageIcon);
        } else {
            resultLabel.setBounds(318,150,150,150);
            resultLabel.setIcon(drawImageIcon);
        }
        
        
        if (GameFrame.AIModeIsOn == false) {
            Collections.sort(Player.getPlayerList());
            Connection connection;
            try {
                //区别讨论AI模式是否打开，选择不同方式改变数据库；
                if (GameFrame.AIModeIsOn == false) {
                    Class.forName("org.sqlite.JDBC");

                    connection = DriverManager.getConnection("jdbc:sqlite:lib/player.db", "Daniel", "123qweasd");

                    String sql = "SELECT UserWinTime,UserGameTime FROM USER WHERE UserName = ?;";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, GameFrame.controller.getWhitePlayer().getName());
                    ResultSet resultSet1 = preparedStatement.executeQuery();
                    int whiteWinTime = 0;
                    int whitePlayTime = 0;

                    while (resultSet1.next()) {
                        whiteWinTime = resultSet1.getInt(1);
                        whitePlayTime = resultSet1.getInt(2);
                    }

                    preparedStatement.close();
                    connection.close();


                    Connection connection1 = DriverManager.getConnection("jdbc:sqlite:lib/player.db", "Daniel", "123qweasd");

                    String sql3 = "UPDATE USER SET UserWinTime = ?,UserGameTime = ? WHERE UserName = ?;";
                    PreparedStatement preparedStatement3 = connection1.prepareStatement(sql3);
                    if (GameFrame.controller.getWhiteScore() > GameFrame.controller.getBlackScore()) {
                        preparedStatement3.setInt(1, whiteWinTime + 1);
                        preparedStatement3.setInt(2, whitePlayTime + 1);
                        preparedStatement3.setString(3, GameFrame.controller.getWhitePlayer().getName());
                        preparedStatement3.executeUpdate();
                    } else {
                        preparedStatement3.setInt(1, whiteWinTime);
                        preparedStatement3.setInt(2, whitePlayTime);
                        preparedStatement3.setString(3, GameFrame.controller.getWhitePlayer().getName());
                        preparedStatement3.executeUpdate();
                    }
                    connection1.close();

                    Connection connection2 = DriverManager.getConnection("jdbc:sqlite:lib/player.db", "Daniel", "123qweasd");

                    String sql2 = "SELECT UserWinTime,UserGameTime FROM USER WHERE UserName = ?;";
                    PreparedStatement preparedStatement5 = connection2.prepareStatement(sql2);
                    preparedStatement5.setString(1, GameFrame.controller.getBlackPlayer().getName());
                    ResultSet resultSet2 = preparedStatement5.executeQuery();
                    int blackWinTime = 0;
                    int blackPlayTime = 0;

                    while (resultSet2.next()) {
                        blackWinTime = resultSet2.getInt(1);
                        blackPlayTime = resultSet2.getInt(2);
                    }

                    String sql4 = "UPDATE USER SET UserWinTime = ?,UserGameTime = ? WHERE UserName = ?;";
                    PreparedStatement preparedStatement4 = connection2.prepareStatement(sql4);
                    if (GameFrame.controller.getWhiteScore() < GameFrame.controller.getBlackScore()) {
                        preparedStatement4.setInt(1, blackWinTime + 1);
                        preparedStatement4.setInt(2, blackPlayTime + 1);
                        preparedStatement4.setString(3, GameFrame.controller.getBlackPlayer().getName());
                        ;
                        preparedStatement4.executeUpdate();
                    } else {
                        preparedStatement4.setInt(1, blackWinTime);
                        preparedStatement4.setInt(2, blackPlayTime + 1);
                        preparedStatement4.setString(3, GameFrame.controller.getBlackPlayer().getName());
                        preparedStatement4.executeUpdate();
                    }
                    //及时断连，防止占线；
                    connection2.close();

                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else if (GameFrame.AIModeIsOn == true) {
            
            Player humanPlayer = GameFrame.controller.getAIModePlayer();
            
            try {
                Class.forName("org.sqlite.JDBC");
                Connection connection = DriverManager.getConnection("jdbc:sqlite:lib/player.db", "Daniel", "123qweasd");
                
                String sql = "SELECT UserWinTime, UserGameTime FROM USER WHERE UserName = ?;";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                
                preparedStatement.setString(1, humanPlayer.getName());
                ResultSet getNameResultSet = preparedStatement.executeQuery();
                
                int playerGameTime = 0;
                int playerWinTime = 0;
                
                while (getNameResultSet.next()) {
                    playerWinTime = getNameResultSet.getInt(1);
                    playerGameTime = getNameResultSet.getInt(2);
                }
                
                connection.close();
                
                Connection connection1 = DriverManager.getConnection("jdbc:sqlite:lib/player.db", "Daniel", "123qweasd");
                
                String sql2 = "UPDATE USER SET UserGameTime = ? WHERE UserName = ?;";
                PreparedStatement preparedStatement1 = connection1.prepareStatement(sql2);
                preparedStatement1.setInt(1, playerGameTime + 1);
                preparedStatement1.setString(2, humanPlayer.getName());
                
                preparedStatement1.executeUpdate();
                
                connection1.close();
                
                if ((GameFrame.controller.getWhitePlayer() == null && GameFrame.controller.getBlackScore() > GameFrame.controller.getWhiteScore()) || (GameFrame.controller.getBlackPlayer() == null && GameFrame.controller.getBlackScore() < GameFrame.controller.getWhiteScore())) {
                    Connection connection2 = DriverManager.getConnection("jdbc:sqlite:lib/player.db", "Daniel", "123qweasd");
                    
                    String sql3 = "UPDATE USER SET UserWinTime = ? WHERE UserName = ?;";
                    PreparedStatement preparedStatement2 = connection2.prepareStatement(sql3);
                    
                    preparedStatement2.setInt(1, playerWinTime + 1);
                    preparedStatement2.setString(2, humanPlayer.getName());
                    preparedStatement2.executeUpdate();
                    connection2.close();
                }
                
                
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            
        }
        this.add(resultLabel);
        
        GameFrame.controller.getGamePanel().setVisible(false);
        this.setVisible(true);
        
        gameFrame.clearAllBtn();
    }
    
    @Override
    public void print(Graphics g) {
        
        g.setColor(Color.WHITE);
        
        g.fillOval(365, 150, 50, 50);
        
    }
}
