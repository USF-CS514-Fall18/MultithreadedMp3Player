package player;

// Imports related to jaudiotagger and jl libraries
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import org.jaudiotagger.audio.*;
import org.jaudiotagger.tag.*;


/** Class Song - represents a song in the SongCollection.
 *  A Song object should store a title, an artist, and
 *  the name of the mp3 file with the song. */
public class Song  {
    // FILL IN CODE: add instance variables


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

        Files nioFile = new Files(filename); // Used java.nio package to open this file
        AudioFile f = AudioFileIO.read(nioFile); // Used AudioFile package to read file

        Tag tag = f.getTag(); // MetaInformation is stored in the Tag interface"

        title = tag.getFirst(FieldKey.TITLE); // FieldKey enum lists all the fields that can be mapped
        artist = tag.getFirst(FieldKey.ARTIST);

        // Has extracted the title and artist using jaudiotagger library.
        
    }


    /** Returns a string representation of the Song: title and artist separated
     * by " by ", for instance:
     * Hot N Cold by Katy Perry
     * @return string representing the song
     */
    public String toString() {
        String res = "";

        // FILL IN CODE

        return res;
    }

    /**
     * Plays the song using Player class of jl library.
     * See project description for details.
     * Please note: this method will likely not be in this class
     * for part 2 of the project. But for part 1 it makes sense to
     * have it in this class
     */
    public void play() {
        // FILL IN CODE
    }

    // Feel free to add other methods as needed

}
