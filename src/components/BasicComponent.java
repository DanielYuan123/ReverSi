package components;

import controller.GameController;
import model.ChessPiece;
import view.GameFrame;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.PropertyPermission;

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
                while (!GameController.isDrawIsOvered()) {
                    System.out.println(1);
                }
                if (GameFrame.controller.getPvcPlayer() != GameFrame.controller.getCurrentPlayer() && GameFrame.controller.getPvcPlayer() != null) {
                    computerStep();
                    System.out.println(2);
                    GameController.setDrawIsOvered(false);
                }
            }
        });
    
//        PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(GameFrame.stepNum);
//        propertyChangeSupport.addPropertyChangeListener(new PropertyChangeListener() {
//            @Override
//            public void propertyChange(PropertyChangeEvent evt) {
//                System.out.println(GameFrame.stepNum);
//            }
//        });
//        propertyChangeSupport.firePropertyChange("1", GameFrame.stepNum, GameFrame.stepNum + 1);
        
    }

    public abstract void onMouseClicked() throws IOException, LineUnavailableException;
    
    public abstract void computerStep();
}
