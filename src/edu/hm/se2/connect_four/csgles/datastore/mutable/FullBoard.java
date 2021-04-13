package edu.hm.se2.connect_four.csgles.datastore.mutable;

import edu.hm.se2.connect_four.csgles.datastore.Field;
import edu.hm.se2.connect_four.csgles.datastore.Player;

import java.util.List;

/**
 * @author Christoph Schwarz (schwarz4@hm.edu)
 * @author Georg Lang (glang@hm.edu)
 * @author Enno Scholz (enno.scholz@hm.edu)
 * @version 04-01-2021
 */

public interface FullBoard {

    /**
     * @return All fields of this board as a list.
     */
    List<Field> getFields();

    /**
     * Toggles the stone for the given player.
     * @param player The Player who toggles the stone.
     * @param xCoordinate x coordinate of the field.
     * @param yCooardinate y coordinate of the field.
     */
    void toggleStone(Player player, int xCoordinate, int yCooardinate);


    /**
     * A single field of the board, specified by x and y coordinates.
     * @param xCoordinate x coordinate of the field.
     * @param yCooardinate y coordinate of the field.
     * @return Returns the specified field.
     */
    Field getField(int xCoordinate, int yCooardinate);

    /**
     * Toggle the highlight of the specified field.
     * @param xCoordinate x coordinate of the field.
     * @param yCooardinate y coordinate of the field.
     * @return ???
     */
    List<Field> setHighlight(int xCoordinate, int yCooardinate);
}
