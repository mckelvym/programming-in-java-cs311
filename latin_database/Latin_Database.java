// mark mckelvy
// april 1, 2004
// latin database program

/****************************************************************************************

 This program uses file with a list of words to display in a list.
 The program allows for the user to add new entries, edit current entries,
 remove entries, copy entries to the system clipboard, sort the entries alphabetically,
 save during runtime, and searching through the list using a textbox that
 updates the list in real-time as the user types
 ****************************************************************************************/

import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;

public class Latin_Database extends JFrame {

    static boolean ok2del = false;
    static int width = 700, height = 500;
    static int xpos, ypos;
    static int sizeoflist = 0;
    static Add addData;
    static Ask ask;
    static BufferedReader reader;
    static Clipboard clipboard;
    static Dimension screen_dimension;
    static Font font;
    static GridBagConstraints gbc;
    static GridBagLayout layout;
    static JButton add, close, copy, edit, remove, save, sort;
    static JList list;
    static JMenuItem jadd, jclose, jcopy, jedit, jremove;
    static JPanel leftpane, rightpane, right, empty;
    static JPopupMenu popup;
    static JScrollPane scrollpane;
    static JTextField searchfield;
    static PrintWriter writer;
    static SecurityManager securitymanager;
    static StringSelection stringselection;
    static String[] listdata;
    static String file = "Database.dat";
    static String title = "Database Search Program";
    static Toolkit toolkit;

    static Latin_Database LatinDatabase;

    Latin_Database() {
        super(title);
        this.setSize(width, height);

        // center the window on the screen
        screen_dimension = Toolkit.getDefaultToolkit().getScreenSize();
        xpos = (int) ((screen_dimension.getWidth() - width) / 2);
        ypos = (int) ((screen_dimension.getHeight() - height) / 2);
        this.setLocation(xpos, ypos);

        // initialize variables
        gbc = new GridBagConstraints();
        layout = new GridBagLayout();
        leftpane = new JPanel();
        rightpane = new JPanel();
        right = new JPanel();
        empty = new JPanel();
        list = new JList();
        scrollpane = new JScrollPane(list);
        add = new JButton("Add");
        close = new JButton("Close");
        copy = new JButton("Copy");
        edit = new JButton("Edit");
        remove = new JButton("Remove");
        save = new JButton("Save");
        sort = new JButton("Sort");
        popup = new JPopupMenu();
        jadd = new JMenuItem("Add");
        jcopy = new JMenuItem("Copy");
        jedit = new JMenuItem("Edit");
        jremove = new JMenuItem("Remove");
        searchfield = new JTextField();
        toolkit = Toolkit.getDefaultToolkit();

        // set layouts
        this.getContentPane().setLayout(layout);
        leftpane.setLayout(new GridLayout(1, 1));
        rightpane.setLayout(new GridLayout(5, 2));
        right.setLayout(new GridLayout(2, 1));
        empty.setLayout(new GridLayout(8, 1));

        // set constraints: x,y,width,height,weightx/y, filltype, object
        setConststraints(1, 1, GridBagConstraints.RELATIVE, 1, .6, 1, GridBagConstraints.BOTH, leftpane);
        setConststraints(2, 1, GridBagConstraints.REMAINDER, 1, .3, 1, GridBagConstraints.BOTH, right);

        // add objects to panes
        this.getContentPane().add(leftpane);
        this.getContentPane().add(right);
        right.add(rightpane);
        right.add(empty);
        leftpane.add(scrollpane);
        rightpane.add(add);
        rightpane.add(edit);
        rightpane.add(copy);
        rightpane.add(remove);
        rightpane.add(save);
        rightpane.add(sort);
        rightpane.add(close);
        empty.add(new JLabel("Search:"));
        empty.add(searchfield);

        // add buttons to the jpopupmenu
        popup.add(jadd);
        popup.add(jedit);
        popup.addSeparator();
        popup.add(jcopy);
        popup.addSeparator();
        popup.add(jremove);
        list.add(popup);

        // set the colors for the objects
        font = new Font("Comic Sans MS", Font.PLAIN, 12);
        list.setFont(font);
        list.setForeground(Color.blue);
        list.setBackground(Color.white);
        list.setSelectionForeground(Color.blue);
        list.setSelectionBackground(Color.yellow);
        font = new Font("Comic Sans MS", Font.BOLD, 12);
        add.setFont(font);
        edit.setFont(font);
        copy.setFont(font);
        remove.setFont(font);
        save.setFont(font);
        close.setFont(font);
        jadd.setFont(font);
        jedit.setFont(font);
        jcopy.setFont(font);
        jremove.setFont(font);
        searchfield.setFont(font);
        searchfield.setColumns(25);

        // get the data
        readData();

        // adds data to the list
        add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == add) {
                    addData = new Add(-1);
                    addData.setVisible(true);

                    addData.addWindowListener(new WindowAdapter() {
                        public void windowClosing(WindowEvent we) {
                            addData.dispose();
                        }
                    });
                }
            }
        });
        // allows editing of current entry
        edit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == edit) {
                    if (list.getSelectedIndex() != -1) {
                        int index = list.getSelectedIndex();
                        addData = new Add(index);
                        addData.edit();
                        addData.setVisible(true);

                        addData.addWindowListener(new WindowAdapter() {
                            public void windowClosing(WindowEvent we) {
                                addData.dispose();
                            }
                        });
                    }
                }
            }
        });
        // copies information from current selection to clipboard
        copy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == copy) {
                    SendToClipboard s2c = new SendToClipboard();
                }
            }
        });
        // removes an item from the list
        remove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == remove) {
                    if (list.getSelectedIndex() != -1) {
                        ask = new Ask();
                        ask.setVisible(true);
                    }
                }
            }
        });
        // saves the data to the file
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == save) {
                    writeData();
                    Done done = new Done("Saved");
                    done.setVisible(true);
                }
            }
        });
        // sorts the data in the list
        sort.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == sort) {
                    ArrayList array = new ArrayList();
                    for (int x = 0; x < sizeoflist; x++) {
                        array.add(listdata[x]);
                    }
                    Collections.sort(array);
                    Object[] object = array.toArray();
                    for (int x = 0; x < sizeoflist; x++) {
                        listdata[x] = new String(object[x].toString());
                    }
                    list.setListData(listdata);
                }
            }
        });
        // popup button for add
        jadd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == jadd) {
                    add.doClick();
                }
            }
        });
        // popup for editing
        jedit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == jedit) {
                    edit.doClick();
                }
            }
        });
        // popup button for copy function
        jcopy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == jcopy) {
                    copy.doClick();
                }
            }
        });
        // popup button for remove
        jremove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == jremove) {
                    remove.doClick();
                }
            }
        });
        // listens for events on the list, and if there is a right click, shows the popup
        list.addMouseListener(new MouseListener() {
            public void mouseExited(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mousePressed(MouseEvent e) {
            }

            public void mouseClicked(MouseEvent e) {
            }

            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    popup.show(list, e.getX(), e.getY());
                }
            }
        });
        // closes the program and saves the data
        close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == close) {
                    writeData();
                    System.exit(0);
                }
            }
        });
        searchfield.addCaretListener(new CaretListener() {
            public void caretUpdate(CaretEvent e) {
                int search_length = 0;
                int another_list_length = 0;
                int another_num = 0;
                String search_string;
                String[] another_list;

                search_string = new String(searchfield.getText());
                search_length = search_string.length();

                for (int x = 0; x < sizeoflist; x++) {
                    if (listdata[x].substring(0, search_length).toUpperCase().equals(search_string.toUpperCase())) {
                        another_list_length++;
                    }
                }
                another_list = new String[another_list_length + 1];
                for (int x = 0; x < sizeoflist; x++) {
                    if (listdata[x].substring(0, search_length).toUpperCase().equals(search_string.toUpperCase())) {
                        another_list[another_num] = listdata[x];
                        another_num++;
                    }
                }
                list.setListData(another_list);
            }
        });
        // stops program if this window is closed
        // closes the program and saves the data
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                writeData();
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        LatinDatabase = new Latin_Database();
        LatinDatabase.setVisible(true);
    }

    // reads data from the file
    public void readData() {
        String temp;
        try {
            reader = new BufferedReader(new FileReader(file));
            if (reader != null) {
                do {
                    temp = reader.readLine();
                    if (temp != null && (temp.length() > 1))
                        sizeoflist++;
                } while (temp != null);
            }
            reader.close();
            listdata = new String[sizeoflist];
            reader = new BufferedReader(new FileReader(file));
            if (reader != null) {
                int x = 0;
                String temp2;
                do {
                    temp2 = reader.readLine();
                    if (temp2 != null && (temp2.length() > 1)) {
                        listdata[x] = temp2;
                        x++;
                    }
                } while (temp2 != null);
                //for(int x = 0; x < sizeoflist; x++){
                //      listdata[x] = reader.readLine();
                //  }
            }
            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        list.setListData(listdata);
    }

    // writes the data to the file
    public void writeData() {
        try {
            writer = new PrintWriter(new FileWriter(file));
            for (int x = 0; x < sizeoflist; x++) {
                writer.println(listdata[x]);
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // updates the list
    public void setData(String string, boolean isEdit) {

        if (!isEdit) {
            String[] temp = new String[sizeoflist + 1];

            for (int x = 0; x < sizeoflist; x++) {
                temp[x] = new String(listdata[x]);
            }
            temp[sizeoflist] = string;
            list.setListData(temp);
            listdata = new String[sizeoflist];
            sizeoflist++;
            listdata = temp;
        } else {
            int index = list.getSelectedIndex();
            listdata[index] = string;
            list.setListData(listdata);
        }
    }

    public void setConststraints(int xposition, int yposition, int objectwidth, int objectheight, double weightx, double weighty, int filltype, Component object) {
        gbc.gridx = xposition;
        gbc.gridy = yposition;
        gbc.gridwidth = objectwidth;
        gbc.gridheight = objectheight;
        gbc.weightx = weightx;
        gbc.weighty = weighty;
        gbc.fill = filltype;
        layout.setConstraints(object, gbc);
    }

    class Add extends JFrame {

        boolean isEdit = false;
        int selectedindex;
        GridBagConstraints agbc = new GridBagConstraints();
        GridBagLayout agbl = new GridBagLayout();
        JButton aok = new JButton("Ok"), acancel = new JButton("Cancel");
        JLabel alabel = new JLabel("Input Data:");
        JTextField ainput = new JTextField();

        Add(int theindex) {
            super("Add Data");
            this.setSize(400, 100);
            screen_dimension = Toolkit.getDefaultToolkit().getScreenSize();
            xpos = (int) ((screen_dimension.getWidth() - 400) / 2);
            ypos = (int) ((screen_dimension.getHeight() - 150) / 2);
            this.setLocation(xpos, ypos);
            this.setBackground(Color.black);
            alabel.setForeground(Color.blue);
            selectedindex = theindex;
            ainput.setColumns(80);

            // set layout
            this.getContentPane().setLayout(agbl);
            agbc.weightx = .2;
            agbc.weighty = 0;
            agbc.gridx = 1;
            agbc.gridy = 1;
            agbc.gridwidth = 1;
            agbc.gridheight = 1;
            agbc.fill = GridBagConstraints.RELATIVE;
            agbl.setConstraints(alabel, agbc);
            agbc.weightx = 0;
            agbc.gridx = 1;
            agbc.gridy = 2;
            agbc.gridwidth = 2;
            agbc.gridheight = 1;
            agbc.fill = GridBagConstraints.HORIZONTAL;
            agbl.setConstraints(ainput, agbc);
            agbc.weightx = .5;
            agbc.weighty = 0;
            agbc.gridx = 1;
            agbc.gridy = 3;
            agbc.gridwidth = 1;
            agbc.fill = GridBagConstraints.RELATIVE;
            agbl.setConstraints(acancel, agbc);
            agbc.gridx = 2;
            agbc.gridwidth = 1;
            agbc.fill = GridBagConstraints.REMAINDER;
            agbl.setConstraints(aok, agbc);
            this.getContentPane().add(alabel);
            this.getContentPane().add(ainput);
            this.getContentPane().add(acancel);
            this.getContentPane().add(aok);

            aok.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == aok) {
                        setData(ainput.getText(), isEdit);
                        LatinDatabase.setVisible(true);
                        if (selectedindex != -1) {
                            list.setSelectedIndex(selectedindex);
                        } else {
                            list.setSelectedIndex(sizeoflist - 1);
                        }
                        dispose();
                    }
                }
            });
            acancel.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == acancel) {
                        LatinDatabase.setVisible(true);
                        dispose();
                    }
                }
            });
            ainput.addMouseListener(new MouseListener() {
                public void mouseExited(MouseEvent e) {
                }

                public void mouseEntered(MouseEvent e) {
                }

                public void mousePressed(MouseEvent e) {
                }

                public void mouseClicked(MouseEvent e) {
                }

                public void mouseReleased(MouseEvent e) {
                    if (e.isPopupTrigger()) {

                        GetClipboard gc = new GetClipboard();
                        ainput.setText(gc.getData());

                    }
                }
            });

        }

        void edit() {
            isEdit = true;
            ainput.setText(new String((String) list.getSelectedValue()));
        }
    }

    class Ask extends JFrame {

        JButton aok = new JButton("Ok"), acancel = new JButton("Cancel");

        Ask() {
            super("Are You Sure?");
            this.setSize(200, 75);
            screen_dimension = Toolkit.getDefaultToolkit().getScreenSize();
            xpos = (int) ((screen_dimension.getWidth() - 200) / 2);
            ypos = (int) ((screen_dimension.getHeight() - 75) / 2);
            this.setLocation(xpos, ypos);

            // set layout
            this.getContentPane().setLayout(new GridLayout(1, 2));
            this.getContentPane().add(aok);
            this.getContentPane().add(acancel);

            aok.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == aok) {
                        ok2del = true;
                        int counter = 0;
                        String[] temp = new String[sizeoflist - 1];
                        for (int x = 0; x < sizeoflist; x++) {
                            if (x != list.getSelectedIndex()) {
                                temp[counter] = listdata[x];
                                counter++;
                            }
                        }
                        listdata = new String[counter];
                        listdata = temp;
                        sizeoflist--;
                        list.setListData(listdata);
                        dispose();
                    }
                }
            });
            acancel.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == acancel) {
                        ok2del = false;
                        dispose();
                    }
                }
            });
        }
    }

    class Done extends JFrame {

        JButton aok = new JButton("Ok");

        Done(String title) {
            super(title);
            this.setSize(125, 65);
            screen_dimension = Toolkit.getDefaultToolkit().getScreenSize();
            xpos = (int) ((screen_dimension.getWidth() - 125) / 2);
            ypos = (int) ((screen_dimension.getHeight() - 65) / 2);
            this.setLocation(xpos, ypos);

            // set layout
            this.getContentPane().setLayout(new GridLayout(1, 1));
            this.getContentPane().add(aok);

            aok.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == aok) {
                        dispose();
                    }
                }
            });
        }
    }

    // pastes information from the clipboard
    class GetClipboard {

        boolean returntemp = true;
        Transferable transferable;
        String temp;

        public String getData() {
            clipboard = toolkit.getSystemClipboard();
            transferable = clipboard.getContents(null);
            try {
                if (transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                    temp = (String) transferable.getTransferData(DataFlavor.stringFlavor);
                    returntemp = true;
                } else {
                    returntemp = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (returntemp) {
                return temp;
            } else {
                return "";
            }
        }
    }

    // copies information about current entry to the clipboard
    class SendToClipboard implements ClipboardOwner {

        boolean ok2proceed = true;

        SendToClipboard() {
            // used to check for clipboard access
            securitymanager = System.getSecurityManager();
            // throws SecurityException if access the system clipboard not allowed.
            if (securitymanager != null) {
                try {
                    securitymanager.checkSystemClipboardAccess();
                } catch (SecurityException e) {
                    ok2proceed = false;
                }
            }
            if ((list.getSelectedIndex() != -1) && (ok2proceed)) {
                // a datatransfer type object must be used for setting objects on the system clipboard
                stringselection = new StringSelection(new String((String) list.getSelectedValue()));
                // "gets" the system clipboard
                clipboard = toolkit.getSystemClipboard();
                // sets the string on the clipboard, using the parameters of a datatransfer object
                // and a clipboard owner (this)
                clipboard.setContents(stringselection, this);
            }
        }

        public void lostOwnership(Clipboard c, Transferable t) {
        }
    }
}
