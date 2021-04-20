package edu.hm.se2.connect_four.csgles.datastore;

import java.util.List;

/**
 * A board with highlight and fields.
 * @author Christoph Schwarz (schwarz4@hm.edu)
 * @author Georg Lang (glang@hm.edu)
 * @author Enno Scholz (enno.scholz@hm.edu)
 * @version 04-01-2021
 */

public interface Board {

    @SuppressWarnings("PMD.CommentRequired")
    List<Field> getFields();
    @SuppressWarnings("PMD.CommentRequired")
    List<Field> getHighlight();
}
