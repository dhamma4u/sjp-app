package in.sjp.app.ui;

import in.sjp.app.excel.constants.TimeRangeType;
import in.sjp.app.ui.component.ActionComponent;
import in.sjp.app.ui.component.FileComponent;
import in.sjp.app.ui.component.TimeComponent;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;


public class SJPAppParentUIWindow extends JFrame {

    Logger logger = Logger.getLogger(SJPAppParentUIWindow.class.getName());
    private static File excelFile = null;

    /**
     * Create the GUI and show it.
     */
    public void createAndShowGUI() throws IOException {

        //Create and set up the window.
        this.setTitle("SJP Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Set up the content pane.
        Container contentPane = this.getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        // File Panel
        FileComponent fileInfoPanel = new FileComponent();

        JLabel labelTimeRangeParent = new JLabel("Time Format expected. [HH:mm]  ");
        labelTimeRangeParent.setFont(new Font("Cambria", Font.BOLD, 20));

        TimeComponent inTimeComponent = new TimeComponent(TimeRangeType.IN);
        TimeComponent outTimeComponent = new TimeComponent(TimeRangeType.OUT);
        ActionComponent actionComponent = new ActionComponent(fileInfoPanel, inTimeComponent, outTimeComponent);

        contentPane.add(fileInfoPanel);
        contentPane.add(labelTimeRangeParent);
        contentPane.add(inTimeComponent);
        contentPane.add(outTimeComponent);
        contentPane.add(actionComponent);

        //Display the window.
        this.pack();
        this.setSize(1000, 500);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
