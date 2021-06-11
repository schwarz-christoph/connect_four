package edu.hm.scholz.enno.connect_four.view;

import edu.hm.cs.rs.se2.ui.UI;
import edu.hm.scholz.enno.connect_four.datastore.Board;
import edu.hm.scholz.enno.connect_four.datastore.Game;
import edu.hm.scholz.enno.connect_four.datastore.Player;

public class ConnectFourPixelGridView implements View{

    final Board board;
    final Game game;
    final Player player1;
    final Player player2;
    final UI ui;

    public ConnectFourPixelGridView(Board board, Game game, Player player1, Player player2, UI ui) {
        this.board = board;
        this.game = game;
        this.player1 = player1;
        this.player2 = player2;
        this.ui = ui;
    }

    @Override
    public void update(Board board, Game game, Player player1, Player player2) {

    }

    @Override
    public void shut() {
        View.super.shut();
    }
}
