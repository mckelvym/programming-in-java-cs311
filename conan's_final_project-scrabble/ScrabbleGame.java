//http://www.wdwcollegeprogram.com/sap/its/mimes/zh_wdwcp/html_home/index-ie.html

import java.awt.*;
import java.awt.event.*;
import java.awt.Graphics.*;
import java.applet.*;
import javax.swing.*;
import java.lang.String.*;
import javax.swing.border.*;
import java.lang.Object.*;
import javax.swing.border.*;
import javax.swing.event.*;
import java.lang.*;

public class ScrabbleGame extends JApplet implements ActionListener, MouseListener {
    JPanel p1, info, playp;
    ;
    JButton[][] board;
    JButton[] tw, tl, dw, dl, rg, pp, plp;
    JButton strt, undo, draw, place, done, yea;
    JTextArea a1;
    JLabel l1, score;
    JButton y, n;
    JDialog di;
    String[] pl1s = {"-", "-", "-", "-", "-", "-", "-"};
    String[] pl2s = {"-", "-", "-", "-", "-", "-", "-"};
    String tlet = "", wrd = "";
    int i, j, twc, dlc, dwc, tlc, rgc, ppc, playc, pls1, pls2, sx, twrd = 0;
    int ac = 9, bc = 2, cc = 2, dc = 4, ec = 12, fc = 2, gc = 3, hc = 2, ic = 9, jc = 1, kc = 1, lc = 4, mc = 2, nc = 6, oc = 8, pc = 2, qc = 1, rc = 6, sc = 4, tc = 6, uc = 4, vc = 2, wc = 2, xc = 1, yc = 2, zc = 1;
    int as = 1, bs = 3, cs = 3, ds = 2, es = 1, fs = 4, gs = 2, hs = 4, is = 1, js = 8, ks = 5, ls = 1, ms = 3, ns = 1, os = 1, ps = 3, qs = 10, rs = 1, ss = 1, ts = 1, us = 1, vs = 4, ws = 4, xs = 8, ys = 4, zs = 10;
    boolean flg = false, twid = false, dwid = false, player1 = true;

    public void init() {
        p1 = new JPanel();
        info = new JPanel();
        playp = new JPanel();
        undo = new JButton("Undo");
        undo.addActionListener(this);
        draw = new JButton("Draw");
        draw.addActionListener(this);
        done = new JButton("Place");
        done.addActionListener(this);
        place = new JButton("Across");
        place.addActionListener(this);
        yea = new JButton("OK");
        yea.addActionListener(this);
        tw = new JButton[8];
        tl = new JButton[12];
        dw = new JButton[16];
        dl = new JButton[24];
        rg = new JButton[165];
        pp = new JButton[7];
        plp = new JButton[7];
        board = new JButton[15][15];
        a1 = new JTextArea();
        twc = dlc = dwc = tlc = rgc = ppc = playc = 0;
        pls1 = pls2 = 0;
        score = new JLabel("Player1 Score: " + pls1);

        p1.setLayout(new GridLayout(15, 15));

        for (i = 0; i < 15; i++) {
            for (j = 0; j < 15; j++) {
                if (i == 7 && j == 7) {
                    strt = new JButton("*");
                    strt.setBackground(new Color(255, 0, 102));
                    strt.setForeground(Color.black);
                    strt.setSize(10, 10);
                    strt.addActionListener(this);
                    board[i][j] = strt;
                } else if ((i == 0 && j == 0) || (i == 0 && j == 7) || (i == 0 && j == 14) || (i == 7 && j == 0) || (i == 7 && j == 14) || (i == 14 && j == 0) || (i == 14 && j == 7) || (i == 14 && j == 14)) {
                    tw[twc] = new JButton();
                    tw[twc].setText("TW");
                    tw[twc].setBackground(new Color(255, 0, 102));
                    tw[twc].setForeground(Color.black);
                    tw[twc].addActionListener(this);
                    tw[twc].setSize(10, 10);
                    tw[twc].addMouseListener(this);
                    board[i][j] = tw[twc];
                    twc++;
                } else if ((i == 1 && (j == 1 || j == 13)) || (i == 2 && (j == 2 || j == 12)) || (i == 3 && (j == 3 || j == 11)) || (i == 4 && (j == 4 || j == 10)) || (i == 10 && (j == 4 || j == 10)) || (i == 11 && (j == 3 || j == 11)) || (i == 12 && (j == 2 || j == 12)) || (i == 13 && (j == 1 || j == 13))) {
                    dw[dwc] = new JButton();
                    dw[dwc].setText("DW");
                    dw[dwc].setBackground(new Color(255, 153, 153));
                    dw[dwc].setForeground(Color.black);
                    dw[dwc].addActionListener(this);
                    dw[dwc].setSize(10, 10);
                    dw[dwc].addMouseListener(this);
                    board[i][j] = dw[dwc];
                    dwc++;
                } else if ((i == 1 && (j == 5 || j == 9)) || (i == 5 && (j == 1 || j == 5 || j == 9 || j == 13)) || (i == 9 && (j == 1 || j == 5 || j == 9 || j == 13)) || (i == 13 && (j == 5 || j == 9))) {
                    tl[tlc] = new JButton();
                    tl[tlc].setText("TL");
                    tl[tlc].setBackground(new Color(102, 153, 255));
                    tl[tlc].addActionListener(this);
                    tl[tlc].setForeground(Color.black);
                    tl[tlc].setSize(10, 10);
                    tl[tlc].addMouseListener(this);
                    board[i][j] = tl[tlc];
                    tlc++;
                } else if ((i == 0 && (j == 3 || j == 11)) || (i == 2 && (j == 6 || j == 8)) || (i == 3 && (j == 0 || j == 7 || j == 14)) || (i == 6 && (j == 2 || j == 6 || j == 8 || j == 12)) || (i == 7 && (j == 3 || j == 11)) || (i == 8 && (j == 2 || j == 6 || j == 8 || j == 12)) || (i == 11 && (j == 0 || j == 7 || j == 14)) || (i == 12 && (j == 6 || j == 8)) || (i == 14 && (j == 3 || j == 11))) {
                    dl[dlc] = new JButton();
                    dl[dlc].setText("DL");
                    dl[dlc].setBackground(new Color(204, 255, 255));
                    dl[dlc].addActionListener(this);
                    dl[dlc].setForeground(Color.black);
                    dl[dlc].setSize(10, 10);
                    dl[dlc].addMouseListener(this);
                    board[i][j] = dl[dlc];
                    dlc++;
                } else {
                    rg[rgc] = new JButton();
                    rg[rgc].addMouseListener(this);
                    rg[rgc].addActionListener(this);
                    rg[rgc].setText("-");
                    rg[rgc].setBackground(new Color(245, 245, 220));
                    rg[rgc].setForeground(Color.black);
                    rg[rgc].setSize(10, 10);
                    board[i][j] = rg[rgc];
                    rgc++;
                }
            }
        }
        for (i = 0; i < 15; i++) {
            for (j = 0; j < 15; j++) {
                p1.add(board[i][j]);
            }
        }
        for (i = 0; i < 7; i++) {
            pp[ppc] = new JButton("-");
            pp[ppc].setForeground(Color.black);
            pp[ppc].setBackground(new Color(245, 245, 220));
            pp[i].addActionListener(this);
            info.add(pp[ppc]);
            ppc++;
        }
        for (i = 0; i < 7; i++) {
            plp[i] = new JButton("-");
            plp[i].setForeground(Color.black);
            plp[i].setBackground(new Color(245, 245, 220));
            playp.add(plp[i]);
        }
        playp.add(place);
        info.add(draw);
        playp.add(undo);
        done.setEnabled(false);
        playp.add(done);
        info.add(score);
        this.getContentPane().setLayout(new FlowLayout());
        this.getContentPane().add(p1);
        this.getContentPane().add(info);
        this.getContentPane().add(playp);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == draw) {
            draw.setEnabled(false);
            done.setEnabled(true);
            for (i = 0; i < 7; i++) {
                pp[i].setEnabled(true);
                do {
                    int drn = (int) ((Math.random()) * 26) + 1;
                    if (drn == 1 && ac > 0 && pp[i].getText() == "-") {
                        pp[i].setText("a");
                        ac--;
                        flg = true;
                    } else if (drn == 2 && bc > 0 && pp[i].getText() == "-") {
                        pp[i].setText("b");
                        bc--;
                        flg = true;
                    } else if (drn == 3 && cc > 0 && pp[i].getText() == "-") {
                        pp[i].setText("c");
                        cc--;
                        flg = true;
                    } else if (drn == 4 && dc > 0 && pp[i].getText() == "-") {
                        pp[i].setText("d");
                        dc--;
                        flg = true;
                    } else if (drn == 5 && ec > 0 && pp[i].getText() == "-") {
                        pp[i].setText("e");
                        ec--;
                        flg = true;
                    } else if (drn == 6 && fc > 0 && pp[i].getText() == "-") {
                        pp[i].setText("f");
                        fc--;
                        flg = true;
                    } else if (drn == 7 && gc > 0 && pp[i].getText() == "-") {
                        pp[i].setText("g");
                        gc--;
                        flg = true;
                    } else if (drn == 8 && hc > 0 && pp[i].getText() == "-") {
                        pp[i].setText("h");
                        hc--;
                        flg = true;
                    } else if (drn == 9 && ic > 0 && pp[i].getText() == "-") {
                        pp[i].setText("i");
                        ic--;
                        flg = true;
                    } else if (drn == 10 && jc > 0 && pp[i].getText() == "-") {
                        pp[i].setText("j");
                        jc--;
                        flg = true;
                    } else if (drn == 11 && kc > 0 && pp[i].getText() == "-") {
                        pp[i].setText("k");
                        kc--;
                        flg = true;
                    } else if (drn == 12 && lc > 0 && pp[i].getText() == "-") {
                        pp[i].setText("l");
                        lc--;
                        flg = true;
                    } else if (drn == 13 && mc > 0 && pp[i].getText() == "-") {
                        pp[i].setText("m");
                        mc--;
                        flg = true;
                    } else if (drn == 14 && nc > 0 && pp[i].getText() == "-") {
                        pp[i].setText("n");
                        nc--;
                        flg = true;
                    } else if (drn == 15 && oc > 0 && pp[i].getText() == "-") {
                        pp[i].setText("o");
                        oc--;
                        flg = true;
                    } else if (drn == 16 && pc > 0 && pp[i].getText() == "-") {
                        pp[i].setText("p");
                        pc--;
                        flg = true;
                    } else if (drn == 17 && qc > 0 && pp[i].getText() == "-") {
                        pp[i].setText("q");
                        qc--;
                        flg = true;
                    } else if (drn == 18 && rc > 0 && pp[i].getText() == "-") {
                        pp[i].setText("r");
                        rc--;
                        flg = true;
                    } else if (drn == 19 && sc > 0 && pp[i].getText() == "-") {
                        pp[i].setText("s");
                        sc--;
                        flg = true;
                    } else if (drn == 20 && tc > 0 && pp[i].getText() == "-") {
                        pp[i].setText("t");
                        tc--;
                        flg = true;
                    } else if (drn == 21 && uc > 0 && pp[i].getText() == "-") {
                        pp[i].setText("u");
                        uc--;
                        flg = true;
                    } else if (drn == 22 && vc > 0 && pp[i].getText() == "-") {
                        pp[i].setText("v");
                        vc--;
                        flg = true;
                    } else if (drn == 23 && wc > 0 && pp[i].getText() == "-") {
                        pp[i].setText("w");
                        wc--;
                        flg = true;
                    } else if (drn == 24 && xc > 0 && pp[i].getText() == "-") {
                        pp[i].setText("x");
                        xc--;
                        flg = true;
                    } else if (drn == 25 && yc > 0 && pp[i].getText() == "-") {
                        pp[i].setText("y");
                        yc--;
                        flg = true;
                    } else if (drn == 26 && zc > 0 && pp[i].getText() == "-") {
                        pp[i].setText("z");
                        zc--;
                        flg = true;
                    }
                    if (pp[i].getText() != "-") {
                        flg = true;
                    }
                } while (flg == false);
            }
        }
        flg = false;
        if (e.getSource() == done) {
            done.setEnabled(false);
            draw.setEnabled(true);
            if (player1 == true) {
                for (i = 0; i < 7; i++) {
                    pl1s[i] = pp[i].getText();
                    pp[i].setText(pl2s[i]);
                }
                player1 = false;
                score.setText("Player2 Score: " + pls2);
            } else if (player1 == false) {
                player1 = true;
                score.setText("Player1 Score: " + pls1);
                for (i = 0; i < 7; i++) {
                    pl2s[i] = pp[i].getText();
                    pp[i].setText(pl1s[i]);
                }
            }
        }
        for (i = 0; i < 7; i++) {
            if (e.getSource() == pp[i]) {
                if (pp[i].getText() == "-") {
                } else {
                    plp[playc].setText(pp[i].getText());
                    playc++;
                    pp[i].setText("-");
                }
            }
        }
        for (i = 0; i < 15; i++) {
            for (j = 0; j < 15; j++) {
                if (e.getSource() == board[i][j]) {
                    if (place.getText() == "Across") {
                        sx = 0;
                        int sj = j;
                        for (int k = 0; k < playc; k++) {
                            if (board[sx][j].getText() == "-" || board[sx][j].getText() == "TW" || board[sx][j].getText() == "TL" || board[sx][j].getText() == "DW" || board[sx][j].getText() == "DL" || board[sx][j].getText() == "*") {
                                scoringPart(board[i][j].getText());
                                board[i][sj].setText(plp[sx].getText());
                                plp[sx].setText("-");
                                sx++;
                                sj++;
                            } else {
                                k--;
                                sj++;
                            }
                        }
                        if (twid == true) {
                            if (player1 == true) {
                                pls1 = pls1 - twrd;
                                twrd = twrd * 3;
                                pls1 = pls1 + twrd;
                                score.setText("Player1 Score: " + pls1);
                                twid = false;
                            } else if (player1 == false) {
                                pls2 = pls2 - twrd;
                                twrd = twrd * 3;
                                pls2 = pls2 + twrd;
                                score.setText("Player2 Score: " + pls2);
                                twid = false;
                            }
                        } else if (dwid == true) {
                            if (player1 == true) {
                                pls1 = pls1 - twrd;
                                twrd = twrd * 2;
                                pls1 = pls1 + twrd;
                                score.setText("Player1 Score:" + pls1);
                                dwid = false;
                            } else if (player1 == false) {
                                pls2 = pls2 - twrd;
                                twrd = twrd * 2;
                                pls2 = pls2 + twrd;
                                score.setText("Player2 Score:" + pls2);
                                dwid = false;
                            }
                        }
                        playc = 0;
                        twrd = 0;
                    } else if (place.getText() == "Down") {
                        sx = i;
                        int sj = 0;
                        for (int k = 0; k < playc; k++) {
                            if (board[sx][j].getText() == "-" || board[sx][j].getText() == "TW" || board[sx][j].getText() == "TL" || board[sx][j].getText() == "DW" || board[sx][j].getText() == "DL" || board[sx][j].getText() == "*") {
                                int tsx = sx;
                                sx = sj;
                                scoringPart(board[i][j].getText());
                                sx = tsx;
                                board[sx][j].setText(plp[sj].getText());
                                plp[sj].setText("-");
                                sx++;
                                sj++;
                            } else {
                                k--;
                                sx++;
                            }
                        }
                        if (twid == true) {
                            if (player1 == true) {
                                pls1 = pls1 - twrd;
                                twrd = twrd * 3;
                                pls1 = pls1 + twrd;
                                score.setText("Player1 Score: " + pls1);
                                twid = false;
                            } else if (player1 == false) {
                                pls2 = pls2 - twrd;
                                twrd = twrd * 3;
                                pls2 = pls2 + twrd;
                                score.setText("Player2 Score: " + pls2);
                                twid = false;
                            }
                        } else if (dwid == true) {
                            if (player1 == true) {
                                pls1 = pls1 - twrd;
                                twrd = twrd * 2;
                                pls1 = pls1 + twrd;
                                score.setText("Player1 Score:" + pls1);
                                dwid = false;
                            } else if (player1 == false) {
                                pls2 = pls2 - twrd;
                                twrd = twrd * 2;
                                pls2 = pls2 + twrd;
                                score.setText("Player2 Score:" + pls2);
                                dwid = false;
                            }
                        }
                        playc = 0;
                        twrd = 0;
                    }
                }
            }
        }
        if (e.getSource() == yea) {
            di.hide();
        }
        if (e.getSource() == undo) {
            for (i = 6; i >= 0; i--) {
                if (plp[i].getText() != "-") {
                    for (j = 0; j < 7; j++) {
                        if (pp[j].getText() == "-") {
                            pp[j].setText(plp[i].getText());
                            plp[i].setText("-");
                            playc = 0;
                            break;
                        }
                    }
                }
            }
        }
        if (e.getSource() == place) {
            if (place.getText() == "Across") {
                place.setText("Down");
            } else if (place.getText() == "Down") {
                place.setText("Across");
            }
        }
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
        for (i = 0; i < 165; i++) {
            if (e.getSource() == rg[i]) {
                rg[i].setBackground(new Color(200, 200, 185));
            }
        }
        for (i = 0; i < 8; i++) {
            if (e.getSource() == tw[i]) {
                tw[i].setBackground(new Color(235, 0, 82));
            }
        }
        for (i = 0; i < 12; i++) {
            if (e.getSource() == tl[i]) {
                tl[i].setBackground(new Color(82, 133, 235));
            }
        }
        for (i = 0; i < 16; i++) {
            if (e.getSource() == dw[i]) {
                dw[i].setBackground(new Color(235, 133, 133));
            }
        }
        for (i = 0; i < 24; i++) {
            if (e.getSource() == dl[i]) {
                dl[i].setBackground(new Color(184, 235, 235));
            }
        }
    }

    public void mouseExited(MouseEvent e) {
        for (i = 0; i < 165; i++) {
            if (e.getSource() == rg[i]) {
                rg[i].setBackground(new Color(245, 245, 220));
            }
        }
        for (i = 0; i < 8; i++) {
            if (e.getSource() == tw[i]) {
                tw[i].setBackground(new Color(255, 0, 102));
            }
        }
        for (i = 0; i < 12; i++) {
            if (e.getSource() == tl[i]) {
                tl[i].setBackground(new Color(102, 153, 255));
            }
        }
        for (i = 0; i < 16; i++) {
            if (e.getSource() == dw[i]) {
                dw[i].setBackground(new Color(255, 153, 153));
            }
        }
        for (i = 0; i < 24; i++) {
            if (e.getSource() == dl[i]) {
                dl[i].setBackground(new Color(204, 255, 255));
            }
        }
    }

    public void scoringPart(String text) {
        int tpls = 0;
        if (plp[sx].getText() == "a") {
            tpls = tpls + as;
        } else if (plp[sx].getText() == "b") {
            tpls = tpls + bs;
        } else if (plp[sx].getText() == "c") {
            tpls = tpls + cs;
        } else if (plp[sx].getText() == "d") {
            tpls = tpls + ds;
        } else if (plp[sx].getText() == "e") {
            tpls = tpls + es;
        } else if (plp[sx].getText() == "f") {
            tpls = tpls + fs;
        } else if (plp[sx].getText() == "g") {
            tpls = tpls + gs;
        } else if (plp[sx].getText() == "h") {
            tpls = tpls + hs;
        } else if (plp[sx].getText() == "i") {
            tpls = tpls + is;
        } else if (plp[sx].getText() == "j") {
            tpls = tpls + js;
        } else if (plp[sx].getText() == "k") {
            tpls = tpls + ks;
        } else if (plp[sx].getText() == "l") {
            tpls = tpls + ls;
        } else if (plp[sx].getText() == "m") {
            tpls = tpls + ms;
        } else if (plp[sx].getText() == "n") {
            tpls = tpls + ns;
        } else if (plp[sx].getText() == "o") {
            tpls = tpls + os;
        } else if (plp[sx].getText() == "p") {
            tpls = tpls + ps;
        } else if (plp[sx].getText() == "q") {
            tpls = tpls + qs;
        } else if (plp[sx].getText() == "r") {
            tpls = tpls + rs;
        } else if (plp[sx].getText() == "s") {
            tpls = tpls + ss;
        } else if (plp[sx].getText() == "t") {
            tpls = tpls + ts;
        } else if (plp[sx].getText() == "u") {
            tpls = tpls + us;
        } else if (plp[sx].getText() == "v") {
            tpls = tpls + vs;
        } else if (plp[sx].getText() == "w") {
            tpls = tpls + ws;
        } else if (plp[sx].getText() == "x") {
            tpls = tpls + xs;
        } else if (plp[sx].getText() == "y") {
            tpls = tpls + ys;
        } else if (plp[sx].getText() == "z") {
            tpls = tpls + zs;
        }
        if (text == "TL") {
            tpls = tpls * 3;
        } else if (text == "DL") {
            tpls = tpls * 2;
        } else if (text == "TW") {
            twid = true;
        } else if (text == "DW") {
            dwid = true;
        }
        if (player1 == true) {
            pls1 = pls1 + tpls;
            twrd = twrd + pls1;
            score.setText("Player1 Score: " + pls1);
        }
        if (player1 == false) {
            pls2 = pls2 + tpls;
            twrd = twrd + pls2;
            score.setText("Player2 Score: " + pls2);
        }
    }
}
