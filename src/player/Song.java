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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
// is java.nio is preferred for this project? Contains Path, Paths, Files, FileSystem, DirectorySystem...


/** Class Song - represents a song in the SongCollection.
 *  A Song object should store a title, an artist, and
 *  the name of the mp3 file with the song. */
public class Song  {
    // FILL IN CODE: add instance variables
    private String title;
    private String artist;
    // take out player - you can access it as a local variable.
    private AdvancedPlayer player; // Was it incorrect to add this additional private var? the reason was I used it in two methods...
    private String filename; // Was it incorrect to add this additional private var?

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

// GOOD EXAMPLE omg :::: https://www.programcreek.com/java-api-examples/?api=org.jaudiotagger.audio.AudioFile

        try {

            File ioFile = new File(filename);


            AudioFile audioFile = AudioFileIO.read(ioFile);
            Tag tag = audioFile.getTag();

            title = tag.getFirst(FieldKey.TITLE);
            artist = tag.getFirst(FieldKey.ARTIST);

            // CHECK THAT THIS IS CORRECT...making an Advanced Player object?? Also, it is private. I need to really makes ure that is ok.
            // Source was just...
            // https://stackoverflow.com/questions/26946826/use-jlayer-to-play-mp3-resource
            FileInputStream fis = new FileInputStream(filename);
            player = new AdvancedPlayer(fis);

        } catch (JavaLayerException | IOException | TagException | CannotReadException | ReadOnlyFileException | InvalidAudioFrameException e) {
            System.out.println("Some exception occurred when reading from file.");
            e.printStackTrace();
        }

    }


    /** Returns a string representation of the Song: title and artist separated
     * by " by ", for instance:
     * Hot N Cold by Katy Perry
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
     * Please note: this method will likely not be in this class
     * for part 2 of the project. But for part 1 it makes sense to
     * have it in this class
     *
     */
//     http://www.javazoom.net/javalayer/docs/docs1.0/javazoom/jl/player/Player.html I saw:
//     Constructor Summary
//     Player(java.io.InputStream stream)
//
//     https://www.programcreek.com/java-api-examples/index.php?source_dir=JMediaPlayer-master/JLayer1.0.1/src/javazoom/jl/player/jlp.java
//     Example with making Player object

    public void play() {
        // FILL IN CODE
        Player player = new Player;  // using Player class of jl library http://www.javazoom.net/javalayer/docs/docs1.0/javazoom/jl/player/Player.html
        // Needs to take a stream

        try {
            player.play();
        } catch (JavaLayerException e) {
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
        Song s = new Song("/Users/liv/IdeaProjects/lab2-liv-yaa/songs/SFW40517_06.mp3");
        System.out.println(s);
    }

}
