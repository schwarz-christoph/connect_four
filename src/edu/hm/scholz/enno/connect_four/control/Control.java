package edu.hm.scholz.enno.connect_four.control;

import edu.hm.scholz.enno.connect_four.datastore.Player;

public interface Control {

    boolean running();

    void step();

    void close();
}
