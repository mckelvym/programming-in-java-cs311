// mark mckelvy
// sorts music library

import java.awt.*;
import java.io.*;
import javax.swing.*;
import java.awt.event.*;

public class SortLibrary extends JFrame {

    static int size;
    static int messages = 1;
    static BufferedReader reader;
    static JButton btnOk;
    static JTextField output;
    static JList jlist;
    static JPanel display;
    static JPanel upper, lower;
    static JScrollPane scrollpane;
    static JTextField input;
    static JTextField outputf;
    static PrintWriter writer;
    static SortFile sortfile;
    static String file = new String("Library.dat");
    static String[] list;

    public SortLibrary() {
        // gives the name to this frame
        super("Sort Library");
        this.setSize(500, 300);

        // create a jpanel to add stuff to
        display = new JPanel();
        upper = new JPanel();
        lower = new JPanel();
        this.getContentPane().setLayout(new GridLayout(1, 1));
        this.getContentPane().add(display);

        btnOk = new JButton("Sort Songs");
        input = new JTextField();
        outputf = new JTextField();
        output = new JTextField("");

        input.setColumns(30);
        outputf.setColumns(30);
        output.setForeground(Color.red);

        display.setLayout(new GridLayout(2, 1));
        display.add(upper);
        display.add(lower);
        upper.setLayout(new GridLayout(4, 2));
        upper.add(new JLabel("Input Filename:"));
        upper.add(input);
        upper.add(new JLabel("Output Filename: "));
        upper.add(outputf);
        upper.add(new JLabel("Press to Sort"));
        upper.add(btnOk);
        upper.add(new JLabel("Output ---> "));
        upper.add(output);
        lower.setLayout(new GridLayout(1, 1));

        btnOk.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == btnOk) {
                    file = new String(input.getText());
                    String outfile = new String(outputf.getText());
                    if (!(file.length() <= 4 || outfile.length() <= 4)) {
                        sortfile = new SortFile();
                        size = sortfile.ListSize(file);
                        list = new String[size];
                        list = sortfile.SortSongs(file, outfile);
                        jlist = new JList(list);
                        scrollpane = new JScrollPane(jlist);
                        lower.add(scrollpane);
                        output.setText("Successful!");
                    } else {
                        if (file.length() <= 4 && outfile.length() <= 4) {
                            output.setText(": I/O Filenames Missing!");
                        } else {
                            if (file.length() <= 4) {
                                output.setText(": Input Filename Missing");
                            }
                            if (outfile.length() <= 4) {
                                output.setText(": Output Filename Missing");
                            }
                        }
                    }
                }
            }
        });
    }

    public static void main(String[] args) throws Exception {
        // create an instance of self and show it
        SortLibrary sortlibrary = new SortLibrary();
        sortlibrary.setVisible(true);
    }

    static class SortFile {

        boolean keepgoing = true;
        int counter = 0;
        Sort sort_list;
        String[] display_list;
        String read;

        SortFile() {
        }

        int ListSize(String file_name) {
            boolean keepgoing = true;
            int counter = 0;
            String read;

            try {
                reader = new BufferedReader(new FileReader(file_name));
            } catch (FileNotFoundException e) {
                read = null;
            }
            ;

            // find out how many songs there are
            while (keepgoing) {
                try {
                    read = reader.readLine();
                } catch (IOException e) {
                    read = null;
                    output.setText(": Reader Error!");
                }
                ;
                if (read != null) {
                    counter++;
                } else {
                    keepgoing = false;
                }
            }

            // close the file being read, then reopen it to read in the information
            try {
                reader.close();
            } catch (IOException e) {
            }
            ;
            return counter;
        }

        String[] SortSongs(String file_name, String outputfile) {
            // open file for input
            try {
                reader = new BufferedReader(new FileReader(file_name));
            } catch (FileNotFoundException e) {
                read = null;
                output.setText(": Reader Error!");
            }
            ;

            // find out how many songs there are
            while (keepgoing) {
                try {
                    read = reader.readLine();
                } catch (IOException e) {
                    read = null;
                    output.setText(": Reader Error!");
                }
                ;
                if (read != null) {
                    counter++;
                } else {
                    keepgoing = false;
                }
            }

            // close the file being read, then reopen it to read in the information
            try {
                reader.close();
            } catch (IOException e) {
            }
            ;
            try {
                reader = new BufferedReader(new FileReader(file_name));
            } catch (FileNotFoundException e) {
                output.setText(": Reader Error!");
            }
            ;

            // initialize the string arrays with the amount of songs to be held
            display_list = new String[counter];
            keepgoing = true;
            counter = 0;

            // read in the song information
            while (keepgoing) {
                try {
                    read = reader.readLine();
                } catch (IOException e) {
                }
                ;
                if (read != null) {
                    display_list[counter] = new String(read);
                    counter++;
                } else {
                    keepgoing = false;
                }
            }
            try {
                reader.close();
            } catch (IOException e) {
            }
            ;
            sort_list = new Sort();
            display_list = sort_list.SortIt(counter + 1, display_list);

            for (int i = 0; i < counter; i++) {
                display_list[i] = new String(display_list[i].substring(0, 1).toUpperCase() + display_list[i].substring(1, display_list[i].length()));
                for (int j = 1; j < display_list[i].length(); j++) {
                    if (display_list[i].charAt(j) != ' ') {
                        if ((j != display_list[i].length() - 1) && (display_list[i].charAt(j + 1) != ' ') && (display_list[i].charAt(j - 1) == ' ')) {
                            display_list[i] = new String(display_list[i].substring(0, j) + display_list[i].substring(j, j + 1).toUpperCase() + display_list[i].substring(j + 1, display_list[i].length()));
                        }
                    }
                }

            }
            try {
                writer = new PrintWriter(new FileWriter(outputfile));
                for (int i = 0; i < counter; i++) {
                    writer.println(display_list[i]);
                }
                writer.close();
            } catch (IOException e) {
                output.setText("File Write Error!");
            }
            return (display_list);
        }
    }

    static class Sort {
        Sort() {
        }

        String[] SortIt(int counter, String display_list[]) {
            String temp;

            // Bubble sort method.
            for (int x = 0; x < counter; x++) {
                for (int y = 0; y < (counter - 2); y++) {
                    if (display_list[y].toUpperCase().compareTo(display_list[y + 1].toUpperCase()) > -1) {
                        temp = new String(display_list[y]);
                        display_list[y] = new String(display_list[y + 1]);
                        display_list[y + 1] = new String(temp);
                    }
                }
            }
            return (display_list);
        }
    }
}
