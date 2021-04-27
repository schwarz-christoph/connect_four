package edu.hm.se2.connect_four.csgles.datastore;

/**
 * A Player with ID and joker usage.
 *
 * @author Christoph Schwarz (schwarz4@hm.edu)
 * @author Georg Lang (glang@hm.edu)
 * @author Enno Scholz (enno.scholz@hm.edu)
 * @version 04-01-2021
 */

public interface Player {

    @SuppressWarnings("PMD.CommentRequired")
    PlayerID getIdentifier();

    @SuppressWarnings("PMD.CommentRequired")
    boolean isBombJokerUsed();

    @SuppressWarnings("PMD.CommentRequired")
    boolean isDeleteJokerUsed();
}
