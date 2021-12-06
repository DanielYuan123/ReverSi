package components;

import model.*;
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
import javax.swing.Timer;


public class ChessGridComponent extends BasicComponent {
    public static int chessSize;
    public static int gridSize;
    public int length=56;
    public static Color gridColor = new Color(255, 150, 50);

    private ChessPiece chessPiece;
    private int row;
    private int col;

    private MyDynamicListener myDynamicListener = new MyDynamicListener();
    public static AudioInputStream soundStream;
    public static Clip soundClip;
    public static FloatControl gainSoundControl;
    private ChessGridComponent[][] formerGridComponent=new ChessGridComponent[8][8];
    private Timer timer = new Timer(1, myDynamicListener);
    private boolean ChangeConstant;


    public ChessGridComponent(int row, int col) {
        this.setSize(gridSize, gridSize);

        this.row = row;
        this.col = col;

    }

    public static void setSound() throws IOException,LineUnavailableException,UnsupportedAudioFileException{
            soundStream = AudioSystem.getAudioInputStream(new File("Music/chessDownSound.wav"));
            soundClip = AudioSystem.getClip();
            soundClip.open(soundStream);
            gainSoundControl = (FloatControl) soundClip.getControl(FloatControl.Type.MASTER_GAIN);
            //clip.start();
    }

    
    
    @Override
    public void onMouseClicked() {
        System.out.printf("%s clicked (%d, %d)\n", GameFrame.controller.getCurrentPlayer(), row, col);
        if (GameFrame.controller.canClick(row, col) || GameFrame.cheatModeIsOpen) {
            if (this.chessPiece == null) {
                this.chessPiece = GameFrame.controller.getCurrentPlayer();
                ChessPiece currentPlayer = GameFrame.controller.getCurrentPlayer();
                System.arraycopy(GameFrame.controller.getGamePanel().getChessGrids(), 0, formerGridComponent, 0, 8);
                ChessPiece otherPlayer = (GameFrame.controller.getCurrentPlayer() == ChessPiece.BLACK) ? (ChessPiece.WHITE) : (ChessPiece.BLACK);
                ChessGridComponent[][] chessGirds = GameFrame.controller.getGamePanel().getChessGrids();
                chessGirds[this.row][this.col].setChessPiece(currentPlayer);
                this.PlaySound();
                //向8个方向修改
                //向上修改
                if (row >= 2) {
                    for (int k = row - 1; k >= 0; k--) {
                        if (chessGirds[row - 1][col].getChessPiece() != otherPlayer) {
                            break;
                        }
                        if (chessGirds[k][col].getChessPiece() != otherPlayer) {
                            if (chessGirds[k][col].getChessPiece() == currentPlayer) {
                                for (int x = row; x > k; x--) {
                                    chessGirds[x][col].setChessPiece(currentPlayer);
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
                            if (chessGirds[k][col].getChessPiece() == chessPiece) {
                                for (int x = row; x < k; x++) {
                                    chessGirds[x][col].setChessPiece(chessPiece);
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
                            if (chessGirds[row][k].getChessPiece() == chessPiece) {
                                for (int y = col; y > k; y--) {
                                    chessGirds[row][y].setChessPiece(chessPiece);
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
                            if (chessGirds[row][k].getChessPiece() == chessPiece) {
                                for (int y = col; y < k; y++) {
                                    chessGirds[row][y].setChessPiece(chessPiece);
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
                            if (chessGirds[k][l].getChessPiece() == chessPiece) {
                                for (int x = row, y = col; x > k && y > l; x--, y--) {
                                    chessGirds[x][y].setChessPiece(chessPiece);
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
                            if (chessGirds[k][l].getChessPiece() == chessPiece) {
                                for (int x = row, y = col; x < k && y > l; x++, y--) {
                                    chessGirds[x][y].setChessPiece(chessPiece);
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
                            if (chessGirds[k][l].getChessPiece() == chessPiece) {
                                for (int x = row, y = col; x > k && y < l; x--, y++) {
                                    chessGirds[x][y].setChessPiece(chessPiece);
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
                            if (chessGirds[k][l].getChessPiece() == chessPiece) {
                                for (int x = row, y = col; x < k && y < l; x++, y++) {
                                    chessGirds[x][y].setChessPiece(chessPiece);
                                }
                            }
                            break;
                        }
                    }
                }
    
                
                GameFrame.stepNum++;
                System.out.println("Step number: " + GameFrame.stepNum);

                int[][] Panel = new int[8][8];
    
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (chessGirds[i][j].getChessPiece() == null) {
                            Panel[i][j] = 0;
                        } else if (chessGirds[i][j].getChessPiece()==ChessPiece.BLACK) {
                            Panel[i][j] = 1;
                        } else if (chessGirds[i][j].getChessPiece()==ChessPiece.WHITE) {
                            Panel[i][j] = -1;
                        }
                    }
                }

                GameFrame.getBoardPanelsList().add(Panel);

                System.out.println("BoardPanelsize: " + GameFrame.getBoardPanelsList().size());

                for (int j = 0; j < 8; j++) {
                    for (int k = 0; k < 8; k++) {
    
                        chessGirds[j][k].judgeChanged(j, k, GameFrame.stepNum);
                        chessGirds[this.row][this.col].ChangeConstant = false;
                        chessGirds[j][k].timer.start();
    
                    }
                }
                GameFrame.controller.swapPlayer();
                //判断游戏是否结束
                if ((gameIsOver() && !GameFrame.cheatModeIsOpen) || !chessBoardPanelHasNull(chessGirds)) {
                    GameFrame.controller.gameOver();
                    GameFrame.controller.getGamePanel().setVisible(false);
                    GameFrame.controller.getStatusPanel().setVisible(false);
                    GameFrame.gameOverPanel.init();
                }
    
            }
            
        }
    
    }
    
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
    
    public void PlaySound(){
        try {
            URL url;
            URI uri;
            File music = new File("Music/chessDownSound.wav");
            uri=music.toURI();
            url=uri.toURL();
            AudioClip audioClip= Applet.newAudioClip(url);
                audioClip.play();
            } catch (MalformedURLException e){
            e.printStackTrace();
        }
    }

    public void drawPiece(Graphics g) {
        //无论如何，重画棋格；
        g.setColor(gridColor);
        g.fillRect(1, 1, this.getWidth() - 2, this.getHeight() - 2);

        if(this.chessPiece == null){
            if(!GameFrame.cheatModeIsOpen){
            if(GameFrame.controller.canClick(this.row,this.col)){
                Graphics2D g2=(Graphics2D)g;
                g2.setStroke(new BasicStroke(4));
                if(GameFrame.controller.getCurrentPlayer()==ChessPiece.BLACK)
                    g2.setColor(new Color(12, 82, 255));
                else{
                    g2.setColor(new Color(255, 0, 0));
                }
                g2.drawRect(8,8,55,55);
                g2.setColor(gridColor);
                g2.drawLine(24,8,47,8);
                g2.drawLine(63,24,63,47);
                g2.drawLine(8,24,8,47);
                g2.drawLine(25,63,47,63);

                }
            }else{
                Graphics2D g2=(Graphics2D)g;
                g2.setStroke(new BasicStroke(4));
                if(GameFrame.controller.getCurrentPlayer()==ChessPiece.BLACK)
                    g2.setColor(new Color(12, 82, 255));
                else {
                    g2.setColor(new Color(255, 0, 0));
                }
                g2.drawRect(8,8,55,55);
                g2.setColor(gridColor);
                g2.drawLine(24,8,47,8);
                g2.drawLine(63,24,63,47);
                g2.drawLine(8,24,8,47);
                g2.drawLine(25,63,47,63);
            }
            timer.stop();
            length=56;
        }

        if (this.chessPiece != null) {

            if(GameFrame.stepNum==1){
                g.setColor(chessPiece.getColor());
                g.fillOval((gridSize - chessSize) / 2, (gridSize - chessSize) / 2, chessSize, 56);
                length=56;
                timer.stop();
            }
            if(GameFrame.stepNum > 1){

                if(this.ChangeConstant){

            Color oppo = chessPiece.getColor()==Color.BLACK ? Color.WHITE:Color.BLACK;

            if(length>0){

                    g.setColor(oppo);
                    g.fillOval((gridSize - Math.abs(length)) / 2, (gridSize - chessSize) / 2, Math.abs(length), 56);


            }
            
            if(length<0){
                g.setColor(this.chessPiece.getColor());
                g.fillOval((gridSize - Math.abs(length)) / 2, (gridSize - chessSize) / 2, Math.abs(length), 56);
            }
            if(length==-chessSize){
                g.setColor(this.chessPiece.getColor());
                g.fillOval((gridSize - Math.abs(length)) / 2, (gridSize - chessSize) / 2, Math.abs(length), 56);
                length=56;
                this.ChangeConstant=false;
                timer.stop();
            }
        }   else {

                    g.setColor(chessPiece.getColor());
                    g.fillOval((gridSize - chessSize) / 2, (gridSize - chessSize) / 2, 56, 56);
                    length=56;
                    this.ChangeConstant=false;
                    timer.stop();
                }
            } else  {
                g.setColor(chessPiece.getColor());
                g.fillOval((gridSize - chessSize) / 2, (gridSize - chessSize) / 2, 56, 56);
                length=56;
                this.ChangeConstant=false;
                timer.stop();
            }
            }
        }

    public void judgeChanged(int j ,int k ,int i){
        if(GameFrame.getBoardPanelsList().get(i-1)[j][k]==GameFrame.getBoardPanelsList().get(i-2)[j][k]){
            this.ChangeConstant=false;
        }else{
            this.ChangeConstant=true;
        }
    }



    @Override
    public void paintComponent(Graphics g) {
        drawPiece(g);
    }


    private class MyDynamicListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int frame=7;

            length-=frame;

            repaint();

        }
    }
}
