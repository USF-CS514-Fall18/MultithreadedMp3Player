package player;

import javax.swing.*;
import java.awt.*;

/** Driver class for part 2 of lab 2, the mp3 player with the GUI.
 *  Do not use it for part 1. */
public class MPlayerWithGUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame ("Mp3 player");
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        SongCollection songs  = new SongCollection();
        MPlayerPanel panel  = new MPlayerPanel(songs);
        panel.setPreferredSize(new Dimension(600,400));
        frame.add (panel);
        frame.pack();
        frame.setVisible(true);
    }
}
