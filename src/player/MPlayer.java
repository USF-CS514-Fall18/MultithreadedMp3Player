package player;

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

            // Get from a directory
            // String directory = new dir;
            // sc.loadSongs(directory);
            songs.loadSongs(args[0]); // args have been set to:  dir

            // User Input / Validation section

            Scanner scanner = new Scanner(System.in);
            boolean valid = true; // Made a bool to control the following do-while statement:

            do {
                System.out.println("Welcome to the MP3 Player. Please enter your song title: ");
                String songTitle = scanner.next();




                if (){

                    System.out.println("Song title found. Playing song...");
                    // Play method here

                //}
                else {
                    System.out.println("Song title not found. Try again: ");
                    // Loop again. Check it
                }



            } while (valid);





        }

    }
}
