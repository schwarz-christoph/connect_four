package edu.hm.se2.connect_four.csgles.logic;

import java.util.List;

/**
 * @author Christoph Schwarz (schwarz4@hm.edu)
 * @author Georg Lang (glang@hm.edu)
 * @author Enno Scholz (enno.scholz@hm.edu)
 * @version 04-01-2021
 */

public interface GameManager {

    List<Move> getMoves();

    /**
     * Executes the provided move.
     * @param move Move to execute.
     */
    void executeMove(Move move);
}
