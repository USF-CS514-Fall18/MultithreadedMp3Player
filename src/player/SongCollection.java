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

                    if (isMP3(file.toString())) {
                        // File is an mp3. Adding to search directory
                        Song song = new Song(file.toString()); // Song(String filename)
                        TreeMap<String, Song> titleSongMap = new TreeMap<String, Song>();
                        titleSongMap.put(song.getTitle(), song);

                        songs.put(song.getArtist(), titleSongMap);
                    }
                    else {
                        System.out.println("Note: Found a file " + file.toString() + " that is not an mp3. Not added to search directory. ");
                    }

                }
        }
        catch (IOException e) {
            System.out.println("Cannot open directory.");
        }

//        System.out.println("Key set: " + songs.keySet());
//        System.out.println(songs.toString());

    }

    // FILL IN CODE: Add other methods as needed - before you start coding, think of what methods you want to have in this class

    /** @param name String representation of the artist
     * @return map entry that the title corresponds to.
     * */
    public String getArtistSongs(String name) {
        String res = "";

        for (String artist: songs.keySet()) {
            if (artist.matches(name)) {
                res = artist;
            }
        }

        return res;
    }

    /** @param title String representation of the title
     * @return Song that the title corresponds to.
     * */
    public Song getSong(String title) {

        Song res = null;

        for (Map<String, Song> val: songs.values()) {
            for (Map.Entry<String, Song> entry: val.entrySet()) {
                if (entry.getKey().matches(title)) {
                    res = entry.getValue();
                }
            }
        }
        return res;
    }



    /** @return String representation of SongCollection
     * */
    public String toString() {
        return "SongCollection{" +
                "songs=" + songs +
                '}';
    }

    /** File extension checker
     * @param filename is the filename
     * @return boolean whether the filename extension is "mp3"
     * */
    public boolean isMP3(String filename) {
        boolean res = false;
        String outString = "";

        if (filename.contains(".")) {
            outString += filename.substring(filename.lastIndexOf(".") + 1);
        }

        if (outString.matches("mp3")) {
            res = true;
        }

        return res;
    }
}

