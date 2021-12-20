package components;

import view.GameFrame;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public abstract class BasicComponent extends JComponent {
    public BasicComponent() {
        this.addMouseListener(new MouseAdapter() {
            //增加鼠标监听器，当对应的棋格被点击时执行
            @Override
            public void mousePressed(MouseEvent e) {
                //使得电脑在下棋时棋格不可被点击
                if (GameFrame.controller.getPvcPlayer() == GameFrame.controller.getCurrentPlayer() || !GameFrame.AIModeIsOn) {
                    try {
                        onMouseClicked();
                        //若进行的是人机模式，则在人下完一步棋之后执行电脑的下棋步骤
                        if (GameFrame.controller.getPvcPlayer() != GameFrame.controller.getCurrentPlayer() && GameFrame.controller.getPvcPlayer() != null) {
                            computerStep();
                        }
                    } catch (IOException | LineUnavailableException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    
    
    }
    
    public abstract void onMouseClicked() throws IOException, LineUnavailableException;
    
    public abstract void computerStep();
    
}
