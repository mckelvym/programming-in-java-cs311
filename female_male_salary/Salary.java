// mark mckelvy
// java
// feb 5, 2004
// lawsuit application

import java.awt.*;
import java.io.*;
import javax.swing.*;

public class Salary {
    static BufferedReader reader;
    static JButton Run;
    static JTextField filename;
    static JFrame display;
    static JLabel l_m, l_f, l_mt, l_ft, l_t, a_m, a_f;
    static PrintWriter writer;
    static String temp;
    static String file;
    static double total_f;
    static double total_m;
    static int numf;
    static int numm;

    public static void main(String[] args) throws Exception {
        // create a JFrame to show the results to the user
        display = new JFrame("Lawsuit");
        filename = new JTextField("");
        l_m = new JLabel("");
        l_f = new JLabel("");
        l_mt = new JLabel("");
        l_ft = new JLabel("");
        l_t = new JLabel("");
        a_m = new JLabel("");
        a_f = new JLabel("");
        Run = new JButton("Calculate and Output");
        display.setVisible(true);
        display.setSize(300, 300);
        display.setLocation(450, 200);
        display.getContentPane().setLayout(new GridLayout(5, 2));
        display.pack();
        display.show();
        display.getContentPane().add(Run);
        display.getContentPane().add(filename);
        display.getContentPane().add(l_m);
        display.getContentPane().add(l_f);
        display.getContentPane().add(l_t);
        display.getContentPane().add(l_mt);
        display.getContentPane().add(l_ft);
        display.getContentPane().add(a_m);
        display.getContentPane().add(a_f);
        reader = new BufferedReader(new FileReader("Data.dat"));
        /*Run.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                        if (e.getSource() == Run){*/
        //file = new String(filename.getText() + ".txt");
        file = "Output.txt";
        writer = new PrintWriter(new FileWriter(file));
        do {
            temp = reader.readLine();
            if (temp != null) {
                if (temp.charAt(0) == 'M') {
                    writer.println("Sex: " + temp.charAt(0));
                    temp = temp.substring(2, temp.length() - 1);
                    total_m += Double.parseDouble(temp);
                    writer.println("Pay: $" + Double.parseDouble(temp));
                    writer.println("");
                    numm++;
                }
                if (temp.charAt(0) == 'F') {
                    writer.println("Sex: " + temp.charAt(0));
                    temp = temp.substring(2, temp.length() - 1);
                    total_f += Double.parseDouble(temp);
                    writer.println("Pay: $" + Double.parseDouble(temp));
                    writer.println("");
                    numf++;
                }

            }
        } while (temp != null);
        l_m = new JLabel("Total Males: " + Double.toString(numm));
        l_f = new JLabel("Total Females: " + Double.toString(numf));
        l_t = new JLabel("Total Employees: " + Double.toString(numm + numf));
        l_mt = new JLabel("Total Male Salary: " + Double.toString(total_m));
        l_ft = new JLabel("Total Female Salary: " + Double.toString(total_f));
        a_m = new JLabel("Average Male Salary: " + Double.toString(total_m / numm));
        a_f = new JLabel("Average Female Salary: " + Double.toString(total_f / numf));
        // sends values to file
        writer.println("Total Males: " + Double.toString(numm));
        writer.println("Total Females: " + Double.toString(numf));
        writer.println("Total Employees: " + Double.toString(numm + numf));
        writer.println("Total Male Salary: " + Double.toString(total_m));
        writer.println("Total Female Salary: " + Double.toString(total_f));
        writer.println("Average Male Salary: " + Double.toString(total_m / numm));
        writer.println("Average Female Salary: " + Double.toString(total_f / numf));
        if ((total_m / numm) > (total_f / numf)) {
            writer.println("");
            writer.println("SUE THOSE MONKEYS!!!!!!!!!!!!");
        } else {
            writer.println("");
            writer.println("YOU ARE IN ERROR");
        }
        // close files being used
        reader.close();
        writer.close();
    }
}
