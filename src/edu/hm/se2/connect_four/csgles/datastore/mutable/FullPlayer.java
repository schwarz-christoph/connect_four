package edu.hm.se2.connect_four.csgles.datastore.mutable;
import edu.hm.se2.connect_four.csgles.datastore.Player;
import edu.hm.se2.connect_four.csgles.datastore.PlayerID;

/**
 * @author Christoph Schwarz, Georg Lang, Enno Scholz
 * @version 04-01-2021
 */

public interface FullPlayer {

    /**
     *
     * @return
     */
    PlayerID getID();

    /**
     *
     * @param player
     * @return
     */
    boolean getUsedBombJoker(Player player);

    /**
     *
     * @param player
     */
    void useBombJoker(Player player);

    /**
     *
     * @param player
     * @return
     */
    boolean getUsedDeleteJoker(Player player);

    /**
     *
     * @param player
     */
    void useDeleteJoker(Player player);
}
