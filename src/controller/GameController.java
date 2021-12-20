package controller;

import PlayerInfo.Player;
import components.ChessGridComponent;
import model.ChessPiece;
import view.*;
import javax.swing.*;
import java.io.*;


public class GameController {
    
    
    private ChessBoardPanel gamePanel;
    private StatusPanel statusPanel;
    private ChessPiece currentPlayer;
    private int blackScore;
    private int whiteScore;
    private Player whitePlayer;
    private Player blackPlayer;
    private ChessPiece pvcPlayer;
    
    public GameController(ChessBoardPanel gamePanel, StatusPanel statusPanel, Player whitePlayer, Player blackPlayer) {
        this.gamePanel = gamePanel;
        this.statusPanel = statusPanel;
        this.currentPlayer = ChessPiece.BLACK;
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
        blackScore = 2;
        whiteScore = 2;
    }
    
    public Player getWhitePlayer() {
        return this.whitePlayer;
    }
    
    public Player getBlackPlayer() {
        return this.blackPlayer;
    }
    
    public Player getAIModePlayer() {
        if (whitePlayer == null) {
            return blackPlayer;
        } else {
            return whitePlayer;
        }
    }
    
    
    public void setPvcPlayer(ChessPiece pvcPlayer) {
        this.pvcPlayer = pvcPlayer;
        if (pvcPlayer == ChessPiece.WHITE) {
            gamePanel.runComputerStep();
        }
    }
    
    public ChessPiece getPvcPlayer() {
        return pvcPlayer;
    }
    
    public int getBlackScore() {
        return this.blackScore;
    }
    
    public int getWhiteScore() {
        return this.whiteScore;
    }
    
    public StatusPanel getStatusPanel() {
        return this.statusPanel;
    }
    
    //交换玩家
    public void swapPlayer() {
        countScore();
        currentPlayer = (currentPlayer == ChessPiece.BLACK) ? ChessPiece.WHITE : ChessPiece.BLACK;
        statusPanel.setPlayerText(currentPlayer.name());
        statusPanel.setScoreText(blackScore, whiteScore);
    }
    
    //计分板
    public void countScore() {
        blackScore = 0;
        whiteScore = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (this.gamePanel.getChessGrids()[i][j].getChessPiece() == ChessPiece.BLACK) {
                    blackScore++;
                }
                if (this.gamePanel.getChessGrids()[i][j].getChessPiece() == ChessPiece.WHITE) {
                    whiteScore++;
                }
            }
        }
    }
    
    
    public ChessPiece getCurrentPlayer() {
        return currentPlayer;
    }
    
    public ChessBoardPanel getGamePanel() {
        return gamePanel;
    }
    
    //读档操作
    public void readFileData(String fileName) {
        
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            
            //检测是否有行棋方，并执行设置或警告
            String firstLine = bufferedReader.readLine();
            if (firstLine.contains("BLACK")) {
                currentPlayer = ChessPiece.BLACK;
            } else if (firstLine.contains("WHITE")) {
                currentPlayer = ChessPiece.WHITE;
            } else {
                JOptionPane.showMessageDialog(null, "缺少行棋方", "Error(error code: 103)", JOptionPane.ERROR_MESSAGE);
            }
            
            //设置状态栏为当前行棋方
            statusPanel.setPlayerText(currentPlayer.name());
            bufferedReader.readLine();
            bufferedReader.readLine();
            bufferedReader.readLine();
    
            //读取并设置黑白两方的分数
            String black = bufferedReader.readLine();
            blackScore = Integer.parseInt(black);
            bufferedReader.readLine();
            String white = bufferedReader.readLine();
            whiteScore = Integer.parseInt(white);
            statusPanel.setScoreText(blackScore, whiteScore);
    
            bufferedReader.readLine();
            bufferedReader.readLine();
    
    
            ChessGridComponent[][] chessGridComponents = gamePanel.getChessGrids();
            for (int i = 0; i < 8; i++) {
                String[] read = bufferedReader.readLine().split(" +");
                if (!((read[0].equals("") && read.length == 9) || read.length == 8)) {
                    JOptionPane.showMessageDialog(null, "棋盘错误", "Error(error code: 101)", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                for (int j = 0; j < 8; j++) {
                    switch ((read.length == 9 ? read[j + 1] : read[j])) {
                        case "NULL":
                            chessGridComponents[i][j].setChessPiece(null);
                            break;
                        case "BLACK":
                            chessGridComponents[i][j].setChessPiece(ChessPiece.BLACK);
                            break;
                        case "WHITE":
                            chessGridComponents[i][j].setChessPiece(ChessPiece.WHITE);
                            break;
                        default:
                            JOptionPane.showMessageDialog(null, "棋子错误", "Error(error code: 102)", JOptionPane.ERROR_MESSAGE);
                            return;
                    }
                }
            }
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    chessGridComponents[i][j].repaint();
                }
            }
            bufferedReader.close();
            JOptionPane.showMessageDialog(null, "Load successfully.");
    
            int[][] chessGridsNum = new int[8][8];
    
            for (int i = 0; i < 8; i++)
                for (int j = 0; j < 8; j++) {
                    if (chessGridComponents[i][j] == null) {
                        chessGridsNum[i][j] = 0;
                    } else if (chessGridComponents[i][j].getChessPiece() == ChessPiece.BLACK) {
                        chessGridsNum[i][j] = 1;
                    } else {
                        chessGridsNum[i][j] = -1;
                    }
                }
    
            GameFrame.getBoardPanelsList().clear();
            GameFrame.getBoardPanelsList().add(chessGridsNum);
            GameFrame.stepNum = 1;
    
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "File not be found!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "其他错误", "Error(error code: 106)", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    public void writeDataToFile(String fileName) {
        
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName));
            ChessGridComponent[][] chessGridComponents = getGamePanel().getChessGrids();
            
            bufferedWriter.write("CurrentPlayer: ");
            bufferedWriter.write(currentPlayer.name());
            bufferedWriter.newLine();
            
            bufferedWriter.write(String.format("\nGameScore: \nBlack: \n%d\nWhite: \n%d\n\n", blackScore, whiteScore));
            
            bufferedWriter.write("ChessBoardPanel: \n");
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (chessGridComponents[i][j].getChessPiece() == null) {
                        bufferedWriter.write(String.format("%8s", "NULL"));
                    } else {
                        bufferedWriter.write(String.format("%7s", chessGridComponents[i][j].getChessPiece().name()));
                    }
                }
                bufferedWriter.newLine();
            }
            
            if (new File(String.format("./%s", fileName)).exists() && !fileName.equals("./UserFiles/null.txt")) {
                JOptionPane.showMessageDialog(null, "Save successfully.");
            }
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public boolean canClick(int row, int col) {
        return gamePanel.canClickGrid(row, col, currentPlayer);
    }
}
