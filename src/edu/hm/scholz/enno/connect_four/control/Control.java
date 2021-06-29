package edu.hm.scholz.enno.connect_four.control;

/**
 * Controller that handles the moves.
 *
 * @author Christoph Schwarz (schwarz4@hm.edu)
 * @author Georg Lang (glang@hm.edu)
 * @author Enno Scholz (enno.scholz@hm.edu)
 * @version 06-25-2021
 */
public interface Control extends Runnable {

    /**
     * Executes moves until the turn ends.
     */
    void step();

    /**
     * Close the controller.
     */
    void close();

    /**
     * Tests if the player is still in the game.
     *
     * @return true if the player is in the game, false instead.
     */
    boolean running();
}
