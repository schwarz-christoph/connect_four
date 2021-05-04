package edu.hm.scholz.enno.connect_four.datastore.mutable;

import edu.hm.scholz.enno.connect_four.datastore.Field;
import edu.hm.scholz.enno.connect_four.datastore.Board;

import java.util.List;

/**
 * A modifiable board.
 * @author Christoph Schwarz (schwarz4@hm.edu)
 * @author Georg Lang (glang@hm.edu)
 * @author Enno Scholz (enno.scholz@hm.edu)
 * @version 04-01-2021
 */

public interface FullBoard extends Board {

    @SuppressWarnings("PMD.CommentRequired")
    void placeStone(Field field);
    @SuppressWarnings("PMD.CommentRequired")
    void removeStone(Field field);
    @SuppressWarnings("PMD.CommentRequired")
    void setHighlight(List<Field> field);
}
