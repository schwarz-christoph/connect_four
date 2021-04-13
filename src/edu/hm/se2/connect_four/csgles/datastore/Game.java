package edu.hm.se2.connect_four.csgles.datastore;

/**
 * @author Christoph Schwarz (schwarz4@hm.edu)
 * @author Georg Lang (glang@hm.edu)
 * @author Enno Scholz (enno.scholz@hm.edu)
 * @version 04-01-2021
 */

public interface Game {
    /**
     * @return The board of the game.
     */
    Board getBoard();

    /**
     * @return All active players.
     */
    Player getActivePlayer();

    /**
     * @return The winner of the game.
     */
    Player getWinner();

    /**
     * @return If the game has started.
     */
    boolean getIsStarted();

    /**
     * @return The amount of non-machine players.
     */
    int getPLayerCount();
}
