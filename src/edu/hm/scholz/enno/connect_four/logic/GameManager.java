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
     * The allowed Moves.
     * @param playerID PlayerID of the player you want the moves from
     * @return List of all possible moves in the current turn.
     */
    List<Move> getMoves(PlayerID playerID);

    /**
     * Executes the provided move, updates the datastore, notifies the observers and returns the next active player.
     * @param move Move to execute.
     * @return The next active player. Can be the same player as before the turn.
     */
    PlayerID executeMove(Move move);
}
