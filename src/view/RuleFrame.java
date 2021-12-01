package view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

public class RuleFrame extends Frame {
    //重写构造器，自动初始化；
    public RuleFrame(String title) {
        super(title);
        this.init();
    }
    
    ;
    
    //初始化方法；
    public void init() {
    
        //设置背景颜色，边界，布局管理器设为绝对布局；
        this.setBackground(Color.gray);
        this.setBounds(600, 600, 600, 920);
        this.setLayout(null);
    
        //创建标签用于存放文字介绍；
        JLabel label = new JLabel();
        JLabel titleLabel = new JLabel();
    
        //设置标签的大小、边界和位置；
        label.setBounds(10, 0, 780, 890);
        label.setSize(780, 890);
        titleLabel.setLocation(20, 0);
        titleLabel.setSize(560, 110);
    
        //添加imagIcon类；
        ImageIcon TitleIcon = new ImageIcon("Image/ReveisiIntroduction.jpeg");
    
        //创建图片类,并且缩放其大小与JLabel相适应；
        Image TitleImage = TitleIcon.getImage();
        TitleImage = TitleImage.getScaledInstance(560, 110, Image.SCALE_AREA_AVERAGING);
    
        //将调整好的image运用到imageIcon上；
        TitleIcon = new ImageIcon(TitleImage);
    
        //放入文字，设置文字的大小，字体；
        label.setText("<html><body><h1>Introduction</h1><p1>Reversi (黑白棋) is a strategy board game for two players, played on an 8×8<br/>uncheckered board. It was invented in 1883.</p1><h1><br/>WHAT IS IT</h1><p1>There are sixty-four identical game pieces called disks (棋子), which are light (白) <br/>on one side and dark (黑) on the other. Players take turns placing disks on the<br/>board with their assigned color facing up. During a play, any disks of the<br/> opponent's color that are in a straight line and bounded by the disk just placed<br/> and another disk of the current player's color are turned over to the current<br/> player's color. The objective of the game is to have the majority of disks turned <br/>to display one's color when the last playable empty square is filled.</p1><br/><h1>HOW TO PLAY</h1><p1>1. The game begins with four disks placed in a square in the middle of the grid,<br/> two facing white-side-up, two dark-side-up, so that the same-colored disks are <br/>on a diagonal. Convention has this such that the dark-side-up disks are to the <br/>north-east and south-west. The dark player moves first.<br/>2. Play always alternates. After placing a dark disk, dark turns over (flips to dark,<br/>captures) the single disk (or chain of light disks) on the line between the new<br/>piece and an anchoring dark piece. No player can look back to the previous status<br/>of disks when playing moves. A valid move is one where at least one piece is<br/>reversed (flipped over).<br/>3. Now light plays. This player operates under the same rules, with the roles<br/>reversed: light lays down a light piece, causing a dark piece to flip.<br/>4. Players take alternate turns. If one player can not make a valid move, play <br/>passes back to the other player. When neither player can move, the game ends. <br/>This occurs when the grid has filled up or when neither player can legally place a <br/>piece in any of the remaining squares. This means the game may end before the <br/>grid is completely filled. This possibility may occur because one player has no <br/>pieces remaining on the board in that player's color.<br/>5. The player with the most pieces on the board at the end of the game wins.</p1></body></html>");
        label.setFont(new Font("ds", Font.PLAIN, 15));
    
        //设置label的imageIcon；
        titleLabel.setIcon(TitleIcon);
    
        //添加标签;
        this.add(titleLabel);
        this.add(label);
    
        //编写窗口监听：若关闭，并非结束进程，而是隐藏；
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                RuleFrame source = (RuleFrame) e.getSource();
                source.setVisible(false);
            }
        });
    
        //设置帮助窗口可见；
        this.setVisible(true);
    
    }
    
    //维修函数；
    public static void main(String[] args) {
        new RuleFrame("RUle").init();
    }
}
