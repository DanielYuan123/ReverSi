package components;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public abstract class BasicComponent extends JComponent {
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
        });
    }

    public abstract void onMouseClicked() throws IOException, LineUnavailableException;
}
