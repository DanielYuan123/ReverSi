package components;

import model.*;
import view.ChessBoardPanel;
import view.GameFrame;
import java.awt.*;
import java.util.Locale;

public class ChessGridComponent extends BasicComponent {
    public static int chessSize;
    public static int gridSize;
    public static Color gridColor = new Color(255, 150, 50);

    private ChessPiece chessPiece;
    private ChessBoardPanel chessGird = null;
    private int row;
    private int col;

    public ChessGridComponent(int row, int col) {
        this.setSize(gridSize, gridSize);

        this.row = row;
        this.col = col;
    }
    
    

    @Override
    public void onMouseClicked() {
        System.out.printf("%s clicked (%d, %d)\n", GameFrame.controller.getCurrentPlayer(), row, col);
        //todo: complete mouse click method
        if (GameFrame.controller.canClick(row, col)) {
            if (this.chessPiece == null) {
                this.chessPiece = GameFrame.controller.getCurrentPlayer();
                ChessPiece currentPlayer = GameFrame.controller.getCurrentPlayer();
                ChessPiece otherPlayer = (GameFrame.controller.getCurrentPlayer() == ChessPiece.BLACK) ? (ChessPiece.WHITE) : (ChessPiece.BLACK);
                ChessGridComponent[][] chessGirds = GameFrame.controller.getGamePanel().getChessGrids();
                
                
                
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
                                    this.add(chessGirds[x][col]);
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
                if (col <= 7) {
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
                if (row >= 2 && col <= 5) {
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
                if (row <= 5 && col >= 0) {
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
                
                
                GameFrame.controller.swapPlayer();
            }
            repaint();
        }
    }
    
    
    public void setChessPiece(ChessPiece chessPiece) {
        this.chessPiece = chessPiece;
    }
    
    public ChessPiece getChessPiece() {
        return chessPiece;
    }
    
    
    public void drawPiece(Graphics g) {
        g.setColor(gridColor);
        g.fillRect(1, 1, this.getWidth() - 2, this.getHeight() - 2);
        if (this.chessPiece != null) {
            g.setColor(chessPiece.getColor());
            g.fillOval((gridSize - chessSize) / 2, (gridSize - chessSize) / 2, chessSize, chessSize);
        }
    }


    @Override
    public void paintComponent(Graphics g) {
        super.printComponents(g);
        drawPiece(g);
    }
}
