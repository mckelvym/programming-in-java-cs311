import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class TicTacToe extends JFrame {

    static int counter = 0;
    static int xpos, ypos;
    static Dimension screen;
    static Font font;
    static JButton[] square;
    static Toolkit toolkit;

    static Open openDialog;
    static TicTacToe tictactoe;

    TicTacToe() {
        super("Tic Tac Toe!");
        this.setSize(400, 400);

        toolkit = this.getToolkit();
        screen = toolkit.getScreenSize();
        xpos = (int) screen.getWidth();
        ypos = (int) screen.getHeight();
        this.setLocation((xpos - 400) / 2, (ypos - 400) / 2);

        font = new Font("Comic Sans MS", Font.BOLD, 40);
        square = new JButton[9];
        for (int x = 0; x < 9; x++) {
            square[x] = new JButton();
            square[x].setBackground(Color.black);
            square[x].setFont(font);
        }

        this.getContentPane().setLayout(new GridLayout(3, 3));
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                this.getContentPane().add(square[counter++]);
            }
        }

        square[0].addMouseListener(new MouseListener() {
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    if (square[0].getText().equals("")) {
                        square[0].setText("X");
                        square[0].setBackground(Color.blue);
                    } else {
                        square[0].setText("");
                        square[0].setBackground(Color.black);
                    }
                } else {
                    if (square[0].getText().equals("")) {
                        square[0].setText("O");
                        square[0].setBackground(Color.red);
                    } else {
                        square[0].setText("");
                        square[0].setBackground(Color.black);
                    }
                }
                check();
            }

            public void mouseClicked(MouseEvent e) {
            }

            public void mousePressed(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }
        });
        square[1].addMouseListener(new MouseListener() {
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    if (square[1].getText().equals("")) {
                        square[1].setText("X");
                        square[1].setBackground(Color.blue);
                    } else {
                        square[1].setText("");
                        square[1].setBackground(Color.black);
                    }
                } else {
                    if (square[1].getText().equals("")) {
                        square[1].setText("O");
                        square[1].setBackground(Color.red);
                    } else {
                        square[1].setText("");
                        square[1].setBackground(Color.black);
                    }
                }
                check();
            }

            public void mouseClicked(MouseEvent e) {
            }

            public void mousePressed(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }
        });
        square[2].addMouseListener(new MouseListener() {
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    if (square[2].getText().equals("")) {
                        square[2].setText("X");
                        square[2].setBackground(Color.blue);
                    } else {
                        square[2].setText("");
                        square[2].setBackground(Color.black);
                    }
                } else {
                    if (square[2].getText().equals("")) {
                        square[2].setText("O");
                        square[2].setBackground(Color.red);
                    } else {
                        square[2].setText("");
                        square[2].setBackground(Color.black);
                    }
                }
                check();
            }

            public void mouseClicked(MouseEvent e) {
            }

            public void mousePressed(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }
        });
        square[3].addMouseListener(new MouseListener() {
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    if (square[3].getText().equals("")) {
                        square[3].setText("X");
                        square[3].setBackground(Color.blue);
                    } else {
                        square[3].setText("");
                        square[3].setBackground(Color.black);
                    }
                } else {
                    if (square[3].getText().equals("")) {
                        square[3].setText("O");
                        square[3].setBackground(Color.red);
                    } else {
                        square[3].setText("");
                        square[3].setBackground(Color.black);
                    }
                }
                check();
            }

            public void mouseClicked(MouseEvent e) {
            }

            public void mousePressed(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }
        });
        square[4].addMouseListener(new MouseListener() {
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    if (square[4].getText().equals("")) {
                        square[4].setText("X");
                        square[4].setBackground(Color.blue);
                    } else {
                        square[4].setText("");
                        square[4].setBackground(Color.black);
                    }
                } else {
                    if (square[4].getText().equals("")) {
                        square[4].setText("O");
                        square[4].setBackground(Color.red);
                    } else {
                        square[4].setText("");
                        square[4].setBackground(Color.black);
                    }
                }
                check();
            }

            public void mouseClicked(MouseEvent e) {
            }

            public void mousePressed(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }
        });
        square[5].addMouseListener(new MouseListener() {
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    if (square[5].getText().equals("")) {
                        square[5].setText("X");
                        square[5].setBackground(Color.blue);
                    } else {
                        square[5].setText("");
                        square[5].setBackground(Color.black);
                    }
                } else {
                    if (square[5].getText().equals("")) {
                        square[5].setText("O");
                        square[5].setBackground(Color.red);
                    } else {
                        square[5].setText("");
                        square[5].setBackground(Color.black);
                    }
                }
                check();
            }

            public void mouseClicked(MouseEvent e) {
            }

            public void mousePressed(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }
        });
        square[6].addMouseListener(new MouseListener() {
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    if (square[6].getText().equals("")) {
                        square[6].setText("X");
                        square[6].setBackground(Color.blue);
                    } else {
                        square[6].setText("");
                        square[6].setBackground(Color.black);
                    }
                } else {
                    if (square[6].getText().equals("")) {
                        square[6].setText("O");
                        square[6].setBackground(Color.red);
                    } else {
                        square[6].setText("");
                        square[6].setBackground(Color.black);
                    }
                }
                check();
            }

            public void mouseClicked(MouseEvent e) {
            }

            public void mousePressed(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }
        });
        square[7].addMouseListener(new MouseListener() {
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    if (square[7].getText().equals("")) {
                        square[7].setText("X");
                        square[7].setBackground(Color.blue);
                    } else {
                        square[7].setText("");
                        square[7].setBackground(Color.black);
                    }
                } else {
                    if (square[7].getText().equals("")) {
                        square[7].setText("O");
                        square[7].setBackground(Color.red);
                    } else {
                        square[7].setText("");
                        square[7].setBackground(Color.black);
                    }
                }
                check();
            }

            public void mouseClicked(MouseEvent e) {
            }

            public void mousePressed(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }
        });
        square[8].addMouseListener(new MouseListener() {
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    if (square[8].getText().equals("")) {
                        square[8].setText("X");
                        square[8].setBackground(Color.blue);
                    } else {
                        square[8].setText("");
                        square[8].setBackground(Color.black);
                    }
                } else {
                    if (square[8].getText().equals("")) {
                        square[8].setText("O");
                        square[8].setBackground(Color.red);
                    } else {
                        square[8].setText("");
                        square[8].setBackground(Color.black);
                    }
                }
                check();
            }

            public void mouseClicked(MouseEvent e) {
            }

            public void mousePressed(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }
        });

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        tictactoe = new TicTacToe();
        openDialog = new Open();
        openDialog.setVisible(true);
    }

    public void check() {

        boolean resetsquares = true;
        String temp = new String("");

        for (int x = 0; x < 9; x++) {
            if (square[x].getText().equals("")) {
                resetsquares = false;
            }
        }
        if (resetsquares) {
            for (int x = 0; x < 9; x++) {
                square[x].setText("");
                square[x].setBackground(Color.black);
            }
        }
        for (int x = 1; x <= 2; x++) {
            if (x == 1)
                temp = new String("O");
            if (x == 2)
                temp = new String("X");

            if (square[0].getText().equals(temp) && square[1].getText().equals(temp) && square[2].getText().equals(temp)) {
                handleWin(temp);
            }
            if (square[3].getText().equals(temp) && square[4].getText().equals(temp) && square[5].getText().equals(temp)) {
                handleWin(temp);
            }
            if (square[6].getText().equals(temp) && square[7].getText().equals(temp) && square[8].getText().equals(temp)) {
                handleWin(temp);
            }
            if (square[0].getText().equals(temp) && square[3].getText().equals(temp) && square[6].getText().equals(temp)) {
                handleWin(temp);
            }
            if (square[1].getText().equals(temp) && square[4].getText().equals(temp) && square[7].getText().equals(temp)) {
                handleWin(temp);
            }
            if (square[2].getText().equals(temp) && square[5].getText().equals(temp) && square[8].getText().equals(temp)) {
                handleWin(temp);
            }
            if (square[0].getText().equals(temp) && square[4].getText().equals(temp) && square[8].getText().equals(temp)) {
                handleWin(temp);
            }
            if (square[2].getText().equals(temp) && square[4].getText().equals(temp) && square[6].getText().equals(temp)) {
                handleWin(temp);
            }
        }
    }

    public void handleWin(String temp) {
        ShowWin showWin = new ShowWin(temp);
        showWin.setVisible(true);
        tictactoe.setVisible(false);
    }

    public void reset() {
        for (int x = 0; x < 9; x++) {
            square[x].setBackground(Color.black);
            square[x].setText("");
        }
    }

    static class Open extends JFrame implements Runnable {

        int counter = 0;
        JLabel title1, title2;
        Thread thread;

        Open() {
            super("Tic Tac Toe");
            this.setSize(170, 70);
            this.setLocation((xpos - 170) / 2, (ypos - 200) / 2);

            title1 = new JLabel("Right Click: X");
            title2 = new JLabel("Left  Click: Y");
            title1.setForeground(Color.blue);
            title2.setForeground(Color.red);
            title1.setHorizontalAlignment(JLabel.CENTER);
            title2.setHorizontalAlignment(JLabel.CENTER);
            this.getContentPane().setBackground(Color.black);

            this.getContentPane().setLayout(new GridLayout(2, 1));
            this.getContentPane().add(title1);
            this.getContentPane().add(title2);

            thread = new Thread(this);
            if (thread != null)
                thread.start();

            this.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent we) {
                    tictactoe.setVisible(true);
                }
            });
        }

        public void run() {
            while (true) {
                if (counter >= 1) {
                    tictactoe.setVisible(true);
                    //thread.stop();
                    dispose();
                }
                counter++;
                try {
                    Thread.sleep(2500);
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }
        }
    }

    class ShowWin extends JFrame {

        JButton ok;
        JLabel title;
        String win;

        ShowWin(String temp) {
            super("Winner!");
            this.setSize(130, 100);
            this.setLocation((xpos - 100) / 2, (ypos - 100) / 2);

            win = temp;
            title = new JLabel(win + " Wins!");
            ok = new JButton("Ok");

            this.getContentPane().setBackground(Color.black);
            ok.setForeground(Color.black);
            title.setHorizontalAlignment(JLabel.CENTER);
            if (win.equals("X"))
                title.setForeground(Color.blue);
            if (win.equals("O"))
                title.setForeground(Color.red);

            this.getContentPane().setLayout(new GridLayout(2, 1));
            this.getContentPane().add(title);
            this.getContentPane().add(ok);

            ok.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == ok) {
                        reset();
                        tictactoe.setVisible(true);
                        dispose();
                    }
                }
            });
            this.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent we) {
                    reset();
                    tictactoe.setVisible(true);
                    dispose();
                }
            });
        }
    }
}
