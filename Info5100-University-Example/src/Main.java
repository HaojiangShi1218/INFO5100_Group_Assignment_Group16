import UserInterface.WorkAreas.Main.MainJFrame;
import javax.swing.SwingUtilities;

/**
 * 主启动类 - 启动大学管理系统
 * Main class to launch the University Management System
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MainJFrame frame = new MainJFrame();
                frame.setVisible(true);
            }
        });
    }
}

