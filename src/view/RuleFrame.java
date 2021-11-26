package view;
import javax.swing.*;
import java.awt.*;
public class RuleFrame extends Frame{
    public RuleFrame(String title){
        super(title);
        this.init();
    };
    public void init(){
        this.setBackground(Color.white);
        this.setBounds(600,600,800,920);
        this.setVisible(true);
        this.setLayout(null);
        //Panel panel = new Panel();
        //panel.setBounds(20,10,60,100);
        Label label = new Label();
        label.setBounds(10,15,780,890);
        label.setText("yuanzihan");
        label.setFont(new Font("ds",Font.PLAIN,15));
        ImageIcon imageIcon = new ImageIcon("");
        this.add(label);
    }
}
