import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.colorchooser.*;

public class AnimApplet extends Applet implements Runnable {
    int i;
    Image[] pics;

    Thread t;

    public void init() {
        pics = new Image[4];
        i = 0;

        for (int x = 0; x <= 3; x++) {
            pics[x] = this.getImage(this.getCodeBase(), this.getParameter(Integer.toString(x + 1)));
        }
        for (int x = 0; x <= 3; x++) {
            if (pics[x] == null)
                System.out.println("Image " + x + " not allocated");
        }
        t = new Thread(this);
        if (t != null) {
            t.start();
        }
    }

    public void run() {
        while (true) {
            repaint();
            i++;
            if (i >= 4)
                i = 0;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }
    }

    //Overriding the update method to eliminate flickering
    public void update(Graphics g) {
        g.drawImage(pics[i], 10, 50, this); //repaint(g);
    }
}
