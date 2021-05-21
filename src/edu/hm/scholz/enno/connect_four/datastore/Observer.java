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
     * Updates the cursor of the view.
     *
     * @param board Reference to datastore object. Can't be null.
     */
    void updateCursor(Board board);

    /**
     * Updates the matrix of the view.
     *
     * @param board  Reference to datastore object. Can't be null.
     * @param player Reference to datastore object. Can't be null.
     */
    void updateMatrix(Board board, Player player);

    /**
     * Updates the winner screen in the view.
     *
     * @param game Reference to datastore object. Can't be null.
     */
    void updateWinner(Game game);

    /**
     * Updates the player select screen of the view.
     *
     * @param game Reference to datastore object. Can't be null.
     */
    void updatePlayerSelect(Game game);
}
