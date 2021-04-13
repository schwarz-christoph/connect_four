package edu.hm.se2.connect_four.csgles.datastore;

import java.util.List;

/**
 * @author Christoph Schwarz (schwarz4@hm.edu)
 * @author Georg Lang (glang@hm.edu)
 * @author Enno Scholz (enno.scholz@hm.edu)
 * @version 04-01-2021
 */

public interface Board {

    /**
     * @return All fields of this board as a list.
     */
    List<Field> getFields();

    /**
     * A single field of the board, specified by x and y coordinates.
     * @param xCoordinate x coordinate of the field.
     * @param yCooardinate y coordinate of the field.
     * @return Returns the specified field.
     */
    Field getField(int xCoordinate, int yCooardinate);

    /**
     * The highlighted fields as a list. Can be 1 or multiple.
     * @return Highlighted fields.
     */
    List<Field> getHighlight();
}
