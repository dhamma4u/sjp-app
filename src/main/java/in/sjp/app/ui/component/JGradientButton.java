package in.sjp.app.ui.component;

import javax.swing.*;
import java.awt.*;


public class JGradientButton extends JButton {
    public JGradientButton(String text) {
        super(text);
        setContentAreaFilled(false);
    }

    public JGradientButton(String text, Icon icon) {
        super(text, icon);
        setContentAreaFilled(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setPaint(new GradientPaint(
                new Point(0, 0),
                getBackground(),
                new Point(0, getHeight() / 3),
                Color.WHITE));
        g2.fillRect(0, 0, getWidth(), getHeight() / 3);
        g2.setPaint(new GradientPaint(
                new Point(0, getHeight() / 3),
                Color.WHITE,
                new Point(0, getHeight()),
                getBackground()));
        g2.fillRect(0, getHeight() / 3, getWidth(), getHeight());
        g2.dispose();

        super.paintComponent(g);
    }
}