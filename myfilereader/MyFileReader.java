// mark mckelvy
// java
// feb 4, 2004
// filereader application

import java.awt.*;
import java.io.*;
import javax.swing.*;

public class MyFileReader {
    static BufferedReader reader;
    static JFrame display;
    static PrintWriter writer;
    static String employee_id;
    static String helper;
    static double hours;
    static double rate;
    static double wages;

    public static void main(String[] args) throws Exception {
        // reader reads the file, writer writes to a file
        reader = new BufferedReader(new FileReader("info.dat"));
        writer = new PrintWriter(new FileWriter("output.txt"));
        // create a JFrame to show the results to the user
        display = new JFrame();
        display.setVisible(true);
        display.setSize(300, 300);
        display.getContentPane().setLayout(new GridLayout(5, 4));
        display.pack();
        display.show();
        // read the first line to check if the file has info
        employee_id = reader.readLine();
        while (employee_id != null) {
            helper = reader.readLine();
            hours = (Double.valueOf(helper)).doubleValue();
            helper = reader.readLine();
            rate = Double.valueOf(helper).doubleValue();
            // overtime
            if (hours > 40) {
                wages = ((40 * rate) + (1.5 * rate * (hours - 40)));
            }
            // regular pay
            else {
                wages = (hours * rate);
            }
            // sends values to JFrame
            display.getContentPane().add(new JLabel("ID: " + employee_id));
            display.getContentPane().add(new JLabel("Hours: " + Double.toString(hours)));
            display.getContentPane().add(new JLabel("Rate: " + Double.toString(rate)));
            display.getContentPane().add(new JLabel("Wages: " + Double.toString(wages)));
            // sends values to file
            writer.println("ID: " + employee_id);
            writer.println("Hours: " + Double.toString(hours));
            writer.println("Rate: " + Double.toString(rate));
            writer.println("Wages: " + Double.toString(wages));
            writer.println("");
            // send values to system output
            System.out.println(employee_id);
            System.out.println(hours);
            System.out.println(rate);
            System.out.println(wages);
            // read in new employee id
            employee_id = reader.readLine();
        }
        // close files being used
        reader.close();
        writer.close();
    }
}
