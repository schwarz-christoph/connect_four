package edu.hm.scholz.enno.connect_four.view;

import edu.hm.scholz.enno.connect_four.datastore.Board;
import edu.hm.scholz.enno.connect_four.datastore.Game;
import edu.hm.scholz.enno.connect_four.datastore.Observer;
import edu.hm.scholz.enno.connect_four.datastore.Player;


public interface View extends Observer {

    /**
     * Close the view
     */
    default void shut() {

    }

    static View make(Game game, Board board, Player player1, Player player2){
        throw new UnsupportedOperationException("Ned benutzen du depp");
    }
}
