import view.GameFrame;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        //javax.swing.SwingUtilities.invokeLater(new Runnable() {
        //    @Override
        //    public void run() {
        //        createAndShowGUI();
        //    }
        //});
        SwingUtilities.invokeLater(() -> {
            GameFrame mainFrame = new GameFrame(800);
            mainFrame.setVisible(true);
        });
    }
}
