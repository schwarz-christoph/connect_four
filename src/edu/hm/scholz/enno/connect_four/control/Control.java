package edu.hm.scholz.enno.connect_four.control;


public interface Control extends Runnable{

    /**
     * Execute a Move
     * @return true if the move was valid and executed, false instead
     */
    boolean step();

    /**
     * Close the controller
     */
    void close();

    /**
     * Tests if the player is still in the game
     * @return true if the player is in the game, false instead
     */
    boolean running();

    /**
     * Starts the controller
     * Returns if the controller ends
     */
    @Override
    default void run() {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
