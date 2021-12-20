package components;

import model.*;
import view.ChessBoardPanel;
import view.GameFrame;
import javax.sound.sampled.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.sql.SQLException;
import java.util.TimerTask;
import javax.swing.Timer;

public class ChessGridComponent extends BasicComponent {
    public static int chessSize;
    public static int gridSize;
    public int length = 56;
    
    private ChessPiece chessPiece;
    private int row;
    private int col;
    private boolean ChangeConstant;
    
    private MyDynamicListener myDynamicListener = new MyDynamicListener();
    private ChessGridComponent[][] formerGridComponent = new ChessGridComponent[8][8];
    private Timer timer = new Timer(1, myDynamicListener);
    
    
    public ChessGridComponent(int row, int col) {
        this.setSize(gridSize, gridSize);
        this.row = row;
        this.col = col;
        
    }
    
    //电脑的下棋步骤
    @Override
    public void computerStep() {
        GameFrame.AIRunning = true;
        //清除上一步棋中可以被点击的棋格
        ChessBoardPanel.getRowCanClicked().clear();
        ChessBoardPanel.getColCanClicked().clear();
        
        //遍历整个棋盘，找到所有可以被点击的棋格
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                GameFrame.controller.canClick(i, j);
            }
        }
    
        if (ChessBoardPanel.getRowCanClicked().size() == 0) {
            GameFrame.controller.swapPlayer();
            return;
        }
        
        //在所有可以下棋的棋格中随机选取一个棋格下棋
        int index = (int) (Math.random() * ChessBoardPanel.getColCanClicked().size());
        int row = ChessBoardPanel.getRowCanClicked().get(index);
        int col = ChessBoardPanel.getColCanClicked().get(index);
    
        //通过延时执行1300ms，模拟电脑的思考时间
        java.util.Timer timer = new java.util.Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                changeChessBoardPanel(row, col);
                GameFrame.AIRunning = false;
            }
        }, 1300);
    
    
    }
    
    //当鼠标点击对应棋格后的步骤（即人的下棋步骤）
    @Override
    public void onMouseClicked() {
        //对是否处在作弊模式下进行分别的处理
        if (GameFrame.controller.canClick(row, col) || GameFrame.cheatModeIsOpen) {
            if (this.chessPiece == null) {
                this.chessPiece = GameFrame.controller.getCurrentPlayer();
                
                //获取当前棋盘上的棋子分布状态
                ChessGridComponent[][] chessGirds = GameFrame.controller.getGamePanel().getChessGrids();
                
                //将当前棋格设置成当前玩家的棋子
                chessGirds[row][col].setChessPiece(this.chessPiece);
                repaint();
                
                changeChessBoardPanel(this.row, this.col);
            }
        }
    
    }
    
    //执行翻棋操作
    public void changeChessBoardPanel(int row, int col) {
        
        System.arraycopy(GameFrame.controller.getGamePanel().getChessGrids(), 0, formerGridComponent, 0, 8);
        ChessPiece otherPlayer = (GameFrame.controller.getCurrentPlayer() == ChessPiece.BLACK) ? (ChessPiece.WHITE) : (ChessPiece.BLACK);
        ChessGridComponent[][] chessGirds = GameFrame.controller.getGamePanel().getChessGrids();
        chessGirds[row][col].chessPiece = GameFrame.controller.getCurrentPlayer();
        
        //播放下棋音效
        this.PlaySound();
        
        //向8个方向修改
        for (int i = 0; i < 1; i++) {
            //向上修改
            if (row >= 2) {
                for (int k = row - 1; k >= 0; k--) {
                    if (chessGirds[row - 1][col].getChessPiece() != otherPlayer) {
                        break;
                    }
                    if (chessGirds[k][col].getChessPiece() != otherPlayer) {
                        if (chessGirds[k][col].getChessPiece() == chessGirds[row][col].chessPiece) {
                            for (int x = row; x > k; x--) {
                                chessGirds[x][col].setChessPiece(chessGirds[row][col].chessPiece);
                            }
                        }
                        break;
                    }
                }
            }
    
            //向下修改
            if (row <= 5) {
                for (int k = row + 1; k <= 7; k++) {
                    if (chessGirds[row + 1][col].getChessPiece() != otherPlayer) {
                        break;
                    }
                    if (chessGirds[k][col].getChessPiece() != otherPlayer) {
                        if (chessGirds[k][col].getChessPiece() == chessGirds[row][col].chessPiece) {
                            for (int x = row; x < k; x++) {
                                chessGirds[x][col].setChessPiece(chessGirds[row][col].chessPiece);
                            }
                        }
                        break;
                    }
                }
            }
    
            //向左修改
            if (col >= 2) {
                for (int k = col - 1; k >= 0; k--) {
                    if (chessGirds[row][col - 1].getChessPiece() != otherPlayer) {
                        break;
                    }
                    if (chessGirds[row][k].getChessPiece() != otherPlayer) {
                        if (chessGirds[row][k].getChessPiece() == chessGirds[row][col].chessPiece) {
                            for (int y = col; y > k; y--) {
                                chessGirds[row][y].setChessPiece(chessGirds[row][col].chessPiece);
                            }
                        }
                        break;
                    }
                }
            }
    
            //向右修改
            if (col <= 5) {
                for (int k = col + 1; k <= 7; k++) {
                    if (chessGirds[row][col + 1].getChessPiece() != otherPlayer) {
                        break;
                    }
                    if (chessGirds[row][k].getChessPiece() != otherPlayer) {
                        if (chessGirds[row][k].getChessPiece() == chessGirds[row][col].chessPiece) {
                            for (int y = col; y < k; y++) {
                                chessGirds[row][y].setChessPiece(chessGirds[row][col].chessPiece);
                            }
                        }
                        break;
                    }
                }
            }
    
            //向左上修改
            if (row >= 2 && col >= 2) {
                for (int k = row - 1, l = col - 1; k >= 0 && l >= 0; k--, l--) {
                    if (chessGirds[row - 1][col - 1].getChessPiece() != otherPlayer) {
                        break;
                    }
                    if (chessGirds[k][l].getChessPiece() != otherPlayer) {
                        if (chessGirds[k][l].getChessPiece() == chessGirds[row][col].chessPiece) {
                            for (int x = row, y = col; x > k && y > l; x--, y--) {
                                chessGirds[x][y].setChessPiece(chessGirds[row][col].chessPiece);
                            }
                        }
                        break;
                    }
                }
            }
    
            //向左下修改
            if (row <= 5 && col >= 2) {
                for (int k = row + 1, l = col - 1; k <= 7 && l >= 0; k++, l--) {
                    if (chessGirds[row + 1][col - 1].getChessPiece() != otherPlayer) {
                        break;
                    }
                    if (chessGirds[k][l].getChessPiece() != otherPlayer) {
                        if (chessGirds[k][l].getChessPiece() == chessGirds[row][col].chessPiece) {
                            for (int x = row, y = col; x < k && y > l; x++, y--) {
                                chessGirds[x][y].setChessPiece(chessGirds[row][col].chessPiece);
                            }
                        }
                        break;
                    }
                }
            }
    
            //向右上修改
            if (row >= 2 && col <= 5) {
                for (int k = row - 1, l = col + 1; k >= 0 && l <= 7; k--, l++) {
                    if (chessGirds[row - 1][col + 1].getChessPiece() != otherPlayer) {
                        break;
                    }
                    if (chessGirds[k][l].getChessPiece() != otherPlayer) {
                        if (chessGirds[k][l].getChessPiece() == chessGirds[row][col].chessPiece) {
                            for (int x = row, y = col; x > k && y < l; x--, y++) {
                                chessGirds[x][y].setChessPiece(chessGirds[row][col].chessPiece);
                            }
                        }
                        break;
                    }
                }
            }
    
            //向右下修改
            if (row <= 5 && col <= 5) {
                for (int k = row + 1, l = col + 1; k <= 7 && l <= 7; k++, l++) {
                    if (chessGirds[row + 1][col + 1].getChessPiece() != otherPlayer) {
                        break;
                    }
                    if (chessGirds[k][l].getChessPiece() != otherPlayer) {
                        if (chessGirds[k][l].getChessPiece() == chessGirds[row][col].chessPiece) {
                            for (int x = row, y = col; x < k && y < l; x++, y++) {
                                chessGirds[x][y].setChessPiece(chessGirds[row][col].chessPiece);
                            }
                        }
                        break;
                    }
                }
            }
        }
        
        
        GameFrame.stepNum++;
        
        int[][] Panel = new int[8][8];
        
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessGirds[i][j].getChessPiece() == null) {
                    Panel[i][j] = 0;
                } else if (chessGirds[i][j].getChessPiece() == ChessPiece.BLACK) {
                    Panel[i][j] = 1;
                } else if (chessGirds[i][j].getChessPiece() == ChessPiece.WHITE) {
                    Panel[i][j] = -1;
                }
            }
        }
        
        GameFrame.getBoardPanelsList().add(Panel);
        
        
        for (int j = 0; j < 8; j++) {
            for (int k = 0; k < 8; k++) {
    
                chessGirds[j][k].judgeChanged(j, k, GameFrame.stepNum);
                chessGirds[row][col].ChangeConstant = false;
                chessGirds[j][k].timer.start();
    
            }
        }
        
        //交换玩家
        GameFrame.controller.swapPlayer();
        
        //判断游戏是否结束
        if ((gameIsOver() && !GameFrame.cheatModeIsOpen) || !chessBoardPanelHasNull(chessGirds)) {
            java.util.Timer timer = new java.util.Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    GameFrame.controller.getStatusPanel().setVisible(false);
                    try {
                        GameFrame.gameOverPanel.init();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }, 800);
        }
    }
    
    //该方法用于判断整个棋盘是否有空白格
    public boolean chessBoardPanelHasNull(ChessGridComponent[][] chessGirds) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessGirds[i][j].getChessPiece() == null) {
                    return true;
                }
            }
        }
        return false;
    }
    
    //判断在普通模式下游戏是否结束
    public boolean gameIsOver() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (GameFrame.controller.canClick(i, j)) {
                    return false;
                }
            }
        }
        
        if (!GameFrame.cheatModeIsOpen) {
            GameFrame.controller.swapPlayer();
        }
        
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (GameFrame.controller.canClick(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    
    public void setChessPiece(ChessPiece chessPiece) {
        this.chessPiece = chessPiece;
    }
    
    public ChessPiece getChessPiece() {
        return chessPiece;
    }
    
    //播放下棋时的音效
    public void PlaySound() {
        try {
            URL url;
            URI uri;
            File music = new File("Music/chessDownSound.wav");
            uri = music.toURI();
            url = uri.toURL();
            AudioClip audioClip = Applet.newAudioClip(url);
            audioClip.play();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
    
    public void drawPiece(Graphics g) {
        
        if (this.chessPiece == null) {
            if (!GameFrame.cheatModeIsOpen) {
                if (GameFrame.controller.canClick(this.row, this.col)) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setStroke(new BasicStroke(4));
                    if (GameFrame.controller.getCurrentPlayer() == ChessPiece.BLACK)
                        g2.setColor(new Color(12, 82, 255));
                    else {
                        g2.setColor(new Color(255, 252, 0));
                    }
                    g2.drawLine(8, 8, 16, 8);
                    g2.drawLine(8, 8, 8, 16);
                    g2.drawLine(62, 8, 54, 8);
                    g2.drawLine(62, 8, 62, 16);
                    g2.drawLine(62, 62, 62, 54);
                    g2.drawLine(62, 62, 54, 62);
                    g2.drawLine(8, 62, 8, 54);
                    g2.drawLine(8, 62, 16, 62);
        
        
                }
            } else {
                Graphics2D g2 = (Graphics2D) g;
                g2.setStroke(new BasicStroke(4));
                if (GameFrame.controller.getCurrentPlayer() == ChessPiece.BLACK)
                    g2.setColor(new Color(12, 82, 255));
                else {
                    g2.setColor(new Color(255, 252, 0));
                }
                g2.drawLine(8, 8, 16, 8);
                g2.drawLine(8, 8, 8, 16);
                g2.drawLine(62, 8, 54, 8);
                g2.drawLine(62, 8, 62, 16);
                g2.drawLine(62, 62, 62, 54);
                g2.drawLine(62, 62, 54, 62);
                g2.drawLine(8, 62, 8, 54);
                g2.drawLine(8, 62, 16, 62);
            }
            timer.stop();
            length = 56;
        }
        
        if (this.chessPiece != null) {
            
            if (GameFrame.stepNum == 1) {
                g.setColor(chessPiece.getColor());
                g.fillOval((gridSize - chessSize) / 2, (gridSize - chessSize) / 2, chessSize, 56);
                length = 56;
                timer.stop();
            }
            if (GameFrame.stepNum > 1) {
    
                if (this.ChangeConstant) {
        
                    Color oppo = chessPiece.getColor() == Color.BLACK ? Color.WHITE : Color.BLACK;
        
                    if (length > 0) {
            
                        g.setColor(oppo);
                        g.fillOval((gridSize - Math.abs(length)) / 2, (gridSize - chessSize) / 2, Math.abs(length), 56);
            
            
                    }
        
                    if (length < 0) {
                        g.setColor(this.chessPiece.getColor());
                        g.fillOval((gridSize - Math.abs(length)) / 2, (gridSize - chessSize) / 2, Math.abs(length), 56);
                    }
                    if (length == -chessSize) {
                        g.setColor(this.chessPiece.getColor());
                        g.fillOval((gridSize - Math.abs(length)) / 2, (gridSize - chessSize) / 2, Math.abs(length), 56);
                        length = 56;
                        this.ChangeConstant = false;
                        timer.stop();
                    }
                } else {
        
                    g.setColor(chessPiece.getColor());
                    g.fillOval((gridSize - chessSize) / 2, (gridSize - chessSize) / 2, 56, 56);
                    length = 56;
                    this.ChangeConstant = false;
                    timer.stop();
                }
            } else {
                g.setColor(chessPiece.getColor());
                g.fillOval((gridSize - chessSize) / 2, (gridSize - chessSize) / 2, 56, 56);
                length = 56;
                this.ChangeConstant = false;
                timer.stop();
            }
        }
    }
    
    public void judgeChanged(int j, int k, int i) {
        if (GameFrame.getBoardPanelsList().get(i - 1)[j][k] == GameFrame.getBoardPanelsList().get(i - 2)[j][k]) {
            this.ChangeConstant = false;
        } else {
            this.ChangeConstant = true;
        }
    }
    
    
    @Override
    public void paintComponent(Graphics g) {
        drawPiece(g);
    }
    
    
    private class MyDynamicListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int frame = 7;
            length -= frame;
            repaint();
        }
    }
}
