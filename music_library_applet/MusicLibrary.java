// mark mckelvy
// java
// feb 10, 2004
// music library

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.event.*;

public class MusicLibrary extends JApplet {

    int int_num_images;
    int amount_of_songs;
    int flistsize = 10;
    BufferedReader reader;
    Color cRadioB = Color.white;
    Color cRadioF = Color.blue;
    Color cTabB = Color.white;
    Color cTabF = Color.blue;
    Color cPlaySF = Color.blue;
    Color cPlaySB = Color.white;
    Color cInputF = Color.blue;
    Color cInputB = Color.yellow;
    Color cPitemF = Color.green;
    Color cPitemB = Color.black;
    Color cListF = Color.blue;
    Color cListB = Color.white;
    Color cListSF = Color.green;
    Color cListSB = Color.black;
    Font font;
    Font lfont;
    Image[] the_images;
    ImageIcon techno_icon;
    ImageIcon rock_icon;
    ImageIcon country_icon;
    ImageIcon classical_icon;
    ImageIcon rap_icon;
    ImageIcon comedy_icon;
    ImageIcon break_icon;
    ImageIcon rnb_icon;
    JTabbedPane tabbedpane;
    PrintWriter writer;
    SortFile sortfile;
    String str_num_images;
    String file_name;
    String[] song_list;
    String song_value;
    MusicPane techno_m;
    MusicPane rock_m;
    MusicPane country_m;
    MusicPane classical_m;
    MusicPane rap_m;
    MusicPane comedy_m;
    MusicPane break_m;
    MusicPane rnb_m;

    public void init() {

        // set the file name
        file_name = new String(getParameter("Library"));

        // get info from file, sort, and send to panes
        sortfile = new SortFile();
        amount_of_songs = sortfile.ListSize(file_name);
        song_list = new String[amount_of_songs];
        song_list = sortfile.SortSongs(file_name);

        // get the number of images from html
        str_num_images = this.getParameter("Number of Images");
        // convert the string to an int and initialize the images array
        int_num_images = Integer.parseInt(str_num_images);
        the_images = new Image[int_num_images];

        // get the actual images from the html and put them in the images array
        for (int i = 0; i < int_num_images; i++) {
            the_images[i] = this.getImage(this.getDocumentBase(), this.getParameter(Integer.toString(i + 1)));
        }

        // create a new icon to add to the tab
        techno_icon = new ImageIcon(the_images[0]);
        rock_icon = new ImageIcon(the_images[1]);
        country_icon = new ImageIcon(the_images[2]);
        classical_icon = new ImageIcon(the_images[3]);
        rap_icon = new ImageIcon(the_images[4]);
        comedy_icon = new ImageIcon(the_images[5]);
        break_icon = new ImageIcon(the_images[6]);
        rnb_icon = new ImageIcon(the_images[7]);

        // create a new class to add to the tabbedpane
        techno_m = new MusicPane(song_list, amount_of_songs, "Techno");
        rock_m = new MusicPane(song_list, amount_of_songs, "Rock");
        country_m = new MusicPane(song_list, amount_of_songs, "Country");
        classical_m = new MusicPane(song_list, amount_of_songs, "Classical");
        rap_m = new MusicPane(song_list, amount_of_songs, "Rap");
        comedy_m = new MusicPane(song_list, amount_of_songs, "Comedy");
        break_m = new MusicPane(song_list, amount_of_songs, "Break");
        rnb_m = new MusicPane(song_list, amount_of_songs, "RNB");

        // create a new tabbedpane
        tabbedpane = new JTabbedPane();

        font = new Font("Comic Sans MS", Font.ITALIC, 14);
        tabbedpane.setFont(font);
        tabbedpane.setForeground(cTabF);
        tabbedpane.setBackground(cTabB);

        // add the tabbedpane to the JApplet
        this.getContentPane().add(tabbedpane);

        // add tabs to the tabbedpane
        tabbedpane.addTab("Techno", techno_icon, techno_m, "Genre: Techno");
        tabbedpane.addTab("Rock", rock_icon, rock_m, "Genre: Rock");
        tabbedpane.addTab("Break", break_icon, break_m, "Genre: Break");
        tabbedpane.addTab("R&B", rnb_icon, rnb_m, "Genre: RNB");
        tabbedpane.addTab("Classical", classical_icon, classical_m, "Genre: Classical");
        tabbedpane.addTab("Rap", rap_icon, rap_m, "Genre: Rap");
        tabbedpane.addTab("Comedy", comedy_icon, comedy_m, "Genre: Comedy");
        tabbedpane.addTab("Country", country_icon, country_m, "Genre: Country");
    }

    class MusicPane extends JPanel {

        boolean keepgoing = true;
        boolean playing = false;
        int dash[] = new int[5];
        int dash_counter;
        int counter;
        AudioClip audio;
        JList JL_songlist;
        JPanel top, mid, bottom;
        JPopupMenu popupmenu;
        JMenuItem pplay;
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
        String read;

        MusicPane(String[] song_list, int amount_of_songs, String filter_genre) {

            // initialize popup menu items
            popupmenu = new JPopupMenu("Options..");
            pplay = new JMenuItem("Play Song");

            // add tabs to popup menu
            popupmenu.add(pplay);

            // initalize counters
            dash_counter = 0;
            counter = 0;

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

            // set colors
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
            popupmenu.setForeground(Color.blue);
            popupmenu.setBackground(Color.yellow);
            font = new Font("Comic Sans MS", Font.ITALIC, 11);
            pplay.setFont(font);
            pplay.setForeground(cPitemF);
            pplay.setBackground(cPitemB);

            // set default values for radio buttons
            radio_artist.setSelected(false);
            radio_album.setSelected(false);
            radio_number.setSelected(false);
            radio_title.setSelected(false);
            radio_year.setSelected(false);
            input.setColumns(30);

            counter = amount_of_songs;

            // initialize the string arrays with the amount of songs to be held
            artist = new String[counter];
            album = new String[counter];
            track = new String[counter];
            title = new String[counter];
            year = new String[counter];
            genre = new String[counter];
            display_list = new String[counter];
            keepgoing = true;
            counter = 0;

            // read in the song information
            for (int j = 0; j < amount_of_songs; j++) {
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
                } else {
                    counter--;
                }
                counter++;
                dash_counter = 0;
            }

            // create one big array to hold each songs info to display to user
            for (int i = 0; i < counter; i++) {
                display_list[i] = artist[i] + " - " + album[i] + " - " + track[i] + " - " + title[i] + " - " + year[i];
            }

            // create a new jlist to display info, then put it in a scrollpane
            JL_songlist = new JList(display_list);
            lfont = new Font("Comic Sans MS", Font.PLAIN, flistsize);
            JL_songlist.setFont(lfont);
            JL_songlist.setForeground(cListF);
            JL_songlist.setBackground(cListB);
            JL_songlist.setSelectionForeground(cListSF);
            JL_songlist.setSelectionBackground(cListSB);
            JL_songlist.add(popupmenu);
            scrollPane = new JScrollPane(JL_songlist);

            // add all buttons and whatnot
            this.setLayout(new GridLayout(3, 1));
            top.setLayout(new GridLayout(3, 2));
            mid.setLayout(new GridLayout(1, 1));
            bottom.setLayout(new GridLayout(2, 1));
            this.add(top);
            this.add(mid);
            this.add(bottom);
            top.add(radio_artist);
            top.add(radio_album);
            top.add(radio_number);
            top.add(radio_title);
            top.add(radio_year);
            mid.add(scrollPane);
            bottom.add(new JLabel("Search:"));
            bottom.add(input);
            try {
                reader.close();
            } catch (IOException e) {
            }
            ;

            // set listeners
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

                            JL_songlist.setListData(display_list);
                            radio_album.setSelected(false);
                            radio_number.setSelected(false);
                            radio_title.setSelected(false);
                            radio_year.setSelected(false);
                        }
                    }
                }
            });

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

                            JL_songlist.setListData(display_list);
                            radio_artist.setSelected(false);
                            radio_number.setSelected(false);
                            radio_title.setSelected(false);
                            radio_year.setSelected(false);
                        }
                    }
                }
            });

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
                            JL_songlist.setListData(display_list);

                            radio_artist.setSelected(false);
                            radio_album.setSelected(false);
                            radio_title.setSelected(false);
                            radio_year.setSelected(false);
                        }
                    }
                }
            });

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

                            JL_songlist.setListData(display_list);

                            radio_artist.setSelected(false);
                            radio_album.setSelected(false);
                            radio_number.setSelected(false);
                            radio_year.setSelected(false);
                        }
                    }
                }
            });

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

                            JL_songlist.setListData(display_list);

                            radio_artist.setSelected(false);
                            radio_album.setSelected(false);
                            radio_number.setSelected(false);
                            radio_title.setSelected(false);
                        }
                    }
                }
            });

            input.addCaretListener(new CaretListener() {
                public void caretUpdate(CaretEvent e) {
                    int str_length = 0;
                    int another_list_length = 0;
                    int another_num = 0;
                    String input_txt = new String(input.getText());
                    String[] another_list;
                    str_length = input_txt.length();
                    for (int i = 0; i < counter; i++) {
                        if (display_list[i].substring(0, str_length).toUpperCase().equals(input_txt.toUpperCase())) {
                            another_list_length++;
                        }
                    }
                    another_list = new String[another_list_length + 1];
                    for (int i = 0; i < counter; i++) {
                        if (display_list[i].substring(0, str_length).toUpperCase().equals(input_txt.toUpperCase())) {
                            another_list[another_num] = display_list[i];
                            another_num++;
                        }
                    }
                    JL_songlist.setListData(another_list);
                }
            });

            JL_songlist.addMouseListener(new MouseListener() {
                public void mouseReleased(MouseEvent e) {
                    if (e.isPopupTrigger()) {
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

            // here


            pplay.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == pplay) {
                        Play play = new Play();
                        play.setVisible(true);
                    }
                }
            });
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
            popupmenu.setForeground(Color.blue);
            popupmenu.setBackground(Color.yellow);
            font = new Font("Comic Sans MS", Font.ITALIC, 11);
            pplay.setFont(font);
            pplay.setForeground(cPitemF);
            pplay.setBackground(cPitemB);
            font = new Font("Comic Sans MS", Font.PLAIN, flistsize);
            JL_songlist.setFont(lfont);
            JL_songlist.setForeground(cListF);
            JL_songlist.setBackground(cListB);
            JL_songlist.setSelectionForeground(cListSF);
            JL_songlist.setSelectionBackground(cListSB);
        }
    }

    class Play extends JDialog {

        AudioClip audio;
        JButton dplay, dstop, dclose;

        Play() {
            this.setTitle("Play");
            this.setSize(300, 100);
            this.getContentPane().setLayout(new GridLayout(1, 3));

            dplay = new JButton("Play");
            dstop = new JButton("Stop");
            dclose = new JButton("Close");

            this.getContentPane().add(dplay);
            this.getContentPane().add(dstop);
            this.getContentPane().add(dclose);

            dplay.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == dplay) {
                        audio = getAudioClip(getDocumentBase(), getParameter("Audio"));
                        audio.play();
                    }
                }
            });

            dstop.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == dstop) {
                        audio.stop();
                    }
                }
            });

            dclose.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == dclose) {
                        dispose();
                    }
                }
            });
        }
    }

    class SortFile {

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
            return (display_list);
        }
    }

    class Sort {
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
