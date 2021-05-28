package edu.hm.scholz.enno.connect_four.logic;

import edu.hm.scholz.enno.connect_four.datastore.Field;
import edu.hm.scholz.enno.connect_four.datastore.PlayerID;
import edu.hm.scholz.enno.connect_four.datastore.mutable.Factory;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullBoard;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullGame;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;


public class GetMovesRegularTest {
    @Rule
    public Timeout globalTimeout = Timeout.millis(1_000);

    @Test
    public void getMovesRegularLeftBorderEmpty() {
        //arrange
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1);
        FullBoard board = Factory.makeBoard();
        GameManager manager = LogicFactory.makeGameManager(board, game);
        board.setHighlight(new ArrayList<>(Arrays.asList(Factory.makeField(0, 1, PlayerID.PLAYER_1))));

        List<Move> expectedMoves = new ArrayList<>(Arrays.asList(Move.LEFT, Move.RIGHT, Move.UP, Move.CONFIRM));

        //act
        List<Move> actualMoves = manager.getMoves(PlayerID.PLAYER_1);

        //assert
        assertEquals(expectedMoves, actualMoves);
    }

    public void getMovesRegularLeftBorderFull() {
        //arrange
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1);
        FullBoard board = Factory.makeBoard();
        GameManager manager = LogicFactory.makeGameManager(board, game);
        board.setHighlight(new ArrayList<>(Arrays.asList(Factory.makeField(0, 1, PlayerID.PLAYER_1))));

        List<Move> expectedMoves = new ArrayList<>(Arrays.asList(Move.LEFT, Move.RIGHT, Move.UP));

        //Place One Stone at the top just for simulating a full column
        board.placeStone(Factory
                .makeField(0, 1, PlayerID.PLAYER_1));

        //act
        List<Move> actualMoves = manager.getMoves(PlayerID.PLAYER_1);

        //assert
        assertEquals(expectedMoves, actualMoves);
    }

    @Test
    public void getMovesRegularRightBorderEmpty() {
        //arrange
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1);
        FullBoard board = Factory.makeBoard();
        GameManager manager = LogicFactory.makeGameManager(board, game);
        board.setHighlight(new ArrayList<>(Arrays.asList(Factory.makeField(7, 1, PlayerID.PLAYER_1))));

        List<Move> expectedMoves = new ArrayList<>(Arrays.asList(Move.LEFT, Move.RIGHT, Move.UP, Move.CONFIRM));

        //act
        List<Move> actualMoves = manager.getMoves(PlayerID.PLAYER_1);

        //assert
        assertEquals(expectedMoves, actualMoves);
    }

    @Test
    public void getMovesRegularRightBorderFull() {
        //arrange
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1);
        FullBoard board = Factory.makeBoard();
        GameManager manager = LogicFactory.makeGameManager(board, game);
        board.setHighlight(new ArrayList<>(Arrays.asList(Factory.makeField(7, 1, PlayerID.PLAYER_1))));

        List<Move> expectedMoves = new ArrayList<>(Arrays.asList(Move.LEFT, Move.RIGHT, Move.UP));

        //Place One Stone at the top just for simulating a full column
        board.placeStone(Factory
                .makeField(7, 1, PlayerID.PLAYER_1));

        //act
        List<Move> actualMoves = manager.getMoves(PlayerID.PLAYER_1);

        //assert
        assertEquals(expectedMoves, actualMoves);
    }

    @Test
    public void getMovesRegularNormalMiddleEmpty() {
        //arrange
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1);
        FullBoard board = Factory.makeBoard();
        GameManager manager = LogicFactory.makeGameManager(board, game);
        board.setHighlight(new ArrayList<>(Arrays.asList(Factory.makeField(3, 1, PlayerID.PLAYER_1))));

        List<Move> expectedMoves = new ArrayList<>(Arrays.asList(Move.LEFT, Move.RIGHT, Move.UP, Move.CONFIRM));

        //act
        List<Move> actualMoves = manager.getMoves(PlayerID.PLAYER_1);

        //assert
        assertEquals(expectedMoves, actualMoves);
    }

    @Test
    public void getMovesRegularNormalMiddleFull() {
        //arrange
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1);
        FullBoard board = Factory.makeBoard();
        GameManager manager = LogicFactory.makeGameManager(board, game);
        board.setHighlight(new ArrayList<>(Arrays.asList(Factory.makeField(3, 1, PlayerID.PLAYER_1))));

        List<Move> expectedMoves = new ArrayList<>(Arrays.asList(Move.LEFT, Move.RIGHT, Move.UP));

        //Place One Stone at the top just for simulating a full column
        board.placeStone(Factory
                .makeField(3, 1, PlayerID.PLAYER_1));

        //act
        List<Move> actualMoves = manager.getMoves(PlayerID.PLAYER_1);

        //assert
        assertEquals(expectedMoves, actualMoves);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getNotImplementedMove(){
        //arrange
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1);
        FullBoard board = Factory.makeBoard();
        GameManager manager = LogicFactory.makeGameManager(board, game);
        board.setHighlight(new ArrayList<>(Arrays.asList(Factory.makeField(4, 4, PlayerID.PLAYER_1))));

        //act
        List<Move> actualMoves = manager.getMoves(PlayerID.PLAYER_1);
    }
}