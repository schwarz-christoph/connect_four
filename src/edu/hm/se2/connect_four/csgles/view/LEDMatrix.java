package edu.hm.se2.connect_four.csgles.view;

/**
 * @author Christoph Schwarz, Georg Lang, Enno Scholz
 * @version 04-01-2021
 */

public interface LEDMatrix {

    /**
     *
     */
    void updateCursor();

    /**
     *
     */
    void updateMatrix();

    /**
     *
     */
    void updateWinner();

    /**
     *
     */
    void updatePlayerSelect();
}
