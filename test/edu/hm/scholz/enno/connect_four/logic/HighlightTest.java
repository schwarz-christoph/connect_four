package edu.hm.scholz.enno.connect_four.logic;

import edu.hm.scholz.enno.connect_four.datastore.Field;
import edu.hm.scholz.enno.connect_four.datastore.PlayerID;
import edu.hm.scholz.enno.connect_four.datastore.mutable.Factory;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullBoard;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullGame;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class HighlightTest {
    @Rule
    public Timeout globalTimeout = Timeout.millis(1_000);

    @Parameterized.Parameters(name = "Field from Y = {0} with Move.{1} to Y = {2}")
    public static Iterable<Object[]> testcasesHighlightTest(){
        return Arrays.asList(new Object[][]{
                {0, Move.RIGHT, 1},
                {1, Move.RIGHT, 2},
                {2, Move.RIGHT, 3},
                {3, Move.RIGHT, 4},
                {4, Move.RIGHT, 5},
                {5, Move.RIGHT, 6},
                {6, Move.RIGHT, 7},
                {7, Move.RIGHT, 0},

                {7, Move.LEFT, 6},
                {6, Move.LEFT, 5},
                {5, Move.LEFT, 4},
                {4, Move.LEFT, 3},
                {3, Move.LEFT, 2},
                {2, Move.LEFT, 1},
                {1, Move.LEFT, 0},
                {0, Move.LEFT, 7},
        });
    }

    private final int xBefore;
    private final int xWant;
    private final Move move;

    public HighlightTest(int yBefore, Move move, int yWant){
        this.xBefore = yBefore;
        this.xWant = yWant;
        this.move = move;
    }

    @Test
    public void highlightTest() {
        //arange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        game.setIsStarted(true);
        board.setHighlight(List.of(Factory.makeField(xBefore, 1, PlayerID.NONE), Factory.makeField(xBefore, 2, PlayerID.NONE), Factory.makeField(xBefore, 3, PlayerID.NONE), Factory.makeField(xBefore, 4, PlayerID.NONE), Factory.makeField(xBefore, 5, PlayerID.NONE), Factory.makeField(xBefore, 6, PlayerID.NONE), Factory.makeField(xBefore, 7, PlayerID.NONE)));
        GameManager manager = LogicFactory.makeGameManager(board, game);
        ArrayList<Field> want = new ArrayList<>();
        Collections.addAll(want, Factory.makeField(xWant, 1, PlayerID.NONE), Factory.makeField(xWant, 2, PlayerID.NONE), Factory.makeField(xWant, 3, PlayerID.NONE), Factory.makeField(xWant, 4, PlayerID.NONE), Factory.makeField(xWant, 5, PlayerID.NONE), Factory.makeField(xWant, 6, PlayerID.NONE), Factory.makeField(xWant, 7, PlayerID.NONE));

        //act
        manager.executeMove(move);

        //assert
        List<Field> have = board.getHighlight();
        assertEquals(want, have);
    }

    @Test
    public void highlightInMenuTest() {
        //arange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        game.setIsStarted(true);
        board.setHighlight(List.of(Factory.makeField(xBefore, 1, PlayerID.NONE), Factory.makeField(xBefore, 2, PlayerID.NONE), Factory.makeField(xBefore, 3, PlayerID.NONE), Factory.makeField(xBefore, 4, PlayerID.NONE), Factory.makeField(xBefore, 5, PlayerID.NONE), Factory.makeField(xBefore, 6, PlayerID.NONE), Factory.makeField(xBefore, 7, PlayerID.NONE)));
        GameManager manager = LogicFactory.makeGameManager(board, game);
        ArrayList<Field> want = new ArrayList<>();
        Collections.addAll(want, Factory.makeField(xBefore, 0, PlayerID.NONE));

        //act
        manager.executeMove(Move.UP);

        //assert
        List<Field> have = board.getHighlight();
        assertEquals(want, have);
    }

    @Test
    public void highlightOutMenuTest() {
        //arange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        game.setIsStarted(true);
        board.setHighlight(List.of(Factory.makeField(xBefore, 0, PlayerID.NONE)));
        GameManager manager = LogicFactory.makeGameManager(board, game);
        ArrayList<Field> want = new ArrayList<>();
        Collections.addAll(want, Factory.makeField(xBefore, 1, PlayerID.NONE), Factory.makeField(xBefore, 2, PlayerID.NONE), Factory.makeField(xBefore, 3, PlayerID.NONE), Factory.makeField(xBefore, 4, PlayerID.NONE), Factory.makeField(xBefore, 5, PlayerID.NONE), Factory.makeField(xBefore, 6, PlayerID.NONE), Factory.makeField(xBefore, 7, PlayerID.NONE));

        //act
        manager.executeMove(Move.DOWN);

        //assert
        List<Field> have = board.getHighlight();
        assertEquals(want, have);
    }

    @Test
    public void highlightMenuTest() {
        //arange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        game.setIsStarted(true);
        board.setHighlight(List.of(Factory.makeField(xBefore, 0, PlayerID.NONE)));
        GameManager manager = LogicFactory.makeGameManager(board, game);
        ArrayList<Field> want = new ArrayList<>();
        Collections.addAll(want, Factory.makeField(xWant, 0, PlayerID.NONE));

        //act
        manager.executeMove(move);

        //assert
        List<Field> have = board.getHighlight();
        assertEquals(want, have);
    }


    @Test
    public void highlightDoNotingTest() {
        //arange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        game.setIsStarted(true);
        board.setHighlight(List.of(Factory.makeField(xBefore, 1, PlayerID.NONE), Factory.makeField(xBefore, 2, PlayerID.NONE), Factory.makeField(xBefore, 3, PlayerID.NONE), Factory.makeField(xBefore, 4, PlayerID.NONE), Factory.makeField(xBefore, 5, PlayerID.NONE), Factory.makeField(xBefore, 6, PlayerID.NONE), Factory.makeField(xBefore, 7, PlayerID.NONE)));
        GameManager manager = LogicFactory.makeGameManager(board, game);
        ArrayList<Field> want = new ArrayList<>();
        Collections.addAll(want, Factory.makeField(xBefore, 1, PlayerID.NONE), Factory.makeField(xBefore, 2, PlayerID.NONE), Factory.makeField(xBefore, 3, PlayerID.NONE), Factory.makeField(xBefore, 4, PlayerID.NONE), Factory.makeField(xBefore, 5, PlayerID.NONE), Factory.makeField(xBefore, 6, PlayerID.NONE), Factory.makeField(xBefore, 7, PlayerID.NONE));

        //act
        manager.executeMove(Move.DOWN);

        //assert
        List<Field> have = board.getHighlight();
        assertEquals(want, have);
    }

    @Test
    public void highlightMenuDoNothingTest() {
        //arange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        game.setIsStarted(true);
        board.setHighlight(List.of(Factory.makeField(xBefore, 0, PlayerID.NONE)));
        GameManager manager = LogicFactory.makeGameManager(board, game);
        ArrayList<Field> want = new ArrayList<>();
        Collections.addAll(want, Factory.makeField(xBefore, 0, PlayerID.NONE));

        //act
        manager.executeMove(Move.UP);

        //assert
        List<Field> have = board.getHighlight();
        assertEquals(want, have);
    }

}
