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
     * @param board Reference to datastore object.
     */
    void updateCursor(Board board);

    /**
     * Updates the matrix of the view.
     *
     * @param board  Reference to datastore object.
     * @param player Reference to datastore object.
     */
    void updateMatrix(Board board, Player player);

    /**
     * Updates the winner screen in the view.
     *
     * @param game Reference to datastore object.
     */
    void updateWinner(Game game);

    /**
     * Updates the player select screen of the view.
     *
     * @param game Reference to datastore object.
     */
    void updatePlayerSelect(Game game);
}
