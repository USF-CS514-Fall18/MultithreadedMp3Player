# Lab 2: Mp3 player
Part 1 (Functionality) Due: Sept 24, 11:59pm

Part 2 (Design + GUI + additional features): Due Oct 1, 11:59pm

For this project, you will implement an mp3 player. You will learn:
- How to use nested maps
- How to do basic file processing in Java using NIO package
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
For part 1, you will write a basic mp3 player with a text-based interface.  Your program should be able to find mp3 files in a directory given by the first command line argument to MPlayer, extract title and artist tags from each mp3 file using an external library called jAudiotagger, create Song objects and store them in a nested TreepMap in SongCollection. 

In a TreeMap, each key is the name of the artist, and the value is a nested TreeMap, where each key is the title of the song written by this artist, and the value is the reference to the corresponding Song. In this project, the user will first search songs by the artist, then by the title - this data structure will make the search efficient. Since it's a TreeMap, all keys will be sorted alphabetically.

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

Your are required to download your own mp3 files and use them for testing your code. amazon has some free mp3 files. Please use only legal sources when downloading music. Note that jaudiotagger library can not recognize mp3 files produces from youtube videos.
