// mark mckelvy
// april 15, 2004
// ThreadClock

import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;

public class Clock extends JFrame implements Runnable {
    // runs the clock
    static JLabel image = new JLabel();
    static JLabel time = new JLabel();
    static Thread thread;
    static int counter = 0;

    static Clock clock;

    Clock() {
        this.setSize(200, 300);
        this.getContentPane().setLayout(new GridLayout(2, 1));
        this.getContentPane().add(image);
        this.getContentPane().add(time);
        this.getContentPane().setBackground(Color.white);
        time.setForeground(Color.black);
        thread = new Thread(this);
        if (thread != null) {
            thread.start();
        }
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        clock = new Clock();
        clock.setVisible(true);
    }

    public void run() {
        while (true) {
            try {
                counter++;
                if (counter == 61) {
                    counter = 1;
                }
                paintInfo("Current Time: ");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
    }

    public void paintInfo(String info) {
        image.setIcon(new ImageIcon(this.getToolkit().getImage(Integer.toString(counter) + ".jpg")));
        int temp;
        Calendar c = Calendar.getInstance();
        System.out.println(info
                + Integer.toString(((temp = c.get(Calendar.HOUR_OF_DAY)) > 13) ? (temp -= 12) : (temp))
                + ":" + Integer.toString(c.get(Calendar.MINUTE))
                + ":" + Integer.toString(c.get(Calendar.SECOND)));
        time.setText(info
                + Integer.toString(((temp = c.get(Calendar.HOUR_OF_DAY)) > 13) ? (temp -= 12) : (temp))
                + ":" + Integer.toString(c.get(Calendar.MINUTE))
                + ":" + Integer.toString(c.get(Calendar.SECOND)));
    }
}
