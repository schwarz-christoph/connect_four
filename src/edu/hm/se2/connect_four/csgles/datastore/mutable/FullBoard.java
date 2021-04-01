package edu.hm.se2.connect_four.csgles.datastore.mutable;

import edu.hm.se2.connect_four.csgles.datastore.Field;

import java.util.List;

/**
 * @author Christoph Schwarz, Georg Lang, Enno Scholz
 * @version 04-01-2021
 */

public interface FullBoard {

    /**
     *
     * @return
     */
    List<Field> getFields();

    /**
     *
     */
    void toggleStone();

    /**
     *
     * @return
     */
    Field getField();

    /**
     *
     * @return
     */
    List<Field> setHighlight();
}
