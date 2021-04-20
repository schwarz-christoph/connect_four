package edu.hm.se2.connect_four.csgles.datastore.mutable;
import edu.hm.se2.connect_four.csgles.datastore.Player;

/**
 * A modifiable player.
 * @author Christoph Schwarz (schwarz4@hm.edu)
 * @author Georg Lang (glang@hm.edu)
 * @author Enno Scholz (enno.scholz@hm.edu)
 * @version 04-01-2021
 */

public interface FullPlayer extends Player {

    @SuppressWarnings("PMD.CommentRequired")
    void useBombJoker();
    @SuppressWarnings("PMD.CommentRequired")
    void useDeleteJoker();
}
