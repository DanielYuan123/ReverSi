package controller;

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

    public GameController(ChessBoardPanel gamePanel, StatusPanel statusPanel) {
        this.gamePanel = gamePanel;
        this.statusPanel = statusPanel;
        this.currentPlayer = ChessPiece.BLACK;
        blackScore = 2;
        whiteScore = 2;
    }

    public int getBlackScore(){
        return this.blackScore;
    }

    public int getWhiteScore(){
        return this.whiteScore;
    }

    public StatusPanel getStatusPanel(){
        return this.statusPanel;
    }

    public void swapPlayer() {
        countScore();
        currentPlayer = (currentPlayer == ChessPiece.BLACK) ? ChessPiece.WHITE : ChessPiece.BLACK;
        statusPanel.setPlayerText(currentPlayer.name());
        statusPanel.setScoreText(blackScore, whiteScore);
    }


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

    public void gameOver() {
        if (blackScore > whiteScore) {
            System.out.println("Black player is win!");
        } else if (blackScore < whiteScore) {
            System.out.println("White player is win!");
        } else {
            System.out.println("Both of you have the same lever!");
        }
    }

    public ChessPiece getCurrentPlayer() {
        return currentPlayer;
    }

    public ChessBoardPanel getGamePanel() {
        return gamePanel;
    }
    

    public void readFileData(String fileName) {
        
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            
            currentPlayer = (bufferedReader.readLine().contains("BLACK")) ? (ChessPiece.BLACK) : (ChessPiece.WHITE);
            statusPanel.setPlayerText(currentPlayer.name());
            bufferedReader.readLine();
            bufferedReader.readLine();
            bufferedReader.readLine();
    
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
                for (int j = 0; j < 8; j++) {
                    String chess = bufferedReader.readLine();
                    switch (chess) {
                        case "NULL":
                            chessGridComponents[i][j].setChessPiece(null);
                            break;
                        case "BLACK":
                            chessGridComponents[i][j].setChessPiece(ChessPiece.BLACK);
                            break;
                        case "WHITE":
                            chessGridComponents[i][j].setChessPiece(ChessPiece.WHITE);
                            break;
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

            int[][] chessGridsNum=new int[8][8];

            for(int i=0;i<8;i++)
                for(int j=0;j<8;j++){
                    if(chessGridComponents[i][j]==null){
                        chessGridsNum[i][j]=0;
                    }else if (chessGridComponents[i][j].getChessPiece()==ChessPiece.BLACK){
                        chessGridsNum[i][j]=1;
                    }else {
                        chessGridsNum[i][j]=-1;
                    }
                }

            GameFrame.getBoardPanelsList().clear();
            GameFrame.getBoardPanelsList().add(chessGridsNum);
            GameFrame.stepNum=1;

        } catch (IOException e) {
            //e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Please input right file name!");
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
                        bufferedWriter.write("NULL\n");
                    } else {
                        bufferedWriter.write(chessGridComponents[i][j].getChessPiece().name());
                        bufferedWriter.write("\n");
                    }
                }
            }
            JOptionPane.showMessageDialog(null, "Save successfully.");
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean canClick(int row, int col) {
        return gamePanel.canClickGrid(row, col, currentPlayer);
    }
}
