package components;

import view.GameFrame;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public abstract class BasicComponent extends JComponent{
    public BasicComponent() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                try {
                    onMouseClicked();
                } catch (IOException | LineUnavailableException ex) {
                    ex.printStackTrace();
                }
            }
    
            @Override
            public void mouseReleased(MouseEvent e) {
                if (GameFrame.controller.getPvcPlayer() != GameFrame.controller.getCurrentPlayer() && GameFrame.controller.getPvcPlayer() != null) {
                    computerStep();
                }
            }
        });

        
    }

    public abstract void onMouseClicked() throws IOException, LineUnavailableException;
    
    public abstract void computerStep();
}
