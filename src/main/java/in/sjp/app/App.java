package in.sjp.app;

import in.sjp.app.ui.SJPAppParentUIWindow;

import javax.swing.*;
import java.io.IOException;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {

        try {
            // UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); // actual
            // // https://www.formdev.com/flatlaf/
            com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatGitHubIJTheme.setup();
            // https://github.com/JFormDesigner/FlatLaf/tree/main/flatlaf-intellij-themes#how-to-use
            //UIManager.setLookAndFeel( new FlatLightLaf() );
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                SJPAppParentUIWindow sjpAppParentUIWindow = new SJPAppParentUIWindow();
                try {
                    sjpAppParentUIWindow.createAndShowGUI();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        System.out.println("Hello World!");
    }


}
