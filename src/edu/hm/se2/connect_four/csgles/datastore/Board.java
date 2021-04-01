package edu.hm.se2.connect_four.csgles.datastore;

import java.util.List;

/**
 * @author Christoph Schwarz, Georg Lang, Enno Scholz
 * @version 04-01-2021
 */

public interface Board {

    /**
     * @return all occupied Fields
     */
    List<Field> getFields();

    /**
     *
     * @return
     */
    Field getField();

    /**
     * @return fields with the indicator
     */
    List<Field> getHighlight();
}
