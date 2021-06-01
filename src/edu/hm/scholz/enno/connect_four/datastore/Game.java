package edu.hm.scholz.enno.connect_four.datastore;

/**
 * A game of Connect Four.
 *
 * @author Christoph Schwarz (schwarz4@hm.edu)
 * @author Georg Lang (glang@hm.edu)
 * @author Enno Scholz (enno.scholz@hm.edu)
 * @version 04-01-2021
 */

public interface Game extends Observable {

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

    @SuppressWarnings("PMD.CommentRequired")
    PlayerActiveJoker getActiveJoker();
}
