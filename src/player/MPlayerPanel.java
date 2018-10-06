package player;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;


/** Provides a nice GUI for the mpl3 player lab.
 * For part 2 of the lab only - do not use or modify it for part 1.
 *
 * TO DO:
 * your code should be able to recursively find mp3 files in a given directory
 * you should be able to efficiently search for songs by an artist whose name starts with the given keyword (Ex: I should be able to type Tayl instead of Taylor Swift)
 * selected song should be playing in a separate thread, so that the user could continue interacting with the program;
 * the user should be able to stop the song.
 * your program should be intergrated with the GUI provided by the instructor (see below)
 *
 */
public class MPlayerPanel extends JPanel {

    private SongCollection songCollection; // collection of songs
    private String[][] titleArtistArray2D; // I added this
    private Song songCurrent; // I added this

    JPanel topPanel, bottomPanel;
    JScrollPane centerPanel;

    Thread currThread = null; //store a reference to the thread that plays the song

    // Buttons
    JButton playButton, stopButton, exitButton, loadMp3Button;

    // the text field and the button used to search for a song
    JTextField searchBox;
    JButton searchButton;

    int selectedSong = -1;
    JTable table = null;
    private final JFileChooser fc = new JFileChooser(); // for loading files from directory

    /** Constructor of class MPlayerPanel
     *
     * @param songCol reference to SongCollection
     */
    public MPlayerPanel(SongCollection songCol) {
        this.songCollection = songCol;
        this.setLayout(new BorderLayout());
        // Create panels: top, center, bottom
        // Create the top panel that has button Load mp3;
        // It also has a textfield and button to search for a song by Artist
        topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(1, 4));

        // create buttons and textfield
        loadMp3Button = new JButton("Load mp3");
        searchBox = new JTextField(5);
        searchButton = new JButton("Search By Artist");

        // buttons of the bottom row
        exitButton = new JButton("Exit");
        playButton = new JButton("Play");
        stopButton = new JButton("Stop");

        // add a listener for each button
        loadMp3Button.addActionListener(new MyButtonListener());
        searchButton.addActionListener(new MyButtonListener());
        exitButton.addActionListener(new MyButtonListener());
        playButton.addActionListener(new MyButtonListener());
        stopButton.addActionListener(new MyButtonListener());

        // add buttons and a textfield to the top panel
        topPanel.add(loadMp3Button);
        topPanel.add(searchBox);
        topPanel.add(searchButton);

        this.add(topPanel, BorderLayout.NORTH); // add top panel to the main panel (on top)

        // Create the bottom panel and add three buttons to it
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 3));
        bottomPanel.add(playButton);
        bottomPanel.add(stopButton);
        bottomPanel.add(exitButton);

        this.add(bottomPanel, BorderLayout.SOUTH); // add bottom panel to the main panel

        // the panel in the center that shows mp3 songs
        centerPanel = new JScrollPane();
        this.add(centerPanel, BorderLayout.CENTER);

        // file chooser: set the default directory to the current directory
        fc.setCurrentDirectory(new File("."));

    }

    /** Shows songs in the table */
    public void displaySongs(String artistQuery) {
        // For part 2 of the lab, you would need to uncomment the code below
        // and provide createTableElems method in class SongCollection
        // FILL IN CODE:

        if (artistQuery.equals("")) {
            titleArtistArray2D = songCollection.createTableElems(); // a String[][] array (2D)
        }

        else {
            titleArtistArray2D = songCollection.createTableElems(artistQuery); // Takes artist query
        }

        String[] columnNames = { "Title", "Artist" };
        table = new JTable(titleArtistArray2D, columnNames);
        centerPanel.getViewport().add(table);
        updateUI();
    }

    /** A inner listener class for buttons and textfields **/
    class MyButtonListener implements ActionListener {

        /**
         * Specifies what should happen when each button is pressed
         * @param e action event (like pressing a button)
         */
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == loadMp3Button) { // for loading songs from mp3 files
                System.out.println("Load mp3 button");

                // read all the mp3 files from mp3 directory
                fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fc.setDialogTitle("Select a directory with mp3 songs");

                int returnVal = fc.showOpenDialog(MPlayerPanel.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File dir = fc.getSelectedFile();

                    // FILL IN CODE - Load songs into SongCollection songs from the given directory - Done
                    songCollection.loadSongsRecursive(dir.getPath()); // I added this

                    displaySongs(""); // I added this. This will update our titleArtistArray2D too now.
                    // Initial artist query will be an empty string, which displays all songs in lib.
                    updateUI(); // starter code
                }
            }

            else if (e.getSource() == playButton) { // for playing the song

                selectedSong = table.getSelectedRow(); // Gives us an integer # of the row
                System.out.println(selectedSong);

                // FILL IN CODE: play selected song in a separate thread (Given the integer # of row)

                String songTitle = titleArtistArray2D[selectedSong][0];
                String songArtist = titleArtistArray2D[selectedSong][1];
                songCurrent = songCollection.getSong(songArtist, songTitle);

                // Start a new thread
                String filename = songCurrent.getFilename();
                System.out.println(filename);
                currThread = new PlayerThread(filename);
                currThread.start();

            }
            else if (e.getSource() == stopButton) { // to stop the song
                // FILL IN CODE
                ((PlayerThread) currThread).player.close();
                currThread.interrupt();

            }
            else if (e.getSource() == exitButton) { // exit
                System.exit(0);
            }

            else if (e.getSource() == searchButton) { // search by artist

                boolean exists = false;
                String searchArtist = searchBox.getText();
                if (searchArtist.matches("")) {
                    displaySongs(searchArtist); // Depending on whether this is empty, displaySongs will use one of two createTableElems methods.
                }

                else {
                    for (String artist : songCollection.getAllArtists()) {
                        if (songCollection.getPartialMatch(searchArtist, artist)) {
                            exists = true;
                        }
                    }

                    if (exists) {
                        System.out.println(searchArtist);
                        displaySongs(searchArtist); // I added this. This will update our titleArtistArray2D too now.
                        updateUI(); // starter code®

                    }
                    else {
                        System.out.println("Your entry does not match any artist names. Try again. ");

                    }
                }
            }
        } // actionPerformed
    } // ButtonListener

    /**
     *     Subclass Thread. Thread creation by extending the Thread class
     *     I am overriding run and adding my own method, quit.
     *
     *     https://docs.oracle.com/javase/tutorial/essential/concurrency/runthread.html
     *     */

    class PlayerThread extends Thread {
        Player player;
        PlayerThread(String filename) {
            FileInputStream file;
            try {
                file = new FileInputStream(filename);
                player = new Player(file);

            } catch (FileNotFoundException e) {
                e.getMessage();
            } catch (JavaLayerException e) {
                e.getMessage();
            }
        }
        public void run() {
            try {
                player.play();
            }
            catch (Exception e) {
                e.getMessage();
            }
        }
    }
}

