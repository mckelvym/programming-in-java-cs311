import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.event.*;

class ConvertString extends JFrame {

    static ConvertString This;

    ConvertString() {
        super("Convert String");
        this.setSize(300, 150);
        this.setLocation((1024 - 300) / 2, (768 - 300) / 2);
        this.getContentPane().setLayout(new GridLayout(1, 1));
        subClass subclass = new subClass();
        this.getContentPane().add(subclass);
    }

    public static void main(String[] args) {
        This = new ConvertString();
        This.setVisible(true);
    }

    class subClass extends JPanel {

        JButton convert, close;
        JTextField input, output;

        subClass() {
            this.setLayout(new GridLayout(2, 2));

            convert = new JButton("Convert");
            close = new JButton("Close");
            input = new JTextField();
            output = new JTextField();

            this.add(input);
            this.add(output);
            this.add(convert);
            this.add(close);

            convert.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == convert) {
                        String getText = input.getText();
                        char[] chars = new char[getText.length()];
                        chars = getText.toCharArray();
                        char temp;
                        for (int i = 0; i < (getText.length() / 2); i++) {
                            temp = chars[i];
                            chars[i] = chars[getText.length() - 1 - i];
                            chars[getText.length() - 1 - i] = temp;
                        }
                        System.out.println(chars);
                    }
                }
            });

            close.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == close) {
                        System.exit(0);
                    }
                }
            });
        }
    }
}
