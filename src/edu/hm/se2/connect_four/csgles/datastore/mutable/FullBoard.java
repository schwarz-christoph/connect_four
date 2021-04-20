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

    List<Field> getFields();

    /**
     * Toggles the stone for the given player.
     * @param player The Player who toggles the stone.
     * @param xCoordinate x coordinate of the field.
     * @param yCooardinate y coordinate of the field.
     */
    void toggleStone(Player player, int xCoordinate, int yCooardinate);
    Field getField(int xCoordinate, int yCooardinate);
    void setHighlight(int xCoordinate, int yCooardinate);
}
