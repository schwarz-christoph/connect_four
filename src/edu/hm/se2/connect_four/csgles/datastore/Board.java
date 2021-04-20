package edu.hm.se2.connect_four.csgles.datastore;

import java.util.List;

/**
 * @author Christoph Schwarz (schwarz4@hm.edu)
 * @author Georg Lang (glang@hm.edu)
 * @author Enno Scholz (enno.scholz@hm.edu)
 * @version 04-01-2021
 */

public interface Board {

    List<Field> getFields();
    Field getField(int xCoordinate, int yCooardinate);
    List<Field> getHighlight();
}
