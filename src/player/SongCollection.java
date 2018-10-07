package player;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Array;
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
    private TreeMap<String, String> songsTree; // I added this

    /** Constructor of class SongCollection
     * */
    public SongCollection() {
        songs = new TreeMap<>();
        songsTree = new TreeMap<>(); // I added this
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

            for (Path file : filesList) {
                if (!(Files.isDirectory(file))) { // Found the file in the dir

                    if (isMP3(file.toString())) { // If file is an mp3, continue

                        Song newSong = new Song(file.toString()); // Create a new song object.
                        String newSongTitle = newSong.getTitle();
                        String newSongArtist = newSong.getArtist();

                        if (songs.containsKey(newSong.getArtist())) {
                            // Artist has already been added. Add new entry to existing "small" map.
                            Map<String, Song> smallMap = songs.get(newSongArtist);
                            smallMap.put(newSongTitle, newSong);

                        }
                        else {
                            // Artist has not yet been added. So make a new small TreeMap and add new entry to it.
                            TreeMap<String, Song> smallMap = new TreeMap<>();
                            smallMap.put(newSongTitle, newSong);
                            songs.put(newSongArtist, smallMap);

                        }
                    }
                }
            }
        }
        catch (IOException e) {
            System.out.println("Cannot open directory.");
        }
    }

    // FILL IN CODE: Add other methods as needed - before you start coding, think of what methods you want to have in this class
    /** @param artistName String representation of the artist name
     * @return  boolean whether search string is in the dir.
     * (Outer level search - keys are artist names)
     * */
    public boolean artistNameExists(String artistName) {
        return (songs.containsKey(artistName));

    }

    /** @param artistName String representation of the artist name
     * @return artistList, an ArrayList<String> object - contains all the artist names
     * (Searches through outer layer of TreeMap songs)
     * */
    public ArrayList<String> getArtistList(String artistName) { // return an arraylist of set
        ArrayList<String> artistList = new ArrayList<>();

        for (String name : songs.keySet()) {
            if (name.matches(artistName)) {
                artistList.add(name);
            }
        }
        return artistList;
    }

    /**
     * @param artistName String representation of the artist name
     * @param songTitle song name String
     * @return songObject , a Song object that the title corresponds to.
     *
     * (Inner level search)
     * */
    public Song getSong(String artistName, String songTitle) {
        if (songs.containsKey(artistName)) {
            if (songs.get(artistName).containsKey(songTitle)) {
                return songs.get(artistName).get(songTitle);
            }
        }
        return null;
    }

    /** @param artistName String representation of the artist name
     * @return songTitleList, which is an arrayList of song titles by the artist.
     * */
    public ArrayList<String> getSongTitleList(String artistName) {


        ArrayList<String> songTitleList = new ArrayList<>();

        // Search through songs TreeMap
        for (String titleName : songs.get(artistName).keySet()) {
            songTitleList.add(titleName); // And add them to the ArrayList

        }
        return songTitleList;
    }

    /** @return String representation of SongCollection
     * */
    public String toString() {
        String res = "";

        for (Map.Entry<String, Map<String, Song>> artistEntry : songs.entrySet()) {
            String artist = artistEntry.getKey();
            res += artist + System.lineSeparator();

            for (Map.Entry<String, Song> titleEntry : artistEntry.getValue().entrySet()) {
                String title = titleEntry.getKey();
                res +=  " - " + title + System.lineSeparator();
            }
            res += System.lineSeparator();
        }
        return res;
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

    /** The following methods are mostly for Part 2: */

    /** I made method loadSongsRecursive
     * Finds all .mp3 files in a given directory, creates Songs from them and adds them to this collection
     * @param dir Name of the project directory that contains mp3 files
     *
     * They should be in order of artist (key)
     */
    public void loadSongsRecursive(String dir) {
        // FILL IN CODE - Largely Copying from the DirectoryStreamExample.java and PathExample.java(Karpenko's examples)
        // Gets all files and subdirectories in a given directory

        Path path = Paths.get(dir); // Made a new path, arg is the String directory name
        try (DirectoryStream<Path> filesList = Files.newDirectoryStream(path.toAbsolutePath())) {

            for (Path p : filesList) {
                if (!(p.toFile().isDirectory())) { // If item is not a directory
                    if (isMP3(p.toString())) { // If file is an mp3, continue

                        Song newSong = new Song(p.toString()); // Create a new song object.
                        String newSongTitle = newSong.getTitle();
                        String newSongArtist = newSong.getArtist();

                        // This did not work because identical keys were overwritten.
                        // songsTree.put(newSongArtist, newSongTitle);  // Adding <Artist, Title> to a TreeMap named songsTree

                        if (songs.containsKey(newSong.getArtist())) {
                            // Artist has already been added. Add new entry to existing "small" map.
                            Map<String, Song> smallMap = songs.get(newSongArtist);
                            smallMap.put(newSongTitle, newSong);

                        } else {
                            // Artist has not yet been added. So make a new small TreeMap and add new entry to it.
                            TreeMap<String, Song> smallMap = new TreeMap<>();
                            smallMap.put(newSongTitle, newSong);
                            songs.put(newSongArtist, smallMap);

                        }
                    }
                }
                else {
                    // If item is a directory, get recursive!
                    loadSongsRecursive(p.toString());
                }
            }
        }
        catch (IOException e) {
            System.out.println("Cannot open directory.");
        }
    }

    /**
     * getSongsSize is a helper function for createTableElems().
     * Used for constructing our 2D Array in createTableElems()
     * @return integer size of nested TreeMap
     *
     * */
    public int getSongsSize() {
        int size = 0;
        for (String artist : songs.keySet()) { // Works
            size += songs.get(artist).size();
        }
        return size;
    }

    /**
     * Creates a 2D array with two columns and enough rows for all the songs
     * Used in the MPlayerPanel class
     * @return arr2D a 2D String array
     *
     ** I am Overloading this method name because I want to pass no arguments to this version.
     * (artistQuery might be an empty String...)
     ** This way, I can return a version of the 2D Array where all items are displayed (default case)
     ** The title and artist from all entries in songs SongCollection are added to the 2D array, which is returned.
     *
     * */
    public String[][] createTableElems() {
        int tableSize = getSongsSize();
        String[][] arr2D = new String[tableSize][2];

        int k = 0;
        for (String artist : songs.keySet()) { // Works

            for (String title : songs.get(artist).keySet()) {
                arr2D[k][0] = title;
                arr2D[k][1] = artist;
                k++;
            }
        }

        SortedMap<String, String> tailMap = songsTree.tailMap("");

        System.out.println("Tailmap: (1)" + mapToString(tailMap));



        //System.out.println("Songs tree: () " + songsTree.toString());

        // Let's morph songsTree into a 2D array!

//        int count = 0;
//        for (Map.Entry<String, String> entry : songsTree.entrySet()) {
//            arr2D[count][0] = entry.getKey();
//            arr2D[count][1] = entry.getValue();
//            count++;
//        }
//
//        System.out.println("Arr2D" + arr2D);

        return arr2D;
    }

    /**
     * Creates a 2D array with two columns and enough rows for all the songs
     * Used in the MPlayerPanel class
     * @param artistQuery the String search item. Used in recursive getPartialMatch method.
     * @return arr2D a 2D String array
     *
     ** I am Overloading this method name because I want to pass one argument (the search query) to this.
     *         // getPartialMatch helps us find:
     *         // - if the name matches some artist name in songs exactly, or
     *         // - if the name matches some artist name in songs partially
     * Then, the title and artist from these entries in songs SongCollection are added to the 2D array, which is returned.
     *
     * */
    public String[][] createTableElems(String artistQuery) {

        int tableSize = getSongsSize();
        String[][] arr2D = new String[tableSize][2];
        int k = 0;

        for (String artist : songs.keySet()) {
            if (getPartialMatch(artistQuery, artist)) {
                for (String title : songs.get(artist).keySet()) {
                    arr2D[k][0] = title;
                    arr2D[k][1] = artist;
                    k++;
                }
            }
        }

        String test = "Fwdslxsh";

        SortedMap<String, String> tailMap = songsTree.tailMap(test, true);

        System.out.println("Tailmap: (2)" + mapToString(tailMap));

//        System.out.println("Songs tree: (takes artistQuery) " + songsTree.toString());
//
//        // Let's morph songsTree into a 2D array!
//
//        int count = 0;
//        for (Map.Entry<String, String> entry : songsTree.entrySet()) {
//            arr2D[count][0] = entry.getKey();
//            arr2D[count][1] = entry.getValue();
//            count++;
//        }
//
//        System.out.println("Arr2D" + arr2D);


        return arr2D;
    }

    public String mapToString(Map<String, String> tm) {
        String out = "";
        for (String s : tm.keySet()) {
            out += s + ": " + tm.get(s) + System.lineSeparator();;
        }
        return out;
    }

    /**
     * Helper function getPartialMatch - Recursive function
     * @param query the String we are searching for
     * @param s the String that exists in the list of Artist names
     *
     * @return true if there is a partial or full match, or false if there is not.
     *
     * This method compares two strings, recursively.
     * Searches through smaller and smaller substrings of existing String s, and finds out if
     * our search query matches it.
     * Note, this fxn doesnt handle case where query == ""...I handle that in MPlayerPanel play button.
     *
     * */
    public boolean getPartialMatch(String query, String s) {
        // Base case - if query name is longer than itemName - false (halts immediately)
        if (query.length() > s.length()) {
            return false;
        }
        // Base case - query is empty or itemName is empty - false
        if (s.length() == 0) {
            return false;
        }
        // Base case - query and itemName match - true
        if (query.equals(s)) {
            return true;
        }
        // Recursive case - don't match, but len > 0 so call the function again:
        return getPartialMatch(query, s.substring(0, s.length() - 1)); // Checks smaller and smaller substrings of s
    }

    /** Getter for all artists
     @return Set<String> Set of all artist names
     */
    public Set<String> getAllArtists() {
        return songs.keySet();
    }
}
