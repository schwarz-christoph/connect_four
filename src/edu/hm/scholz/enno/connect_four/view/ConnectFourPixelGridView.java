package edu.hm.scholz.enno.connect_four.view;

import edu.hm.cs.rs.se2.ui.UI;
import edu.hm.scholz.enno.connect_four.datastore.Board;
import edu.hm.scholz.enno.connect_four.datastore.Game;
import edu.hm.scholz.enno.connect_four.datastore.Player;

public class ConnectFourPixelGridView implements View{

    @Override
    public void update(Board board, Game game, Player player1, Player player2) {

    }

    @Override
    public void shut() {
        View.super.shut();
    }
}
