package edu.hm.scholz.enno.connect_four.datastore;

/**
 * An observable with methods to register and notify registered observers.
 *
 * @author Christoph Schwarz (schwarz4@hm.edu)
 * @author Georg Lang (glang@hm.edu)
 * @author Enno Scholz (enno.scholz@hm.edu)
 * @version 04-20-2021
 */

public interface Observable {

    /**
     * To register changes.
     *
     * @param observer The observer to register. Can't be null.
     */
    void register(Observer observer);

    /**
     * Communicates changes of the matrix to the registered observers.
     *
     * @param board   Reference to a board datastore object. Can't be null.
     * @param game    Reference to a game datastore object. Can't be null.
     * @param player1 Reference to a player datastore object. Can't be null.
     * @param player2 Reference to another player datastore object. Can't be null.
     */
    void notifyObservers(Board board, Game game, Player player1, Player player2);
}
