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
        Song songObject = null;

        for (String name : songs.keySet()) { // Iterate through artist names

            for (String title : songs.get(name).keySet()) { // Iterate through song names

                if (artistName.matches(name) && songTitle.matches(title))  {
                    songObject = songs.get(artistName).get(title); // Go through both TreeMap layers to get the song object
                }
            }
        }
        return songObject;
    }

    /** @param artistName String representation of the artist name
     * @return songTitleList, which is an arrayList of song titles by the artist.
     * */
    public ArrayList<String> getSongTitleList(String artistName) {

        ArrayList<String> songTitleList = new ArrayList<>();

        // Search through songs TreeMap
        for (String aName : songs.keySet()) {
            if (aName.equals(artistName)) {
                for (String title : songs.get(aName).keySet()) { // Find all titles.
                    songTitleList.add(title); // And add them to the ArrayList
                }
            }
        }
        return songTitleList;
    }

    /** @return String representation of SongCollection
     * */
    public String toString() {
        String res = "";

        for (Map.Entry<String, Map<String, Song>> artistEntry : songs.entrySet()) {
            String artist = artistEntry.getKey();
            res += artist + ": \n";

            for (Map.Entry<String, Song> titleEntry : artistEntry.getValue().entrySet()) {
                String title = titleEntry.getKey();
                res +=  " - " + title + "\n";
            }
            res += "\n";
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
     * */
    public String[][] createTableElems() {
        // artistname might be blank...
        // If so, call this version of createTableElems.
        // It will create a FULL SIZED 2D array (all songs).

        int tableSize = getSongsSize();
        //System.out.println("Nested Map Size: " + tableSize);

        String[][] arr2D = new String[tableSize][2];

        int k = 0;
        for (String artist : songs.keySet()) { // Works

            for (String title : songs.get(artist).keySet()) {
                arr2D[k][0] = title;
                arr2D[k][1] = artist;
                k++;
            }

        }

        // Print 2D array:
//        System.out.println("Final 2D array...");
//        for (int i = 0; i < arr2D.length; i++) {
//            for (int j = 0; j < arr2D[i].length; j++) {
//                System.out.print(arr2D[0][j] + " ");
//                System.out.println(arr2D[1][j]);
//            }

        //}

       return arr2D;
    }

    public String[][] createTableElems(String artistQuery) {
        // Overloading this method name because I want to pass an argument (the search query) to this.
        // It will return a NARROWED DOWN 2D array (only songs matching artistQuery).

        int tableSize = getSongsSize();
        //System.out.println("Nested Map Size: " + tableSize);

        String[][] arr2D = new String[tableSize][2];

        int k = 0;
        for (String artist : songs.keySet()) { // Works

            if (artist.equals(artistQuery)) {

                for (String title : songs.get(artist).keySet()) {
                    arr2D[k][0] = title;
                    arr2D[k][1] = artist;
                    k++;
                }
            }

        }


        return arr2D;
    }

}

