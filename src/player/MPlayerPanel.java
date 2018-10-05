package player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;


/** Provides a nice GUI for the mpl3 player lab.
 * For part 2 of the lab only - do not use or modify it for part 1.
 */
public class MPlayerPanel extends JPanel {

    private SongCollection songCollection; // collection of songs

    private String[][] titleArtistArray2D; // I added this

    private Song songCurrent; // I added this

    //private String artistNameQuery = ""; // I added this - for searching for artist name


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

        updateUI(); // needed?

    }

    /** A inner listener class for buttons and textfields **/
    class MyButtonListener implements ActionListener {

        /** Specifies what should happen when each button is pressed
         *
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
                    songCollection.loadSongs(dir.getPath()); // I added this
                    displaySongs(""); // I added this. This will update our titleArtistArray2D too now.
                    updateUI(); // starter code

                    System.out.println(dir);

                }
            }

            else if (e.getSource() == playButton) { // for playing the song

                selectedSong = table.getSelectedRow(); // Gives us an integer # of the row

                System.out.println(selectedSong);

                // FILL IN CODE: play selected song in a separate thread (Given the integer # of row)

                String songTitle = titleArtistArray2D[selectedSong][0];
                String songArtist = titleArtistArray2D[selectedSong][1];

                System.out.println("Extracted song title: " + songTitle);
                System.out.println("Extracted song artist: " + songArtist);



                System.out.println("// Now search for song title!");
                songCurrent = songCollection.getSong(songArtist, songTitle);


                System.out.println("Song to play: " + songCurrent.toString());
                songCurrent.play();


            } else if (e.getSource() == stopButton) { // to stop the song
                // FILL IN CODE
                // Do we need to do songCollection.method?? Why can't I access this Song object.
                songCurrent.stop(); // This does not work!!




            } else if (e.getSource() == exitButton) { // exit
                System.exit(0);
            }

            else if (e.getSource() == searchButton) { // search by artist
                String searchArtist = searchBox.getText();
                System.out.println("search Artist button");
                System.out.println(searchArtist);

                // FILL IN CODE
                // Filter songs by the given artist (searchArtist)
                // Display only songs by this artist



                // FILL IN CODE - Load songs into SongCollection songs from the given directory - Done
                ///songCollection.loadSongs(dir.getPath()); // I added this

                displaySongs(searchArtist); // I added this. This will update our titleArtistArray2D too now.
                updateUI(); // starter code




            }
        } // actionPerformed
    } // ButtonListener
}
