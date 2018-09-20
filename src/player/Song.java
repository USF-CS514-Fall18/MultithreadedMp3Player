package player;

// Imports related to jaudiotagger and jl libraries
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import org.jaudiotagger.audio.*;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.*;

import java.io.File;
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

    /** Constructor of class Song.
     * Takes the name of the file that contains the mp3 of this song,
     * and extracts title and artist tags from it using
     * jaudiotagger library. See lab description for details.
     *
     * @param filename name of mp3 file that contains the song
     */
    public Song(String filename) {
        // FILL IN CODE
// My source for this constructor: http://www.jthink.net/jaudiotagger/examples_read.jsp
// I'm guessing we use NIO (Buffered) for the data...???
//        Files nioFile = new Files(filename); // Used java.nio package to open this file
//        AudioFile f = AudioFileIO.read(nioFile); // Used AudioFile package to read file
//        Tag tag = f.getTag(); // MetaInformation is stored in the Tag interface"
//
//        title = tag.getFirst(FieldKey.TITLE); // FieldKey enum lists all the fields that can be mapped
//        artist = tag.getFirst(FieldKey.ARTIST);// Has extracted the title and artist using jaudiotagger library.

// Now, we need to play the file.
// So we need to use IO (Stream), since that is the paramter for .play(), see below...
// GOOD EXAMPLE omg :::: https://www.programcreek.com/java-api-examples/?api=org.jaudiotagger.audio.AudioFile


        try {

            File ioFile = new File(filename);


            AudioFile audioFile = AudioFileIO.read(ioFile);
            Tag tag = audioFile.getTag();

            title = tag.getFirst(FieldKey.TITLE);
            artist = tag.getFirst(FieldKey.ARTIST);
        } catch (IOException | TagException | CannotReadException | ReadOnlyFileException | InvalidAudioFrameException e) {
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
        // String res = "";

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
     *
     *http://www.javazoom.net/javalayer/docs/docs1.0/javazoom/jl/player/Player.html I saw:
     Constructor Summary
     Player(java.io.InputStream stream)
     */
    public void play() {
        // FILL IN CODE
        Player playa = new Player(f);  // using Player class of jl library http://www.javazoom.net/javalayer/docs/docs1.0/javazoom/jl/player/Player.html
        // Needs to take a stream
        // Makes a new song collection too when we instantiate new MPlayer object
        playa.play();

    }

    // Feel free to add other methods as needed

}
