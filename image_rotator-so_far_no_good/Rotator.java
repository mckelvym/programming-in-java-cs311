/*     Rotator

An applet that animates a sequence of frames stored in a single file.
Based on JavaSpin by Blake Stone (http://www.dkw.com/bstone).

http://rsb.info.nih.gov/nih-image/Java/Rotator/

Parameters:
  image   Name of image file, no default
  frames  Number of frames, default = 'columns' * 'rows'
  rate    Rate in frames per second, default = 6
  columns Number of frames across the image, default = 'frames'
  rows    Number of frames down the image, default = 1

*/

import java.awt.*;

public class Rotator extends java.applet.Applet implements Runnable {

    int frameWidth, frameHeight;
    int nColumns = 1;
    int nRows = 1;
    int nFrames = 1;
    int delay = 167;  // milliseconds (6 frames/sec)
    int frame = 0;
    boolean suspended = false;
    Thread spinThread;
    Image spinImage;


    public void init() {
        String param = getParameter("width");
        if (param != null)
            frameWidth = Integer.parseInt(param);
        param = getParameter("height");
        if (param != null)
            frameHeight = Integer.parseInt(param);
        param = getParameter("frames");
        if (param != null)
            nFrames = Integer.parseInt(param);
        param = getParameter("rate");
        if (param != null)
            delay = 1000 / Integer.parseInt(param);
        param = getParameter("columns");
        if (param != null)
            nColumns = Integer.parseInt(param);
        param = getParameter("rows");
        if (param != null)
            nRows = Integer.parseInt(param);

        if (delay < 10)
            delay = 10;
        if (nFrames == 1)
            nFrames = nColumns * nRows;
        if (nColumns == 1 && nRows == 1)
            nColumns = nFrames;

        spinImage = getImage(getCodeBase(), getParameter("image"));
    }


    public void start() {
        if (spinThread == null) {
            spinThread = new Thread(this, "Spin");
            spinThread.start();
        }
    }


    public void run() {
        while (spinThread != null) {
            repaint();
            try {
                spinThread.sleep(delay);
            } catch (InterruptedException e) {
            }
        }
    }


    public void update(Graphics g) {
        paint(g);
    }


    public void paint(Graphics g) {
        //Draw current frame
        //Use negative coordinates so drawImage will crop frame
        g.drawImage(spinImage,
                -frameWidth * (frame % nColumns),
                -frameHeight * ((frame / nColumns) % nRows),
                this);
        if (++frame == nFrames) frame = 0;
    }


    public boolean mouseDown(Event evt, int x, int y) {
        if (suspended)
            spinThread.resume();
        else
            spinThread.suspend();
        suspended = !suspended;
        return true;
    }


    public void stop() {
        spinThread.stop();
        spinThread = null;
    }

}// Rotator
