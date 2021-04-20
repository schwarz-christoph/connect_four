package edu.hm.se2.connect_four.csgles.datastore;

/**
 * A field with coordinates and owning player.
 * @author Christoph Schwarz (schwarz4@hm.edu)
 * @author Georg Lang (glang@hm.edu)
 * @author Enno Scholz (enno.scholz@hm.edu)
 * @version 04-01-2021
 */

public interface Field {
    @SuppressWarnings("PMD.CommentRequired")
    int xCoordinate();
    @SuppressWarnings("PMD.CommentRequired")
    int yCoordinate();
    @SuppressWarnings("PMD.CommentRequired")
    Player owner();
}
