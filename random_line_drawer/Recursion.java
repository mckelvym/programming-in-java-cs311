import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Recursion extends JFrame {

    static int counter = -1;
    static Recursion recursion;

    Recursion() {
        super("Loading");
        this.setSize(500, 500);

        Draw draw = new Draw();

        this.getContentPane().add(draw);

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        recursion = new Recursion();
        recursion.setVisible(true);
    }

    class Draw extends JApplet implements Runnable {

        Thread thread;

        Draw() {
            thread = new Thread(this);
            if (thread != null)
                thread.start();
        }

        public void run() {
            while (true) {
                repaint();
                //counter++;
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }
        }

        public void paint(Graphics g) {
            int x1, x2, y1, y2;
            x1 = (int) (Math.random() * 500);
            x2 = (int) (Math.random() * 500);
            y1 = (int) (Math.random() * 500);
            y2 = (int) (Math.random() * 500);
            g.setColor(new Color(0, 0, 200));
            g.drawLine(x1, y1, x2, y2);
        }
    }
}
