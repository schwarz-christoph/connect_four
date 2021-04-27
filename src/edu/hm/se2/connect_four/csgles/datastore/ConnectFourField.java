package edu.hm.se2.connect_four.csgles.datastore;

/**
 * A field.
 * @author Christoph Schwarz (schwarz4@hm.edu)
 * @author Georg Lang (glang@hm.edu)
 * @author Enno Scholz (enno.scholz@hm.edu)
 * @param xCoordinate The x coordinate of the field.
 * @param yCoordinate The y coordinate of the field.
 * @param owner The ID of the player that owns this field.
 * @version 04-01-2021
 */

public record ConnectFourField(int xCoordinate, int yCoordinate, PlayerID owner) implements Field{

    public static Field make(int xCoordinate, int yCoordinate, PlayerID owner){
        return new ConnectFourField(xCoordinate, yCoordinate, owner);
    }

}
