package in.sjp.app.ui.component;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Objects;

@Getter
@Setter
@Slf4j
public class FileComponent extends JPanel {

    private File excelFile = null;
    private JLabel filePathWithNameLabel = new JLabel();

    public FileComponent() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel files", "xlsx", "xls");
        fileChooser.addChoosableFileFilter(filter);
        fileChooser.setFileFilter(filter);

        JGradientButton fileChooserButton = new JGradientButton("Open Attendance Excel File...");
        fileChooserButton.setPreferredSize(new Dimension(250, 40));
        fileChooserButton.addActionListener(
                (final ActionEvent e) -> {
                    excelFile = (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
                            ? fileChooser.getSelectedFile() : null;
                    log.info("FileSelectionStatus: {},  Selected File: {}",
                            Objects.nonNull(excelFile), (Objects.nonNull(excelFile) ? excelFile.getPath() : "NULL"));
                    if (Objects.nonNull(excelFile) && excelFile.isFile()) {
                        filePathWithNameLabel.setText(excelFile.getPath());
                    }
                });

        // File Panel
        this.add(fileChooserButton);
        this.add(filePathWithNameLabel);
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
    }
}
