package edu.hm.scholz.enno.connect_four.logic;


import edu.hm.scholz.enno.connect_four.datastore.PlayerID;
import edu.hm.scholz.enno.connect_four.datastore.mutable.Factory;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullBoard;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullGame;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullPlayer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ExecuteMoveTest.class, GetMovesMenuTest.class, HighlightTest.class, GetMovesRegularTest.class})
public class ConnectFourManagerTest {

    @Test(expected = NullPointerException.class)
    public void ConnectFourManagerNullBoard(){
        //arrange
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1);
        GameManager manager = LogicFactory.makeGameManager(null, game);
    }

    @Test(expected = NullPointerException.class)
    public void ConnectFourManagerNullGame(){
        //arrange
        FullBoard board = Factory.makeBoard();
        GameManager manager = LogicFactory.makeGameManager(board, null);
    }

    @Test(expected = NullPointerException.class)
    public void ConnectFourManagerPlayer_2NUll(){
        FullPlayer player1 = Factory.makePlayer(PlayerID.PLAYER_1);
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1);

        //Act
        ConnectFourManager gameManager = new ConnectFourManager(board, game, player1, null);
    }

    @Test(expected = NullPointerException.class)
    public void ConnectFourManagerPlayer_1NUll(){
        FullPlayer player2 = Factory.makePlayer(PlayerID.PLAYER_1);
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1);

        //Act
        ConnectFourManager gameManager = new ConnectFourManager(board, game, null, player2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ConnectFourManagerBothPlayerSameID(){
        FullPlayer player1 = Factory.makePlayer(PlayerID.PLAYER_1);
        FullPlayer player2 = Factory.makePlayer(PlayerID.PLAYER_1);
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1);

        //Act
        ConnectFourManager gameManager = new ConnectFourManager(board, game, player1, player2);
    }

    @Test
    public void ConnectFourManagerCorrect(){
        FullPlayer player1 = Factory.makePlayer(PlayerID.PLAYER_1);
        FullPlayer player2 = Factory.makePlayer(PlayerID.PLAYER_2);
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1);

        //Act
        ConnectFourManager gameManager = new ConnectFourManager(board, game, player1, player2);
    }
}