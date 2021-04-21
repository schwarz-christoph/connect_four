package edu.hm.se2.connect_four.csgles.datastore;

/**
 * A field.
 * @author Christoph Schwarz (schwarz4@hm.edu)
 * @author Georg Lang (glang@hm.edu)
 * @author Enno Scholz (enno.scholz@hm.edu)
 * @version 04-01-2021
 */

public record ConnectFourField(int xCoordinate, int yCoordinate, Player owner) implements Field{}
