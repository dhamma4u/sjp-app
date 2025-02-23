package in.sjp.app.ui.component;

import in.sjp.app.excel.ExcelHandler;
import in.sjp.app.excel.InputDetails;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ActionComponent extends JPanel {

    Logger logger = Logger.getLogger(ActionComponent.class.getName());
    JButton updateButton;
    JLabel labelMessage = new JLabel();

    /**
     * @param fileComponent    FileUIComponent as Input
     * @param inTimeComponent  IN Time UI Component as Input
     * @param outTimeComponent OUT time UI Component as Input
     * @throws IOException Exception in Case File ( Image Icon ) missing or issue with Provided path
     */
    public ActionComponent(FileComponent fileComponent, TimeComponent inTimeComponent, TimeComponent outTimeComponent) throws IOException {
        Image img = ImageIO.read(Objects.requireNonNull(getClass().getResource("/work-process.png")));
        ImageIcon imageIcon = new ImageIcon(img);
        this.updateButton = new JGradientButton(" Update ", imageIcon);
        updateButton.setPressedIcon(imageIcon);
        updateButton.setHorizontalTextPosition(JButton.CENTER);
        updateButton.setVerticalTextPosition(JButton.CENTER);
        updateButton.setPreferredSize(new Dimension(250, 50));
        updateButton.setEnabled(false);

        this.add(updateButton);
        this.add(labelMessage);


        // Label Value Change
        fileComponent.getFilePathWithNameLabel().addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                logger.info("Label Text Change Listener:  type: " + evt.getPropertyName() + ", oldValue: " + evt.getOldValue() + " , newText: " + evt.getNewValue());
                logger.log(Level.INFO, " FilePathLabel: " + fileComponent.getFilePathWithNameLabel().getText());
                logger.log(Level.INFO, " FilePath: " + (Objects.nonNull(fileComponent.getExcelFile()) ? fileComponent.getExcelFile().getPath() : "NULL"));
                boolean buttonStatus = Objects.nonNull(evt.getNewValue()) && Objects.nonNull(fileComponent.getExcelFile()) && fileComponent.getExcelFile().isFile();
                updateButton.setEnabled(buttonStatus);
                logger.info(" Process and Update Button Status : " + buttonStatus + " , Status in Reality: " + updateButton.isEnabled());
                JFrame parentFrame = (JFrame) SwingUtilities.windowForComponent(fileComponent);
                parentFrame.repaint();
            }
        });

        // button Action
        updateButton.addActionListener((ActionEvent e) -> {

            logger.log(Level.INFO, " Process and Update Button Clicked.");
            logger.log(Level.INFO, " FilePath: " + fileComponent.getFilePathWithNameLabel().getText());
            logger.log(Level.INFO, " IN TIME Ranges:: start -> " + inTimeComponent.getFieldTimeRangeStart().getText() + " , end -> " + inTimeComponent.getFieldTimeRangeEnd().getText());
            logger.log(Level.INFO, " OUT TIME Ranges:: start -> " + outTimeComponent.getFieldTimeRangeStart().getText() + " , end -> " + outTimeComponent.getFieldTimeRangeEnd().getText());

            InputDetails inputDetails = InputDetails.builder()
                    .excelFile(fileComponent.getExcelFile())
                    .inTimeRangeStart(inTimeComponent.getFieldTimeRangeStart().getText())
                    .inTimeRangeEnd(inTimeComponent.getFieldTimeRangeEnd().getText())
                    .outTimeRangeStart(outTimeComponent.getFieldTimeRangeStart().getText())
                    .outTimeRangeEnd(outTimeComponent.getFieldTimeRangeEnd().getText())
                    .build();

            this.setEnabled(false); // Button Disabled.
            labelMessage.setText(" Processing...");
            JFrame parentFrame = (JFrame) SwingUtilities.windowForComponent(fileComponent);
            parentFrame.repaint();

            // Actual Process Start :: Caller Method
           String outputFilePath = ExcelHandler.manipulateAttendance(inputDetails);
           labelMessage.setText("Created New File With Required Changes: " + outputFilePath );
           labelMessage.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
           labelMessage.setForeground(Color.BLUE);
           parentFrame.repaint();
        });
    }
}






