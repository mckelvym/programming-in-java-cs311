import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class RandomLines extends JFrame {

    static int counter = 0;
    static Draw draw;
    static RandomLines randomLines;

    RandomLines() {
        super("Loading");
        this.setSize(1200, 700);

        draw = new Draw();

        this.getContentPane().add(draw);

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });

        draw.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent me) {
                draw.setBackground(Color.black);
                draw.setBackground(new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)));
                System.out.println("Cleared");
            }

            public void mousePressed(MouseEvent me) {
            }

            public void mouseReleased(MouseEvent me) {
            }

            public void mouseEntered(MouseEvent me) {
            }

            public void mouseExited(MouseEvent me) {
            }
        });
    }

    public static void main(String[] args) {
        randomLines = new RandomLines();
        randomLines.setVisible(true);
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
                counter++;
                if (counter >= 2) {
                    counter = 0;
                    draw.setBackground(Color.black);
                    draw.setBackground(new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)));
                }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }
        }

        public void paint(Graphics g) {
            int x1, x2, y1, y2, r, gr, b;
            for (int x = 0; x <= 200; x++) {
                x1 = (int) (Math.random() * 1200);
                x2 = (int) (Math.random() * 1200);
                y1 = (int) (Math.random() * 700);
                y2 = (int) (Math.random() * 700);
                r = (int) (Math.random() * 255);
                gr = (int) (Math.random() * 255);
                b = (int) (Math.random() * 255);
                g.setColor(new Color(r, gr, b));
                g.drawLine(x1, y1, x2, y2);
            }
        }
    }
}
