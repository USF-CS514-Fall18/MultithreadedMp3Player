package player;

import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;

import java.util.ArrayList;
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
        }

        // FILL IN CODE
        else {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please enter the directory containing the .mp3 files: ");
            String directory = scanner.nextLine();
            songs.loadSongs(directory); // User should enter : dir (or other directory)

            String artistName;

            while (true) {
                System.out.println(songs);
                System.out.println("Search the song by the artist or press Q to exit: ");
                artistName = scanner.nextLine();

                if (artistName.matches("Q")) {
                    System.out.println("Goodbye");
                    break;

                } else if (songs.artistNameExists(artistName)) { // If artist exists...

                    ArrayList<String> artistList = songs.getArtistList(artistName); // Make an ArrayList of all the names

                    System.out.println("These are all the songs by this artist. Which one would you like to play?");
                    for (String artName : artistList) {
                        ArrayList<String> songList = songs.getSongTitleList(artName);

                        for (String songName : songList) {
                            System.out.println("- " + songName);
                        }
                    }

                    boolean askForSong = false;
                    do {
                        String songName = scanner.nextLine();

                        if (songs.getSong(artistName, songName) == null) {

                            System.out.println("You entered an invalid song name " + songName + ". Please try again: ");
                            askForSong = true;

                        } else {

                            try {
                                String stop;
                                do {
                                    Song foundSong = songs.getSong(artistName, songName);
                                    System.out.println("Playing the song: " + foundSong.toString());
                                    foundSong.play();

                                    System.out.println("To stop, enter 'S'");
                                    Scanner sc = new Scanner(System.in);
                                    stop = sc.nextLine();

                                } while (stop.matches("S"));

                            } catch (NullPointerException e) {
                                System.out.println("Cannot play song.");
                            }
                        }
                    } while (askForSong);
                } else {
                    System.out.println("Artist not found. Try again: ");
                }
            }
        }
    }
}




