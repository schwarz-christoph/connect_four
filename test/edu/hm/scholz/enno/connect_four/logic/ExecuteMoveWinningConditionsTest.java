package edu.hm.scholz.enno.connect_four.logic;

import edu.hm.scholz.enno.connect_four.datastore.PlayerID;
import edu.hm.scholz.enno.connect_four.datastore.mutable.Factory;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullBoard;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullGame;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ExecuteMoveWinningConditionsTest {

    @Rule
    public Timeout globalTimeout = Timeout.millis(1_000);

    @Test
    public void executeMoveHorizontalRightTest(){

        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        game.setIsStarted(true);
        GameManager manager = LogicFactory.makeGameManager(board, game);

        board.setHighlight(List.of(Factory.makeField(3, 1, PlayerID.NONE),
                Factory.makeField(3, 2, PlayerID.NONE),
                Factory.makeField(3, 3, PlayerID.NONE),
                Factory.makeField(3, 4, PlayerID.NONE),
                Factory.makeField(3, 5, PlayerID.NONE),
                Factory.makeField(3, 6, PlayerID.NONE),
                Factory.makeField(3, 7, PlayerID.NONE)));

        //arrange stones
        board.placeStone(Factory.makeField(0, 7, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(1, 7, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(2, 7, PlayerID.PLAYER_1));

        PlayerID wantWinner = PlayerID.PLAYER_1;

        //act
        manager.executeMove(Move.CONFIRM);
        PlayerID actualWinner = game.getWinner();

        //assert
        assertEquals(wantWinner, actualWinner);
    }

    @Test
    public void executeMoveHorizontalRightTestPlayer2(){

        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_2, board);
        game.setIsStarted(true);
        GameManager manager = LogicFactory.makeGameManager(board, game);

        board.setHighlight(List.of(Factory.makeField(3, 1, PlayerID.NONE),
                Factory.makeField(3, 2, PlayerID.NONE),
                Factory.makeField(3, 3, PlayerID.NONE),
                Factory.makeField(3, 4, PlayerID.NONE),
                Factory.makeField(3, 5, PlayerID.NONE),
                Factory.makeField(3, 6, PlayerID.NONE),
                Factory.makeField(3, 7, PlayerID.NONE)));

        //arrange stones
        board.placeStone(Factory.makeField(0, 7, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(1, 7, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(2, 7, PlayerID.PLAYER_2));

        PlayerID wantWinner = PlayerID.PLAYER_2;

        //act
        manager.executeMove(Move.CONFIRM);
        PlayerID actualWinner = game.getWinner();

        //assert
        assertEquals(wantWinner, actualWinner);
    }

    @Test
    public void executeMoveHorizontalLeftTest(){

        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        game.setIsStarted(true);
        GameManager manager = LogicFactory.makeGameManager(board, game);

        board.setHighlight(List.of(Factory.makeField(0, 1, PlayerID.NONE),
                Factory.makeField(0, 2, PlayerID.NONE),
                Factory.makeField(0, 3, PlayerID.NONE),
                Factory.makeField(0, 4, PlayerID.NONE),
                Factory.makeField(0, 5, PlayerID.NONE),
                Factory.makeField(0, 6, PlayerID.NONE),
                Factory.makeField(0, 7, PlayerID.NONE)));

        //arrange stones
        board.placeStone(Factory.makeField(1, 7, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(2, 7, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(3, 7, PlayerID.PLAYER_1));

        PlayerID wantWinner = PlayerID.PLAYER_1;

        //act
        manager.executeMove(Move.CONFIRM);
        PlayerID actualWinner = game.getWinner();

        //assert
        assertEquals(wantWinner, actualWinner);
    }

    @Test
    public void executeMoveVerticalTest(){

        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        game.setIsStarted(true);
        GameManager manager = LogicFactory.makeGameManager(board, game);

        board.setHighlight(List.of(Factory.makeField(0, 1, PlayerID.NONE),
                Factory.makeField(0, 2, PlayerID.NONE),
                Factory.makeField(0, 3, PlayerID.NONE),
                Factory.makeField(0, 4, PlayerID.NONE),
                Factory.makeField(0, 5, PlayerID.NONE),
                Factory.makeField(0, 6, PlayerID.NONE),
                Factory.makeField(0, 7, PlayerID.NONE)));

        //arrange stones
        board.placeStone(Factory.makeField(0, 7, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(0, 6, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(0, 5, PlayerID.PLAYER_1));

        PlayerID wantWinner = PlayerID.PLAYER_1;

        //act
        manager.executeMove(Move.CONFIRM);
        PlayerID actualWinner = game.getWinner();

        //assert
        assertEquals(wantWinner, actualWinner);
    }

    @Test
    public void executeMoveDiagonalUpperRightTest(){

        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        game.setIsStarted(true);
        GameManager manager = LogicFactory.makeGameManager(board, game);

        board.setHighlight(List.of(Factory.makeField(3, 1, PlayerID.NONE),
                Factory.makeField(3, 2, PlayerID.NONE),
                Factory.makeField(3, 3, PlayerID.NONE),
                Factory.makeField(3, 4, PlayerID.NONE),
                Factory.makeField(3, 5, PlayerID.NONE),
                Factory.makeField(3, 6, PlayerID.NONE),
                Factory.makeField(3, 7, PlayerID.NONE)));

        //arrange stones
        board.placeStone(Factory.makeField(0, 7, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(1, 7, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(1, 6, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(2, 7, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(2, 6, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(2, 5, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(3, 7, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(3, 6, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(3, 5, PlayerID.PLAYER_2));

        PlayerID wantWinner = PlayerID.PLAYER_1;

        //act
        manager.executeMove(Move.CONFIRM);
        PlayerID actualWinner = game.getWinner();

        //assert
        assertEquals(wantWinner, actualWinner);
    }

    @Test
    public void executeMoveDiagonalUpperRightTest2(){

        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        game.setIsStarted(true);
        GameManager manager = LogicFactory.makeGameManager(board, game);

        board.setHighlight(List.of(Factory.makeField(7, 1, PlayerID.NONE),
                Factory.makeField(7, 2, PlayerID.NONE),
                Factory.makeField(7, 3, PlayerID.NONE),
                Factory.makeField(7, 4, PlayerID.NONE),
                Factory.makeField(7, 5, PlayerID.NONE),
                Factory.makeField(7, 6, PlayerID.NONE),
                Factory.makeField(7, 7, PlayerID.NONE)));

        //arrange stones
        board.placeStone(Factory.makeField(4, 7, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(5, 6, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(5, 7, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(6, 5, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(6, 6, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(6, 7, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(7, 5, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(7, 6, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(7, 7, PlayerID.PLAYER_2));

        PlayerID wantWinner = PlayerID.PLAYER_1;

        //act
        manager.executeMove(Move.CONFIRM);
        PlayerID actualWinner = game.getWinner();

        //assert
        assertEquals(wantWinner, actualWinner);
    }

    @Test
    public void executeMoveDiagonalUpperRightTest3(){

        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        game.setIsStarted(true);
        GameManager manager = LogicFactory.makeGameManager(board, game);

        board.setHighlight(List.of(Factory.makeField(7, 1, PlayerID.NONE),
                Factory.makeField(7, 2, PlayerID.NONE),
                Factory.makeField(7, 3, PlayerID.NONE),
                Factory.makeField(7, 4, PlayerID.NONE),
                Factory.makeField(7, 5, PlayerID.NONE),
                Factory.makeField(7, 6, PlayerID.NONE),
                Factory.makeField(7, 7, PlayerID.NONE)));

        //arrange stones
        board.placeStone(Factory.makeField(4, 4, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(4, 5, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(4, 6, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(4, 7, PlayerID.PLAYER_2));

        board.placeStone(Factory.makeField(5, 3, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(5, 4, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(5, 5, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(5, 6, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(5, 7, PlayerID.PLAYER_2));

        board.placeStone(Factory.makeField(6, 2, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(6, 3, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(6, 4, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(6, 5, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(6, 6, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(6, 7, PlayerID.PLAYER_2));

        board.placeStone(Factory.makeField(7, 2, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(7, 3, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(7, 4, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(7, 5, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(7, 6, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(7, 7, PlayerID.PLAYER_1));

        PlayerID wantWinner = PlayerID.PLAYER_1;

        //act
        manager.executeMove(Move.CONFIRM);
        PlayerID actualWinner = game.getWinner();

        //assert
        assertEquals(wantWinner, actualWinner);
    }

    @Test
    public void executeMoveDiagonalUpperRightTest4(){

        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        game.setIsStarted(true);
        GameManager manager = LogicFactory.makeGameManager(board, game);

        board.setHighlight(List.of(Factory.makeField(3, 1, PlayerID.NONE),
                Factory.makeField(3, 2, PlayerID.NONE),
                Factory.makeField(3, 3, PlayerID.NONE),
                Factory.makeField(3, 4, PlayerID.NONE),
                Factory.makeField(3, 5, PlayerID.NONE),
                Factory.makeField(3, 6, PlayerID.NONE),
                Factory.makeField(3, 7, PlayerID.NONE)));

        //arrange stones
        board.placeStone(Factory.makeField(0, 7, PlayerID.PLAYER_1));

        board.placeStone(Factory.makeField(1, 6, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(1, 7, PlayerID.PLAYER_2));

        board.placeStone(Factory.makeField(2, 5, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(2, 6, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(2, 7, PlayerID.PLAYER_2));

        board.placeStone(Factory.makeField(3, 5, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(3, 6, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(3, 7, PlayerID.PLAYER_2));

        PlayerID wantWinner = PlayerID.PLAYER_1;

        //act
        manager.executeMove(Move.CONFIRM);
        PlayerID actualWinner = game.getWinner();

        //assert
        assertEquals(wantWinner, actualWinner);
    }

    @Test
    public void executeMoveDiagonalUpperRightTest5(){

        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        game.setIsStarted(true);
        GameManager manager = LogicFactory.makeGameManager(board, game);

        board.setHighlight(List.of(Factory.makeField(3, 1, PlayerID.NONE),
                Factory.makeField(3, 2, PlayerID.NONE),
                Factory.makeField(3, 3, PlayerID.NONE),
                Factory.makeField(3, 4, PlayerID.NONE),
                Factory.makeField(3, 5, PlayerID.NONE),
                Factory.makeField(3, 6, PlayerID.NONE),
                Factory.makeField(3, 7, PlayerID.NONE)));

        //arrange stones
        board.placeStone(Factory.makeField(0, 4, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(0, 5, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(0, 6, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(0, 7, PlayerID.PLAYER_2));

        board.placeStone(Factory.makeField(1, 3, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(1, 4, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(1, 5, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(1, 6, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(1, 7, PlayerID.PLAYER_2));

        board.placeStone(Factory.makeField(2, 2, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(2, 3, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(2, 4, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(2, 5, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(2, 6, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(2, 7, PlayerID.PLAYER_2));

        board.placeStone(Factory.makeField(3, 2, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(3, 3, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(3, 4, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(3, 5, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(3, 6, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(3, 7, PlayerID.PLAYER_1));

        PlayerID wantWinner = PlayerID.PLAYER_1;

        //act
        manager.executeMove(Move.CONFIRM);
        PlayerID actualWinner = game.getWinner();

        //assert
        assertEquals(wantWinner, actualWinner);
    }

    @Test
    public void executeMoveDiagonalUpperLeftTest(){

        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        game.setIsStarted(true);
        GameManager manager = LogicFactory.makeGameManager(board, game);

        board.setHighlight(List.of(Factory.makeField(0, 1, PlayerID.NONE),
                Factory.makeField(0, 2, PlayerID.NONE),
                Factory.makeField(0, 3, PlayerID.NONE),
                Factory.makeField(0, 4, PlayerID.NONE),
                Factory.makeField(0, 5, PlayerID.NONE),
                Factory.makeField(0, 6, PlayerID.NONE),
                Factory.makeField(0, 7, PlayerID.NONE)));

        //arrange stones
        board.placeStone(Factory.makeField(3, 7, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(2, 7, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(2, 6, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(1, 7, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(1, 6, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(1, 5, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(0, 7, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(0, 6, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(0, 5, PlayerID.PLAYER_2));

        PlayerID wantWinner = PlayerID.PLAYER_1;

        //act
        manager.executeMove(Move.CONFIRM);
        PlayerID actualWinner = game.getWinner();

        //assert
        assertEquals(wantWinner, actualWinner);
    }

    @Test
    public void executeMoveDiagonalUpperLeftTest2(){

        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        game.setIsStarted(true);
        GameManager manager = LogicFactory.makeGameManager(board, game);

        board.setHighlight(List.of(Factory.makeField(0, 1, PlayerID.NONE),
                Factory.makeField(0, 2, PlayerID.NONE),
                Factory.makeField(0, 3, PlayerID.NONE),
                Factory.makeField(0, 4, PlayerID.NONE),
                Factory.makeField(0, 5, PlayerID.NONE),
                Factory.makeField(0, 6, PlayerID.NONE),
                Factory.makeField(0, 7, PlayerID.NONE)));

        //arrange stones
        board.placeStone(Factory.makeField(0, 5, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(0, 6, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(0, 7, PlayerID.PLAYER_2));

        board.placeStone(Factory.makeField(1, 5, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(1, 6, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(1, 7, PlayerID.PLAYER_2));

        board.placeStone(Factory.makeField(2, 6, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(2, 7, PlayerID.PLAYER_2));

        board.placeStone(Factory.makeField(3, 7, PlayerID.PLAYER_1));

        PlayerID wantWinner = PlayerID.PLAYER_1;

        //act
        manager.executeMove(Move.CONFIRM);
        PlayerID actualWinner = game.getWinner();

        //assert
        assertEquals(wantWinner, actualWinner);
    }

    @Test
    public void executeMoveDiagonalUpperLeftTest3(){

        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        game.setIsStarted(true);
        GameManager manager = LogicFactory.makeGameManager(board, game);

        board.setHighlight(List.of(Factory.makeField(0, 1, PlayerID.NONE),
                Factory.makeField(0, 2, PlayerID.NONE),
                Factory.makeField(0, 3, PlayerID.NONE),
                Factory.makeField(0, 4, PlayerID.NONE),
                Factory.makeField(0, 5, PlayerID.NONE),
                Factory.makeField(0, 6, PlayerID.NONE),
                Factory.makeField(0, 7, PlayerID.NONE)));

        //arrange stones
        board.placeStone(Factory.makeField(0, 2, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(0, 3, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(0, 4, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(0, 5, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(0, 6, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(0, 7, PlayerID.PLAYER_2));

        board.placeStone(Factory.makeField(1, 2, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(1, 3, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(1, 4, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(1, 5, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(1, 6, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(1, 7, PlayerID.PLAYER_2));

        board.placeStone(Factory.makeField(2, 3, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(2, 3, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(2, 4, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(2, 5, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(2, 6, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(2, 7, PlayerID.PLAYER_1));


        board.placeStone(Factory.makeField(3, 4, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(3, 5, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(3, 6, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(3, 7, PlayerID.PLAYER_2));

        PlayerID wantWinner = PlayerID.PLAYER_1;

        //act
        manager.executeMove(Move.CONFIRM);
        PlayerID actualWinner = game.getWinner();

        //assert
        assertEquals(wantWinner, actualWinner);
    }

    @Test
    public void executeMoveDiagonalUpperLeftTest4(){

        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        game.setIsStarted(true);
        GameManager manager = LogicFactory.makeGameManager(board, game);

        board.setHighlight(List.of(Factory.makeField(4, 1, PlayerID.NONE),
                Factory.makeField(4, 2, PlayerID.NONE),
                Factory.makeField(4, 3, PlayerID.NONE),
                Factory.makeField(4, 4, PlayerID.NONE),
                Factory.makeField(4, 5, PlayerID.NONE),
                Factory.makeField(4, 6, PlayerID.NONE),
                Factory.makeField(4, 7, PlayerID.NONE)));

        //arrange stones
        board.placeStone(Factory.makeField(4, 2, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(4, 3, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(4, 4, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(4, 5, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(4, 6, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(4, 7, PlayerID.PLAYER_2));


        board.placeStone(Factory.makeField(5, 2, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(5, 3, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(5, 4, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(5, 5, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(5, 6, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(5, 7, PlayerID.PLAYER_2));


        board.placeStone(Factory.makeField(6, 3, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(6, 3, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(6, 4, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(6, 5, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(6, 6, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(6, 7, PlayerID.PLAYER_1));


        board.placeStone(Factory.makeField(7, 4, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(7, 5, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(7, 6, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(7, 7, PlayerID.PLAYER_2));

        PlayerID wantWinner = PlayerID.PLAYER_1;

        //act
        manager.executeMove(Move.CONFIRM);
        PlayerID actualWinner = game.getWinner();

        //assert
        assertEquals(wantWinner, actualWinner);
    }
}
