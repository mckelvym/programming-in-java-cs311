import java.awt.*;
import javax.swing.*;

public class ProgressDraw extends JFrame {

    static int progress = 0;
    static DrawThread drawThread;
    static ProgressDraw progressDraw;

    ProgressDraw() {
        super();
        this.setSize(300, 100);

        drawThread = new DrawThread();

        this.getContentPane().add(drawThread);
    }

    public static void main(String[] args) {
        progressDraw = new ProgressDraw();
        progressDraw.setVisible(true);
    }

    class DrawThread extends JApplet implements Runnable {

        Thread thread;

        DrawThread() {
            thread = new Thread(this);
            if (thread != null)
                thread.start();
        }

        public void run() {
            while (true) {
                try {
                    progress++;
                    if (progress == 300) {
                        progress = 0;
                    }
                    repaint();
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                }
            }
        }

        public void paint(Graphics g) {
            g.drawLine(progress, 0, progress, 100);
        }
    }
}
