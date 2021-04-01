package edu.hm.se2.connect_four.csgles.logic;

import java.util.List;

/**
 * @author Christoph Schwarz, Georg Lang, Enno Scholz
 * @version 04-01-2021
 */

public interface GameManager {

    /**
     *
     * @return
     */
    List<Move> getMoves();

    /**
     *
     */
    void executeMove();
}
