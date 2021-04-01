package edu.hm.se2.connect_four.csgles.datastore;

/**
 * @author Christoph Schwarz, Georg Lang, Enno Scholz
 * @version 04-01-2021
 */

public interface Player {

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
     * @return
     */
    boolean getUsedDeleteJocker(Player player);
}
