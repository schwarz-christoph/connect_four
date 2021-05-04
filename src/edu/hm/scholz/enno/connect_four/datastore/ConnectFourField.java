package edu.hm.scholz.enno.connect_four.datastore;

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
}
