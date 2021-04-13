package edu.hm.se2.connect_four.csgles.datastore.mutable;

import edu.hm.se2.connect_four.csgles.datastore.Board;
import edu.hm.se2.connect_four.csgles.datastore.Player;

/**
 * @author Christoph Schwarz (schwarz4@hm.edu)
 * @author Georg Lang (glang@hm.edu)
 * @author Enno Scholz (enno.scholz@hm.edu)
 * @version 04-01-2021
 */

public interface FullGame {

    /**
     * @return The board of the game.
     */
    Board getBoard();

    /**
     * @return The active player
     */
    Player getActivePLayer();

    /**
     * @return The winner of the whole game
     */
    Player getWinner();

    /**
     * @return The player which starts the game
     */
    boolean getIsStarted();

    /**
     *
     * @return The amount of non-machine players.
     */
    int getPlayerCount();

    /**
     * @param activePlayer The player whose turn it is.
     */
    void setActivePlayer(Player activePlayer);

    /**
     * @param winner The player who won the game. Can be None when the game ends in a tie.
     */
    void setWinner(Player winner);

    /**
     * @param isStarted Whether the game is started or not.
     * @return ???
     */
    boolean setIsStarted(boolean isStarted);

    /**
     * @param playerCount Amount of non-machine players.
     * @return ???
     */
    int setPlayerCount(int playerCount);
}
