import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.colorchooser.*;

public class AniApplet extends Applet implements Runnable {
    int i;
    String[] dip = new String[5];

    Thread t;

    public void init() {
        dip[0] = new String("D-");
        dip[1] = new String("I-");
        dip[2] = new String("P-");
        dip[3] = new String("A-");
        dip[4] = new String("L!");
        t = new Thread(this);
        if (t != null) {
            t.start();
        }
    }

    public void run() {
        while (true) {
            repaint();
            i++;
            if (i >= 5)
                i = 0;
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
            }
        }
    }

    //Overriding the update method to eliminate flickering
    public void update(Graphics g) {
        for (int x = 0; x <= i; x++) {
            g.drawString(dip[x], (12 * x) + 10, 50); //repaint(g);
        }
    }
}
