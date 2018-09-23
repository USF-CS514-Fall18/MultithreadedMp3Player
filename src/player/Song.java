package player;

// Imports related to jaudiotagger and jl libraries
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import javazoom.jl.player.advanced.AdvancedPlayer;
import org.jaudiotagger.audio.*;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.*;

import java.io.*;
import java.nio.file.Files;
// is java.nio is preferred for this project? Contains Path, Paths, Files, FileSystem, DirectorySystem...


/** Class Song - represents a song in the SongCollection.
 *  A Song object should store a title, an artist, and
 *  the name of the mp3 file with the song. */
public class Song  {
    // FILL IN CODE: add instance variables
    private String title;
    private String artist;
    private String filename;

    /** Constructor of class Song.
     * Takes the name of the file that contains the mp3 of this song,
     * and extracts title and artist tags from it using
     * jaudiotagger library. See lab description for details.
     *
     * @param filename name of mp3 file that contains the song
     */
    public Song(String filename) {
        // FILL IN CODE
        this.filename = filename;

        // Src :::: https://www.programcreek.com/java-api-examples/?api=org.jaudiotagger.audio.AudioFile

        try {

            File ioFile = new File(filename);


            AudioFile audioFile = AudioFileIO.read(ioFile);
            Tag tag = audioFile.getTag();

            title = tag.getFirst(FieldKey.TITLE);
            artist = tag.getFirst(FieldKey.ARTIST);

//            // https://stackoverflow.com/questions/26946826/use-jlayer-to-play-mp3-resource
//            FileInputStream fis = new FileInputStream(filename);
//            // player = new AdvancedPlayer(fis);

        } catch ( IOException | TagException | CannotReadException | ReadOnlyFileException | InvalidAudioFrameException e) {
            System.out.println("Some exception occurred when reading from file.");
            e.printStackTrace();
        }
    }

    /** Returns a string representation of the Song: title and artist separated
     * by " by ", for instance: Hot N Cold by Katy Perry
     * @return string representing the song
     */
    public String toString() {
        // FILL IN CODE

        String res = title + " by " + artist;
        return res;
    }

    /**
     * Plays the song using Player class of jl library. JLayer is a Java library that allows MP3 files to be played from Java programs.
     * See project description for details.
     * Please note: this method will likely not be in this class for part 2 of the project. But for part 1 it makes sense to
     * have it in this class
     *
     * Here's the example I used - OS:
     * https://www.programcreek.com/java-api-examples/?api=javazoom.jl.player.Player
     */

    public void play() {
        // FILL IN CODE

        try {
            FileInputStream fis = new FileInputStream(filename);
            BufferedInputStream bis = new BufferedInputStream(fis);

            Player player = new Player(bis);
            player.play();

        } catch (JavaLayerException | FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    /** Feel free to add other methods as needed */

    /** Return the title of the song - I added this - OS
     * @return title
     */
    public String getTitle() {
        return title;
    }
    /** Return the artist of the song - I added this - OS
     * @return title
     */
    public String getArtist() {
        return artist;
    }

    public static void main(String[] args) {
        Song s = new Song("dir/Jai Paul - BTSTU (Edit).mp3");
        System.out.println(s);

        s.play();
    }

}
