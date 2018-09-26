package player;

import java.util.Map;
import java.util.Scanner;

/**
 * The Driver class for part 1 of lab 2, the text-based mp3 player.
 * Creates SongCollection using songs from the directory specified by args[0].
 * Interacts with the user allowing the user to find songs by artist,
 * then by title and play them.
 *
 *
 * I am going off my previous work with the LibraryCatalog user interface... - OS
 */
public class MPlayer {
    public static void main(String[] args) {
        SongCollection songs = new SongCollection(); // Constructor makes an empty 2D TreeMap too.

        if (args.length == 0) {
            System.out.println("No argument provided");
            return; // Why does she have this here?
        }

        // FILL IN CODE
        else {

            songs.loadSongs(args[0]); // args have been set to:  dir

            Scanner scanner = new Scanner(System.in);
            boolean valid = true; // Made a bool to control the following do-while statement:

            do {

                System.out.println("Welcome to the MP3 Player. Please enter the artist name: ");
                String artistName = scanner.next();

                try {
                    if (songs.searchForArtistName(artistName) == true) {

                        Map<String, Song> artistSet = songs.getArtistSet(artistName); // Create new variable to search through
                        // songs.toString(); This is still a work in progress

                        // Now, search for the song:
                        System.out.println("Artist found. Please enter the song name: ");
                        String songName = scanner.next();
                        if (artistSet.searchForSongName(songName, artistSet) == true) {


                            try {
                                Song foundSong = 
                                System.out.println("Playing song. ");
                                foundSong.play();
                            } catch (NullPointerException e) {
                                System.out.println("Cannot play song.");
                            }

                        } else {
                            System.out.println("Song not found. try again...");
                        }
                    } else {
                        System.out.println("Artist not found. Try again...");
                    }
                }

            } while (valid);


        }

    }
}
