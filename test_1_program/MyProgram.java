// mark mckelvy
// java test 1
// programming portion

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class MyProgram extends JApplet {

    BufferedReader reader;
    JButton calcpay;
    JButton next;
    JButton start;
    JButton end;
    JLabel lname;
    JLabel lhours;
    JLabel lrate;
    JTextField tfname;
    JTextField tfhours;
    JTextField tfrate;
    JTextArea info;
    String temp;
    String name;
    String social;
    double num_hours;
    double pay_rate;
    double total;
    int[] ast = new int[8];
    int astc = 0;

    public void init() {
        calcpay = new JButton("Calc Pay");
        next = new JButton("Next");
        start = new JButton("Start Over");
        end = new JButton("End/Close File");
        info = new JTextArea(6, 25);
        tfname = new JTextField(20);
        tfhours = new JTextField(20);
        tfrate = new JTextField(20);
        lname = new JLabel("Name: ");
        lhours = new JLabel("Hours: ");
        lrate = new JLabel("Rate: ");

        this.getContentPane().setLayout(new GridLayout(6, 2));
        this.getContentPane().add(lname);
        this.getContentPane().add(tfname);
        this.getContentPane().add(lhours);
        this.getContentPane().add(tfhours);
        this.getContentPane().add(lrate);
        this.getContentPane().add(tfrate);
        this.getContentPane().add(calcpay);
        this.getContentPane().add(next);
        this.getContentPane().add(info);
        this.getContentPane().add(start);
        this.getContentPane().add(end);
        this.getContentPane().add(info);

        try {
            reader = new BufferedReader(new FileReader("employee.dat"));
        } catch (FileNotFoundException e) {
        }

        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    reader.close();
                } catch (IOException i) {
                }
                try {
                    reader = new BufferedReader(new FileReader("employee.dat"));
                } catch (FileNotFoundException f) {
                }
            }
        });
        end.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == end) {
                    try {
                        reader.close();
                    } catch (IOException i) {
                    }
                }
            }
        });
        next.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == next) {
                    try {
                        temp = reader.readLine();
                    } catch (IOException i) {
                    }
                    if (temp != null) {
                        for (int i = 0; i < temp.length(); i++) {
                            if (temp.charAt(i) == '*') {
                                ast[astc] = i;
                                astc++;
                            }
                        }
                        name = new String(temp.substring(ast[0] + 1, ast[1]));
                        num_hours = Double.parseDouble(temp.substring(ast[2] + 1, ast[3]));
                        pay_rate = Double.parseDouble(temp.substring(ast[4] + 1, ast[5]));
                        social = new String(temp.substring(ast[6] + 1, ast[7]));
                        astc = 0;
                        tfname.setText(name);
                        tfhours.setText(Double.toString(num_hours));
                        tfrate.setText(Double.toString(pay_rate));
                    } else {
                        tfname.setText("");
                        tfhours.setText("");
                        tfrate.setText("");
                        info.setText("End of Database :(");
                    }
                }
            }
        });
        calcpay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == calcpay) {
                    if (num_hours < 40) {
                        total = num_hours * pay_rate;
                    } else {
                        total = (num_hours - 40) * (pay_rate * 1.5) + (num_hours * pay_rate);
                    }
                    info.setText("Payroll Report:\nEmployee #" + social.substring(0, 2) + "\n" + name + "\nSS# " + social + "\nTotal Pay: " + Double.toString(total));
                }
            }
        });
    }
}
