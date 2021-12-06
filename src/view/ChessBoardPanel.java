package view;

import components.ChessGridComponent;
import model.ChessPiece;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ChessBoardPanel extends JPanel {
    private final int CHESS_COUNT = 8;
    private ChessGridComponent[][] chessGrids;

    public ChessBoardPanel() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        this.setVisible(true);
        this.setFocusable(true);
        this.setLayout(null);
        this.setBackground(Color.BLACK);
        this.setSize(560, 560);
        ChessGridComponent.gridSize = 560 / CHESS_COUNT;
        ChessGridComponent.chessSize = (int) (ChessGridComponent.gridSize * 0.8);
        initialChessGrids();
    }

    public ChessBoardPanel(int width, int height) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        this.setVisible(true);
        this.setFocusable(true);
        this.setLayout(null);
        this.setBackground(Color.BLACK);
        int length = Math.min(width, height);
        this.setSize(length, length);
        ChessGridComponent.gridSize = length / CHESS_COUNT;
        ChessGridComponent.chessSize = (int) (ChessGridComponent.gridSize * 0.8);
        System.out.printf("width = %d height = %d gridSize = %d chessSize = %d\n",
                width, height, ChessGridComponent.gridSize, ChessGridComponent.chessSize);

        initialChessGrids();//return empty chessboard
        initialGame();//add initial four chess
        int[][] chessGridComponents=new int[8][8];
        for(int i=0;i<8;i++) {
            for (int j = 0; j < 8; j++) {
                if (chessGrids[i][j].getChessPiece()==null) {
                    chessGridComponents[i][j] = 0;
                } else if (chessGrids[i][j].getChessPiece().equals(ChessPiece.BLACK)) {
                    chessGridComponents[i][j] = 1;
                } else {
                    chessGridComponents[i][j] = -1;
                }
            }
        }
        GameFrame.getBoardPanelsList().add(chessGridComponents);
        GameFrame.stepNum++;
        repaint();
    }
    
    public ChessGridComponent[][] getChessGrids() {
        return chessGrids;
    }
    
    
    
    /**
     * set an empty chessboard
     */
    public void initialChessGrids() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        chessGrids = new ChessGridComponent[CHESS_COUNT][CHESS_COUNT];

        //draw all chess grids
        for (int i = 0; i < CHESS_COUNT; i++) {
            for (int j = 0; j < CHESS_COUNT; j++) {
                ChessGridComponent gridComponent = new ChessGridComponent(i, j);
                gridComponent.setLocation(j * ChessGridComponent.gridSize, i * ChessGridComponent.gridSize);
                chessGrids[i][j] = gridComponent;
                this.add(chessGrids[i][j]);
            }
        }
    }

    /**
     * initial origin four chess
     */
    public void initialGame() {
        chessGrids[3][3].setChessPiece(ChessPiece.BLACK);
        chessGrids[3][4].setChessPiece(ChessPiece.WHITE);
        chessGrids[4][3].setChessPiece(ChessPiece.WHITE);
        chessGrids[4][4].setChessPiece(ChessPiece.BLACK);
    }
    
    
    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
    }

    public boolean canClickGrid(int row, int col, ChessPiece currentPlayer) {
    
        ChessPiece otherPlayer = (currentPlayer == ChessPiece.BLACK) ? (ChessPiece.WHITE) : (ChessPiece.BLACK);
    
        if (chessGrids[row][col].getChessPiece() != currentPlayer && chessGrids[row][col].getChessPiece() != otherPlayer) {
    
            //向上判断
            if (row >= 2) {
                for (int k = row - 1; k >= 0; k--) {
                    if (chessGrids[row - 1][col].getChessPiece() != otherPlayer) {
                        break;
                    }
                    if (k != row - 1 && chessGrids[k][col].getChessPiece() == null) {
                        break;
                    }
                    if (chessGrids[k][col].getChessPiece() == currentPlayer) {
                        return true;
                    }
                }
            }
    
    
            //向下判断
            if (row <= 5) {
                for (int k = row + 1; k <= 7; k++) {
                    if (chessGrids[row + 1][col].getChessPiece() != otherPlayer) {
                        break;
                    }
                    if (k != row + 1 && chessGrids[k][col].getChessPiece() == null) {
                        break;
                    }
                    if (chessGrids[k][col].getChessPiece() == currentPlayer) {
                        return true;
                    }
                }
            }
    
            //向左判断
            if (col >= 2) {
                for (int k = col - 1; k >= 0; k--) {
                    if (chessGrids[row][col - 1].getChessPiece() != otherPlayer) {
                        break;
                    }
                    if (k != col - 1 && chessGrids[row][k].getChessPiece() == null) {
                        break;
                    }
                    if (chessGrids[row][k].getChessPiece() == currentPlayer) {
                        return true;
                    }
                }
            }
    
            //向右判断
            if (col <= 5) {
                for (int k = col + 1; k <= 7; k++) {
                    if (chessGrids[row][col + 1].getChessPiece() != otherPlayer) {
                        break;
                    }
                    if (k != col + 1 && chessGrids[row][k].getChessPiece() == null) {
                        break;
                    }
                    if (chessGrids[row][k].getChessPiece() == currentPlayer) {
                        return true;
                    }
                }
            }
    
            //向左上判断
            if (row >= 2 && col >= 2) {
                for (int k = row - 1, l = col - 1; k >= 0 && l >= 0; k--, l--) {
                    if (chessGrids[row - 1][col - 1].getChessPiece() != otherPlayer) {
                        break;
                    }
                    if (k != row - 1 && l != col - 1 && chessGrids[k][l].getChessPiece() == null) {
                        break;
                    }
                    if (chessGrids[k][l].getChessPiece() == currentPlayer) {
                        return true;
                    }
                }
            }
    
            //向左下判断
            if (row <= 5 && col >= 2) {
                for (int k = row + 1, l = col - 1; k <= 7 && l >= 0; k++, l--) {
                    if (chessGrids[row + 1][col - 1].getChessPiece() != otherPlayer) {
                        break;
                    }
                    if (k != row + 1 && l != col - 1 && chessGrids[k][l].getChessPiece() == null) {
                        break;
                    }
                    if (chessGrids[k][l].getChessPiece() == currentPlayer) {
                        return true;
                    }
                }
            }
    
            //向右上判断
            if (row >= 2 && col <= 5) {
                for (int k = row - 1, l = col + 1; k >= 0 && l <= 7; k--, l++) {
                    if (chessGrids[row - 1][col + 1].getChessPiece() != otherPlayer) {
                        break;
                    }
                    if (k != row - 1 && l != col + 1 && chessGrids[k][l].getChessPiece() == null) {
                        break;
                    }
                    if (chessGrids[k][l].getChessPiece() == currentPlayer) {
                        return true;
                    }
                }
            }
    
            //向右下判断
            if (row <= 5 && col <= 5) {
                for (int k = row + 1, l = col + 1; k <= 7 && l <= 7; k++, l++) {
                    if (chessGrids[row + 1][col + 1].getChessPiece() != otherPlayer) {
                        break;
                    }
                    if (k != row + 1 && l != col + 1 && chessGrids[k][l].getChessPiece() == null) {
                        break;
                    }
                    if (chessGrids[k][l].getChessPiece() == currentPlayer) {
                        return true;
                    }
                }
            }
        }
        
        return false;
    
    }

    
}
