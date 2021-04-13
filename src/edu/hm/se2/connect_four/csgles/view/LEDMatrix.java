package edu.hm.se2.connect_four.csgles.view;

/**
 * @author Christoph Schwarz (schwarz4@hm.edu)
 * @author Georg Lang (glang@hm.edu)
 * @author Enno Scholz (enno.scholz@hm.edu)
 * @version 04-01-2021
 */

public interface LEDMatrix {

    /**
     * Triggers update of the cursor position.
     */
    void updateCursor();

    /**
     * Triggers update of the field.
     */
    void updateMatrix();

    /**
     * Triggers update of the game end screen.
     */
    void updateWinner();

    /**
     * Triggers update of the player selection screen.
     */
    void updatePlayerSelect();
}
