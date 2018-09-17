# Lab 2: Mp3 player
Part 1 (Functionality) Due: Sept 24, 11:59pm

Part 2 (Design + GUI + additional features): Due Oct 1, 11:59pm

Click on the following link to get starter code for this project: https://classroom.github.com/a/MaJO1mn_

For this project, you will implement an mp3 player. You will learn:
- How to use TreeMap and nested maps
- How to do basic file processing in Java using nio package
- How to use external libraries
- How to design your program according to principles of OOP design
- How to integrate your program with the GUI provided by the instructor

Note: You must submit part 1 of the lab no later than Sep 24. After you complete part 1 and submit it to github, you have to schedule a code review 
with the instructor or one of the TAs. During the code review:
- you will demo your fully functional text-based mp3 player, 
- you will be asked questions about your code and 
- you will be told what to improve in Part 2 of the lab. You will also be assigned a grade for part 1 of the lab. 
Failure to answer questions about the code or reproduce small parts of it during the code review, may result in a 0 for the assignment.

After you pass the code review for part 1, you may start working on part 2 of the lab. In part 2, you are expected to make changes to your design and style
as suggested by the instructor or the TA during your code review of part 1, as well as add additional features and integrate your program with the GUI provided by the instructor (see below for details).
Part 2 will be graded mostly offline, although some students might be invited for the second code review.

## Part 1
For part 1, you will write a basic mp3 player with a text-based interface (the driver class for part 1 is MPlayer).  Your program should be able to find mp3 files in a directory given by the first command line argument to MPlayer, extract title and artist tags from each mp3 file using an external library called jAudiotagger (see below), create Song objects and store them in a nested TreepMap in SongCollection. 

### SongCollection
This class will store all songs in a nested TreeMap. In a TreeMap, each key is the name of the artist, and the value is a nested TreeMap, where each key is the title of the song written by this artist, and the value is the reference to the corresponding Song. In this project, the user will first search songs by the artist, then by the title - this data structure will make the search efficient. Since it's a TreeMap, all keys will be sorted alphabetically. This class should also contain methods to manipulate the collection of songs - it is up to you to determine what these methods are.

### User Interface
The program should allow the user to search for songs based on the artist, and then based on the title, and play the selected song using the JavaZOOM JLayer library.  Here is a sample run:

Search the song by the artist or press Q to exit: 

Taylor Swift

These are all the songs by this artist. Which one would you like to play?

I Knew You Were Trouble.

Love Story

You Belong With Me



Love Story

Playing the song: Love Story by Taylor Swift

Search the song by the artist or press Q to exit: 

Q

### Extracting title and artist tags from the mp3 file
Given an mp3 file, use jaudiotagger library to extract an artist and a title from it. Example:

AudioFile f = AudioFileIO.read(new File("file.mp3"));

Tag tag = f.getTag();

String artist = tag.getFirst(FieldKey.ARTIST);

### Playing a song
We can use JLayer library for playing mp3 files from your Java program. To play an mp3 file using JLayer library, you need to create a Player object (this class is in JLayer library) from the given mp3 file and invoke the play method to begin playback:

Player player = new Player(new FileInputStream(filename));
player.play();

The close method stops playback. The user will not be able to interact with the mp3 player, while the song is playing. In Part 2 of the lab, you will improve the user experience, by allowing the user to interact with the interface even while the song is playing.

Your are required to download your own mp3 files and use them for testing your code. amazon has some free mp3 files. Please use only legal sources when downloading music. Note that jaudiotagger library can not recognize mp3 files generated from youtube videos.

## Part 2
You may start working on part 2 only after you pass the code review for part 1. In Part 2, you need to  (a) improve your design as suggested during the code review, (b) add several features to the program (see below) and (c) integrate your program with the GUI provided by the instructor (see below).

### Features of part 2
In addition to all features of part 1, you need to add the following features to your program:

- your code should be able to recursively find mp3 files in a given directory
- you should be able to efficiently search for songs by an artist whose name starts with the given keyword (Ex: I should be able to type Tayl instead of Taylor Swift)
- selected song should be playing in a separate thread, so that the user could continue interacting with the program;
- the user should be able to stop the song.
- your program should be intergrated with the GUI provided by the instructor (see below)

### GUI (class MPlayerPanel)
The driver class for part 2 is MPlayerWithGui. It uses MPlayerPanel class written by the instructor. You need to fill in code in several places of this class and add additional methods in SongCollection class. The instructor will post a pdf that will describe this part in detail using snapshots of GUI.

You can generate JavaDoc documentation for the project by selecting the lab, and clicking Tools->Generate JavaDoc.

## Submission requirements
To be able to move onto part 2, you need to complete part 1, push it to github before Sep 24 and demonstrate a fully functional text-based mp3 player to the instructor during the code review (see above). You need to have at least 8 commits for each part of this lab, otherwise you may not get any credit for it. 

Assignment is to be completed individually. You may not search the web to help you with this assignment; not even for partial solutions, however tiny; you may not receive help from your classmates, family or friends; this is considered cheating in this class. You are expected to complete it using only Java 8 API, project description, instructor's starter code and JavaDoc for the project, as well our lectures and code examples. Coming to the instructor, the TAs or USF CS tutors for help is always allowed for any assignment.
