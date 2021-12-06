package view;

import controller.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GameFrame extends JFrame {
    private ChessBoardPanel chessBoardPanel;
    private StatusPanel statusPanel;
    private Container container=this.getContentPane();
    public static GameController controller;
    public static GameOverPanel gameOverPanel;
    private static ArrayList<int[][]>boardPanelsList=new ArrayList<>(5);
    public static int stepNum=0;
    public int cheatMode = 1;
    public static boolean cheatModeIsOpen = false;
    public static int NightModeChangeConstant=1;

    public static ArrayList<int[][]> getBoardPanelsList(){
        return GameFrame.boardPanelsList;
    }


    public GameFrame(int frameSize) {
        cheatModeIsOpen =false;
        System.out.println("CheatMode is close!");
    
        this.setTitle("2021F CS102A Project Reversi");
        this.setLayout(null);
        this.setSize(frameSize, frameSize);
        this.setLocationRelativeTo(null);
        ImageIcon FrameIcon = new ImageIcon("Image/棋盘图标.png");
        this.setIconImage(FrameIcon.getImage());
        

        chessBoardPanel = new ChessBoardPanel((int) (this.getWidth() * 0.7), (int) (this.getHeight() * 0.7));
        chessBoardPanel.setLocation((this.getWidth() - chessBoardPanel.getWidth()) / 2 - 10, (this.getHeight() - chessBoardPanel.getHeight()) / 3);
        

        statusPanel = new StatusPanel(560, (int) (this.getHeight() * 0.1));
        statusPanel.setLocation((this.getWidth() - chessBoardPanel.getWidth()) / 2 - 10, 0);
        
        gameOverPanel=new GameOverPanel(this);
        
        controller = new GameController(chessBoardPanel, statusPanel);

        this.add(chessBoardPanel);
        this.add(statusPanel);
        this.add(gameOverPanel);


        //new一个自制的事件监听；
        MyBtnActionListener myBtnActionListener = new MyBtnActionListener(this);

        JButton restartBtn = new JButton("Restart");
        restartBtn.setSize(100, 50);
        restartBtn.setLocation((this.getWidth() - chessBoardPanel.getWidth()) / 15, (this.getHeight() + chessBoardPanel.getHeight()) / 2);
        add(restartBtn);
        restartBtn.addActionListener(myBtnActionListener);

        
        JButton loadGameBtn = new JButton("Load");
        loadGameBtn.setSize(100, 50);
        loadGameBtn.setLocation(restartBtn.getX()+restartBtn.getWidth()+30, restartBtn.getY());
        add(loadGameBtn);
        loadGameBtn.addActionListener(myBtnActionListener);
        
        
        JButton saveGameBtn = new JButton("Save");
        saveGameBtn.setSize(100, 50);
        saveGameBtn.setLocation(loadGameBtn.getX()+restartBtn.getWidth()+30, restartBtn.getY());
        add(saveGameBtn);
        saveGameBtn.addActionListener(myBtnActionListener);
        
        //new一个自制夜间监听事件；
        NightModeSetter nightModeSetter=new NightModeSetter(this);

        JButton nightModeBtn= new JButton("Nightmode");
        nightModeBtn.setSize(100,50);
        nightModeBtn.setLocation(saveGameBtn.getX()+saveGameBtn.getWidth()+30, restartBtn.getY());
        add(nightModeBtn);
        nightModeBtn.addActionListener(nightModeSetter);

        
        JButton cheatMode = new JButton("CheatMode");
        cheatMode.setSize(100, 50);
        cheatMode.setLocation(nightModeBtn.getX() + nightModeBtn.getWidth() + 30, restartBtn.getY());
        add(cheatMode);
        cheatMode.addActionListener(myBtnActionListener);
        
        JButton regretChess = new JButton("Regret");
        regretChess.setSize(100, 50);
        regretChess.setLocation(cheatMode.getX() + cheatMode.getWidth() + 30, restartBtn.getY());
        add(regretChess);
        regretChess.addActionListener(myBtnActionListener);

        myFrameWindowListener myFrameWindowListener=new myFrameWindowListener(this);
        this.addWindowListener(myFrameWindowListener);

        this.setVisible(true);
        //this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

        
    }

    //构建一个事件监听的内部类；
    private class MyBtnActionListener implements ActionListener{

        //设置GameFrame的成员变量；
        private GameFrame gameFrame;

        //重写构造器，使监听器可以接触当前类；
        public MyBtnActionListener(GameFrame gameFrame) {
            this.gameFrame = gameFrame;
        }

        /*
        实现ActionListener方法，restart则使当前GameFrame不可见，并重新new一个GameFrame;除此之外，加入Save和Load的事件监听方法；
         */
        @Override
        public void actionPerformed(ActionEvent e) {

            //使监听器能区分触发的不同button以触发不同效果；
            if(e.getActionCommand().equals("Restart")){
                System.out.println("clicked Restart button.");
                this.gameFrame.setVisible(false);
                GameFrame.getBoardPanelsList().clear();
                GameFrame.stepNum=0;
                new GameFrame(800);

            }else if(e.getActionCommand().equals("Load")){
                System.out.println("clicked Load Btn");
                String filePath = "./UserFiles/" + JOptionPane.showInputDialog("Load the game:", "input the path here");
                controller.readFileData(filePath);
            }else if(e.getActionCommand().equals("Save")){
                System.out.println("clicked Save Btn");
                String filePath = "./UserFiles/" + JOptionPane.showInputDialog("Save the game:", "input the path here");
                controller.writeDataToFile(filePath);
            } else if(e.getActionCommand().equals("CheatMode")) {
                if (cheatMode == 1) {
                    cheatModeIsOpen = true;
                    cheatMode = -1;
                    System.out.println("CheatMode is opened!");
                    repaint();
                } else if (cheatMode == -1) {
                    cheatModeIsOpen = false;
                    cheatMode = 1;
                    System.out.println("CheatMode is closed!");
                    repaint();
                }
            } else if (e.getActionCommand().equals("Regret")) {
                System.out.println("Regret is clicked.");
                
            }
        }
    }

    //创建夜间模式事件监听类；
    private class NightModeSetter implements ActionListener{
        private int nightModeInt=-1;
        private GameFrame client;

        //构造器重写，引入GameFrame变量；
        public NightModeSetter(GameFrame gameFrame){
            this.client=gameFrame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("NightMode button clicked.");
            if(this.nightModeInt==1){
                client.container.setBackground(new Color(238,238,238, 255));
                client.statusPanel.setBackground(new Color(238,238,238));
                client.statusPanel.setPlayerLabelColor(Color.BLACK);
                client.statusPanel.setScoreLabelColor(Color.BLACK);
                this.nightModeInt=-1;
                client.NightModeChangeConstant=-1;
            }else if(this.nightModeInt==-1){
                client.container.setBackground(Color.BLACK);
                client.statusPanel.setPlayerLabelColor(Color.white);
                client.statusPanel.setScoreLabelColor(Color.WHITE);
                client.statusPanel.setBackground(Color.BLACK);
                this.nightModeInt=1;
                client.NightModeChangeConstant=-1;
            }
        }
    }

    private class myFrameWindowListener extends WindowAdapter{

        public GameFrame gameFrame;

        public myFrameWindowListener(GameFrame gameFrame){
            this.gameFrame=gameFrame;
        }

        @Override
        public void windowClosing(WindowEvent e) {
            GameFrame.boardPanelsList.clear();
            GameFrame.stepNum=0;
            gameFrame.setVisible(false);

        }
    }

    //维修函数；
    public static void main(String[] args) {
        new GameFrame(800);
    }
}
