import java.awt.Graphics;
import java.awt.Event;
import java.awt.Font;
import java.awt.Color;
import java.awt.*;

public class ping2 extends java.applet.Applet implements Runnable {

    char currkey;
    int ypos = 100, y1pos = 100;
    int flag = 0, xpos, y, k = 0;
    Thread runner;
    int score1 = 0, score2 = 0;

    public void init() {
        setBackground(Color.black);
        setFont(new Font("Helvetica", Font.BOLD, 15));

    }

    public void start() {
        if (runner == null) ;
        {
            runner = new Thread(this);
            runner.start();
        }
    }

    public void stop() {
        if (runner != null) ;
        {
            runner.stop();
            runner = null;
        }
    }

    public void run() {
        while (true) {
            k += 10;
            s(20, 1);

            repeat();
        }
    }

    /* from L tp R D */
    public void s(int x, int y1) {
        for (xpos = x, y = y1; xpos <= this.size().width - 5 || y <= this.size().height - 8; y += 2, xpos += 2) {
            if (y == this.size().height - 9 || y == this.size().height - 8)
                s1(xpos, y);
            if (xpos == this.size().width - 10 || xpos == this.size().width - 9)
                s3(xpos, y);
            if ((y == y1pos - 4 || y == y1pos - 2 || y == y1pos - 3 || y == y1pos - 1 || y == y1pos + 1 || y == y1pos + 2 || y == y1pos + 3 || y == y1pos + 4 || y == y1pos + 5 || y == y1pos + 6 || y == y1pos + 7 || y == y1pos + 8 || y == y1pos + 9 || y == y1pos + 10 || y == y1pos + 11 || y == y1pos + 12 || y == y1pos + 13 || y == y1pos + 14 || y == y1pos + 15 || y == y1pos + 16 || y == y1pos + 17 || y == y1pos + 18 || y == y1pos + 19 || y == y1pos + 20 || y == y1pos + 21 || y == y1pos + 22 || y == y1pos + 23 || y == y1pos + 24 || y == y1pos + 25 || y == y1pos + 26 || y == y1pos + 27 || y == y1pos + 28 || y == y1pos + 29 || y == y1pos + 30) && (xpos == 244 || xpos == 245 || xpos == 246 || xpos == 247 || xpos == 248 || xpos == 249 || xpos == 250)) {
                score2++;
                s3(xpos, y);
            }
            repeat();
        }
    }

    /* from L to R U */
    public void s1(int x, int y1) {
        for (xpos = x, y = y; xpos <= this.size().width - 5; xpos += 2, y -= 2) {
            if (y == 5 || y == 4)
                s(xpos, y);
            if (xpos == this.size().width - 10 || xpos == this.size().width - 9)
                s2(xpos, y);
            if ((y == y1pos - 4 || y == y1pos - 2 || y == y1pos - 3 || y == y1pos - 1 || y == y1pos + 1 || y == y1pos + 2 || y == y1pos + 3 || y == y1pos + 4 || y == y1pos + 5 || y == y1pos + 6 || y == y1pos + 7 || y == y1pos + 8 || y == y1pos + 9 || y == y1pos + 10 || y == y1pos + 11 || y == y1pos + 12 || y == y1pos + 13 || y == y1pos + 14 || y == y1pos + 15 || y == y1pos + 16 || y == y1pos + 17 || y == y1pos + 18 || y == y1pos + 19 || y == y1pos + 20 || y == y1pos + 21 || y == y1pos + 22 || y == y1pos + 23 || y == y1pos + 24 || y == y1pos + 25 || y == y1pos + 26 || y == y1pos + 27 || y == y1pos + 28 || y == y1pos + 29 || y == y1pos + 30) && (xpos == 244 || xpos == 245 || xpos == 246 || xpos == 247 || xpos == 248 || xpos == 249 || xpos == 250)) {
                score2++;
                s2(xpos, y);
            }
            repeat();
        }
    }

    /* from R to L U */
    public void s2(int x, int y1) {
        for (xpos = x, y = y1; y >= 0; y -= 2, xpos -= 2) {
            if (y == 5 || y == 4)
                s3(xpos, y);
            if (xpos == 0 || xpos == 1)
                s1(xpos, y);
            if ((y == ypos - 4 || y == ypos - 2 || y == ypos - 3 || y == ypos - 1 || y == ypos + 1 || y == ypos + 2 || y == ypos + 3 || y == ypos + 4 || y == ypos + 5 || y == ypos + 6 || y == ypos + 7 || y == ypos + 8 || y == ypos + 9 || y == ypos + 10 || y == ypos + 11 || y == ypos + 12 || y == ypos + 13 || y == ypos + 14 || y == ypos + 15 || y == ypos + 16 || y == ypos + 17 || y == ypos + 18 || y == ypos + 19 || y == ypos + 20 || y == ypos + 21 || y == ypos + 22 || y == ypos + 23 || y == ypos + 24 || y == ypos + 25 || y == ypos + 26 || y == ypos + 27 || y == ypos + 28 || y == ypos + 29 || y == ypos + 30) && (xpos == 50 || xpos == 49 || xpos == 48 || xpos == 47 || xpos == 46 || xpos == 45)) {
                score1++;
                s1(xpos, y);
            }
            repeat();
        }
    }

    /* from R to L D */
    public void s3(int x, int y1) {
        for (xpos = x, y = y1; xpos >= 0; xpos -= 2, y += 2) {
            if (y == this.size().height - 9 || y == this.size().height - 8)
                s4(xpos, y);
            if (xpos == 0 || xpos == 1)
                s(xpos, y);
            if ((y == ypos - 4 || y == ypos - 2 || y == ypos - 3 || y == ypos - 1 || y == ypos + 1 || y == ypos + 2 || y == ypos + 3 || y == ypos + 4 || y == ypos + 5 || y == ypos + 6 || y == ypos + 7 || y == ypos + 8 || y == ypos + 9 || y == ypos + 10 || y == ypos + 11 || y == ypos + 12 || y == ypos + 13 || y == ypos + 14 || y == ypos + 15 || y == ypos + 16 || y == ypos + 17 || y == ypos + 18 || y == ypos + 19 || y == ypos + 20 || y == ypos + 21 || y == ypos + 22 || y == ypos + 23 || y == ypos + 24 || y == ypos + 25 || y == ypos + 26 || y == ypos + 27 || y == ypos + 28 || y == ypos + 29 || y == ypos + 30) && (xpos == 50 || xpos == 49 || xpos == 48 || xpos == 47 || xpos == 46 || xpos == 45)) {
                score1++;
                s(xpos, y);
            }
            repeat();
        }
    }

    /* from R to L U mid */
    public void s4(int x, int y1) {
        for (xpos = x, y = y1; xpos >= 0; xpos -= 2, y -= 2) {
            if (xpos == 5 || xpos == 4)
                s5(xpos, y);
            if (y == 5 || y == 4)
                s3(xpos, y);
            if ((y == ypos - 4 || y == ypos - 2 || y == ypos - 3 || y == ypos - 1 || y == ypos + 1 || y == ypos + 2 || y == ypos + 3 || y == ypos + 4 || y == ypos + 5 || y == ypos + 6 || y == ypos + 7 || y == ypos + 8 || y == ypos + 9 || y == ypos + 10 || y == ypos + 11 || y == ypos + 12 || y == ypos + 13 || y == ypos + 14 || y == ypos + 15 || y == ypos + 16 || y == ypos + 17 || y == ypos + 18 || y == ypos + 19 || y == ypos + 20 || y == ypos + 21 || y == ypos + 22 || y == ypos + 23 || y == ypos + 24 || y == ypos + 25 || y == ypos + 26 || y == ypos + 27 || y == ypos + 28 || y == ypos + 29 || y == ypos + 30) && (xpos == 50 || xpos == 49 || xpos == 48 || xpos == 47 || xpos == 46 || xpos == 45)) {
                score1++;
                s1(xpos, y);
            }
            repeat();
        }
    }

    /* from L to R U mid */
    public void s5(int x, int y1) {
        for (xpos = x, y = y1; y >= 0; xpos += 2, y -= 2) {
            if (y == 5 || y == 4) {
                k += 5;
                s(xpos, y);
            }
            if (xpos == this.size().width - 10 || xpos == this.size().width - 9)
                s4(xpos, y);
            if ((y == y1pos - 4 || y == y1pos - 2 || y == y1pos - 3 || y == y1pos - 1 || y == y1pos + 1 || y == y1pos + 2 || y == y1pos + 3 || y == y1pos + 4 || y == y1pos + 5 || y == y1pos + 6 || y == y1pos + 7 || y == y1pos + 8 || y == y1pos + 9 || y == y1pos + 10 || y == y1pos + 11 || y == y1pos + 12 || y == y1pos + 13 || y == y1pos + 15 || y == y1pos + 16 || y == y1pos + 17 || y == y1pos + 18 || y == y1pos + 19 || y == y1pos + 20 || y == y1pos + 21 || y == y1pos + 22 || y == y1pos + 23 || y == y1pos + 24 || y == y1pos + 25 || y == y1pos + 26 || y == y1pos + 27 || y == y1pos + 28 || y == y1pos + 29 || y == y1pos + 30) && (xpos == 244 || xpos == 245 || xpos == 246 || xpos == 247 || xpos == 248 || xpos == 249 || xpos == 250)) {
                score2++;
                s2(xpos, y);
            }
            repeat();
        }

    }

    public void repeat() {
        repaint();
        pause(20);
    }

    public boolean keyDown(Event evt, int key) {

        switch (key) {

            case Event.DOWN:

                if (ypos >= this.size().height - 30) {
                    ypos = this.size().height - 30;
                    break;
                }
                ypos += 3;

                break;
            case Event.UP:


                if (ypos <= 0) {
                    ypos = 0;
                    break;
                }
                ypos -= 3;

                break;
            case Event.LEFT:
                if (y1pos >= this.size().height - 30) {
                    y1pos = this.size().height - 30;
                    break;
                }
                y1pos += 3;

                break;
            case Event.RIGHT:


                if (y1pos <= 0) {
                    y1pos = 0;
                    break;
                }
                y1pos -= 3;

                break;
            default:
                if (key == 108)
                    flag = 1;
                else
                    flag = 0;

        }
        repaint();

        return true;
    }


    void pause(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
        }
    }

    public void paint(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(50, ypos, 5, 30);
        g.setColor(Color.blue);
        g.fillRect(250, y1pos, 5, 30);

        g.setColor(Color.yellow);
        g.fillOval(xpos, y, 10, 10);

        g.setColor(Color.white);
        g.drawString("PlayerA: " + score1, 10, 10);
        g.drawString("PlayerB: " + score2, 220, 10);
    }

}
