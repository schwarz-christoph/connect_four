package edu.hm.se2.connect_four.csgles.datastore.mutable;
import edu.hm.se2.connect_four.csgles.datastore.Player;
import edu.hm.se2.connect_four.csgles.datastore.PlayerID;

/**
 * @author Christoph Schwarz (schwarz4@hm.edu)
 * @author Georg Lang (glang@hm.edu)
 * @author Enno Scholz (enno.scholz@hm.edu)
 * @version 04-01-2021
 */

public interface FullPlayer {

    /**
     * @return The ID of this player.
     */
    PlayerID getID();

    /**
     * @return If the player used the bomb joker.
     */
    boolean getUsedBombJoker();

    /**
     * Consume the bomb joker of this player.
     */
    void useBombJoker();

    /**
     * @return If the player used the delete joker.
     */
    boolean getUsedDeleteJoker();

    /**
     * Consume the delete Joker of this Player.
     */
    void useDeleteJoker();
}
