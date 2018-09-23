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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
        // Copying from the DirectoryStreamExample.java and PathExample.java(Karpenko's examples)
        // Gets all files and subdirectories in a given directory
        String out = "";

        String currPath = "dir";
        System.out.println("Info about " + currPath);
        Path path = Paths.get(currPath); // Made a new path, arg is the String directory name

        try (DirectoryStream<Path> filesList = Files.newDirectoryStream(path.toAbsolutePath())) {

            for (Path file : filesList)
                if (!(Files.isDirectory(file))) { // Found the file in the dir

                    System.out.println("Creating song object from " + file.toString());


                    Song song = new Song(file.toString()); // Song(String filename)


                    System.out.println("Song object created " + song.getTitle() + " by " + song.getArtist());

                    //song.play();// works


////                    // Add song object to the collection (TreeMap):
////                    // First we want to find all songs with given artist and make a Map<String, Song>:
////                    // Made a new Map to store Title, Song:
////                    // Map<String, Song> titleSongMap = new Map<String, Song>;
////                    //titleSongMap.put(song.getTitle(), song);
////
////                    // This should be at a higher level
////                    //songs.put(song.getArtist(), titleSongMap);
//
                }
        }
        catch (IOException e) {
            System.out.println("Cannot open directory.");
        }


        //System.out.println(out);
    }

    // FILL IN CODE: Add other methods as needed - before you start coding, think of what methods you want to have in this class

     /** @param title title of the song
     * @return reference to the Song with the given title
      *
      * Searches through the TreeMap (aka songs)
      * I made this - OS
      * */
    public Song findSongTitle(String title) {
//        for (Song s: songs) {
//            if (s.getTitle().equals(title)) {
//                return s;
//            }
//        }
//        // If we cannot find song,
        return null;
    }

    /** @param artist of the song
     * @return reference to the Song with the given artist
     *
     * Searches through the TreeMap (aka songs)
     * I made this - OS
     * */
    public Song findSongArtist(String artist) {
//        for (Song s: songs) {
//            if (s.getArtist().equals(artist)) {
//                return s;
//            }
//        }
        // If we cannot find song,
        return null;
    }

    /** @param path is the current path
     *
     * Nearly identical to Karpenko's example PathExample.java
     * I added this - OS
     * */
    public static void printPathInformation(Path path) {
        System.out.println("Path, toString: " + path.toString());
        System.out.println("Path Parent " + path.getParent());
        System.out.println("Path Root: " + path.getRoot());
        System.out.println("Path Filename: " + path.getFileName());

        try {
            System.out.println("Size of Files: " + Files.size(path));
            System.out.println("Owner of Files: " + Files.getOwner(path));
            System.out.println("Does Files exist? " + Files.exists(path));

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println();
    }

    /** @return String representation of SongCollection
     * */
    public String toString() {
        return "SongCollection{" +
                "songs=" + songs +
                '}';
    }
}


//  Copied from Karpenko - printPathInformation(path);
//
//        currPath = ".";
//        System.out.println("Info about " + currPath);
//        Path relativeDir = Paths.get(currPath); // Made a new path, arg is the String directory name
//        printPathInformation(relativeDir);
//
//
//        Path absoluteDir = relativeDir.toAbsolutePath(); // Made a new path, arg is the String directory name
//        System.out.println("Info about " + absoluteDir);
//        printPathInformation(relativeDir);
//
//        // combine paths together
//        Path srcDirectory = Paths.get(".", "dir/Stylo.mp3");
//        printPathInformation(srcDirectory);
//
//        // canonical path
//        Path path2 = Paths.get("./dir/././Stylo.mp3");
//        System.out.println("Absolute path: " + path2.toAbsolutePath());
//        Path canonicalPath = path.toAbsolutePath().normalize();
//        printPathInformation(canonicalPath);
//
//*/
