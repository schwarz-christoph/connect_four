package edu.hm.se2.connect_four.csgles.datastore;

public interface Game {
    /**
     *
     * @return
     */
    Board getBoard();

    /**
     *
     * @return
     */
    Player getActivePlayer();

    /**
     *
     * @return
     */
    Player getWinner();

    /**
     *
     * @return
     */
    boolean getIsStarted();

    /**
     *
     * @return
     */
    int getPLayerCount();
}
