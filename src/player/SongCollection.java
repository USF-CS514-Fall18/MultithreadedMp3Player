package player;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.util.*;

/** SongCollection class stores all the songs in a nested TreeMap (see below),
 *  and has methods to manipulate songs.
 *
 *      // A TreeMap, where each key is an artist, and the value is another TreeMap,
 *     // where each key is the title of the song written by this artist, and the value
 *     // is the reference to the corresponding Song. Note: this data structure will make
 *     // the search efficient when the user first searches by artist, then by title.
 *     // Since it's a TreeMap, all keys will be sorted alphabetically.
 */

public class SongCollection {
    private Map<String, Map<String, Song>> songs;

    /** Constructor of class SongCollection
     * */
    public SongCollection() {
        songs = new TreeMap<>();
    }

    /** Finds all .mp3 files in a given directory, creates Songs from them and adds them to this collection
     * Note you are not allowed to use class File; You are required to use classes from nio package we discussed in class.
     * @param dir Name of the project directory that contains mp3 files
     *
     * They should be in order of artist (key)
     */
    public void loadSongs(String dir) {
        // FILL IN CODE - Largely Copying from the DirectoryStreamExample.java and PathExample.java(Karpenko's examples)
        // Gets all files and subdirectories in a given directory

        Path path = Paths.get(dir); // Made a new path, arg is the String directory name

        try (DirectoryStream<Path> filesList = Files.newDirectoryStream(path.toAbsolutePath())) {

            for (Path file : filesList)
                if (!(Files.isDirectory(file))) { // Found the file in the dir

                    Song song = new Song(file.toString()); // Song(String filename)
                    TreeMap<String, Song> titleSongMap = new TreeMap<String, Song>();
                    titleSongMap.put(song.getTitle(), song);

                    songs.put(song.getArtist(), titleSongMap);

                }
        }
        catch (IOException e) {
            System.out.println("Cannot open directory.");
        }

        System.out.println("Key set: " + songs.keySet());
        System.out.println(songs.toString());

    }

    // FILL IN CODE: Add other methods as needed - before you start coding, think of what methods you want to have in this class

    /** @return String representation of SongCollection
     * */
    public String toString() {
        return "SongCollection{" +
                "songs=" + songs +
                '}';
    }
}

