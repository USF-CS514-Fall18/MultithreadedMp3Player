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

        System.out.println("Welcome to the MP3 Player. ");

        if (args.length == 0) {
            System.out.println("No argument provided");
            return;
        }

        // FILL IN CODE
        else {
            songs.loadSongs(args[0]); // args have been set to:  dir
            Scanner scanner = new Scanner(System.in);
            boolean valid = true; // Made a bool to control the following do-while statement:


            System.out.println("To string: " + songs.toString());

            do {
                System.out.println("Please enter the artist name: ");
                String artistName = scanner.nextLine();


                if (songs.searchForArtistName(artistName)) { // If artist exists...

                    System.out.println("Artist found: " + artistName);

                    Map<String, Song> artistSet = songs.getArtistSet(artistName); // Create new variable to search through


                    // Now, search for the song:
                    System.out.println("Please enter the song name: ");

                    boolean askForSong = false;

                    do {
                        String songName = scanner.nextLine();

                        if (!(songs.getSongBool(songName))) {
                            System.out.println("You entered the INVALID song name " + songName + " try again: ");
                            askForSong = true;

                        } else {

                            System.out.println("You entered the VALID song name " + songName);


                            try {
                                Song foundSong = songs.getSong(songName);
                                System.out.println("Found song. Playing song. ");
                                foundSong.play(); // works!


                            } catch (NullPointerException e) {
                                System.out.println("Cannot play song.");
                            }
                        }
                    } while (askForSong);

                }
                else {
                    System.out.println("Artist not found. Try again: ");
                }

            } while (valid);

        }
    }
}




