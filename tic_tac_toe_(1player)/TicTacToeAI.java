import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class TicTacToeAI extends JFrame {

    static int counter = 0;
    static int xpos, ypos;
    static Color playerColor, computerColor;
    static Dimension screen;
    static Font font;
    static JButton[] square;
    static String playerSymbol, computerSymbol;
    static Toolkit toolkit;

    static ShowWin showWin;
    static Open openDialog;
    static TicTacToeAI tictactoe;

    TicTacToeAI() {
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

        square[0].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == square[0]) {
                    if (square[0].getText().equals("")) {
                        update(0, true);
                    }
                }
            }
        });
        square[1].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == square[1]) {
                    if (square[1].getText().equals("")) {
                        update(1, true);
                    }
                }
            }
        });
        square[2].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == square[2]) {
                    if (square[2].getText().equals("")) {
                        update(2, true);
                    }
                }
            }
        });
        square[3].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == square[3]) {
                    if (square[3].getText().equals("")) {
                        update(3, true);
                    }
                }
            }
        });
        square[4].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == square[4]) {
                    if (square[4].getText().equals("")) {
                        update(4, true);
                    }
                }
            }
        });
        square[5].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == square[5]) {
                    if (square[5].getText().equals("")) {
                        update(5, true);
                    }
                }
            }
        });
        square[6].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == square[6]) {
                    if (square[6].getText().equals("")) {
                        update(6, true);
                    }
                }
            }
        });
        square[7].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == square[7]) {
                    if (square[7].getText().equals("")) {
                        update(7, true);
                    }
                }
            }
        });
        square[8].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == square[8]) {
                    if (square[8].getText().equals("")) {
                        update(8, true);
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        tictactoe = new TicTacToeAI();
        openDialog = new Open();
        showWin = new ShowWin();
        openDialog.setVisible(true);
    }

    public void goAI() {
        boolean done = false;
        int rand;

        while (!done) {
            rand = (int) (Math.random() * 9);
            if (square[rand].getText().equals("")) {
                done = true;
                update(rand, false);
            }
        }
    }

    public void update(int index, boolean isUser) {
        if (isUser) {
            square[index].setBackground(playerColor);
            square[index].setText(playerSymbol);
            check();
            goAI();
        } else {
            check();
            square[index].setBackground(computerColor);
            square[index].setText(computerSymbol);
            check();
        }
    }

    public void check() {

        boolean resetsquares = false;
        boolean isFull = true;
        String temp = new String("");

        for (int x = 0; x < 9; x++) {
            if (square[x].getText().equals("")) {
                isFull = false;
            }
        }
        if (true) {
            for (int x = 1; x <= 2; x++) {
                if (x == 1)
                    temp = new String("O");
                else if (x == 2)
                    temp = new String("X");

                if (square[0].getText().equals(temp) && square[1].getText().equals(temp) && square[2].getText().equals(temp)) {
                    handleWin(temp);
                } else if (square[3].getText().equals(temp) && square[4].getText().equals(temp) && square[5].getText().equals(temp)) {
                    handleWin(temp);
                } else if (square[6].getText().equals(temp) && square[7].getText().equals(temp) && square[8].getText().equals(temp)) {
                    handleWin(temp);
                } else if (square[0].getText().equals(temp) && square[3].getText().equals(temp) && square[6].getText().equals(temp)) {
                    handleWin(temp);
                } else if (square[1].getText().equals(temp) && square[4].getText().equals(temp) && square[7].getText().equals(temp)) {
                    handleWin(temp);
                } else if (square[2].getText().equals(temp) && square[5].getText().equals(temp) && square[8].getText().equals(temp)) {
                    handleWin(temp);
                } else if (square[0].getText().equals(temp) && square[4].getText().equals(temp) && square[8].getText().equals(temp)) {
                    handleWin(temp);
                } else if (square[2].getText().equals(temp) && square[4].getText().equals(temp) && square[6].getText().equals(temp)) {
                    handleWin(temp);
                } else if (isFull) {
                    resetsquares = true;
                }
            }
        }
        if (resetsquares) {
            reset();
            tictactoe.setVisible(false);
            openDialog.setVisible(true);
        }

    }

    public void handleWin(String temp) {
        showWin.update(temp);
        showWin.setVisible(true);
        openDialog.setVisible(false);
        tictactoe.setVisible(false);
    }

    public void reset() {
        for (int x = 0; x < 9; x++) {
            square[x].setBackground(Color.black);
            square[x].setText("");
        }
    }

    static class Open extends JFrame {

        GoFirst gf;
        JLabel title;
        JButton title1, title2;

        Open() {
            super("Tic Tac Toe");
            this.setSize(170, 100);
            this.setLocation((xpos - 170) / 2, (ypos - 230) / 2);

            title = new JLabel("Choose One:");
            title1 = new JButton("Right Click: X");
            title2 = new JButton("Left  Click: Y");
            title.setForeground(Color.green);
            title1.setForeground(Color.blue);
            title2.setForeground(Color.red);
            title1.setBackground(Color.black);
            title2.setBackground(Color.black);
            this.getContentPane().setBackground(Color.black);

            this.getContentPane().setLayout(new GridLayout(3, 1));
            this.getContentPane().add(title);
            this.getContentPane().add(title1);
            this.getContentPane().add(title2);

            title1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == title1) {
                        playerSymbol = new String("X");
                        playerColor = Color.blue;
                        computerSymbol = new String("O");
                        computerColor = Color.red;
                        gf = new GoFirst();
                        gf.setVisible(true);
                        openDialog.setVisible(false);
                        //dispose();
                    }
                }
            });
            title2.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == title2) {
                        playerSymbol = new String("O");
                        playerColor = Color.red;
                        computerSymbol = new String("X");
                        computerColor = Color.blue;
                        gf = new GoFirst();
                        gf.setVisible(true);
                        openDialog.setVisible(false);
                        //dispose();
                    }
                }
            });
            this.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent we) {
                    System.exit(0);
                }
            });
        }
    }

    static class ShowWin extends JFrame {

        JButton ok;
        JLabel title;
        String win;

        ShowWin() {
            super("Winner!");
            this.setSize(130, 100);
            this.setLocation((xpos - 100) / 2, (ypos - 100) / 2);

            title = new JLabel();
            ok = new JButton("Ok");

            this.getContentPane().setBackground(Color.black);
            ok.setForeground(Color.black);
            title.setHorizontalAlignment(JLabel.CENTER);

            this.getContentPane().setLayout(new GridLayout(2, 1));
            this.getContentPane().add(title);
            this.getContentPane().add(ok);

            ok.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == ok) {
                        tictactoe.reset();
                        openDialog.setVisible(true);
                        showWin.setVisible(false);
                    }
                }
            });
            this.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent we) {
                    System.exit(0);
                }
            });
        }

        public void update(String temp) {
            win = temp;
            this.setLocation((xpos - 100) / 2, (ypos - 100) / 2);
            title.setText(win + " Wins!");
            if (win.equals("X"))
                title.setForeground(Color.blue);
            if (win.equals("O"))
                title.setForeground(Color.red);
        }
    }

    static class GoFirst extends JFrame {

        JButton yes, no;
        JLabel title1;
        JPanel upper, lower;

        GoFirst() {
            super("Go First?");
            this.setSize(200, 100);
            this.setLocation((xpos - 200) / 2, (ypos - 100) / 2);

            yes = new JButton("Yes");
            no = new JButton("No");
            title1 = new JLabel("Would You Like To Go First?");
            upper = new JPanel();
            lower = new JPanel();

            upper.setBackground(Color.black);
            lower.setBackground(Color.black);
            title1.setForeground(Color.green);
            title1.setHorizontalAlignment(JLabel.CENTER);

            this.getContentPane().setLayout(new GridLayout(2, 1));
            this.getContentPane().add(upper);
            this.getContentPane().add(lower);
            upper.setLayout(new GridLayout(1, 1));
            upper.add(title1);
            lower.add(yes);
            lower.add(no);

            yes.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == yes) {
                        tictactoe.setVisible(true);
                        dispose();
                    }
                }
            });
            no.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == no) {
                        tictactoe.update((int) (Math.random() * 9), false);
                        tictactoe.setVisible(true);
                        dispose();
                    }
                }
            });
            this.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent we) {
                    System.exit(0);
                }
            });
        }
    }
}
// hangs when there is a tie
