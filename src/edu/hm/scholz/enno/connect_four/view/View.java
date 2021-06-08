package edu.hm.scholz.enno.connect_four.view;

import edu.hm.scholz.enno.connect_four.datastore.Observer;


public interface View extends Observer {

    /**
     * Close the view
     */
    default void shut(){

    }
}
