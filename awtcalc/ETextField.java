/*
 * ETextField.java
 *
 * Created on January 10, 2001, 3:25 PM
 */


/**
 * @author Ernest Criss Jr.
 * @version
 */

import java.awt.*;

public class ETextField extends Canvas {

    String message = "";
    boolean firstTime = true;
    int x;

    public ETextField(int width, int height) {
        setSize(width, height);
    }

    /** Creates new ETextField */
    public void setText(String text) {
        firstTime = false;
        message = text;
        x += 3;
        repaint();
    }

    public void paint(Graphics g) {

        g.drawRect(0, 0, size().width - 1, size().height - 1);
        g.drawString(message, 3, 13);

    }
}
