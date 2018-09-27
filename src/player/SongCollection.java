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

        // Make a new nested treemap:


        Path path = Paths.get(dir); // Made a new path, arg is the String directory name

        try (DirectoryStream<Path> filesList = Files.newDirectoryStream(path.toAbsolutePath())) {

//            TreeMap<String, TreeMap<String, Song>> bigMap = new TreeMap<>();
//            System.out.println(bigMap);

            for (Path file : filesList) {
                if (!(Files.isDirectory(file))) { // Found the file in the dir

                    if (isMP3(file.toString())) { // If file is an mp3, continue

                        Song newSong = new Song(file.toString()); // Create a new song object.
                        String newSongTitle = newSong.getTitle();
                        String newSongArtist = newSong.getArtist();


                        if (songs.containsKey(newSong.getArtist())) {
                            // Artist has already been added.
                            // So there is a corresponding TreeMap value in the map.
                            // System.out.println("Artist " + newSongArtist + " has already been added. ");
                            Map<String, Song> smallMap = songs.get(newSongArtist);
                            smallMap.put(newSongTitle, newSong);

//                            for (Song song : smallMap.values()) {
//                                System.out.print(song.getTitle() + ", ");
//                            }
//                            System.out.println();

                        }
                        else {
                            // Artist has not yet been added.
                            // So make a new TreeMap value and add they key:value to the map.

                            //System.out.println("Artist has not yet been added. ");
                            TreeMap<String, Song> smallMap = new TreeMap<>();

                            smallMap.put(newSongTitle, newSong);
                            songs.put(newSongArtist, smallMap);

                        }


                    } else {
                        System.out.println("Note: Found a file " + file.toString() + " that is not an mp3. Not added to search directory. ");
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
    public boolean searchForArtistName(String artistName) {
        boolean res = false;

        if (songs.containsKey(artistName))
            res = true;
        return res;
    }

    /** @param artistName String representation of the artist name
     * @return map entry that the artist name corresponds to.
     * (Outer level search - keys are artist names, values are TreeMaps<String, Song>)
     * */
    public Map<String, Song> getArtistSet(String artistName) {
        Map<String, Song> res = null;

        for (String name : songs.keySet()) {
            if (name.matches(artistName)) {
                res = songs.get(artistName);
            }
        }
        return res;
    }


    /** @param songName String representation of the song name
     * @return boolean whether search string is in the dir.
     * (Inner level search - keys are song names, values are Map objects)
     * */
    public boolean searchForSong(String songName, Map<String, Song> songSet) {
        boolean res = true;

//        for (String sn : songSet.keySet()) {
//            if (sn.matches(songName)) {
//                res = true;
//                System.out.println(res);
//            }
//        }

        // if (songSet.containsKey(songName)) res = true;

        return res;
    }

//    /** @param songName String representation of the song name
//     * @param songSet the inner Map that we are looking for (now that we know the artist)
//     * @return Song object that the song name corresponds to.
//     * (Inner level search - keys are song names, values are Map objects)
//     * */
//    public Song getSong(String songName, Map<String, Song> songSet) {
//        Song res = null;
//
////        for (String sn : songSet.keySet()) {
////            if (sn.matches(songName)) {
////                res = songSet.keySet().get(songName);
////                System.out.println(res.toString());
////            }
////        }
//
//        return res;
//    }

    /** @param title String representation of the title
     * @return Song that the title corresponds to.
     * */
    public Song getSong(String title) {

        Song res = null;

        if (title.matches("")) {
            return res;
        }

        for (Map<String, Song> val: songs.values()) {
            for (Map.Entry<String, Song> entry: val.entrySet()) {
                if (entry.getKey().matches(title)) {
                    res = entry.getValue();
                }
            }
        }
        return res;
    }

    /** @param title String representation of the title
     * @return boolean if the song is found.
     * */
    public boolean getSongBool(String title) {

        boolean res = false;

        if (title.matches("")) {
            return res;
        }

        for (Map<String, Song> val: songs.values()) {
            for (Map.Entry<String, Song> entry: val.entrySet()) {
                if (entry.getKey().matches(title)) {
                    res = true;
                }
            }
        }
        return res;
    }

    /** @return String representation of SongCollection
     * WOrk in progress...
     * */
    public String toString() {
        String res = "";


        for (Map.Entry<String, Map<String, Song>> artistEntry : songs.entrySet()) {

            String artist = artistEntry.getKey().toString();

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
}

