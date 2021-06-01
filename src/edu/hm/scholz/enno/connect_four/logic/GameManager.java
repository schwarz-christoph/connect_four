package edu.hm.scholz.enno.connect_four.logic;

import edu.hm.scholz.enno.connect_four.datastore.PlayerID;
import java.util.List;

/**
 * A game manager.
 * @author Christoph Schwarz (schwarz4@hm.edu)
 * @author Georg Lang (glang@hm.edu)
 * @author Enno Scholz (enno.scholz@hm.edu)
 * @version 04-01-2021
 */

public interface GameManager {

    /**
     * The allowed Moves
     * @return List of all possible moves in the current turn.
     */
    List<Move> getMoves(PlayerID playerID);

    /**
     * Executes the provided move, updates the datastore and notifies the observers.
     * @param move Move to execute.
     * @return if the move was successfully.
     */
    boolean executeMove(Move move);
}
