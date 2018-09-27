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

            String artistName;

            do {
                System.out.println("Search the song by the artist or press Q to exit: ");
                artistName = scanner.nextLine();

                if (artistName.matches("Q")) {
                    valid = false;
                    System.out.println("Goodbye");
                    break;
                }

                else if (songs.searchForArtistName(artistName)) { // If artist exists...

                    Map<String, Song> artistSet = songs.getArtistSet(artistName); // Get "small" map object to search through

                    System.out.println("These are all the songs by this artist. Which one would you like to play?");
                    for (String s : artistSet.keySet()) {
                        System.out.println("- " + s);
                    }

                    boolean askForSong = false;
                    do {
                        String songName = scanner.nextLine();

                        if (!(songs.getSongBool(songName, artistSet))) {
                            System.out.println("You entered an invalid song name " + songName + ". Please try again: ");
                            askForSong = true;

                        } else {

                            try {
                                Song foundSong = songs.getSong(songName, artistSet);
                                System.out.println("Playing the song: " + foundSong.toString());
                                foundSong.play();
                                
                                



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




