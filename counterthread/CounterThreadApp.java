import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CounterThreadApp extends JFrame implements Runnable {
    static JButton start = new JButton("Start"), stop = new JButton("Stop");
    static JLabel label = new JLabel();
    static Thread t;
    static int Count;
    static CounterThreadApp cta;

    CounterThreadApp() {
        this.setSize(300, 100);
        this.getContentPane().setLayout(new GridLayout(1, 3));
        this.getContentPane().add(label);
        this.getContentPane().add(start);
        this.getContentPane().add(stop);

        label.setForeground(Color.blue);
        this.getContentPane().setBackground(Color.white);
        label.setText(Integer.toString(Count = 0));

        startThread();

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                //stopThread();
                System.exit(0);
            }
        });

        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == start) {
                    startThread();
                }
            }
        });

        stop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == stop) {
                    stopThread(t);
                }
            }
        });
    }

    public static void main(String[] args) {
        cta = new CounterThreadApp();
        cta.setVisible(true);
    }

    public void run() {
        while (true) {
            Count++;
            output();
            try {
                t.sleep(10);
            } catch (InterruptedException e) {
            }
        }
    }

    public void output() {
        label.setText(Integer.toString(Count));
    }

    public void startThread() {
        t = new Thread(this);
        if (t != null) {
            t.start();
        }
    }

    public void stopThread(Thread t) {
        //t = null;
        try {
            t.stop();
        } catch (Exception e) {
            t = null;
            e.printStackTrace();
        }
    }
}
