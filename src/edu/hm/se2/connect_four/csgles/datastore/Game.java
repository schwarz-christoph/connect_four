package edu.hm.se2.connect_four.csgles.datastore;

/**
 * A game of Connect Four.
 * @author Christoph Schwarz (schwarz4@hm.edu)
 * @author Georg Lang (glang@hm.edu)
 * @author Enno Scholz (enno.scholz@hm.edu)
 * @version 04-01-2021
 */

public interface Game {

    @SuppressWarnings("PMD.CommentRequired")
    Board getBoard();
    @SuppressWarnings("PMD.CommentRequired")
    PlayerID getActivePlayer();
    @SuppressWarnings("PMD.CommentRequired")
    PlayerID getWinner();
    @SuppressWarnings("PMD.CommentRequired")
    boolean isStarted();
    @SuppressWarnings("PMD.CommentRequired")
    int getPLayerCount();
}
