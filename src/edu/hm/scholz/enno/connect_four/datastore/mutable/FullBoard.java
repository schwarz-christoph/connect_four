package edu.hm.scholz.enno.connect_four.datastore.mutable;

import edu.hm.scholz.enno.connect_four.datastore.Field;
import edu.hm.scholz.enno.connect_four.datastore.Board;

import java.util.List;

/**
 * A modifiable board.
 * @author Christoph Schwarz (schwarz4@hm.edu)
 * @author Georg Lang (glang@hm.edu)
 * @author Enno Scholz (enno.scholz@hm.edu)
 * @version 04-01-2021
 */

public interface FullBoard extends Board {

    /**
     * Place a stone in the game.
     * @param field Where to place the stone. Can't be null.
     */
    void placeStone(Field field);

    /**
     * Remove a stone from the game.
     * @param field Has to be in the list of Stones and can't be null.
     */
    void removeStone(Field field);

    /**
     * Set the highlight of the game.
     * @param field Which fields to highlight. Can't be null.
     */
    void setHighlight(List<Field> field);
}
