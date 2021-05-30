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

    public char getMoveName() {
        final char moveChar;
        switch (this) {
            case UP -> moveChar = 'U';
            case DOWN -> moveChar = 'D';
            case LEFT -> moveChar = 'L';
            case RIGHT -> moveChar = 'R';
            case CONFIRM -> moveChar = 'C';
            default -> moveChar = '?';
        }
        return moveChar;
    }

    @Override
    public String toString() {
        final String moveDescription;
        switch (this) {
            case UP -> moveDescription = "Select field above";
            case DOWN -> moveDescription = "Select field below";
            case LEFT -> moveDescription = "Select left field";
            case RIGHT -> moveDescription = "Select right field";
            case CONFIRM -> moveDescription = "Confirm selected field";
            default -> moveDescription = "Unknown move";
        }
        return moveDescription;
    }
}
