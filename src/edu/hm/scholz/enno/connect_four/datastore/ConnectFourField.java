package edu.hm.scholz.enno.connect_four.datastore;

import edu.hm.scholz.enno.connect_four.common.Settings;

/**
 * A field.
 *
 * @param xCoordinate The x coordinate of the field.
 * @param yCoordinate The y coordinate of the field.
 * @param owner       The ID of the player that owns this field.
 * @author Christoph Schwarz (schwarz4@hm.edu)
 * @author Georg Lang (glang@hm.edu)
 * @author Enno Scholz (enno.scholz@hm.edu)
 * @version 04-01-2021
 */

public record ConnectFourField(int xCoordinate, int yCoordinate, PlayerID owner) implements Field {
    public ConnectFourField {
        if(xCoordinate < 0 || xCoordinate > Settings.fieldSize)
            throw new IllegalArgumentException("xCoordinate can't be less than 0 or greater than FieldSize.");

        if(yCoordinate < 0 || yCoordinate > Settings.fieldSize)
            throw new IllegalArgumentException("yCoordinate can't be less than 0 or greater than FieldSize.");

        if(owner == null)
            throw new IllegalArgumentException("Owner can't be null.");
    }
}
