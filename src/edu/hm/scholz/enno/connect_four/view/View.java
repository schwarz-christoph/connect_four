package edu.hm.scholz.enno.connect_four.view;

import edu.hm.scholz.enno.connect_four.datastore.Observer;

/**
 * A View with the ability to shut down the game
 *
 * @author Christoph Schwarz (schwarz4@hm.edu)
 * @author Georg Lang (glang@hm.edu)
 * @author Enno Scholz (enno.scholz@hm.edu)
 * @version 06-25-2021
 */
public interface View extends Observer {

    /**
     * Close the view
     */
    default void shut() {
    }
}
