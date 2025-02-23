package in.sjp.app.ui.component;

import in.sjp.app.excel.constants.TimeRangeType;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

@Getter
@Setter
public class TimeComponent extends JPanel {

    Logger logger = Logger.getLogger(ActionComponent.class.getName());

    private TimeRangeType timeRangeType;

    // Spinner with number
    JSpinner hourNumberSpinnerStart = new JSpinner(new SpinnerNumberModel(0, 0, 24, 1));
    JSpinner minutesNumberSpinnerStart = new JSpinner(new SpinnerNumberModel(0, 0, 60, 1));

    JSpinner hourNumberSpinnerEnd = new JSpinner(new SpinnerNumberModel(0, 0, 24, 1));
    JSpinner minutesNumberSpinnerEnd = new JSpinner(new SpinnerNumberModel(0, 0, 60, 1));

    private JLabel labelTimeRangeStart;
    private JLabel labelTimeRangeEnd;

    private JTextField fieldTimeRangeStart;
    private JTextField fieldTimeRangeEnd;

    public TimeComponent(TimeRangeType timeRangeType) {
        this.timeRangeType = timeRangeType;

        hourNumberSpinnerStart.setValue(TimeRangeType.IN.equals(timeRangeType) ? 9 : 14);
        minutesNumberSpinnerStart.setValue(TimeRangeType.IN.equals(timeRangeType) ? 45 : 2);

        hourNumberSpinnerEnd.setValue(TimeRangeType.IN.equals(timeRangeType) ? 10 : 14);
        minutesNumberSpinnerEnd.setValue(TimeRangeType.IN.equals(timeRangeType) ? 2 : 15);

        String prefix = timeRangeType.name();

        labelTimeRangeStart = new JLabel(prefix + " TimeRangeStart: ");
        labelTimeRangeEnd = new JLabel(prefix + " TimeRangeEnd: ");

        fieldTimeRangeStart = new JTextField(20);
        fieldTimeRangeEnd = new JTextField(20);

        fieldTimeRangeEnd.setText("10:10"); // Index out of bound
        fieldTimeRangeStart.setText("10:10"); // Index out of bound
        fieldTimeRangeEnd.setEnabled(false);
        fieldTimeRangeStart.setEnabled(false);

        setTextFieldValuesBySelection(fieldTimeRangeStart,  hourNumberSpinnerStart, true);
        setTextFieldValuesBySelection(fieldTimeRangeStart,  minutesNumberSpinnerStart, false);
        setTextFieldValuesBySelection(fieldTimeRangeEnd,  hourNumberSpinnerEnd, true);
        setTextFieldValuesBySelection(fieldTimeRangeEnd,  minutesNumberSpinnerEnd, false);

        JPanel panelRangeStart = new JPanel();
        JPanel panelRangeEnd = new JPanel();

        panelRangeStart.add(labelTimeRangeStart);
        panelRangeStart.add(hourNumberSpinnerStart);
        panelRangeStart.add(minutesNumberSpinnerStart);
        panelRangeStart.add(fieldTimeRangeStart);

        panelRangeEnd.add(labelTimeRangeEnd);
        panelRangeEnd.add(hourNumberSpinnerEnd);
        panelRangeEnd.add(minutesNumberSpinnerEnd);
        panelRangeEnd.add(fieldTimeRangeEnd);

        this.add(panelRangeStart);
        this.add(panelRangeEnd);

        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        addNumberSpinnerListeners();

    }

    private void addNumberSpinnerListeners() {
        // Add change listener to the spinner
        hourNumberSpinnerStart.addChangeListener(
                new ChangeListener() {
                    public void stateChanged(ChangeEvent e) {
                        setTextFieldValuesBySelection(fieldTimeRangeStart, (JSpinner) e.getSource(), true);
                    }
                }
        );


        // Add change listener to the spinner
        minutesNumberSpinnerStart.addChangeListener(
                new ChangeListener() {
                    public void stateChanged(ChangeEvent e) {
                        setTextFieldValuesBySelection(fieldTimeRangeStart, (JSpinner) e.getSource(), false);
                    }
                }
        );

        // Add change listener to the spinner
        hourNumberSpinnerEnd.addChangeListener(
                new ChangeListener() {
                    public void stateChanged(ChangeEvent e) {
                        setTextFieldValuesBySelection(fieldTimeRangeEnd, (JSpinner) e.getSource(), true);
                    }
                }
        );

        // Add change listener to the spinner
        minutesNumberSpinnerEnd.addChangeListener(
                new ChangeListener() {
                    public void stateChanged(ChangeEvent e) {
                        setTextFieldValuesBySelection(fieldTimeRangeEnd, (JSpinner) e.getSource(), false);
                    }
                }
        );
    }

    /**
     *
     * @param targetTextField textField For Value Setting
     * @param spinner value Selected By Spinner component
     */
    private void setTextFieldValuesBySelection(JTextField targetTextField, JSpinner spinner, boolean isHourSpinner ) {
        String[] timeValues = targetTextField.getText().split(":");
        String spinnerValue = ((int) spinner.getModel().getValue() < 10) ? "0" + spinner.getModel().getValue() : String.valueOf(spinner.getModel().getValue());
        String newTimeValue = (isHourSpinner)
                ? spinnerValue + ":" + timeValues[1]
                : timeValues[0] + ":" + spinnerValue;
        targetTextField.setText(newTimeValue);
    }
}
