import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Loading extends JFrame {

    static int counter = -1;
    static Loading loading;

    Loading() {
        super("Loading");
        this.setSize(300, 80);

        Draw draw = new Draw();

        this.getContentPane().add(draw);

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        loading = new Loading();
        loading.setVisible(true);
    }

    class Draw extends JApplet implements Runnable {

        boolean drawpercent = true;
        Thread thread;

        Draw() {
            thread = new Thread(this);
            if (thread != null)
                thread.start();
        }

        public void run() {
            while (true) {
                repaint();
                counter++;
                if (counter == 300) {
                    counter = -1;
                }
                try {
                    Thread.sleep(30);
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }
        }

        public void paint(Graphics g) {
            g.setColor(new Color(0, 0, 200));
            for (int x = -1; x < (counter - 1); x++) {
                g.drawLine(x, 0, x, 60);
            }
            g.setColor(Color.white);
            for (int x = (counter - 1); x <= 300; x++) {
                g.drawLine(x, 0, x, 60);
            }
            g.setColor(Color.black);
            g.drawString("Loading...", 0, 50);
            g.drawString("Loading...", 1, 50);
            if (drawpercent) {
                for (int x = 0; x <= 1; x++) {
                    g.drawString(Integer.toString((counter / 3) + 1) + "%", 265, 50);
                    g.drawString(Integer.toString((counter / 3) + 1) + "%", 264, 50);
                }
            } else
                drawpercent = !drawpercent;
        }
    }
}
