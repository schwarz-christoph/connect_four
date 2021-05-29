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
        game.setIsStarted(true);
        FullBoard board = Factory.makeBoard();
        GameManager manager = LogicFactory.makeGameManager(board, game);
        board.setHighlight(new ArrayList<>(Arrays.asList(Factory.makeField(0, 1, PlayerID.PLAYER_1))));

        List<Move> expectedMoves = new ArrayList<>(Arrays.asList(Move.CONFIRM, Move.UP,  Move.RIGHT, Move.LEFT));

        //act
        List<Move> actualMoves = manager.getMoves(PlayerID.PLAYER_1);

        //assert
        assertEquals(expectedMoves, actualMoves);
    }

    public void getMovesRegularLeftBorderFull() {
        //arrange
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1);
        game.setIsStarted(true);
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
        game.setIsStarted(true);
        FullBoard board = Factory.makeBoard();
        GameManager manager = LogicFactory.makeGameManager(board, game);
        board.setHighlight(new ArrayList<>(Arrays.asList(Factory.makeField(7, 1, PlayerID.PLAYER_1))));

        List<Move> expectedMoves = new ArrayList<>(Arrays.asList(Move.CONFIRM, Move.UP,  Move.RIGHT, Move.LEFT));

        //act
        List<Move> actualMoves = manager.getMoves(PlayerID.PLAYER_1);

        //assert
        assertEquals(expectedMoves, actualMoves);
    }

    @Test
    public void getMovesRegularRightBorderFull() {
        //arrange
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1);
        game.setIsStarted(true);
        FullBoard board = Factory.makeBoard();
        GameManager manager = LogicFactory.makeGameManager(board, game);
        board.setHighlight(new ArrayList<>(Arrays.asList(Factory.makeField(7, 1, PlayerID.PLAYER_1))));

        List<Move> expectedMoves = new ArrayList<>(Arrays.asList(Move.CONFIRM, Move.UP,  Move.RIGHT, Move.LEFT));

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
        game.setIsStarted(true);
        FullBoard board = Factory.makeBoard();
        GameManager manager = LogicFactory.makeGameManager(board, game);
        board.setHighlight(new ArrayList<>(Arrays.asList(Factory.makeField(3, 1, PlayerID.PLAYER_1))));

        List<Move> expectedMoves = new ArrayList<>(Arrays.asList(Move.CONFIRM, Move.UP,  Move.RIGHT, Move.LEFT));

        //act
        List<Move> actualMoves = manager.getMoves(PlayerID.PLAYER_1);

        //assert
        assertEquals(expectedMoves, actualMoves);
    }

    @Test
    public void getMovesRegularNormalMiddleFull() {
        //arrange
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1);
        game.setIsStarted(true);
        FullBoard board = Factory.makeBoard();
        GameManager manager = LogicFactory.makeGameManager(board, game);
        board.setHighlight(new ArrayList<>(Arrays.asList(Factory.makeField(3, 1, PlayerID.PLAYER_1))));

        List<Move> expectedMoves = new ArrayList<>(Arrays.asList(Move.CONFIRM, Move.UP,  Move.RIGHT, Move.LEFT));

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
        game.setIsStarted(true);
        FullBoard board = Factory.makeBoard();
        GameManager manager = LogicFactory.makeGameManager(board, game);
        board.setHighlight(new ArrayList<>(Arrays.asList(Factory.makeField(4, 4, PlayerID.PLAYER_1))));

        //act
        List<Move> actualMoves = manager.getMoves(PlayerID.PLAYER_1);
    }

    @Test
    public void getMovesInPlayerSelect(){
        //arrange
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1);
        FullBoard board = Factory.makeBoard();
        GameManager manager = LogicFactory.makeGameManager(board, game);

        //act
        List<Move> have = manager.getMoves(PlayerID.PLAYER_1);

        //assert
        List<Move> want = new ArrayList<>(Arrays.asList(Move.CONFIRM, Move.RIGHT, Move.LEFT));
        assertEquals(want, have);
    }

    @Test
    public void getMovesWhilePlayerWonTest(){
        //arrange
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1);
        FullBoard board = Factory.makeBoard();
        game.setIsStarted(true);
        GameManager manager = LogicFactory.makeGameManager(board, game);

        game.setWinner(PlayerID.PLAYER_2);
        //act
        List<Move> have = manager.getMoves(PlayerID.PLAYER_1);

        //assert
        List<Move> want = new ArrayList<>(Arrays.asList(Move.CONFIRM));
        assertEquals(want, have);
    }
}