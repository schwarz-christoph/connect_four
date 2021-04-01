package edu.hm.se2.connect_four.csgles.datastore;

/**
 * @author Christoph Schwarz, Georg Lang, Enno Scholz
 * @version 04-01-2021
 */

public interface Field {

    /**
     *
     * @return
     */
    int getCoordinateX();

    /**
     *
     * @return
     */
    int getCoordinateY();

    /**
     *
     * @return
     */
    Player owner();
}
