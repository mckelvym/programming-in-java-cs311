//  Mark McKelvy
//  Java Calculator

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.border.*;
import javax.swing.plaf.*;
import javax.swing.*;

public class JavaCalc extends JApplet {
    GridBagLayout gridbag = new GridBagLayout();
    GridBagConstraints gbc = new GridBagConstraints();
    JPanel display;
    JPanel numbers;
    JButton one;
    JButton two;
    JButton three;
    JButton four;
    JButton five;
    JButton six;
    JButton seven;
    JButton eight;
    JButton nine;
    JButton zero;
    JButton plusminus;
    JButton decimal;
    JButton add;
    JButton subtract;
    JButton multiply;
    JButton divide;
    JButton equals;
    JButton squareroot;
    JButton inverse;
    JButton clear;
    JButton square;
    JButton clearentry;
    JButton backspace;
    JButton pi;
    JButton x_to_y;
    JButton e_to_x;
    JButton abs;
    JButton memory;
    JButton clear_memory;
    JButton cosine;
    JButton sine;
    JButton tangent;
    JButton arc_cos;
    JButton arc_sin;
    JButton arc_tan;
    JButton logarithm;
    JButton radians2degrees;
    JButton degrees2radians;
    JButton hmm;
    JTextField Display;
    JTextField memory_display;
    JMenuBar menu_bar;
    JMenu file, edit;
    JMenuItem file_exit;
    JMenuItem edit_undo;
    KeyStroke key;
    String undo_clear;
    String equation;
    UIManager look_and_feel;
    boolean pos_neg = true;
    boolean clear_next = false;
    boolean decimal_used = false;
    boolean memory_in_use = false;
    boolean undo = false;
    double value = 0;
    double temp = 0;
    int last_button = -1;

    public void init() {
            /*try{
                look_and_feel.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
            }
            catch(ClassNotFoundException e){}
            catch(UnsupportedLookAndFeelException e){}
            catch(IllegalAccessException e){}
            catch(InstantiationException e){}*/

        undo_clear = new String("0");
        display = new JPanel();
        numbers = new JPanel();
        Display = new JTextField("0");
        one = new JButton(" 1 ");
        two = new JButton(" 2 ");
        three = new JButton(" 3 ");
        four = new JButton(" 4 ");
        five = new JButton(" 5 ");
        six = new JButton(" 6 ");
        seven = new JButton(" 7 ");
        eight = new JButton(" 8 ");
        nine = new JButton(" 9 ");
        zero = new JButton(" 0 ");
        add = new JButton(" + ");
        subtract = new JButton(" - ");
        multiply = new JButton(" * ");
        divide = new JButton(" / ");
        equals = new JButton(" = ");
        squareroot = new JButton(" sqrt ");
        inverse = new JButton(" 1/x ");
        clear = new JButton("Clear All");
        plusminus = new JButton("+/-");
        decimal = new JButton(" . ");
        square = new JButton("x^2");
        clearentry = new JButton("Clear");
        backspace = new JButton("BkSpc");
        pi = new JButton("Pi");
        x_to_y = new JButton("x^y");
        e_to_x = new JButton("e^x");
        abs = new JButton("|Abs|");
        memory = new JButton("+ MR");
        clear_memory = new JButton("Clr MR");
        cosine = new JButton("Cos");
        sine = new JButton("Sin");
        tangent = new JButton("Tan");
        arc_cos = new JButton("ACos");
        arc_sin = new JButton("ASin");
        arc_tan = new JButton("ATan");
        logarithm = new JButton("Log");
        radians2degrees = new JButton("Rad->Deg");
        degrees2radians = new JButton("Deg->Rad");
        memory_display = new JTextField();
        hmm = new JButton("hmm");

        one.setToolTipText("1");
        two.setToolTipText("2");
        three.setToolTipText("3");
        four.setToolTipText("4");
        five.setToolTipText("5");
        six.setToolTipText("6");
        seven.setToolTipText("7");
        eight.setToolTipText("8");
        nine.setToolTipText("9");
        zero.setToolTipText("0");
        add.setToolTipText("Add");
        subtract.setToolTipText("Subtract");
        multiply.setToolTipText("Multiply");
        divide.setToolTipText("Divide");
        equals.setToolTipText("Equal or Enter");
        Display.setToolTipText("Display Area");
        squareroot.setToolTipText("Square Root");
        square.setToolTipText("Square");
        clearentry.setToolTipText("Clear Display Area");
        clear.setToolTipText("Clear All");
        inverse.setToolTipText("Inverse");
        plusminus.setToolTipText("Change Sign");
        backspace.setToolTipText("Back Space");
        pi.setToolTipText("Pi");
        x_to_y.setToolTipText("Raise to a Power");
        e_to_x.setToolTipText("Raise Exponential");
        abs.setToolTipText("Absolute Value");
        memory.setToolTipText("Add to Memory");
        memory_display.setToolTipText("Memory Display Box");
        clear_memory.setToolTipText("Erase Memory");
        cosine.setToolTipText("Cosine");
        sine.setToolTipText("Sine");
        tangent.setToolTipText("Tangent");
        arc_cos.setToolTipText("Arc Cosine");
        arc_sin.setToolTipText("Arc Sine");
        arc_tan.setToolTipText("Arc Tangent");
        logarithm.setToolTipText("Logarithm");
        radians2degrees.setToolTipText("Radians to Degrees");
        degrees2radians.setToolTipText("Degrees to Radians");
        hmm.setToolTipText("Hmm");

        Display.setForeground(Color.blue);
        one.setForeground(Color.blue);
        two.setForeground(Color.blue);
        three.setForeground(Color.blue);
        four.setForeground(Color.blue);
        five.setForeground(Color.blue);
        six.setForeground(Color.blue);
        seven.setForeground(Color.blue);
        eight.setForeground(Color.blue);
        nine.setForeground(Color.blue);
        zero.setForeground(Color.blue);
        add.setForeground(Color.blue);
        subtract.setForeground(Color.blue);
        multiply.setForeground(Color.blue);
        divide.setForeground(Color.blue);
        equals.setForeground(Color.blue);
        squareroot.setForeground(Color.blue);
        inverse.setForeground(Color.blue);
        clear.setForeground(Color.blue);
        plusminus.setForeground(Color.blue);
        decimal.setForeground(Color.blue);
        square.setForeground(Color.blue);
        clearentry.setForeground(Color.blue);
        backspace.setForeground(Color.blue);
        pi.setForeground(Color.blue);
        x_to_y.setForeground(Color.blue);
        e_to_x.setForeground(Color.blue);
        abs.setForeground(Color.blue);
        memory.setForeground(Color.blue);
        clear_memory.setForeground(Color.blue);
        cosine.setForeground(Color.blue);
        sine.setForeground(Color.blue);
        tangent.setForeground(Color.blue);
        arc_cos.setForeground(Color.blue);
        arc_sin.setForeground(Color.blue);
        arc_tan.setForeground(Color.blue);
        logarithm.setForeground(Color.blue);
        radians2degrees.setForeground(Color.blue);
        degrees2radians.setForeground(Color.blue);
        hmm.setForeground(Color.blue);
        memory_display.setBackground(Color.white);
        memory_display.setDisabledTextColor(Color.red);

        Display.setHorizontalAlignment(JTextField.RIGHT);
        Display.setColumns(24);
        memory_display.setEnabled(false);
        memory_display.setHorizontalAlignment(JLabel.RIGHT);

        menu_bar = new JMenuBar();
        file = new JMenu("File");
        edit = new JMenu("Edit");
        file_exit = new JMenuItem("Exit");
        edit_undo = new JMenuItem("Undo Last Clear");
        file.add(file_exit);
        edit.add(edit_undo);
        menu_bar.add(file);
        menu_bar.add(edit);
        this.setJMenuBar(menu_bar);
        // change the look manager
        // gridbag insets
        this.getContentPane().setLayout(gridbag);
        display.setLayout(new GridLayout(1, 1));
        numbers.setLayout(new GridLayout(10, 4));
        gbc.weightx = 1.0;
        gbc.weighty = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridheight = GridBagConstraints.RELATIVE;
        gbc.fill = GridBagConstraints.BOTH;
        gridbag.setConstraints(display, gbc);
        this.getContentPane().add(display);
        gbc.weighty = 1.0;
        gbc.gridheight = GridBagConstraints.REMAINDER;
        gridbag.setConstraints(numbers, gbc);
        this.getContentPane().add(numbers);
        display.add(Display);
        numbers.add(seven);
        numbers.add(eight);
        numbers.add(nine);
        numbers.add(divide);
        numbers.add(four);
        numbers.add(five);
        numbers.add(six);
        numbers.add(multiply);
        numbers.add(one);
        numbers.add(two);
        numbers.add(three);
        numbers.add(subtract);
        numbers.add(zero);
        numbers.add(plusminus);
        numbers.add(decimal);
        numbers.add(add);
        numbers.add(squareroot);
        numbers.add(inverse);
        numbers.add(clear);
        numbers.add(equals);
        numbers.add(square);
        numbers.add(pi);
        numbers.add(clearentry);
        numbers.add(abs);
        numbers.add(x_to_y);
        numbers.add(e_to_x);
        numbers.add(backspace);
        numbers.add(memory);
        numbers.add(radians2degrees);
        numbers.add(degrees2radians);
        numbers.add(clear_memory);
        numbers.add(memory_display);
        numbers.add(cosine);
        numbers.add(sine);
        numbers.add(tangent);
        numbers.add(logarithm);
        numbers.add(arc_cos);
        numbers.add(arc_sin);
        numbers.add(arc_tan);
        numbers.add(hmm);

        file_exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == file_exit) {
                    System.exit(0);
                }
            }
        });

        edit_undo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == edit_undo) {
                    Display.setText(undo_clear);
                }
            }
        });

        one.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if ((e.getSource() == one)) {
                    if (clear_next) {
                        clear_next = false;
                        Display.setText("0");
                    }
                    String helper = new String(Display.getText());
                    if (helper.length() >= 15) {
                    } else {
                        if (helper.charAt(0) == '0') {
                            Display.setText(helper.substring(1, helper.length()));
                        }
                        Display.setText(Display.getText() + "1");
                    }
                }
            }
        });

        this.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == '1') {
                    if (clear_next) {
                        clear_next = false;
                        Display.setText("0");
                    }
                    String helper = new String(Display.getText());
                    if (helper.length() >= 15) {
                    } else {
                        if (helper.charAt(0) == '0') {
                            Display.setText(helper.substring(1, helper.length()));
                        }
                        Display.setText(Display.getText() + "1");
                    }
                }
            }

            public void keyReleased(KeyEvent e) {
            }

            public void keyTyped(KeyEvent e) {
            }
        });

        two.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == two) {
                    if (clear_next) {
                        clear_next = false;
                        Display.setText("0");
                    }
                    String helper = new String(Display.getText());
                    if (helper.length() >= 15) {
                    } else {
                        if (helper.charAt(0) == '0') {
                            Display.setText(helper.substring(1, helper.length()));
                        }
                        Display.setText(Display.getText() + "2");
                    }
                }
            }
        });

        this.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == '2') {
                    if (clear_next) {
                        clear_next = false;
                        Display.setText("0");
                    }
                    String helper = new String(Display.getText());
                    if (helper.length() >= 15) {
                    } else {
                        if (helper.charAt(0) == '0') {
                            Display.setText(helper.substring(1, helper.length()));
                        }
                        Display.setText(Display.getText() + "2");
                    }

                }
            }

            public void keyReleased(KeyEvent e) {
            }

            public void keyTyped(KeyEvent e) {
            }
        });

        three.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == three) {
                    if (clear_next) {
                        clear_next = false;
                        Display.setText("0");
                    }
                    String helper = new String(Display.getText());
                    if (helper.length() >= 15) {
                    } else {
                        if (helper.charAt(0) == '0') {
                            Display.setText(helper.substring(1, helper.length()));
                        }
                        Display.setText(Display.getText() + "3");
                    }
                }
            }
        });

        this.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == '3') {
                    if (clear_next) {
                        clear_next = false;
                        Display.setText("0");
                    }
                    String helper = new String(Display.getText());
                    if (helper.length() >= 15) {
                    } else {
                        if (helper.charAt(0) == '0') {
                            Display.setText(helper.substring(1, helper.length()));
                        }
                        Display.setText(Display.getText() + "3");
                    }

                }
            }

            public void keyReleased(KeyEvent e) {
            }

            public void keyTyped(KeyEvent e) {
            }
        });

        four.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == four) {
                    if (clear_next) {
                        clear_next = false;
                        Display.setText("0");
                    }
                    String helper = new String(Display.getText());
                    if (helper.length() >= 15) {
                    } else {
                        if (helper.charAt(0) == '0') {
                            Display.setText(helper.substring(1, helper.length()));
                        }
                        Display.setText(Display.getText() + "4");
                    }
                }
            }
        });

        this.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == '4') {
                    if (clear_next) {
                        clear_next = false;
                        Display.setText("0");
                    }
                    String helper = new String(Display.getText());
                    if (helper.length() >= 15) {
                    } else {
                        if (helper.charAt(0) == '0') {
                            Display.setText(helper.substring(1, helper.length()));
                        }
                        Display.setText(Display.getText() + "4");
                    }

                }
            }

            public void keyReleased(KeyEvent e) {
            }

            public void keyTyped(KeyEvent e) {
            }
        });

        five.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == five) {
                    if (clear_next) {
                        clear_next = false;
                        Display.setText("0");
                    }
                    String helper = new String(Display.getText());
                    if (helper.length() >= 15) {
                    } else {
                        if (helper.charAt(0) == '0') {
                            Display.setText(helper.substring(1, helper.length()));
                        }
                        Display.setText(Display.getText() + "5");
                    }
                }
            }
        });

        this.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == '5') {
                    if (clear_next) {
                        clear_next = false;
                        Display.setText("0");
                    }
                    String helper = new String(Display.getText());
                    if (helper.length() >= 15) {
                    } else {
                        if (helper.charAt(0) == '0') {
                            Display.setText(helper.substring(1, helper.length()));
                        }
                        Display.setText(Display.getText() + "5");
                    }

                }
            }

            public void keyReleased(KeyEvent e) {
            }

            public void keyTyped(KeyEvent e) {
            }
        });

        six.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == six) {
                    if (clear_next) {
                        clear_next = false;
                        Display.setText("0");
                    }
                    String helper = new String(Display.getText());
                    if (helper.length() >= 15) {
                    } else {
                        if (helper.charAt(0) == '0') {
                            Display.setText(helper.substring(1, helper.length()));
                        }
                        Display.setText(Display.getText() + "6");
                    }
                }
            }
        });

        this.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == '6') {
                    if (clear_next) {
                        clear_next = false;
                        Display.setText("0");
                    }
                    String helper = new String(Display.getText());
                    if (helper.length() >= 15) {
                    } else {
                        if (helper.charAt(0) == '0') {
                            Display.setText(helper.substring(1, helper.length()));
                        }
                        Display.setText(Display.getText() + "6");
                    }

                }
            }

            public void keyReleased(KeyEvent e) {
            }

            public void keyTyped(KeyEvent e) {
            }
        });

        seven.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == seven) {
                    if (clear_next) {
                        clear_next = false;
                        Display.setText("0");
                    }
                    String helper = new String(Display.getText());
                    if (helper.length() >= 15) {
                    } else {
                        if (helper.charAt(0) == '0') {
                            Display.setText(helper.substring(1, helper.length()));
                        }
                        Display.setText(Display.getText() + "7");
                    }
                }
            }
        });

        this.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == '7') {
                    if (clear_next) {
                        clear_next = false;
                        Display.setText("0");
                    }
                    String helper = new String(Display.getText());
                    if (helper.length() >= 15) {
                    } else {
                        if (helper.charAt(0) == '0') {
                            Display.setText(helper.substring(1, helper.length()));
                        }
                        Display.setText(Display.getText() + "7");
                    }

                }
            }

            public void keyReleased(KeyEvent e) {
            }

            public void keyTyped(KeyEvent e) {
            }
        });

        eight.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == eight) {
                    if (clear_next) {
                        clear_next = false;
                        Display.setText("0");
                    }
                    String helper = new String(Display.getText());
                    if (helper.length() >= 15) {
                    } else {
                        if (helper.charAt(0) == '0') {
                            Display.setText(helper.substring(1, helper.length()));
                        }
                        Display.setText(Display.getText() + "8");
                    }
                }
            }
        });

        this.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == '8') {
                    if (clear_next) {
                        clear_next = false;
                        Display.setText("0");
                    }
                    String helper = new String(Display.getText());
                    if (helper.length() >= 15) {
                    } else {
                        if (helper.charAt(0) == '0') {
                            Display.setText(helper.substring(1, helper.length()));
                        }
                        Display.setText(Display.getText() + "8");
                    }

                }
            }

            public void keyReleased(KeyEvent e) {
            }

            public void keyTyped(KeyEvent e) {
            }
        });

        nine.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == nine) {
                    if (clear_next) {
                        clear_next = false;
                        Display.setText("0");
                    }
                    String helper = new String(Display.getText());
                    if (helper.length() >= 15) {
                    } else {
                        if (helper.charAt(0) == '0') {
                            Display.setText(helper.substring(1, helper.length()));
                        }
                        Display.setText(Display.getText() + "9");
                    }
                }
            }
        });

        this.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == '9') {
                    if (clear_next) {
                        clear_next = false;
                        Display.setText("0");
                    }
                    String helper = new String(Display.getText());
                    if (helper.length() >= 15) {
                    } else {
                        if (helper.charAt(0) == '0') {
                            Display.setText(helper.substring(1, helper.length()));
                        }
                        Display.setText(Display.getText() + "9");
                    }
                }
            }

            public void keyReleased(KeyEvent e) {
            }

            public void keyTyped(KeyEvent e) {
            }
        });

        zero.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == zero) {
                    if (clear_next) {
                        clear_next = false;
                        Display.setText("0");
                    }
                    String helper = new String(Display.getText());
                    if (helper.length() >= 15) {
                    } else {
                        if (helper.charAt(0) == '0') {
                            Display.setText(helper.substring(1, helper.length()));
                        }
                        Display.setText(Display.getText() + "0");
                    }
                }
            }
        });

        this.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == '0') {
                    if (clear_next) {
                        clear_next = false;
                        Display.setText("0");
                    }
                    String helper = new String(Display.getText());
                    if (helper.length() >= 15) {
                    } else {
                        if (helper.charAt(0) == '0') {
                            Display.setText(helper.substring(1, helper.length()));
                        }
                        Display.setText(Display.getText() + "0");
                    }

                }
            }

            public void keyReleased(KeyEvent e) {
            }

            public void keyTyped(KeyEvent e) {
            }
        });

        pi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == pi) {
                    if (clear_next) {
                        clear_next = false;
                        Display.setText("0");
                    }
                    Display.setText(new String(Double.toString(StrictMath.PI)));
                }
            }
        });

        decimal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == decimal) {
                    if (!decimal_used) {
                        if (clear_next) {
                            Display.setText("0");
                        }
                        String helper = new String(Display.getText());
                        if (helper.length() >= 15) {
                        } else {
                            if (helper.charAt(0) == '0') {
                                Display.setText(helper.substring(1, helper.length()));
                            }
                            Display.setText(Display.getText() + ".");
                        }
                        if (clear_next = false)
                            decimal_used = true;
                    }
                }
            }
        });

        this.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == '.') {
                    if (!decimal_used) {
                        if (clear_next) {
                            Display.setText("0");
                        }
                        String helper = new String(Display.getText());
                        if (helper.length() >= 15) {
                        } else {
                            if (helper.charAt(0) == '0') {
                                Display.setText(helper.substring(1, helper.length()));
                            }
                            Display.setText(Display.getText() + ".");
                        }
                        if (clear_next = false)
                            decimal_used = true;
                    }
                }
            }

            public void keyReleased(KeyEvent e) {
            }

            public void keyTyped(KeyEvent e) {
            }
        });

        inverse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == inverse) {
                    if ((new String(Display.getText()).charAt(0) != '0') || (new String(Display.getText()).length() != 1)) {
                        if ((Double.toString((1 / Double.parseDouble(new String(Display.getText())))).charAt(Double.toString((1 / Double.parseDouble(new String(Display.getText())))).length() - 2) == '.') && (Double.toString((1 / Double.parseDouble(new String(Display.getText())))).charAt(Double.toString((1 / Double.parseDouble(new String(Display.getText())))).length() - 1) == '0')) {
                            Display.setText(new String(Double.toString((1 / Double.parseDouble(new String(Display.getText())))).substring(0, Double.toString((1 / Double.parseDouble(new String(Display.getText())))).length() - 2)));
                        } else {
                            Display.setText(Double.toString((1 / Double.parseDouble(new String(Display.getText())))));
                        }
                    }
                }
            }
        });

        radians2degrees.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == radians2degrees) {
                    String helper = new String(Double.toString(Double.parseDouble(new String(Display.getText())) * (180 / StrictMath.PI)));
                    if ((helper.charAt(helper.length() - 2) == '.') && (helper.charAt(helper.length() - 1) == '0')) {
                        Display.setText(new String(helper.substring(0, helper.length() - 2)));
                    } else {
                        Display.setText(helper);
                    }
                }
            }
        });

        degrees2radians.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == degrees2radians) {
                    String helper = new String(Double.toString(Double.parseDouble(new String(Display.getText())) * (StrictMath.PI / 180)));
                    if ((helper.charAt(helper.length() - 2) == '.') && (helper.charAt(helper.length() - 1) == '0')) {
                        Display.setText(new String(helper.substring(0, helper.length() - 2)));
                    } else {
                        Display.setText(helper);
                    }
                }
            }
        });

        plusminus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == plusminus) {
                    String helper = new String(Display.getText());
                    if (helper.charAt(0) != '0') {
                        if (helper.charAt(0) == '-') {
                            Display.setText(helper.substring(1, helper.length()));
                        } else {
                            Display.setText("-" + Display.getText());
                        }
                    }
                }
            }
        });

        squareroot.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == squareroot) {
                    String helper = new String(Double.toString(StrictMath.sqrt(Double.parseDouble(new String(Display.getText())))));
                    if (helper.length() <= 15) {
                        if ((helper.charAt(helper.length() - 2) == '.') && (helper.charAt(helper.length() - 1) == '0')) {
                            Display.setText(new String(helper.substring(0, helper.length() - 2)));
                        } else {
                            Display.setText(helper);
                        }
                    } else {
                        Display.setText(helper.substring(0, 15));
                    }
                }
            }
        });

        square.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == square) {
                    String helper = new String(Double.toString(Double.parseDouble(new String(Display.getText())) * Double.parseDouble(new String(Display.getText()))));
                    if (helper.length() <= 15) {
                        if ((helper.charAt(helper.length() - 2) == '.') && (helper.charAt(helper.length() - 1) == '0')) {
                            Display.setText(new String(helper.substring(0, helper.length() - 2)));
                        } else {
                            Display.setText(helper);
                        }
                    } else {
                        Display.setText(helper.substring(0, 15));
                    }
                }
            }
        });

        x_to_y.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == x_to_y) {
                    if (last_button != 0) {
                        String helper = new String(Display.getText());
                        if (helper.charAt(0) == '-') {
                            pos_neg = false;
                            helper = new String(helper.substring(1, helper.length()));
                        } else {
                            pos_neg = true;
                        }
                        temp = Double.parseDouble(helper);
                        if (!pos_neg) {
                            temp -= (2 * temp);
                        }
                        if (value == 0) {
                            value += temp;
                        } else {
                            value = StrictMath.pow(value, temp);
                        }
                    } else {
                    }
                    clear_next = true;
                    decimal_used = false;
                    last_button = 5;
                }
            }
        });

        e_to_x.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == e_to_x) {
                    decimal_used = false;
                    last_button = 6;
                }
            }
        });

        cosine.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == cosine) {
                    decimal_used = false;
                    last_button = 7;
                }
            }
        });

        sine.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == sine) {
                    decimal_used = false;
                    last_button = 8;
                }
            }
        });

        tangent.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == tangent) {
                    decimal_used = false;
                    last_button = 9;
                }
            }
        });

        logarithm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == logarithm) {
                    decimal_used = false;
                    last_button = 10;
                }
            }
        });

        arc_cos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == arc_cos) {
                    decimal_used = false;
                    last_button = 11;
                }
            }
        });

        arc_sin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == arc_sin) {
                    decimal_used = false;
                    last_button = 12;
                }
            }
        });

        arc_tan.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == arc_tan) {
                    decimal_used = false;
                    last_button = 13;
                }
            }
        });

        abs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == abs) {
                    String helper = new String(Double.toString(StrictMath.abs(Double.parseDouble(Display.getText()))));
                    Display.setText(helper);
                    if ((helper.charAt(helper.length() - 2) == '.') && (helper.charAt(helper.length() - 1) == '0')) {
                        Display.setText(new String(helper.substring(0, helper.length() - 2)));
                    } else {
                        Display.setText(helper);
                    }
                }
            }
        });

        memory.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == memory) {
                    if (clear_next) {
                        clear_next = false;
                        Display.setText("0");
                    }
                    if (!memory_in_use) {
                        memory_in_use = true;
                        memory_display.setText(Display.getText());
                        memory.setText("- MR");
                        memory.setToolTipText("Recall from Memory");
                    } else {
                        memory_in_use = false;
                        Display.setText(memory_display.getText());
                        memory_display.setText("");
                        memory.setText("+ MR");
                        memory.setToolTipText("Add to Memory");
                    }
                }
            }
        });

        clear_memory.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == clear_memory) {
                    memory_in_use = false;
                    memory_display.setText("");
                    memory.setText("+ MR");
                    memory.setToolTipText("Add to Memory");
                }
            }
        });

        clear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == clear) {
                    undo_clear = new String(Display.getText());
                    value = 0;
                    temp = 0;
                    last_button = -2;
                    decimal_used = false;
                    Display.setText("0");
                }
            }
        });

        this.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_DELETE) {
                    undo_clear = new String(Display.getText());
                    value = 0;
                    temp = 0;
                    last_button = -2;
                    decimal_used = false;
                    Display.setText("0");

                }

            }

            public void keyReleased(KeyEvent e) {
            }

            public void keyTyped(KeyEvent e) {
            }
        });

        clearentry.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == clearentry) {
                    undo_clear = new String(Display.getText());
                    last_button = -2;
                    decimal_used = false;
                    Display.setText("0");
                }
            }
        });

        backspace.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == backspace) {
                    String helper = new String(Display.getText());
                    if (helper.length() != 0) {
                        Display.setText(new String(helper.substring(0, helper.length() - 1)));
                    }
                    if (helper.length() <= 1) {
                        Display.setText("0");
                    }
                }
            }
        });

        this.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_BACK_SPACE) {
                    String helper = new String(Display.getText());
                    if (helper.length() != 0) {
                        Display.setText(new String(helper.substring(0, helper.length() - 1)));
                    }
                    if (helper.length() <= 1) {
                        Display.setText("0");
                    }

                }
            }

            public void keyReleased(KeyEvent e) {
            }

            public void keyTyped(KeyEvent e) {
            }
        });

        add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == add) {
                    if (last_button != 0) {
                        String helper = new String(Display.getText());
                        if (helper.charAt(0) == '-') {
                            pos_neg = false;
                            helper = new String(helper.substring(1, helper.length()));
                        } else {
                            pos_neg = true;
                        }
                        temp = Double.parseDouble(helper);
                        if (!pos_neg) {
                            temp -= (2 * temp);
                        }
                        value += temp;
                        temp = 0;

                    }
                    clear_next = true;
                    decimal_used = false;
                    last_button = 1;
                }
            }
        });

        this.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == '+') {
                    if (last_button != 0) {
                        String helper = new String(Display.getText());
                        if (helper.charAt(0) == '-') {
                            pos_neg = false;
                            helper = new String(helper.substring(1, helper.length()));
                        } else {
                            pos_neg = true;
                        }
                        temp = Double.parseDouble(helper);
                        if (!pos_neg) {
                            temp -= (2 * temp);
                        }
                        value += temp;
                        temp = 0;

                    }
                    clear_next = true;
                    decimal_used = false;
                    last_button = 1;

                }
            }

            public void keyReleased(KeyEvent e) {
            }

            public void keyTyped(KeyEvent e) {
            }
        });

        subtract.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == subtract) {
                    if (last_button != 0) {
                        String helper = new String(Display.getText());
                        if (helper.charAt(0) == '-') {
                            pos_neg = false;
                            helper = new String(helper.substring(1, helper.length()));
                        } else {
                            pos_neg = true;
                        }
                        temp = Double.parseDouble(helper);
                        if (!pos_neg) {
                            temp -= (2 * temp);
                        }
                        if (value <= 0) {
                            value = temp - value;
                        } else {
                            value = value - temp;
                        }
                        temp = 0;

                    }
                    clear_next = true;
                    decimal_used = false;
                    last_button = 2;
                }
            }
        });

        this.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == '-') {
                    if (last_button != 0) {
                        String helper = new String(Display.getText());
                        if (helper.charAt(0) == '-') {
                            pos_neg = false;
                            helper = new String(helper.substring(1, helper.length()));
                        } else {
                            pos_neg = true;
                        }
                        temp = Double.parseDouble(helper);
                        if (!pos_neg) {
                            temp -= (2 * temp);
                        }
                        if (value <= 0) {
                            value = temp - value;
                        } else {
                            value = value - temp;
                        }
                        temp = 0;

                    }
                    clear_next = true;
                    decimal_used = false;
                    last_button = 2;
                }
            }

            public void keyReleased(KeyEvent e) {
            }

            public void keyTyped(KeyEvent e) {
            }
        });

        multiply.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == multiply) {
                    if (last_button != 0) {
                        String helper = new String(Display.getText());
                        if (helper.charAt(0) == '-') {
                            pos_neg = false;
                            helper = new String(helper.substring(1, helper.length()));
                        } else {
                            pos_neg = true;
                        }
                        temp = Double.parseDouble(helper);
                        if (!pos_neg) {
                            temp -= (2 * temp);
                        }
                        if (value == 0) {
                            value += temp;
                        } else {
                            value *= temp;
                        }
                    } else {
                    }
                    clear_next = true;
                    decimal_used = false;
                    last_button = 3;
                }
            }
        });

        this.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == '*') {
                    if (last_button != 0) {
                        String helper = new String(Display.getText());
                        if (helper.charAt(0) == '-') {
                            pos_neg = false;
                            helper = new String(helper.substring(1, helper.length()));
                        } else {
                            pos_neg = true;
                        }
                        temp = Double.parseDouble(helper);
                        if (!pos_neg) {
                            temp -= (2 * temp);
                        }
                        if (value == 0) {
                            value += temp;
                        } else {
                            value *= temp;
                        }
                    } else {
                    }
                    clear_next = true;
                    decimal_used = false;
                    last_button = 3;
                }
            }

            public void keyReleased(KeyEvent e) {
            }

            public void keyTyped(KeyEvent e) {
            }
        });

        divide.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == divide) {
                    if (last_button != 0) {
                        String helper = new String(Display.getText());
                        if (helper.charAt(0) == '-') {
                            pos_neg = false;
                            helper = new String(helper.substring(1, helper.length()));
                        } else {
                            pos_neg = true;
                        }
                        temp = Double.parseDouble(helper);
                        if (!pos_neg) {
                            temp -= (2 * temp);
                        }
                        if (value == 0) {
                            value += temp;
                        } else {
                            value /= temp;
                        }
                    } else {
                    }
                    clear_next = true;
                    decimal_used = false;
                    last_button = 4;
                }
            }
        });

        this.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == '/') {
                    if (last_button != 0) {
                        String helper = new String(Display.getText());
                        if (helper.charAt(0) == '-') {
                            pos_neg = false;
                            helper = new String(helper.substring(1, helper.length()));
                        } else {
                            pos_neg = true;
                        }
                        temp = Double.parseDouble(helper);
                        if (!pos_neg) {
                            temp -= (2 * temp);
                        }
                        if (value == 0) {
                            value += temp;
                        } else {
                            value /= temp;
                        }
                    } else {
                    }
                    clear_next = true;
                    decimal_used = false;
                    last_button = 4;
                }
            }

            public void keyReleased(KeyEvent e) {
            }

            public void keyTyped(KeyEvent e) {
            }
        });

        equals.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == equals) {
                    String helper = new String(Display.getText());
                    if (helper.charAt(0) == '-') {
                        pos_neg = false;
                        helper = new String(helper.substring(1, helper.length()));
                    } else {
                        pos_neg = true;
                    }
                    temp = Double.parseDouble(helper);
                    if (!pos_neg) {
                        temp -= (2 * temp);
                    }
                    switch (last_button) {
                        case 1:
                            value += temp;
                            temp = 0;
                            break;
                        case 2:
                            value -= temp;
                            temp = 0;
                            break;
                        case 3:
                            value *= temp;
                            temp = 0;
                            break;
                        case 4:
                            value /= temp;
                            temp = 0;
                            break;
                        case 5:
                            value = StrictMath.pow(value, temp);
                            temp = 0;
                            break;
                        case 6:
                            value = StrictMath.pow(StrictMath.E, temp);
                            temp = 0;
                            break;
                        case 7:
                            value = StrictMath.cos(temp);
                            temp = 0;
                            break;
                        case 8:
                            value = StrictMath.sin(temp);
                            temp = 0;
                            break;
                        case 9:
                            value = StrictMath.tan(temp);
                            temp = 0;
                            break;
                        case 10:
                            value = StrictMath.log(temp);
                            temp = 0;
                            break;
                        case 11:
                            value = StrictMath.acos(temp);
                            temp = 0;
                            break;
                        case 12:
                            value = StrictMath.asin(temp);
                            temp = 0;
                            break;
                        case 13:
                            value = StrictMath.atan(temp);
                            temp = 0;
                            break;
                    }
                    if ((Double.toString(value).charAt(Double.toString(value).length() - 2) == '.') && (Double.toString(value).charAt(Double.toString(value).length() - 1) == '0')) {
                        Display.setText(new String(Double.toString(value).substring(0, Double.toString(value).length() - 2)));
                    } else {
                        Display.setText(Double.toString(value));
                    }
                    decimal_used = false;
                    last_button = 0;
                }
            }
        });

        this.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
                if ((e.getKeyChar() == KeyEvent.VK_ENTER) || (e.getKeyChar() == KeyEvent.VK_EQUALS)) {
                    String helper = new String(Display.getText());
                    if (helper.charAt(0) == '-') {
                        pos_neg = false;
                        helper = new String(helper.substring(1, helper.length()));
                    } else {
                        pos_neg = true;
                    }
                    temp = Double.parseDouble(helper);
                    if (!pos_neg) {
                        temp -= (2 * temp);
                    }
                    switch (last_button) {
                        case 1:
                            value += temp;
                            temp = 0;
                            break;
                        case 2:
                            value -= temp;
                            temp = 0;
                            break;
                        case 3:
                            value *= temp;
                            temp = 0;
                            break;
                        case 4:
                            value /= temp;
                            temp = 0;
                            break;
                        case 5:
                            value = StrictMath.pow(value, temp);
                            temp = 0;
                            break;
                        case 6:
                            value = StrictMath.pow(StrictMath.E, temp);
                            temp = 0;
                            break;
                        case 7:
                            value = StrictMath.cos(temp);
                            temp = 0;
                            break;
                        case 8:
                            value = StrictMath.sin(temp);
                            temp = 0;
                            break;
                        case 9:
                            value = StrictMath.tan(temp);
                            temp = 0;
                            break;
                        case 10:
                            value = StrictMath.log(temp);
                            temp = 0;
                            break;
                        case 11:
                            value = StrictMath.acos(temp);
                            temp = 0;
                            break;
                        case 12:
                            value = StrictMath.asin(temp);
                            temp = 0;
                            break;
                        case 13:
                            value = StrictMath.atan(temp);
                            temp = 0;
                            break;
                    }
                    if ((Double.toString(value).charAt(Double.toString(value).length() - 2) == '.') && (Double.toString(value).charAt(Double.toString(value).length() - 1) == '0')) {
                        Display.setText(new String(Double.toString(value).substring(0, Double.toString(value).length() - 2)));
                    } else {
                        Display.setText(Double.toString(value));
                    }
                    decimal_used = false;
                    last_button = 0;
                }
            }

            public void keyReleased(KeyEvent e) {
            }

            public void keyTyped(KeyEvent e) {
            }
        });

        hmm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == hmm) {
                    if (!undo) {
                        undo = true;
                        one.setText("Face");
                        two.setText("Face");
                        three.setText("Face");
                        four.setText("Face");
                        five.setText("Face");
                        six.setText("Face");
                        seven.setText("Face");
                        eight.setText("Face");
                        nine.setText("Face");
                        zero.setText("Face");
                        decimal.setText("Face");
                        square.setText("Face");
                        squareroot.setText("Face");
                        x_to_y.setText("Face");
                        e_to_x.setText("Face");
                        cosine.setText("Face");
                        sine.setText("Face");
                        tangent.setText("Face");
                        arc_cos.setText("Face");
                        arc_sin.setText("Face");
                        arc_tan.setText("Face");
                        logarithm.setText("Face");
                        add.setText("Face");
                        subtract.setText("Face");
                        multiply.setText("Face");
                        divide.setText("Face");
                        equals.setText("Face");
                        memory.setText("Face");
                        clear_memory.setText("Face");
                        clear.setText("Face");
                        clearentry.setText("Face");
                        backspace.setText("Face");
                        inverse.setText("Face");
                        pi.setText("Face");
                        plusminus.setText("Face");
                        degrees2radians.setText("Face");
                        radians2degrees.setText("Face");
                        memory_display.setText("Face");
                        Display.setText("Face");
                        abs.setText("Face");
                        hmm.setText("UNDO!");
                    } else {
                        undo = false;
                        Display.setText("0");
                        one.setText(" 1 ");
                        two.setText(" 2 ");
                        three.setText(" 3 ");
                        four.setText(" 4 ");
                        five.setText(" 5 ");
                        six.setText(" 6 ");
                        seven.setText(" 7 ");
                        eight.setText(" 8 ");
                        nine.setText(" 9 ");
                        zero.setText(" 0 ");
                        add.setText(" + ");
                        subtract.setText(" - ");
                        multiply.setText(" * ");
                        divide.setText(" / ");
                        equals.setText(" = ");
                        squareroot.setText(" sqrt ");
                        inverse.setText(" 1/x ");
                        clear.setText("Clear All");
                        plusminus.setText("+/-");
                        decimal.setText(" . ");
                        square.setText("x^2");
                        clearentry.setText("Clear");
                        backspace.setText("BkSpc");
                        pi.setText("Pi");
                        x_to_y.setText("x^y");
                        e_to_x.setText("e^x");
                        abs.setText("|Abs|");
                        memory.setText("+ MR");
                        clear_memory.setText("Clr MR");
                        cosine.setText("Cos");
                        sine.setText("Sin");
                        tangent.setText("Tan");
                        arc_cos.setText("ACos");
                        arc_sin.setText("ASin");
                        arc_tan.setText("ATan");
                        logarithm.setText("Log");
                        radians2degrees.setText("Rad->Deg");
                        degrees2radians.setText("Deg->Rad");
                        memory_display.setText("");
                        hmm.setText("hmm");
                    }
                }
            }
        });
    }
}
