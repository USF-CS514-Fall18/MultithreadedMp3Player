package player;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/** SongCollection class stores all the songs in a nested TreeMap (see below),
 *  and has methods to manipulate songs.
 *
 *      // A TreeMap, where each key is an artist, and the value is another TreeMap,
 *     // where each key is the title of the song written by this artist, and the value
 *     // is the reference to the corresponding Song. Note: this data structure will make
 *     // the search efficient when the user first searches by artist, then by title.
 *     // Since it's a TreeMap, all keys will be sorted alphabetically.
 */


// For first draft, I am going to add some methods similar to the LibraryCatalog class

public class SongCollection {

    // In a TreeMap, each key is the name of the artist, and the value is a nested TreeMap, where each key
    // is the title of the song written by this artist, and the value is the reference to the corresponding Song.
    private Map<String, Map<String, Song>> songs;


    /** Constructor of class SongCollection
     * */
    public SongCollection() {
        songs = new TreeMap<>();

    }

    /** Finds all .mp3 files in a given directory, creates Songs from them
     * and adds them to this collection
     * Note you are not allowed to use class File; You are required to use
     * classes from nio package we discussed in class.
     * @param dir Name of the project directory that contains mp3 files
     *
     *            They should be in order of artist (key)
     */
    public void loadSongs(String dir) {
        // FILL IN CODE
        // Copying from the DirectoryStreamExample.java (Karpenko's example)
        // Gets all files and subdirectories in a given directory

        Path path = Paths.get(dir); // Made a new path, arg is the String directory name
        try (DirectoryStream<Path> filesList = Files.newDirectoryStream(path)) {
            for (Path file : filesList) {
                if (!(Files.isDirectory(file))) { // Found the file in the dir
                    System.out.println("Creating song object from " + file.toString());

                    // Trying to create new Path object from file in FilesList
                    // This was a wonky way to make it become a string but I want to double check it
                    Song song = new Song(file.getFileName().toString()); // Song(String filename)

                    System.out.println("Song object created " + song.toString());

                    // Add song object to the collection (TreeMap):
                    // boolean alreadyThere = songs.containsKey(song.getArtist()); // key is the name of artist
                    Map<String, Song> titleSongMap = new Map<String, Song>;
                    titleSongMap.put(song.getTitle(), song);

                    songs.put(song.getArtist(), titleSongMap);




                }
            }
        }
        catch (IOException e) {
            System.out.println("Cannot open directory.");
        }
    }

    // FILL IN CODE
    // Add other methods as needed - before you start coding, think of what
    // methods you want to have in this class

     /** @param title title of the song
     * @return reference to the Song with the given title
      *
      * Searches through the TreeMap (aka songs)
      * I made this - OS
      * */
    public Song findSong(String title) {

        for (Song s: songs) {
            if (s.getTitle().equals(title)) {
                return s;
            }
        }
        // If we cannot find song,
        return null;
    }

    /** @param artist of the song
     * @return reference to the Song with the given artist
     *
     * Searches through the TreeMap (aka songs)
     * I made this - OS
     * */
    public Song findSong(String artist) {
        for (Song s: songs) {
            if (s.getArtist().equals(artist)) {
                return s;
            }
        }
        // If we cannot find song,
        return null;
    }

}
