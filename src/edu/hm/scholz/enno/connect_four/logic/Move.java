package edu.hm.scholz.enno.connect_four.logic;

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
        final char moveChar;
        moveChar = switch (this) {
            case UP -> 'U';
            case DOWN -> 'D';
            case LEFT -> 'L';
            case RIGHT -> 'R';
            default -> 'C';
        };
        
        return moveChar;
    }

    @Override
    public String toString() {
        final String moveDescription;
        moveDescription = switch (this) {
            case UP -> "Select field above";
            case DOWN ->  "Select field below";
            case LEFT ->  "Select left field";
            case RIGHT ->  "Select right field";
            default ->  "Confirm selected field"; //Confirm
        };

        return moveDescription;
    }
}
