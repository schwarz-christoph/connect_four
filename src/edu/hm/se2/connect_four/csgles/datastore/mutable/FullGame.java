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

    Board getBoard();
    Player getActivePLayer();
    Player getWinner();
    boolean getIsStarted();
    int getPlayerCount();
    void setActivePlayer(Player activePlayer);
    void setWinner(Player winner);
    void setIsStarted(boolean isStarted);
    void setPlayerCount(int playerCount);
}
