import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class RandomLines2 extends JFrame {

    static Draw draw;
    static RandomLines2 randomLines;

    RandomLines2() {
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
                draw.setBackground(Color.white);
                //draw.setBackground(new Color((int)(Math.random() * 255),(int)(Math.random() * 255),(int)(Math.random() * 255)));
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
        randomLines = new RandomLines2();
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
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }
        }

        public void paint(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            int x1, x2, y1, y2, r, gr, b;
            for (int x = 0; x < 50; x++) {
                x1 = (int) (Math.random() * 1200);
                x2 = (int) (Math.random() * 1200);
                y1 = (int) (Math.random() * 700);
                y2 = (int) (Math.random() * 700);
                r = (int) (Math.random() * 255);
                gr = (int) (Math.random() * 255);
                b = (int) (Math.random() * 255);
                g2d.setColor(new Color(r, gr, b));
                //g2d.setStroke(new BasicStroke(10));
                g2d.draw3DRect(x1 - 50, y1 - 50, x2, y2, true);
            }
            //g.drawLine(x1, y1, x2, y2);
            //}
        }
    }
}
