package edu.hm.scholz.enno.connect_four.datastore;

/**
 * An observer with methods to update a view.
 *
 * @author Christoph Schwarz (schwarz4@hm.edu)
 * @author Georg Lang (glang@hm.edu)
 * @author Enno Scholz (enno.scholz@hm.edu)
 * @version 04-20-2021
 */

public interface Observer {
    /**
     * Updates the entire view according to the supplied datastore objects.
     *
     * @param board Reference to board datastore object. Can't be null.
     * @param game Reference to game datastore object. Can't be null.
     * @param player1 Reference to a player datastore object. Can't be null.
     * @param player2 Reference to another player datastore object. Can't be null.
     */
    void update(Board board, Game game, Player player1, Player player2);
}
