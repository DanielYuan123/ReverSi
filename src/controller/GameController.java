package controller;

import components.ChessGridComponent;
import model.ChessPiece;
import view.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


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

    public void swapPlayer() {
        countScore();
        currentPlayer = (currentPlayer == ChessPiece.BLACK) ? ChessPiece.WHITE : ChessPiece.BLACK;
        statusPanel.setPlayerText(currentPlayer.name());
        statusPanel.setScoreText(blackScore, whiteScore);
    }


    public void countScore() {
        blackScore=0;
        whiteScore=0;
        for(int i=0;i<8;i++) {
            for (int j = 0; j < 8; j++) {
                if(this.gamePanel.getChessGrids()[i][j].getChessPiece()==ChessPiece.BLACK){
                    blackScore++;
                }
                if(this.gamePanel.getChessGrids()[i][j].getChessPiece()==ChessPiece.WHITE){
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


    public void setGamePanel(ChessBoardPanel gamePanel) {
        this.gamePanel = gamePanel;
    }


    public void readFileData(String fileName) {
        //todo: read date from file
        List<String> fileData = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                fileData.add(line);
            }
            fileData.forEach(System.out::println);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeDataToFile(String fileName) {
        
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName));
            ChessGridComponent[][] chessGridComponents = getGamePanel().getChessGrids();
            
            bufferedWriter.write("CurrentPlayer: ");
            bufferedWriter.write(currentPlayer.name());
            bufferedWriter.newLine();
            
            bufferedWriter.write(String.format("GameScore: Black: %d  White: %d\n\n", blackScore, whiteScore));
            
            bufferedWriter.write("ChessBoardPanel: \n");
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (chessGridComponents[i][j].getChessPiece() == null) {
                        bufferedWriter.write("NULL   ");
                    } else {
                        bufferedWriter.write(chessGridComponents[i][j].getChessPiece().name());
                        bufferedWriter.write(" ");
                    }
                }
                bufferedWriter.newLine();
            }
            
            System.out.println("Save successfully.");
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean canClick(int row, int col) {
        return gamePanel.canClickGrid(row, col, currentPlayer);
    }
}
