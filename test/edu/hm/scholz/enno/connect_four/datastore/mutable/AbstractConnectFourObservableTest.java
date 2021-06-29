package edu.hm.scholz.enno.connect_four.datastore.mutable;

import edu.hm.scholz.enno.connect_four.datastore.Board;
import edu.hm.scholz.enno.connect_four.datastore.Game;
import edu.hm.scholz.enno.connect_four.datastore.Player;
import edu.hm.scholz.enno.connect_four.datastore.PlayerID;
import org.junit.Test;

public class AbstractConnectFourObservableTest {

    @Test (expected = IllegalArgumentException.class)
    public void registerPreconditionsTest() {
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1);
        game.register(null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void notifyObserversBoardNull() {
        Game game = Factory.makeGame(PlayerID.PLAYER_1);
        Player player1 = Factory.makePlayer(PlayerID.PLAYER_1);
        Player player2 = Factory.makePlayer(PlayerID.PLAYER_2);
        game.notifyObservers(null, game, player1, player2);
    }

    @Test (expected = IllegalArgumentException.class)
    public void notifyObserversGameNull() {
        Game game = Factory.makeGame(PlayerID.PLAYER_1);
        Board board = Factory.makeBoard();
        Player player1 = Factory.makePlayer(PlayerID.PLAYER_1);
        Player player2 = Factory.makePlayer(PlayerID.PLAYER_2);
        game.notifyObservers(board, null, player1, player2);
    }

    @Test (expected = IllegalArgumentException.class)
    public void notifyObserversPlayer1Null() {
        Game game = Factory.makeGame(PlayerID.PLAYER_1);
        Board board = Factory.makeBoard();
        Player player2 = Factory.makePlayer(PlayerID.PLAYER_2);
        game.notifyObservers(board, game, null, player2);
    }

    @Test (expected = IllegalArgumentException.class)
    public void notifyObserversPlayer2Null() {
        Game game = Factory.makeGame(PlayerID.PLAYER_1);
        Board board = Factory.makeBoard();
        Player player1 = Factory.makePlayer(PlayerID.PLAYER_1);
        game.notifyObservers(board, game, player1, null);
    }
}