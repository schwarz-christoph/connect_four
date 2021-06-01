package edu.hm.scholz.enno.connect_four.logic;

import edu.hm.scholz.enno.connect_four.datastore.PlayerID;
import edu.hm.scholz.enno.connect_four.datastore.mutable.Factory;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullBoard;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullGame;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullPlayer;
import org.junit.Test;

public class ConnectFourManagerConstructorTest {

    @Test(expected = IllegalArgumentException.class)
    public void ConnectFourManagerNullBoard(){
        //arrange
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1);
        GameManager manager = LogicFactory.makeGameManager(null, game);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ConnectFourManagerNullGame(){
        //arrange
        FullBoard board = Factory.makeBoard();
        GameManager manager = LogicFactory.makeGameManager(board, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ConnectFourManagerPlayer_2NUll(){
        FullPlayer player1 = Factory.makePlayer(PlayerID.PLAYER_1);
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);

        //act
        ConnectFourManager gameManager = new ConnectFourManager(board, game, player1, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ConnectFourManagerPlayer_1NUll(){
        FullPlayer player2 = Factory.makePlayer(PlayerID.PLAYER_1);
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);

        //act
        ConnectFourManager gameManager = new ConnectFourManager(board, game, null, player2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ConnectFourManagerBothPlayerSameID(){
        FullPlayer player1 = Factory.makePlayer(PlayerID.PLAYER_1);
        FullPlayer player2 = Factory.makePlayer(PlayerID.PLAYER_1);
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);

        //act
        ConnectFourManager gameManager = new ConnectFourManager(board, game, player1, player2);
    }

    @Test
    public void ConnectFourManagerCorrect(){
        FullPlayer player1 = Factory.makePlayer(PlayerID.PLAYER_1);
        FullPlayer player2 = Factory.makePlayer(PlayerID.PLAYER_2);
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);

        //act
        ConnectFourManager gameManager = new ConnectFourManager(board, game, player1, player2);
    }
}
