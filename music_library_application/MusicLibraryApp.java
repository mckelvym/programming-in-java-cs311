/*******************************
 **        mark mckelvy       **
 **        feb 10, 2004       **
 ** music library application **
 *******************************/

// INFORMATION //
/************************************************************************************
 *
 * The program is an application runnable from the DOS prompt. The program
 * allows playing of audio clips, and is also a music library organizational tool.
 *
 * Login
 * Upon running the program, the user is prompted with a login window,
 * which is centered in the middle of the screen. The user must already
 * have a username and an associated password to log into the program.
 * The username and password are stored in a data file. The main window
 * is opened, and the login screen closes if the username and password
 * is correct.
 *
 * Main Window
 * The main window of the program contains several different genre tabs
 * for various song genres. The tabs include thumbnail icons to help
 * the user to identify each genre. In addition to the genre tabs,
 * there is an extra tab for configuring certain items of the program.
 *
 * Genre Pane
 * A specific genre pane, which is specified by the text on each tab,
 * contains several elements for manipulating songs that are read from
 * a central file as text names. Each pane accesses the main data file
 * containing all songs, according to artist, album, track number,
 * track title, year published and genre. This information is separated
 * by hyphens so that the program can distinguish between each unit of
 * information. The pane separates the songs that are of its particular
 * genre and outputs them into a scrollable list, sorted, and with
 * initial caps for each word to aid in readability.
 *
 * Above the list that the pane displays, are five labeled radio buttons.
 * Each button allows the list of songs to be sorted by artist, album,
 * track number, track title, or year. Additionally, the user is able
 * to right click anywhere on the list and a popup menu with all of the
 * same functions in addition to some others are available.
 *
 * Below the song list is a text field and four buttons.  The text
 * field allows the user to type text while the list is updated
 * (sorted) according to the songs that match the entered text in real
 * time. The buttons below allow for adding new songs, removing songs,
 * playing songs, and closing the program. Also, the popup menu that
 * appears when the user right clicks on the list shows these same
 * options along with the previously mentioned sorting options.
 *
 * Configure Pane
 * The configure pane allows the user to change the colors of some
 * of the components in the program. To the left is a color chooser
 * component with three tabs. The first tab contains a palette of
 * colors to choose from, the second tabs allows the user to pick
 * more specifically a certain shade of a color, and the last tab
 * allows the user to set the precise intensity of red, green, and
 * blue, on a 0-255 scale. To the right are several buttons that allow
 * the colors to be changed on the list background, foreground,
 * button colors, and more. The color settings are saved in a data
 * file that is loaded with the program. Also, there is a text field
 * and a button that allows a user to change library files (where the
 * songs are stored). If this is changed, the program will load the
 * new songs into the each of the genre panes. The last button on the
 * configure pane allows adding and removing of users.
 *
 * Adding/Removing Users
 * When the button for adding/removing users is clicked, the main
 * window closes and the user is shown a list with the current users
 * and options to add or remove users. When the "Add User" button is
 * clicked, the user is presented with a box that the user must enter
 * a username, and a password, and the password again for verification.
 * When the button for removing users is clicked, the currently
 * selected user is immediately removed. Lastly, there is a back button
 * for the user to return to the program.
 *
 * Play Dialog Box
 * The user may click the button to play a song, or a click on the
 * popup menu item that allows a song to be played. In any case, the
 * main window closes and the user is presented with a small dialog
 * box that has the current status of the song (playing or stopped),
 * the name of the song that was selected, and buttons for play, stop,
 * and back.
 *
 * Final Words:
 * The program currently has more than 2,500 lines of code. Tidying of a
 * few dialogs, outputting of songs lists, and any other useful functions
 * are more likely than not going to be added. The program does not
 * play mpeg layer 3 (.mp3) files as this requires a significant amount
 * of extra code, as well as several files to be copied to the java
 * source directory which is not allowed on the school's systems. The
 * program does serve as useful way to keep track of songs, but is not
 * currently useful as a true media player.
 *
 ************************************************************************************/

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.colorchooser.*;

public class MusicLibraryApp extends JFrame {

    static int amount_of_songs;
    static int flistsize = 10;
    static int int_num_images;
    static int red, green, blue;
    static int width = 900, height = 550;
    static BufferedReader reader;
    static Color cRadioB = Color.white;
    static Color cRadioF = Color.blue;
    static Color cTabB = Color.white;
    static Color cTabF = Color.blue;
    static Color cPlaySF = Color.blue;
    static Color cPlaySB = Color.white;
    static Color cInputF = Color.blue;
    static Color cInputB = Color.yellow;
    static Color cPitemF = Color.green;
    static Color cPitemB = Color.black;
    static Color cListF = Color.blue;
    static Color cListB = Color.white;
    static Color cListSF = Color.green;
    static Color cListSB = Color.black;
    static Dimension screen;
    static Font font;
    static Font lfont;
    static Image[] the_images;
    static ImageIcon techno_icon;
    static ImageIcon rock_icon;
    static ImageIcon country_icon;
    static ImageIcon classical_icon;
    static ImageIcon rap_icon;
    static ImageIcon comedy_icon;
    static ImageIcon break_icon;
    static ImageIcon rnb_icon;
    static ImageIcon config_icon;
    static JPanel display;
    static JTabbedPane tabbedpane;
    static PrintWriter writer;
    static String file_name;
    static String song_value;
    static String str_num_images;
    static String strred, strgreen, strblue;
    static String[] song_list;
    static String[] str_images;

    static ConfigurePane config_m;
    static EditUsers editusers;
    static MusicPane techno_m;
    static MusicPane rock_m;
    static MusicPane country_m;
    static MusicPane classical_m;
    static MusicPane rap_m;
    static MusicPane comedy_m;
    static MusicPane break_m;
    static MusicPane rnb_m;
    static MyDialog mydialog;
    static PlayDialog playDialog;
    static SortFile sortfile;

    static MusicLibraryApp me;

    public MusicLibraryApp() {
        super("Music Library");
        // set size and location
        setSize(width, height);
        // get the current resolution
        screen = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((int) ((screen.getWidth() - width) / 2), (int) ((screen.getHeight() - height) / 2));
        // panel on which everything goes
        display = new JPanel();

        // login dialog
        mydialog = new MyDialog();
        mydialog.setVisible(true);
        mydialog.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        this.getContentPane().setLayout(new GridLayout(1, 1));
        this.getContentPane().add(display);
        display.setBackground(Color.black);

        // get info from file, sort, and send to panes
        sortfile = new SortFile();
        amount_of_songs = sortfile.ListSize(file_name);
        song_list = new String[amount_of_songs];
        song_list = sortfile.SortSongs(file_name);

        // create a new icon to add to each tab
        techno_icon = new ImageIcon(the_images[0]);
        rock_icon = new ImageIcon(the_images[1]);
        country_icon = new ImageIcon(the_images[2]);
        classical_icon = new ImageIcon(the_images[3]);
        rap_icon = new ImageIcon(the_images[4]);
        comedy_icon = new ImageIcon(the_images[5]);
        break_icon = new ImageIcon(the_images[6]);
        rnb_icon = new ImageIcon(the_images[7]);
        config_icon = new ImageIcon(the_images[8]);

        // create a new class to add to the tabbedpane
        techno_m = new MusicPane(song_list, amount_of_songs, "Techno");
        rock_m = new MusicPane(song_list, amount_of_songs, "Rock");
        country_m = new MusicPane(song_list, amount_of_songs, "Country");
        classical_m = new MusicPane(song_list, amount_of_songs, "Classical");
        rap_m = new MusicPane(song_list, amount_of_songs, "Rap");
        comedy_m = new MusicPane(song_list, amount_of_songs, "Comedy");
        break_m = new MusicPane(song_list, amount_of_songs, "Break");
        rnb_m = new MusicPane(song_list, amount_of_songs, "RNB");
        config_m = new ConfigurePane();

        // create a new jtabbedpane for each genre
        tabbedpane = new JTabbedPane();

        // set the font of the tabbed pane and the color
        font = new Font("Comic Sans MS", Font.ITALIC, 14);
        tabbedpane.setFont(font);
        tabbedpane.setForeground(cTabF);
        tabbedpane.setBackground(cTabB);

        // add the tabbedpane to the JApplet
        display.add(tabbedpane);

        // add tabs to the tabbedpane
        tabbedpane.addTab("Techno", techno_icon, techno_m, "Genre: Techno");
        tabbedpane.addTab("Rock", rock_icon, rock_m, "Genre: Rock");
        tabbedpane.addTab("Break", break_icon, break_m, "Genre: Break");
        tabbedpane.addTab("R&B", rnb_icon, rnb_m, "Genre: RNB");
        tabbedpane.addTab("Classical", classical_icon, classical_m, "Genre: Classical");
        tabbedpane.addTab("Rap", rap_icon, rap_m, "Genre: Rap");
        tabbedpane.addTab("Comedy", comedy_icon, comedy_m, "Genre: Comedy");
        tabbedpane.addTab("Country", country_icon, country_m, "Genre: Country");
        tabbedpane.addTab("Configure", config_icon, config_m, "Configure");

        // if the window is closed, write information to the file and close
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                writeToFile();
                System.exit(0);
            }
        });
    }

    public static void main(String args[]) {
        me = new MusicLibraryApp();
    }

    // writes to the System.dat file what the last library file was, the images being used,
    // and the last colors used...so that the program can load them on next startup
    // aka...memory
    public void writeToFile() {
        try {
            writer = new PrintWriter(new FileWriter("System.dat"));
            writer.println(file_name);
            writer.println(str_num_images);
            for (int i = 0; i < int_num_images; i++) {
                writer.println(str_images[i]);
            }
            writer.println(cRadioB.getRed() + "," + cRadioB.getGreen() + "," + cRadioB.getBlue());
            writer.println(cRadioF.getRed() + "," + cRadioF.getGreen() + "," + cRadioF.getBlue());
            writer.println(cTabB.getRed() + "," + cTabB.getGreen() + "," + cTabB.getBlue());
            writer.println(cTabF.getRed() + "," + cTabF.getGreen() + "," + cTabF.getBlue());
            writer.println(cInputF.getRed() + "," + cInputF.getGreen() + "," + cInputF.getBlue());
            writer.println(cInputB.getRed() + "," + cInputB.getGreen() + "," + cInputB.getBlue());
            writer.println(cPitemF.getRed() + "," + cPitemF.getGreen() + "," + cPitemF.getBlue());
            writer.println(cPitemB.getRed() + "," + cPitemB.getGreen() + "," + cPitemB.getBlue());
            writer.println(cListF.getRed() + "," + cListF.getGreen() + "," + cListF.getBlue());
            writer.println(cListB.getRed() + "," + cListB.getGreen() + "," + cListB.getBlue());
            writer.println(cListSF.getRed() + "," + cListSF.getGreen() + "," + cListSF.getBlue());
            writer.println(cListSB.getRed() + "," + cListSB.getGreen() + "," + cListSB.getBlue());
            writer.close();
        } catch (IOException io) {
        }
    }

    // used for debugging
    public void msg(String Msg) {
        System.out.println(Msg);
    }

    // this is a panel for each genre, according to the genre sent to it
    class MusicPane extends JPanel {

        boolean keepgoing = true;
        int dash[] = new int[5];
        int dash_counter;
        int counter;
        int genrecounter = 0;
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        JButton add = new JButton("Add Song");
        JButton close = new JButton("Close");
        JButton play = new JButton("Play Song");
        JButton remove = new JButton("Remove Song");
        JLabel lsearch = new JLabel("Search Song --> ");
        JList JL_songlist;
        // JMenuBar menu_bar;
        // JMenu edit;
        JMenuItem r_colors;
        JPanel top, mid, bottom;
        JPopupMenu popupmenu;
        JMenuItem pplay;
        JMenuItem padd;
        JMenuItem pedit;
        JMenuItem premove;
        JMenuItem partist;
        JMenuItem palbum;
        JMenuItem pnumber;
        JMenuItem ptitle;
        JMenuItem pyear;
        JMenuItem poutput;
        JRadioButton radio_artist;
        JRadioButton radio_album;
        JRadioButton radio_number;
        JRadioButton radio_title;
        JRadioButton radio_year;
        JScrollPane scrollPane;
        JTextField input;
        String[] artist;
        String[] album;
        String[] track;
        String[] title;
        String[] year;
        String[] genre;
        String[] display_list;
        String filtergenre;
        String read;

        // creates a panel with a specified genre of music filtered and shown on it
        // with search options, add/remove, close, et cetera...
        MusicPane(String[] song_list, int amount_of_songs, String filter_genre) {

            // initialize the local variable for later..
            filtergenre = filter_genre;

            /*** This does nothing right now ***
             *
             * // initialize menu items
             * menu_bar = new JMenuBar();
             * edit = new JMenu("Edit");
             *
             * // add main tabs to menu bar
             * menu_bar.add(edit);
             *
             * // set the menu bar
             * setJMenuBar(menu_bar);
             *
             ***/

            // initialize popup menu items
            popupmenu = new JPopupMenu("Options..");
            pplay = new JMenuItem("Play Song");
            partist = new JMenuItem("Sort By Artist");
            palbum = new JMenuItem("Sort By Album");
            pnumber = new JMenuItem("Sort By Track Number");
            ptitle = new JMenuItem("Sort By Title");
            pyear = new JMenuItem("Sort By Year");
            padd = new JMenuItem("Add");
            premove = new JMenuItem("Remove");
            pedit = new JMenuItem("Edit");

            // initialize radio buttons and top and bottom panels
            radio_artist = new JRadioButton("Sort by Artist");
            radio_album = new JRadioButton("Sort by Album");
            radio_number = new JRadioButton("Sort by Track Number");
            radio_title = new JRadioButton("Sort by Title");
            radio_year = new JRadioButton("Sort by Year");
            input = new JTextField();
            top = new JPanel();
            mid = new JPanel();
            bottom = new JPanel();

            // add tabs to popup menu
            popupmenu.add(pplay);
            popupmenu.addSeparator();
            popupmenu.add(padd);
            popupmenu.add(pedit);
            popupmenu.addSeparator();
            popupmenu.add(premove);
            popupmenu.addSeparator();
            popupmenu.add(partist);
            popupmenu.add(palbum);
            popupmenu.add(pnumber);
            popupmenu.add(ptitle);
            popupmenu.add(pyear);

            // set the colors
            top.setBackground(cRadioB);
            font = new Font("Comic Sans MS", Font.PLAIN, 12);
            radio_artist.setFont(font);
            radio_artist.setForeground(cRadioF);
            radio_artist.setBackground(cRadioB);
            radio_album.setFont(font);
            radio_album.setForeground(cRadioF);
            radio_album.setBackground(cRadioB);
            radio_number.setFont(font);
            radio_number.setForeground(cRadioF);
            radio_number.setBackground(cRadioB);
            radio_title.setFont(font);
            radio_title.setForeground(cRadioF);
            radio_title.setBackground(cRadioB);
            radio_year.setFont(font);
            radio_year.setForeground(cRadioF);
            radio_year.setBackground(cRadioB);
            input.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
            input.setForeground(cInputF);
            input.setBackground(cInputB);
            font = new Font("Comic Sans MS", Font.PLAIN, 11);
            // menu_bar.setBackground(Color.white);
            // edit.setFont(font);
            // edit.setForeground(Color.blue);
            // edit.setBackground(Color.white);
            popupmenu.setForeground(Color.blue);
            popupmenu.setBackground(Color.yellow);
            font = new Font("Comic Sans MS", Font.ITALIC, 11);
            padd.setFont(font);
            padd.setForeground(cPitemF);
            padd.setBackground(cPitemB);
            pedit.setFont(font);
            pedit.setForeground(cPitemF);
            pedit.setBackground(cPitemB);
            premove.setFont(font);
            premove.setForeground(cPitemF);
            premove.setBackground(cPitemB);
            pplay.setFont(font);
            pplay.setForeground(cPitemF);
            pplay.setBackground(cPitemB);
            partist.setFont(font);
            partist.setForeground(cPitemF);
            partist.setBackground(cPitemB);
            palbum.setFont(font);
            palbum.setForeground(cPitemF);
            palbum.setBackground(cPitemB);
            pnumber.setFont(font);
            pnumber.setForeground(cPitemF);
            pnumber.setBackground(cPitemB);
            ptitle.setFont(font);
            ptitle.setForeground(cPitemF);
            ptitle.setBackground(cPitemB);
            pyear.setFont(font);
            pyear.setForeground(cPitemF);
            pyear.setBackground(cPitemB);
            lsearch.setHorizontalAlignment(JTextField.RIGHT);
            lsearch.setBackground(Color.white);
            lsearch.setForeground(Color.black);
            bottom.setBackground(Color.white);

            // set default values for radio buttons
            radio_artist.setSelected(false);
            radio_album.setSelected(false);
            radio_number.setSelected(false);
            radio_title.setSelected(false);
            radio_year.setSelected(false);

            // set the amount of columns for the search textfield
            input.setColumns(30);

            // add everything to the panel
            this.setLayout(new GridLayout(3, 1));
            top.setLayout(new GridLayout(3, 2));
            mid.setLayout(new GridLayout(1, 1));
            bottom.setLayout(layout);

            // 3 panels
            this.add(top);
            this.add(mid);
            this.add(bottom);

            // radio buttons
            top.add(radio_artist);
            top.add(radio_album);
            top.add(radio_number);
            top.add(radio_title);
            top.add(radio_year);

            // other buttons
            bottom.add(lsearch);
            bottom.add(input);
            bottom.add(add);
            bottom.add(remove);
            bottom.add(play);
            bottom.add(close);

            gbc.gridwidth = 1;
            gbc.gridheight = 1;
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 1;
            gbc.weighty = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            layout.setConstraints(lsearch, gbc);
            gbc.gridx = 1;
            gbc.gridy = 0;
            layout.setConstraints(input, gbc);
            gbc.fill = GridBagConstraints.NONE;
            gbc.gridx = 0;
            gbc.gridy = 1;
            layout.setConstraints(add, gbc);
            gbc.gridx = 1;
            gbc.gridy = 1;
            layout.setConstraints(remove, gbc);
            gbc.gridx = 0;
            gbc.gridy = 2;
            layout.setConstraints(play, gbc);
            gbc.gridx = 1;
            gbc.gridy = 2;
            layout.setConstraints(close, gbc);

            // initalize counters that will be used for filtering songs
            dash_counter = 0;
            counter = amount_of_songs;
            genrecounter = 0;

            // for each song...
            for (int j = 0; j < amount_of_songs; j++) {
                // find the dashes...
                for (int i = 0; i < (song_list[j].length() - 1); i++) {
                    if (song_list[j].charAt(i) == '-') {
                        dash[dash_counter] = i;
                        dash_counter++;
                    }
                }
                // separate according to...
                if (song_list[j].substring(dash[4] + 2, song_list[j].length()).toUpperCase().equals(filter_genre.toUpperCase())) {
                    genrecounter++;
                } else {
                    counter--;
                }
                counter++;
                dash_counter = 0;
            }

            // initialize the string arrays with the amount of songs to be held
            artist = new String[genrecounter];
            album = new String[genrecounter];
            track = new String[genrecounter];
            title = new String[genrecounter];
            year = new String[genrecounter];
            genre = new String[genrecounter];
            display_list = new String[genrecounter];

            // reset counters
            counter = 0;
            genrecounter = 0;

            // ** read in the song information
            // for each song...
            for (int j = 0; j < amount_of_songs; j++) {
                // find the dashes..
                for (int i = 0; i < (song_list[j].length() - 1); i++) {
                    if (song_list[j].charAt(i) == '-') {
                        dash[dash_counter] = i;
                        dash_counter++;
                    }
                }
                // separate according to...
                if (song_list[j].substring(dash[4] + 2, song_list[j].length()).toUpperCase().equals(filter_genre.toUpperCase())) {
                    artist[counter] = new String(song_list[j].substring(0, dash[0] - 1));
                    album[counter] = new String(song_list[j].substring(dash[0] + 2, dash[1] - 1));
                    track[counter] = new String(song_list[j].substring(dash[1] + 2, dash[2] - 1));
                    title[counter] = new String(song_list[j].substring(dash[2] + 2, dash[3] - 1));
                    year[counter] = new String(song_list[j].substring(dash[3] + 2, dash[4] - 1));
                    genre[counter] = new String(song_list[j].substring(dash[4] + 2, song_list[j].length()));
                    genrecounter++;
                } else {
                    counter--;
                }
                counter++;
                dash_counter = 0;
            }
            try {
                reader.close();
            } catch (IOException e) {
            }
            ;

            // initialize the main array for displaying this panel's songs
            for (int i = 0; i < genrecounter; i++) {
                display_list[i] = artist[i] + " - " + album[i] + " - " + track[i] + " - " + title[i] + " - " + year[i];
            }

            // create a new jlist to display info, then put it in a scrollpane
            JL_songlist = new JList(display_list);

            // adds a scrollbar to the jlist
            scrollPane = new JScrollPane(JL_songlist);

            // set font and colors for the list
            lfont = new Font("Comic Sans MS", Font.PLAIN, 10);
            JL_songlist.setFont(lfont);
            JL_songlist.setForeground(cListF);
            JL_songlist.setBackground(cListB);
            JL_songlist.setSelectionForeground(cListSF);
            JL_songlist.setSelectionBackground(cListSB);
            JL_songlist.add(popupmenu);

            // add the song list
            mid.add(scrollPane);

            // *** set listeners ***
            // allows adding of songs to the library
            add.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == add) {
                        // class that handles adding a new song
                        AddSong addsong = new AddSong(filtergenre);
                        addsong.setVisible(true);
                        me.setVisible(false);
                        addsong.addWindowListener(new WindowAdapter() {
                            public void windowClosing(WindowEvent we) {
                                me.setVisible(true);
                                dispose();
                            }
                        });
                    }
                }
            });

            // allows removing of songs from the library
            remove.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == remove) {
                        int index = JL_songlist.getSelectedIndex();
                        int tracker = -1;
                        int counter = 0;
                        String tempdata;
                        String[] templist;

                        // make sure a song is selected
                        if (index != -1) {
                            // port the data to a temporary array
                            tempdata = artist[index] + " - " + album[index] + " - " + track[index] + " - " + title[index] + " - " + year[index] + " - " + genre[index];
                            SortFile tempsort = new SortFile();
                            int amt = tempsort.ListSize(file_name);
                            String[] songlist = new String[amt];
                            songlist = tempsort.SortSongs(file_name);

                            // finds what song was selected
                            for (int x = 0; x < amt; x++) {
                                if (tempdata.equals(songlist[x])) {
                                    tracker = x;
                                }
                            }

                            // allocates memory for a list - 1
                            templist = new String[amt - 1];

                            // copy from the old list to the temp list all the other songs
                            for (int x = 0; x < amt; x++) {
                                if (x != tracker) {
                                    templist[counter] = new String(songlist[x]);
                                    counter++;
                                }
                            }
                            amt--;

                            // copy the temp list back into the main list
                            songlist = new String[amt];
                            songlist = templist;

                            // write the information to the file
                            try {
                                writer = new PrintWriter(new FileWriter(file_name));
                                for (int x = 0; x < amt; x++) {
                                    writer.println(songlist[x]);
                                }
                                writer.close();
                            } catch (Exception pe) {
                                pe.printStackTrace();
                            }

                            // update each tab's list
                            techno_m.setList(songlist, amt, "Techno");
                            rock_m.setList(songlist, amt, "Rock");
                            country_m.setList(songlist, amt, "Country");
                            classical_m.setList(songlist, amt, "Classical");
                            rap_m.setList(songlist, amt, "Rap");
                            comedy_m.setList(songlist, amt, "Comedy");
                            break_m.setList(songlist, amt, "Break");
                            rnb_m.setList(songlist, amt, "RNB");
                        }
                    }
                }
            });

            // used for playing songs
            play.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == play) {
                        pplay.doClick();
                    }
                }
            });

            // saves the information and exits the program
            close.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == close) {
                        writeToFile();
                        System.exit(0);
                    }
                }
            });

            // sorts the information according to artist
            radio_artist.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == radio_artist) {
                        if (radio_artist.isSelected()) {
                            radio_artist.setForeground(Color.black);
                            radio_album.setForeground(Color.blue);
                            radio_number.setForeground(Color.blue);
                            radio_title.setForeground(Color.blue);
                            radio_year.setForeground(Color.blue);
                            for (int i = 0; i < counter; i++) {
                                display_list[i] = artist[i] + " - " + album[i] + " - " + track[i] + " - " + title[i] + " - " + year[i];
                            }

                            // send the data to another class to be sorted
                            Sort sort = new Sort();
                            display_list = sort.SortIt(counter, display_list);

                            // update the list
                            JL_songlist.setListData(display_list);

                            // make sure none of the other buttons are selected
                            radio_album.setSelected(false);
                            radio_number.setSelected(false);
                            radio_title.setSelected(false);
                            radio_year.setSelected(false);
                        }
                    }
                }
            });

            // sorts the information according to album
            radio_album.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == radio_album) {
                        if (radio_album.isSelected()) {
                            radio_artist.setForeground(Color.blue);
                            radio_album.setForeground(Color.black);
                            radio_number.setForeground(Color.blue);
                            radio_title.setForeground(Color.blue);
                            radio_year.setForeground(Color.blue);
                            for (int i = 0; i < counter; i++) {
                                display_list[i] = album[i] + " - " + track[i] + " - " + title[i] + " - " + year[i];
                            }

                            // send the data to another class to be sorted
                            Sort sort = new Sort();
                            display_list = sort.SortIt(counter, display_list);

                            // update the list
                            JL_songlist.setListData(display_list);

                            // make sure this is the only selected button
                            radio_artist.setSelected(false);
                            radio_number.setSelected(false);
                            radio_title.setSelected(false);
                            radio_year.setSelected(false);
                        }
                    }
                }
            });

            // sorts the information according to the track number
            radio_number.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == radio_number) {
                        if (radio_number.isSelected()) {
                            radio_artist.setForeground(Color.blue);
                            radio_album.setForeground(Color.blue);
                            radio_number.setForeground(Color.black);
                            radio_title.setForeground(Color.blue);
                            radio_year.setForeground(Color.blue);
                            for (int i = 0; i < counter; i++) {
                                display_list[i] = track[i] + " - " + artist[i] + " - " + album[i] + " - " + title[i] + " - " + year[i];
                            }

                            // send the data to another class to be sorted
                            Sort sort = new Sort();
                            display_list = sort.SortIt(counter, display_list);
                            display_list = sort.SortIt(counter, display_list);

                            // update the list
                            JL_songlist.setListData(display_list);

                            // make sure no other buttons are clicked
                            radio_artist.setSelected(false);
                            radio_album.setSelected(false);
                            radio_title.setSelected(false);
                            radio_year.setSelected(false);
                        }
                    }
                }
            });

            // show the information according to the title
            radio_title.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == radio_title) {
                        if (radio_title.isSelected()) {
                            radio_artist.setForeground(Color.blue);
                            radio_album.setForeground(Color.blue);
                            radio_number.setForeground(Color.blue);
                            radio_title.setForeground(Color.black);
                            radio_year.setForeground(Color.blue);
                            for (int i = 0; i < counter; i++) {
                                display_list[i] = title[i] + " - " + artist[i] + " - " + album[i] + " - " + track[i] + " - " + year[i];
                            }

                            // send the data to another class to be sorted
                            Sort sort = new Sort();
                            display_list = sort.SortIt(counter, display_list);

                            // update the list
                            JL_songlist.setListData(display_list);

                            // make sure no other button is selected
                            radio_artist.setSelected(false);
                            radio_album.setSelected(false);
                            radio_number.setSelected(false);
                            radio_year.setSelected(false);
                        }
                    }
                }
            });

            // show the information according to production year
            radio_year.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == radio_year) {
                        if (radio_year.isSelected()) {
                            radio_artist.setForeground(Color.blue);
                            radio_album.setForeground(Color.blue);
                            radio_number.setForeground(Color.blue);
                            radio_title.setForeground(Color.blue);
                            radio_year.setForeground(Color.black);
                            for (int i = 0; i < counter; i++) {
                                display_list[i] = year[i] + " - " + artist[i] + " - " + album[i] + " - " + track[i] + " - " + title[i];
                            }

                            // send the data to another class to be sorted
                            Sort sort = new Sort();
                            display_list = sort.SortIt(counter, display_list);

                            // update the list
                            JL_songlist.setListData(display_list);

                            // make sure no other buttons are selected
                            radio_artist.setSelected(false);
                            radio_album.setSelected(false);
                            radio_number.setSelected(false);
                            radio_title.setSelected(false);
                        }
                    }
                }
            });

            // listens for typing on the search textfield and updates the list as the user types
            input.addCaretListener(new CaretListener() {
                public void caretUpdate(CaretEvent e) {
                    int str_length = 0;
                    int another_list_length = 0;
                    int another_num = 0;
                    String input_txt = new String(input.getText());
                    String[] another_list;
                    str_length = input_txt.length();

                    // finds out how many songs will now be shown
                    for (int i = 0; i < counter; i++) {
                        if (display_list[i].substring(0, str_length).toUpperCase().equals(input_txt.toUpperCase())) {
                            another_list_length++;
                        }
                    }

                    // allocates memory for a temporary list
                    another_list = new String[another_list_length + 1];

                    // copies only the "narrowed" down information from the main list
                    for (int i = 0; i < counter; i++) {
                        if (display_list[i].substring(0, str_length).toUpperCase().equals(input_txt.toUpperCase())) {
                            another_list[another_num] = display_list[i];
                            another_num++;
                        }
                    }

                    // updates the list
                    JL_songlist.setListData(another_list);
                }
            });

            // listens for mouse clicks, more specifically, right mouse clicks and
            // shows a popupmenu for the user with choices...
            JL_songlist.addMouseListener(new MouseListener() {
                public void mouseReleased(MouseEvent e) {
                    if (e.isPopupTrigger()) {
                        // show the menu
                        popupmenu.show(JL_songlist, e.getX(), e.getY());
                    }
                }

                public void mouseEntered(MouseEvent e) {
                }

                public void mouseExited(MouseEvent e) {
                }

                public void mousePressed(MouseEvent e) {
                }

                public void mouseClicked(MouseEvent e) {
                }
            });

            // shows a dialog that is used for playing songs
            pplay.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == pplay) {
                        // check to see if a song is selected
                        if (!JL_songlist.isSelectionEmpty()) {
                            playDialog = new PlayDialog(artist[JL_songlist.getSelectedIndex()], title[JL_songlist.getSelectedIndex()]);
                            me.setVisible(false);
                            playDialog.setVisible(true);
                            playDialog.addWindowListener(new WindowAdapter() {
                                public void windowClosing(WindowEvent we) {
                                    playDialog.dispose();
                                    me.setVisible(true);
                                }
                            });
                        }
                    }
                }
            });

            // sorts information by artist
            partist.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == partist) {
                        radio_artist.doClick();
                    }
                }
            });

            // sorts information by album
            palbum.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == palbum) {
                        radio_album.doClick();
                    }
                }
            });

            // sorts information by track number
            pnumber.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == pnumber) {
                        radio_number.doClick();
                    }
                }
            });

            // sorts information by track title
            ptitle.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == ptitle) {
                        radio_title.doClick();
                    }
                }
            });

            // sorts information by production year
            pyear.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == pyear) {
                        radio_year.doClick();
                    }
                }
            });

            // lets the user add a new song
            padd.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == padd) {
                        add.doClick();
                    }
                }
            });

            /* here
             */
            pedit.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == pedit) {
                        int index = JL_songlist.getSelectedIndex();
                        String tart = artist[index];
                        String talb = album[index];
                        String ttrk = track[index];
                        String ttit = title[index];
                        String tyr = year[index];
                        // make sure a song is selected
                        if (index != -1) {
                            premove.doClick();
                            AddSong addsong = new AddSong(filtergenre, tart, talb, ttrk, ttit, tyr);
                            addsong.setVisible(true);
                            me.setVisible(false);
                            addsong.addWindowListener(new WindowAdapter() {
                                public void windowClosing(WindowEvent we) {
                                    me.setVisible(true);
                                    dispose();
                                }
                            });
                        }
                    }
                }
            });

            // lets the user remove a song
            premove.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == premove) {
                        remove.doClick();
                    }
                }
            });
        }

        // this function is passed a list of data, its size, and what genre of songs to pick from
        // and then it sets the data it shows in its list
        public void setList(String[] song_list, int amount_of_songs, String filter_genre) {
            dash_counter = 0;
            genrecounter = 0;
            counter = amount_of_songs;

            // ** this first loop is just for memory allocation
            // for each song...
            for (int j = 0; j < amount_of_songs; j++) {
                // count where the dashes are...
                for (int i = 0; i < (song_list[j].length() - 1); i++) {
                    if (song_list[j].charAt(i) == '-') {
                        dash[dash_counter] = i;
                        dash_counter++;
                    }
                }
                // separate according to the genre...
                if (song_list[j].substring(dash[4] + 2, song_list[j].length()).toUpperCase().equals(filter_genre.toUpperCase())) {
                    genrecounter++;
                } else {
                    counter--;
                }
                counter++;
                dash_counter = 0;
            }

            // initialize the arrays with the amount of songs to be held
            artist = new String[genrecounter];
            album = new String[genrecounter];
            track = new String[genrecounter];
            title = new String[genrecounter];
            year = new String[genrecounter];
            genre = new String[genrecounter];
            display_list = new String[genrecounter];
            counter = 0;
            genrecounter = 0;

            // ** this second loop is for actually storing the data from the file
            // for each song...
            for (int j = 0; j < amount_of_songs; j++) {
                // find the dashes again...
                for (int i = 0; i < (song_list[j].length() - 1); i++) {
                    if (song_list[j].charAt(i) == '-') {
                        dash[dash_counter] = i;
                        dash_counter++;
                    }
                }
                // separate according to...
                if (song_list[j].substring(dash[4] + 2, song_list[j].length()).toUpperCase().equals(filter_genre.toUpperCase())) {
                    artist[genrecounter] = new String(song_list[j].substring(0, dash[0] - 1));
                    album[genrecounter] = new String(song_list[j].substring(dash[0] + 2, dash[1] - 1));
                    track[genrecounter] = new String(song_list[j].substring(dash[1] + 2, dash[2] - 1));
                    title[genrecounter] = new String(song_list[j].substring(dash[2] + 2, dash[3] - 1));
                    year[genrecounter] = new String(song_list[j].substring(dash[3] + 2, dash[4] - 1));
                    genre[genrecounter] = new String(song_list[j].substring(dash[4] + 2, song_list[j].length()));
                    genrecounter++;
                } else {
                    counter--;
                }
                counter++;
                dash_counter = 0;
            }

            // allocate memory for the "main list" in the pane
            display_list = new String[genrecounter];
            // put information into the array
            for (int i = 0; i < genrecounter; i++) {
                display_list[i] = artist[i] + " - " + album[i] + " - " + track[i] + " - " + title[i] + " - " + year[i];
            }
            // display the information on this panel's list
            JL_songlist.setListData(display_list);
        }

        public void setColors() {
            top.setBackground(cRadioB);
            font = new Font("Comic Sans MS", Font.PLAIN, 12);
            radio_artist.setFont(font);
            radio_artist.setForeground(cRadioF);
            radio_artist.setBackground(cRadioB);
            radio_album.setFont(font);
            radio_album.setForeground(cRadioF);
            radio_album.setBackground(cRadioB);
            radio_number.setFont(font);
            radio_number.setForeground(cRadioF);
            radio_number.setBackground(cRadioB);
            radio_title.setFont(font);
            radio_title.setForeground(cRadioF);
            radio_title.setBackground(cRadioB);
            radio_year.setFont(font);
            radio_year.setForeground(cRadioF);
            radio_year.setBackground(cRadioB);
            input.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
            input.setForeground(cInputF);
            input.setBackground(cInputB);
            font = new Font("Comic Sans MS", Font.PLAIN, 11);
            // menu_bar.setBackground(Color.white);
            // edit.setFont(font);
            // edit.setForeground(Color.blue);
            // edit.setBackground(Color.white);
            popupmenu.setForeground(Color.blue);
            popupmenu.setBackground(Color.yellow);
            font = new Font("Comic Sans MS", Font.ITALIC, 11);
            padd.setFont(font);
            padd.setForeground(cPitemF);
            padd.setBackground(cPitemB);
            pedit.setFont(font);
            pedit.setForeground(cPitemF);
            pedit.setBackground(cPitemB);
            premove.setFont(font);
            premove.setForeground(cPitemF);
            premove.setBackground(cPitemB);
            pplay.setFont(font);
            pplay.setForeground(cPitemF);
            pplay.setBackground(cPitemB);
            partist.setFont(font);
            partist.setForeground(cPitemF);
            partist.setBackground(cPitemB);
            palbum.setFont(font);
            palbum.setForeground(cPitemF);
            palbum.setBackground(cPitemB);
            pnumber.setFont(font);
            pnumber.setForeground(cPitemF);
            pnumber.setBackground(cPitemB);
            ptitle.setFont(font);
            ptitle.setForeground(cPitemF);
            ptitle.setBackground(cPitemB);
            pyear.setFont(font);
            pyear.setForeground(cPitemF);
            pyear.setBackground(cPitemB);
            lfont = new Font("Comic Sans MS", Font.PLAIN, 10);
            JL_songlist.setFont(lfont);
            JL_songlist.setForeground(cListF);
            JL_songlist.setBackground(cListB);
            JL_songlist.setSelectionForeground(cListSF);
            JL_songlist.setSelectionBackground(cListSB);
        }
    }

    // tab/class that allows color changing, library changing, and user adding
    class ConfigurePane extends JPanel {

        int num_users;
        JButton bRadioB = new JButton("Set Radio Background");
        JButton bRadioF = new JButton("Set Radio Foreground");
        JButton bTabB = new JButton("Set Tab Background");
        JButton bTabF = new JButton("Set Tab Foreground");
        JButton bInputF = new JButton("Set Input Foreground");
        JButton bInputB = new JButton("Set Input Background");
        JButton bPitemF = new JButton("Set Popup Foreground");
        JButton bPitemB = new JButton("Set Popup Background");
        JButton bListF = new JButton("Set List Foreground");
        JButton bListB = new JButton("Set List Background");
        JButton bListSF = new JButton("Set List Selected Foreground");
        JButton bListSB = new JButton("Set List Selected Background");
        JButton bLibrary = new JButton("Set Library File");
        JButton addRemove = new JButton("Add/Remove Users");
        JButton backup = new JButton("Backup Library File");
        JTextField tfLibrary = new JTextField(20);
        JColorChooser jcolorchooser;
        JPanel left;
        JPanel right;
        JPanel upper_right;
        JPanel upper_font;
        JPanel lower_right;
        String temp;
        String[] passwords;
        String[] users;

        ConfigurePane() {
            left = new JPanel();
            right = new JPanel();
            upper_right = new JPanel();
            upper_font = new JPanel();
            lower_right = new JPanel();
            jcolorchooser = new JColorChooser();

            left.setBackground(Color.black);
            upper_right.setBackground(Color.black);
            lower_right.setBackground(Color.black);
            tfLibrary.setText(file_name);

            this.setLayout(new GridLayout(1, 2));
            right.setLayout(new GridLayout(2, 1));
            upper_right.setLayout(new GridLayout(6, 2));
            lower_right.setLayout(new GridLayout(7, 3));
            this.add(left);
            this.add(right);
            left.add(jcolorchooser);
            right.add(upper_right);
            right.add(lower_right);
            upper_right.add(bRadioB);
            upper_right.add(bRadioF);
            upper_right.add(bTabB);
            upper_right.add(bTabF);
            upper_right.add(bInputB);
            upper_right.add(bInputF);
            upper_right.add(bPitemB);
            upper_right.add(bPitemF);
            upper_right.add(bListB);
            upper_right.add(bListF);
            upper_right.add(bListSB);
            upper_right.add(bListSF);
            lower_right.add(tfLibrary);
            lower_right.add(bLibrary);
            lower_right.add(addRemove);
            lower_right.add(backup);

            // handles the dialog box for adding and removing users
            addRemove.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == addRemove) {
                        BufferedReader read;
                        // reads in user data and sends it to the editusers class
                        try {
                            read = new BufferedReader(new FileReader("Users.dat"));
                        } catch (FileNotFoundException fnfe) {
                            read = null;
                        }
                        ;
                        try {
                            num_users = Integer.parseInt(read.readLine());
                            passwords = new String[num_users];
                            users = new String[num_users];
                            for (int i = 0; i < num_users; i++) {
                                temp = new String(read.readLine());
                                for (int x = 0; x < temp.length(); x++) {
                                    if (temp.charAt(x) == ':') {
                                        users[i] = new String(temp.substring(0, x));
                                        passwords[i] = new String(temp.substring(x + 1, temp.length()));
                                    }
                                }
                            }
                            read.close();
                        } catch (Exception io) {
                            io.printStackTrace();
                        }
                        editusers = new EditUsers(users, passwords, num_users);
                        me.setVisible(false);
                        editusers.setVisible(true);
                        editusers.addWindowListener(new WindowAdapter() {
                            public void windowClosing(WindowEvent we) {
                                editusers.dispose();
                                me.setVisible(true);
                            }
                        });
                    }
                }
            });

            // updates all jlists when button is pressed/file is changed
            bLibrary.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == bLibrary) {
                        file_name = new String(tfLibrary.getText());
                        writeToFile();

                        SortFile temp = new SortFile();
                        int amt = temp.ListSize(file_name);
                        String[] songs = new String[amt];
                        songs = temp.SortSongs(file_name);

                        // update each tab's list
                        techno_m.setList(songs, amt, "Techno");
                        rock_m.setList(songs, amt, "Rock");
                        country_m.setList(songs, amt, "Country");
                        classical_m.setList(songs, amt, "Classical");
                        rap_m.setList(songs, amt, "Rap");
                        comedy_m.setList(songs, amt, "Comedy");
                        break_m.setList(songs, amt, "Break");
                        rnb_m.setList(songs, amt, "RNB");
                    }
                }
            });

            // allows saving of the music library information to another file
            backup.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == backup) {
                        try {
                            writer = new PrintWriter(new FileWriter(tfLibrary.getText()));
                        } catch (Exception E) {
                            E.printStackTrace();
                        }
                        for (int x = 0; x < amount_of_songs; x++) {
                            writer.println(song_list[x]);
                        }
                        writer.close();
                        tfLibrary.setText(file_name);
                    }
                }
            });

            // the following are for setting the color from the jcolorchooser of various
            // objects within the program
            bRadioB.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == bRadioB) {
                        cRadioB = jcolorchooser.getColor();
                        setcolors();
                    }
                }
            });

            bRadioF.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == bRadioF) {
                        cRadioF = jcolorchooser.getColor();
                        setcolors();
                    }
                }
            });

            bTabB.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == bTabB) {
                        cTabB = jcolorchooser.getColor();
                        setcolors();
                    }
                }
            });

            bTabF.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == bTabF) {
                        cTabF = jcolorchooser.getColor();
                        setcolors();
                    }
                }
            });

            bInputF.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == bInputF) {
                        cInputF = jcolorchooser.getColor();
                        setcolors();
                    }
                }
            });

            bInputB.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == bInputB) {
                        cInputB = jcolorchooser.getColor();
                        setcolors();
                    }
                }
            });

            bPitemF.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == bPitemF) {
                        cPitemF = jcolorchooser.getColor();
                        setcolors();
                    }
                }
            });

            bPitemB.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == bPitemB) {
                        cPitemB = jcolorchooser.getColor();
                        setcolors();
                    }
                }
            });

            bListF.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == bListF) {
                        cListF = jcolorchooser.getColor();
                        setcolors();
                    }
                }
            });

            bListB.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == bListB) {
                        cListB = jcolorchooser.getColor();
                        setcolors();
                    }
                }
            });

            bListSF.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == bListSF) {
                        cListSF = jcolorchooser.getColor();
                        setcolors();
                    }
                }
            });

            bListSB.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == bListSB) {
                        cListSB = jcolorchooser.getColor();
                        setcolors();
                    }
                }
            });
        }

        // once the color variables have new colors, this function calls each tabs setcolors func
        // so that the new colors will be applied
        public void setcolors() {
            techno_m.setColors();
            rock_m.setColors();
            country_m.setColors();
            classical_m.setColors();
            rap_m.setColors();
            comedy_m.setColors();
            break_m.setColors();
            rnb_m.setColors();
        }
    }

    // class for adding new songs to the file/list
    class AddSong extends JFrame {

        int w = 200;
        int h = 200;
        int counter = 0;
        int size = 0;
        JButton b_ok;
        JButton b_cancel;
        JLabel l_artist;
        JLabel l_album;
        JLabel l_track;
        JLabel l_title;
        JLabel l_year;
        JLabel l_genre;
        JLabel l_thegenre;
        JTextField tf_artist;
        JTextField tf_album;
        JTextField tf_track;
        JTextField tf_title;
        JTextField tf_year;
        String[] temp;
        String tempread;
        String art, alb, trk, tit, yr, gr;

        AddSong(String filter_genre) {
            super("Add Song");
            this.setSize(w, h);
            this.setLocation((int) ((screen.getWidth() - w) / 2), (int) ((screen.getHeight() - h) / 2));

            this.getContentPane().setLayout(new GridLayout(7, 2));

            b_ok = new JButton("Ok");
            b_cancel = new JButton("Cancel");
            l_artist = new JLabel("Artist Name:");
            l_album = new JLabel("Album Title:");
            l_track = new JLabel("Track Number:");
            l_title = new JLabel("Track Title:");
            l_year = new JLabel("Year:");
            l_genre = new JLabel("Genre:");
            l_thegenre = new JLabel(filter_genre);
            tf_artist = new JTextField();
            tf_album = new JTextField();
            tf_track = new JTextField();
            tf_title = new JTextField();
            tf_year = new JTextField();
            gr = filter_genre;

            this.getContentPane().setBackground(Color.white);
            l_artist.setForeground(Color.blue);
            l_album.setForeground(Color.blue);
            l_track.setForeground(Color.blue);
            l_title.setForeground(Color.blue);
            l_year.setForeground(Color.blue);
            l_genre.setForeground(Color.blue);
            l_thegenre.setForeground(Color.red);

            this.getContentPane().add(l_artist);
            this.getContentPane().add(tf_artist);
            this.getContentPane().add(l_album);
            this.getContentPane().add(tf_album);
            this.getContentPane().add(l_track);
            this.getContentPane().add(tf_track);
            this.getContentPane().add(l_title);
            this.getContentPane().add(tf_title);
            this.getContentPane().add(l_year);
            this.getContentPane().add(tf_year);
            this.getContentPane().add(l_genre);
            this.getContentPane().add(l_thegenre);
            this.getContentPane().add(b_cancel);
            this.getContentPane().add(b_ok);

            // will add a new song to the list
            b_ok.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == b_ok) {
                        art = tf_artist.getText();
                        alb = tf_album.getText();
                        trk = tf_track.getText();
                        tit = tf_title.getText();
                        yr = tf_year.getText();

                        if ((art.length() != 0) && (tit.length() != 0) && (!(art.equals("No Artist"))) && (!(tit.equals("No Title")))) {

                            if (alb.length() == 0) {
                                alb = new String(" ");
                            }
                            if (trk.length() == 0) {
                                trk = new String("00");
                            }
                            if (yr.length() == 0) {
                                yr = new String("0000");
                            }

                            Ask ask = new Ask(false);
                            ask.setVisible(true);

                            ask.addWindowListener(new WindowAdapter() {
                                public void windowClosing(WindowEvent we) {
                                    dispose();
                                }
                            });
                        } else {
                            if ((tf_artist.getText().length() == 0)) {
                                tf_artist.setText("No Artist");
                            }
                            if ((tf_title.getText().length() == 0)) {
                                tf_title.setText("No Title");
                            }
                        }


                    }
                }
            });
            // do not add new information to the file
            b_cancel.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == b_cancel) {
                        me.setVisible(true);
                        dispose();
                    }
                }
            });

        }

        AddSong(String filter_genre, String artist, String album, String track, String title, String year) {
            super("Edit Song");
            this.setSize(w, h);
            this.setLocation((int) ((screen.getWidth() - w) / 2), (int) ((screen.getHeight() - h) / 2));

            this.getContentPane().setLayout(new GridLayout(7, 2));

            b_ok = new JButton("Ok");
            b_cancel = new JButton("Cancel");
            l_artist = new JLabel("Artist Name:");
            l_album = new JLabel("Album Title:");
            l_track = new JLabel("Track Number:");
            l_title = new JLabel("Track Title:");
            l_year = new JLabel("Year:");
            l_genre = new JLabel("Genre:");
            l_thegenre = new JLabel(filter_genre);
            tf_artist = new JTextField(artist);
            tf_album = new JTextField(album);
            tf_track = new JTextField(track);
            tf_title = new JTextField(title);
            tf_year = new JTextField(year);
            gr = filter_genre;

            this.getContentPane().setBackground(Color.white);
            l_artist.setForeground(Color.blue);
            l_album.setForeground(Color.blue);
            l_track.setForeground(Color.blue);
            l_title.setForeground(Color.blue);
            l_year.setForeground(Color.blue);
            l_genre.setForeground(Color.blue);
            l_thegenre.setForeground(Color.red);

            this.getContentPane().add(l_artist);
            this.getContentPane().add(tf_artist);
            this.getContentPane().add(l_album);
            this.getContentPane().add(tf_album);
            this.getContentPane().add(l_track);
            this.getContentPane().add(tf_track);
            this.getContentPane().add(l_title);
            this.getContentPane().add(tf_title);
            this.getContentPane().add(l_year);
            this.getContentPane().add(tf_year);
            this.getContentPane().add(l_genre);
            this.getContentPane().add(l_thegenre);
            this.getContentPane().add(b_cancel);
            this.getContentPane().add(b_ok);

            // will add a new song to the list
            b_ok.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == b_ok) {
                        art = tf_artist.getText();
                        alb = tf_album.getText();
                        trk = tf_track.getText();
                        tit = tf_title.getText();
                        yr = tf_year.getText();

                        if ((art.length() != 0) && (tit.length() != 0) && (!(art.equals("No Artist"))) && (!(tit.equals("No Title")))) {

                            if (alb.length() == 0) {
                                alb = new String(" ");
                            }
                            if (trk.length() == 0) {
                                trk = new String("00");
                            }
                            if (yr.length() == 0) {
                                yr = new String("0000");
                            }

                            Ask ask = new Ask(true);
                            ask.setVisible(true);

                            ask.addWindowListener(new WindowAdapter() {
                                public void windowClosing(WindowEvent we) {
                                    dispose();
                                }
                            });
                        } else {
                            if ((tf_artist.getText().length() == 0)) {
                                tf_artist.setText("No Artist");
                            }
                            if ((tf_title.getText().length() == 0)) {
                                tf_title.setText("No Title");
                            }
                        }
                    }
                }
            });
            // do not add new information to the file
            b_cancel.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == b_cancel) {
                        me.setVisible(true);
                        dispose();
                    }
                }
            });
        }

        public void Dispose() {
            dispose();
        }

        class Ask extends JFrame {

            boolean change = true;
            boolean isEdit;
            CycleColors c;
            JButton yes = new JButton("Yes");
            JButton no = new JButton("No");
            JLabel info = new JLabel("Error");
            JPanel top = new JPanel();
            JPanel bottom = new JPanel();

            Ask(boolean Edit) {
                super("Is This Correct?");
                w += 300;
                h -= 100;
                isEdit = Edit;
                this.setSize(w, h);
                this.setLocation((int) ((screen.getWidth() - w) / 2), (int) ((screen.getHeight() - h) / 2));

                this.getContentPane().setLayout(new GridLayout(2, 1));
                bottom.setLayout(new GridLayout(1, 2));

                this.getContentPane().add(top);
                this.getContentPane().add(bottom);
                top.add(info);
                bottom.add(yes);
                bottom.add(no);

                info.setText(art + " - " + alb + " - " + trk + " - " + tit + " - " + yr + " - " + gr);
                top.setBackground(Color.black);

                c = new CycleColors();

                // add the new information
                yes.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (e.getSource() == yes) {
                            try {
                                reader = new BufferedReader(new FileReader(file_name));
                                if (reader != null) {
                                    tempread = reader.readLine();
                                    while (tempread != null) {
                                        counter++;
                                        tempread = reader.readLine();
                                    }
                                }
                                reader.close();
                                temp = new String[counter + 1];
                                size = counter;
                                counter = 0;
                                reader = new BufferedReader(new FileReader(file_name));
                                if (reader != null) {
                                    temp[counter] = reader.readLine();
                                    counter++;
                                    /////// here
                                    for (int x = 0; x < size; x++) {
                                        temp[counter] = reader.readLine();
                                        counter++;
                                    }
                                }
                                reader.close();
                                temp[counter - 1] = new String(art + " - " + alb + " - " + trk + " - " + tit + " - " + yr + " - " + gr);

                                if (!isEdit)
                                    amount_of_songs++;
                                song_list = new String[amount_of_songs];

                                song_list = temp;

                                if (isEdit) {// writes new songs to file
                                    writer = new PrintWriter(new FileWriter(file_name));
                                    for (int x = 0; x < (amount_of_songs); x++) {
                                        writer.println(song_list[x]);
                                    }
                                    writer.close();
                                } else {
                                    writer = new PrintWriter(new FileWriter(file_name));
                                    for (int x = 0; x < (amount_of_songs - 1); x++) {
                                        writer.println(song_list[x]);
                                    }
                                    writer.println(art + " - " + alb + " - " + trk + " - " + tit + " - " + yr + " - " + gr);
                                    writer.close();
                                }


                                SortFile sorttemp = new SortFile();
                                song_list = sorttemp.SortSongs(file_name);

                                // update each tab's list
                                techno_m.setList(song_list, amount_of_songs, "Techno");
                                rock_m.setList(song_list, amount_of_songs, "Rock");
                                country_m.setList(song_list, amount_of_songs, "Country");
                                classical_m.setList(song_list, amount_of_songs, "Classical");
                                rap_m.setList(song_list, amount_of_songs, "Rap");
                                comedy_m.setList(song_list, amount_of_songs, "Comedy");
                                break_m.setList(song_list, amount_of_songs, "Break");
                                rnb_m.setList(song_list, amount_of_songs, "RNB");
                                me.setVisible(true);
                                dispose();
                                Dispose();

                            } catch (IOException io) {
                                io.printStackTrace();
                            }
                        }
                    }
                });
                // do not add the new information
                no.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (e.getSource() == no) {
                            dispose();
                        }
                    }
                });
            }

            class CycleColors implements Runnable {

                CycleColors() {
                    Thread thread = new Thread(this);
                    if (thread != null)
                        thread.start();
                }

                public void run() {
                    while (true) {
                        if (change)
                            info.setForeground(Color.blue);
                        else
                            info.setForeground(Color.green);
                        change = !change;
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException ie) {
                        }
                    }
                }
            }
        }
    }

    // class for adding a new user and password
    class NewUser extends JFrame {

        int num_users;
        JButton ok = new JButton("Ok");
        JButton cancel = new JButton("Cancel");
        JLabel l1 = new JLabel("Username: "), l2 = new JLabel("Password: "), l3 = new JLabel("Re-Enter: ");
        JPasswordField field1 = new JPasswordField(), field2 = new JPasswordField();
        JTextField username = new JTextField();
        String[] users;
        String[] passwords;

        NewUser(String[] user, String[] password, int num) {
            super("Edit Users");
            this.setSize(200, 200);
            this.setLocation((int) ((screen.getWidth() - 200) / 2), (int) ((screen.getHeight() - 200) / 2));

            this.getContentPane().setLayout(new GridLayout(4, 2));
            this.getContentPane().add(l1);
            this.getContentPane().add(username);
            this.getContentPane().add(l2);
            this.getContentPane().add(field1);
            this.getContentPane().add(l3);
            this.getContentPane().add(field2);
            this.getContentPane().add(cancel);
            this.getContentPane().add(ok);

            field1.setEchoChar('*');
            field2.setEchoChar('*');
            l1.setForeground(Color.black);
            l2.setForeground(Color.black);
            l3.setForeground(Color.black);

            users = user;
            passwords = password;
            num_users = num;

            // checks if pass1 and pass2 match then writes new user name/pass to file
            // then updates users and passwords variables and sends them to the editusers class
            ok.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == ok) {
                        String one = new String(field1.getPassword());
                        String two = new String(field2.getPassword());
                        if (!one.equals(two)) {
                            username.setText("Invalid!");
                        }
                        if ((!username.getText().equals("")) && (one.equals(two))) {
                            try {
                                writer = new PrintWriter(new FileWriter("Users.dat"));
                                writer.println(num_users + 1);
                                for (int i = 0; i < (num_users); i++) {
                                    writer.println(users[i] + ":" + passwords[i]);
                                }
                                char[] chars = new char[one.length()];
                                char temp;
                                chars = one.toCharArray();
                                for (int i = 0; i < (one.length() / 2); i++) {
                                    temp = chars[i];
                                    chars[i] = chars[one.length() - 1 - i];
                                    chars[one.length() - 1 - i] = temp;
                                }
                                temp = chars[0];
                                chars[0] = chars[one.length() - 1];
                                chars[one.length() - 1] = temp;
                                one = new String(chars);
                                writer.println(username.getText() + ":" + one);
                                writer.close();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }

                            num_users++;
                            String[] temp1 = users;
                            String[] temp2 = passwords;
                            users = new String[num_users];
                            passwords = new String[num_users];
                            for (int i = 0; i < (num_users - 1); i++) {
                                users[i] = temp1[i];
                                passwords[i] = temp2[i];
                            }
                            users[num_users - 1] = new String(username.getText());
                            passwords[num_users - 1] = new String(one);
                            editusers.setData(users, passwords, num_users);
                            editusers.setVisible(true);
                            setVisible(false);
                        }
                    }
                }
            });
            // do nothing but hide and redisplay editusers
            cancel.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == cancel) {
                        editusers.setVisible(true);
                        setVisible(false);
                    }
                }
            });
        }
    }

    // class that allows the adding and removing of users that can login
    class EditUsers extends JFrame {

        int num_users;
        GridBagLayout layout;
        GridBagConstraints gbc;
        JButton add = new JButton("Add User");
        JButton remove = new JButton("Remove User");
        JButton back = new JButton("Back");
        JList list;
        JMenuItem padd, premove;
        JPopupMenu popup;
        JScrollPane scrollPane;
        NewUser newuser;
        String[] passwords;
        String[] users;

        EditUsers(String[] Users, String[] Passwords, int numusers) {
            super("Edit Users");
            this.setSize(300, 300);
            this.setLocation((int) ((screen.getWidth() - 300) / 2), (int) ((screen.getHeight() - 300) / 2));

            num_users = numusers;
            users = Users;
            passwords = Passwords;

            list = new JList(users);
            scrollPane = new JScrollPane(list);
            layout = new GridBagLayout();
            gbc = new GridBagConstraints();

            // initialize popup menu items
            popup = new JPopupMenu("Options..");
            padd = new JMenuItem("Add New..");
            premove = new JMenuItem("Remove User");

            // add tabs to popup menu
            popup.add(padd);
            popup.add(premove);
            list.add(popup);

            setConststraints(0, 0, 2, 5, 1, 1, GridBagConstraints.BOTH, scrollPane);
            setConststraints(0, 6, 1, 1, 1, 1, GridBagConstraints.HORIZONTAL, add);
            setConststraints(1, 6, 1, 1, .9, 1, GridBagConstraints.HORIZONTAL, remove);
            setConststraints(0, 7, 2, 1, 1, 1, GridBagConstraints.HORIZONTAL, back);

            this.getContentPane().setLayout(layout);
            this.getContentPane().add(scrollPane);
            this.getContentPane().add(add);
            this.getContentPane().add(remove);
            this.getContentPane().add(back);

            // list that displays the users that are currently added
            list.addMouseListener(new MouseListener() {
                public void mouseReleased(MouseEvent e) {
                    if (e.isPopupTrigger()) {
                        popup.show(list, e.getX(), e.getY());
                    }
                }

                public void mouseEntered(MouseEvent e) {
                }

                public void mouseExited(MouseEvent e) {
                }

                public void mousePressed(MouseEvent e) {
                }

                public void mouseClicked(MouseEvent e) {
                }
            });

            // popup - add
            padd.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == padd) {
                        add.doClick();
                    }
                }
            });

            // popup - remove
            premove.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == premove) {
                        remove.doClick();
                    }
                }
            });

            // add button that shows the newuser jframe to add a new user
            add.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == add) {
                        newuser = new NewUser(users, passwords, num_users);
                        editusers.setVisible(false);
                        newuser.setVisible(true);

                        newuser.addWindowListener(new WindowAdapter() {
                            public void windowClosing(WindowEvent we) {
                                editusers.setVisible(true);
                                newuser.dispose();
                            }
                        });
                    }
                }
            });

            // button that clears the selected* user name, also works with popupmenu
            // *if no username is selected, it will do nothing
            remove.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == remove) {
                        int extra_counter = 0;
                        String[] temp1 = new String[num_users - 1];
                        String[] temp2 = new String[num_users - 1];
                        if (!list.isSelectionEmpty()) {
                            for (int i = 0; i < (num_users); i++) {
                                if (i != list.getSelectedIndex()) {
                                    temp1[extra_counter] = users[i];
                                    temp2[extra_counter] = passwords[i];
                                    extra_counter++;
                                }
                            }
                            num_users--;
                            users = new String[num_users];
                            passwords = new String[num_users];
                            users = temp1;
                            passwords = temp2;
                            list.setListData(users);
                            // write to file
                            try {
                                writer = new PrintWriter(new FileWriter("Users.dat"));
                                writer.println(num_users);
                                for (int i = 0; i < num_users; i++) {
                                    writer.println(users[i] + ":" + passwords[i]);
                                }
                                writer.close();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                }
            });

            // gets rid of this and goes back to main window
            back.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == back) {
                        me.setVisible(true);
                        dispose();
                    }
                }
            });
        }

        // allows data to be sent to the class to update the user list
        public void setData(String[] us, String[] pa, int num) {
            users = new String[num];
            passwords = new String[num];
            num_users = num;
            users = us;
            passwords = pa;
            list.setListData(users);
        }

        // useful for gridbaglayout settings, send values and object and the func does the rest ;)
        public void setConststraints(int xpos, int ypos, int width, int height, double weightx, double weighty, int filltype, JComponent object) {
            gbc.gridx = xpos;
            gbc.gridy = ypos;
            gbc.gridwidth = width;
            gbc.gridheight = height;
            gbc.weightx = weightx;
            gbc.weighty = weighty;
            gbc.fill = filltype;
            layout.setConstraints(object, gbc);
        }
    }

    // class that displays a dialog box when a song is selected for play
    // the dialog is /supposed/ to allow playing and stopping of music
    // but only linear wav's and au files are used, so not too useful
    class PlayDialog extends JDialog implements ActionListener {

        AudioClip audio;
        JButton play = new JButton("Play");
        JButton stop = new JButton("Stop");
        JButton back = new JButton("Back");
        JLabel playing = new JLabel("Stopped..");
        JLabel song = new JLabel();
        JPanel t = new JPanel(), b = new JPanel();
        JPanel bb = new JPanel(), random1 = new JPanel(), random2 = new JPanel();
        String songvalue;

        PlayDialog(String artist, String track) {
            this.setTitle("Play File Options");
            this.setSize(300, 200);
            this.setLocation((int) ((screen.getWidth() - 300) / 2), (int) ((screen.getHeight() - 200) / 2.5));
            this.getContentPane().setLayout(new GridLayout(2, 1));
            this.getContentPane().add(t);
            this.getContentPane().add(b);
            t.setLayout(new GridLayout(2, 1));
            t.add(playing);
            t.add(song);
            b.setLayout(new GridLayout(1, 3));
            b.add(random1);
            b.add(bb);
            b.add(random2);
            bb.setLayout(new GridLayout(3, 1));
            bb.add(play);
            bb.add(stop);
            bb.add(back);

            play.addActionListener(this);
            stop.addActionListener(this);
            back.addActionListener(this);

            // displays the current song that has been selected
            songvalue = new String(artist + " - " + track);

            song.setText(songvalue);
            t.setBackground(Color.black);
            random1.setBackground(Color.black);
            random2.setBackground(Color.black);
            playing.setBackground(Color.black);
            playing.setForeground(Color.green);
            song.setBackground(Color.black);
            song.setForeground(Color.green);
        }

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == play) {
                // since we can't play /any/ ol song, such as mp3's, the code below will not work
                //try{audio = (new JApplet()).newAudioClip(new File(songvalue).toURL());}catch(Exception io){};

                // an applet is required for .getAudioClip, so "create" an applet...
                try {
                    audio = (new JApplet()).newAudioClip(new File("1.mid").toURL());
                } catch (Exception io) {
                }
                if (playing.getText().equals("Stopped..")) {
                    audio.play();
                    playing.setText("Playing..");
                }
            }
            if (e.getSource() == stop) {
                if (playing.getText().equals("Playing..")) {
                    audio.stop();
                    playing.setText("Stopped..");
                }
            }
            if (e.getSource() == back) {
                me.setVisible(true);
                dispose();
            }
        }
    }

    // first thing the user sees
    // ..lets the user enter a username and password
    class MyDialog extends JDialog {

        int num_users;
        int message = 0;
        BufferedReader read;
        Creator c;
        JButton Ok;
        JLabel Msg, userl, passl;
        JPanel top, bottom, middle;
        JPasswordField password;
        JTextField username;
        String creator = "Created by Mark McKelvy";
        String[] passwords;
        String[] users;

        public MyDialog() {
            this.setTitle("Mark Amp v0.8");
            this.setSize(200, 180);
            this.setLocation((int) ((screen.getWidth() - 200) / 2), (int) ((screen.getHeight() - 200) / 2));

            top = new JPanel();
            bottom = new JPanel();
            middle = new JPanel();
            userl = new JLabel("User: ");
            passl = new JLabel("Pass: ");
            Ok = new JButton("      Ok      ");
            Msg = new JLabel(creator);
            c = new Creator();
            username = new JTextField(20);
            password = new JPasswordField(20);

            this.getContentPane().setLayout(new GridLayout(3, 1));
            this.getContentPane().add(top);
            this.getContentPane().add(middle);
            this.getContentPane().add(bottom);
            top.setLayout(new GridLayout(1, 1));
            top.add(Msg);
            middle.setLayout(new GridLayout(2, 2));
            middle.add(userl);
            middle.add(username);
            middle.add(passl);
            middle.add(password);
            bottom.add(Ok);

            Msg.setHorizontalAlignment(SwingConstants.CENTER);
            Msg.setForeground(Color.blue);
            top.setBackground(Color.white);
            bottom.setBackground(Color.white);
            middle.setBackground(Color.white);
            userl.setForeground(Color.blue);
            passl.setForeground(Color.blue);

            try {
                read = new BufferedReader(new FileReader("Users.dat"));
            } catch (FileNotFoundException e) {
                read = null;
            }
            ;
            try {
                num_users = Integer.parseInt(read.readLine());
                passwords = new String[num_users];
                users = new String[num_users];
                for (int i = 0; i < num_users; i++) {
                    String temp = new String(read.readLine());
                    for (int x = 0; x < temp.length(); x++) {
                        if (temp.charAt(x) == ':') {
                            users[i] = new String(temp.substring(0, x));
                            passwords[i] = new String(temp.substring(x + 1, temp.length()));
                        }
                    }
                }
                read.close();
            } catch (IOException io) {
            }
            for (int c = 0; c < num_users; c++) {
                char[] chars = new char[passwords[c].length()];
                char temp;
                chars = passwords[c].toCharArray();
                for (int i = 0; i < (passwords[c].length() / 2); i++) {
                    temp = chars[i];
                    chars[i] = chars[passwords[c].length() - 1 - i];
                    chars[passwords[c].length() - 1 - i] = temp;
                }
                temp = chars[0];
                chars[0] = chars[passwords[c].length() - 1];
                chars[passwords[c].length() - 1] = temp;
                passwords[c] = new String(chars);
            }

            try {
                reader = new BufferedReader(new FileReader("System.dat"));
            } catch (FileNotFoundException e) {
                reader = null;
            }
            ;

            if (reader == null) {
                Msg.setHorizontalAlignment(SwingConstants.CENTER);
                Msg.setForeground(Color.blue);
                top.setBackground(Color.white);
                bottom.setBackground(Color.white);
                middle.setBackground(Color.white);

                this.getContentPane().add(top);
                this.getContentPane().add(bottom);
                top.add(new JLabel("ERROR! System.dat Not Found!"));
                bottom.setLayout(new GridLayout(2, 1));
                bottom.add(middle);
                bottom.add(Ok);

                Ok.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (e.getSource() == Ok) {
                            dispose();
                        }
                    }
                });
            } else {
                try {
                    file_name = new String(reader.readLine());
                    str_num_images = new String(reader.readLine());
                    int_num_images = Integer.parseInt(str_num_images);
                    the_images = new Image[int_num_images];
                    str_images = new String[int_num_images];

                    // get the images and put them in the images array
                    for (int i = 0; i < int_num_images; i++) {
                        str_images[i] = reader.readLine();
                        the_images[i] = (new JFrame()).getToolkit().createImage(str_images[i]);
                    }

                    String[] readtemp = new String[12];
                    Color[] colors = new Color[12];
                    int[] separations = new int[2];
                    int keeptrack = 0;

                    for (int c = 0; c < 12; c++) {
                        readtemp[c] = new String(reader.readLine());
                        for (int i = 0; i < readtemp[c].length(); i++) {
                            if (readtemp[c].charAt(i) == ',') {
                                separations[keeptrack] = i;
                                keeptrack++;
                            }
                        }
                        keeptrack = 0;
                        red = Integer.parseInt(readtemp[c].substring(0, separations[0]));
                        green = Integer.parseInt(readtemp[c].substring(separations[0] + 1, separations[1]));
                        blue = Integer.parseInt(readtemp[c].substring(separations[1] + 1, readtemp[c].length()));
                        colors[c] = new Color(red, green, blue);
                    }
                    (cRadioB) = colors[0];
                    (cRadioF) = colors[1];
                    (cTabB) = colors[2];
                    (cTabF) = colors[3];
                    (cInputF) = colors[4];
                    (cInputB) = colors[5];
                    (cPitemF) = colors[6];
                    (cPitemB) = colors[7];
                    (cListF) = colors[8];
                    (cListB) = colors[9];
                    (cListSF) = colors[10];
                    (cListSB) = colors[11];
                    reader.close();
                } catch (IOException e) {
                }
                ;

                // checks to see that the user has the correct password
                Ok.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (e.getSource() == Ok) {
                            int uindex = -1;
                            int pindex = -1;
                            boolean userfound = false;
                            String temp1 = new String(username.getText());
                            String temp2 = new String(password.getPassword());
                            for (int c = 0; c < num_users; c++) {
                                if (users[c].toUpperCase().equals(temp1.toUpperCase())) {
                                    uindex = c;
                                    userfound = true;
                                }
                                if (passwords[c].toUpperCase().equals(temp2.toUpperCase())) {
                                    pindex = c;
                                    userfound = true;
                                }
                            }
                            if ((uindex == pindex) && (userfound == true)) {
                                // used to give the program time to load
                                ProgressDraw progressDraw = new ProgressDraw();
                                progressDraw.setVisible(true);

                                progressDraw.addWindowListener(new WindowAdapter() {
                                    public void windowClosing(WindowEvent we) {
                                        System.exit(0);
                                    }
                                });
                                dispose();
                            }
                            if ((userfound == false) || (uindex == -1)) {
                                username.setForeground(Color.red);
                                username.setText("Username NOT Found!");
                            } else if (pindex == -1) {
                                username.setForeground(Color.red);
                                username.setText("Password Incorrect!");
                            }
                        }
                    }
                });

            }
        }

        // animates the opening credits title
        class Creator implements Runnable {

            Thread thread;

            Creator() {
                thread = new Thread(this);
                if (thread != null)
                    thread.start();
            }

            public void run() {
                while (true) {
                    try {
                        if (message == 0) {
                            Msg.setText("Created");
                        }
                        if (message == 1) {
                            Msg.setText("By");
                        }
                        if (message == 2) {
                            Msg.setText("Mark");
                        }
                        if (message == 3) {
                            Msg.setText("McKelvy");
                            message = -1;
                        }
                        message++;
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                    }
                }
            }
        }
    }

    // displays a "loading" bar while the program loads necessary files
    public class ProgressDraw extends JFrame {

        int counter = -1;
        DrawThread drawThread;

        ProgressDraw() {
            super();
            this.setSize(300, 60);
            this.setLocation((int) ((screen.getWidth() - 300) / 2), (int) ((screen.getHeight() - 60) / 2));
            drawThread = new DrawThread();
            this.getContentPane().add(drawThread);
        }

        // kills poppa daddy
        public void Dispose() {
            dispose();
        }

        // thread that animates the progress bar
        class DrawThread extends JApplet implements Runnable {

            boolean drawpercent = false;
            Thread thread;

            DrawThread() {
                thread = new Thread(this);
                if (thread != null)
                    thread.start();
            }

            public void run() {
                while (true) {
                    try {
                        repaint();
                        counter++;
                        if (counter == 300) {
                            me.setVisible(true);
                            Dispose();
                        }
                        Thread.sleep(5);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            // draws the progress bar
            public void paint(Graphics g) {
                g.setColor(new Color(0, 0, 200));
                for (int x = -1; x < (counter - 1); x++) {
                    g.drawLine(x, 0, x, 60);
                }
                g.setColor(Color.white);
                for (int x = (counter - 1); x <= 300; x++) {
                    g.drawLine(x, 0, x, 60);
                }
                g.setColor(Color.black);
                g.drawString("Loading...", 0, 23);
                g.drawString("Loading...", 1, 23);
                if (drawpercent) {
                    for (int x = 0; x <= 1; x++) {
                        g.drawString(Integer.toString((counter / 3) + 1) + "%", 265, 23);
                        g.drawString(Integer.toString((counter / 3) + 1) + "%", 264, 23);
                    }
                } else
                    drawpercent = !drawpercent;
            }
        }
    }

    // opens the library file, sorts it, and capitalizes all words, then sends it to each tab
    // so that they can pick which songs they want
    class SortFile {

        boolean keepgoing = true;
        int counter = 0;
        Sort sort_list;
        String[] display_list;
        String read;

        SortFile() {
        }

        // returns the size of the file (the amount of songs present)
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
                }
                ;
                if (read != null) {
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
            return counter;
        }

        // returns a sorted and reformatted list
        String[] SortSongs(String file_name) {
            // open file for input
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
            // sends it to a sorting class to be sorted
            sort_list = new Sort();
            display_list = sort_list.SortIt(counter, display_list);

            // capitalizes every word
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
                reader.close();
            } catch (IOException e) {
            }
            ;
            // output the newly sorted and capitalized list to a file
            try {
                writer = new PrintWriter(new FileWriter(file_name));
                for (int i = 0; i < counter; i++) {
                    writer.println(display_list[i]);
                }
                writer.close();
            } catch (IOException e) {
            }

            return (display_list);
        }
    }

    // sorts a list of data sent to it using the bubble sort method
    class Sort {

        Sort() {
        }

        String[] SortIt(int counter, String display_list[]) {
            String temp;

            // Bubble Sort Method. Heart of the Program.
            for (int x = 0; x < counter; x++) {
                for (int y = 0; y < (counter - 1); y++) {
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
