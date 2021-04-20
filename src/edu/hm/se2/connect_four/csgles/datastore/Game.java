package edu.hm.se2.connect_four.csgles.datastore;

/**
 * @author Christoph Schwarz (schwarz4@hm.edu)
 * @author Georg Lang (glang@hm.edu)
 * @author Enno Scholz (enno.scholz@hm.edu)
 * @version 04-01-2021
 */

public interface Game {

    Board getBoard();
    Player getActivePlayer();
    Player getWinner();
    boolean getIsStarted();
    int getPLayerCount();
}
