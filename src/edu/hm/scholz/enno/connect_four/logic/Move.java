package edu.hm.scholz.enno.connect_four.logic;

import java.util.Map;

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
     * A mapping of enum value to move name (character).
     */
    static final Map<Move, Character> MOVE_NAMES = Map.of(UP, 'U', DOWN, 'D', LEFT, 'L', RIGHT, 'R', CONFIRM, 'C');

    /**
     * A mapping of enum value to description (string).
     */
    static final Map<Move, String> MOVE_DESCRIPTIONS = Map.of(
            UP, "Select field above",
            DOWN, "Select field below",
            LEFT, "Select left field",
            RIGHT, "Select right field",
            CONFIRM, "Confirm selected field");

    /**
     * Get a move with a single unique char.
     * @return the move which corresponds to the char
     */
    public char getMoveName() {
        return MOVE_NAMES.get(this);
    }

    @Override
    public String toString() {
        return MOVE_DESCRIPTIONS.get(this);
    }
}
