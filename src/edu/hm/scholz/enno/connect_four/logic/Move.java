package edu.hm.scholz.enno.connect_four.logic;

import java.util.HashMap;

/**
 * All the moves that can be made.
 * @author Christoph Schwarz (schwarz4@hm.edu)
 * @author Georg Lang (glang@hm.edu)
 * @author Enno Scholz (enno.scholz@hm.edu)
 * @version 04-01-2021
 */

public enum Move {
    UP, DOWN, LEFT, RIGHT, CONFIRM;

    /**
     * Get a move with a single unique char.
     * @return the move which corresponds to the char
     */
    public char getMoveName() {
        final HashMap<Move, Character> moveNames = new HashMap<>();
        moveNames.put(UP, 'U');
        moveNames.put(DOWN, 'D');
        moveNames.put(LEFT, 'L');
        moveNames.put(RIGHT, 'R');
        moveNames.put(CONFIRM, 'C');
        
        return moveNames.get(this);
    }

    @Override
    public String toString() {
        final HashMap<Move, String> moveDescriptions = new HashMap<>();
        moveDescriptions.put(UP, "Select field above");
        moveDescriptions.put(DOWN, "Select field below");
        moveDescriptions.put(LEFT, "Select left field");
        moveDescriptions.put(RIGHT, "Select right field");
        moveDescriptions.put(CONFIRM, "Confirm selected field");

        return moveDescriptions.get(this);
    }
}
