package edu.hm.scholz.enno.connect_four.logic;

import edu.hm.scholz.enno.connect_four.datastore.Field;
import edu.hm.scholz.enno.connect_four.datastore.PlayerID;
import edu.hm.scholz.enno.connect_four.datastore.mutable.Factory;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullBoard;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullGame;
import org.junit.Test;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class HighlightTest {
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
                {7, Move.RIGHT, 1},

                {7, Move.LEFT, 6},
                {6, Move.LEFT, 5},
                {5, Move.LEFT, 4},
                {4, Move.LEFT, 3},
                {3, Move.LEFT, 2},
                {2, Move.LEFT, 1},
                {1, Move.LEFT, 7},
        });
    }

    private final int xBefore;
    private final int xWant;
    private final Move move;

    public HighlightTest(int yBefore, int yWant, Move move){
        this.xBefore = yBefore;
        this.xWant = yWant;
        this.move = move;
    }

    @Test
    public void highlightTest() {
        //arange
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1);
        FullBoard board = Factory.makeBoard();
        board.setHighlight(List.of(Factory.makeField(xBefore, 1, PlayerID.NONE), Factory.makeField(xBefore, 2, PlayerID.NONE), Factory.makeField(xBefore, 3, PlayerID.NONE), Factory.makeField(xBefore, 4, PlayerID.NONE), Factory.makeField(xBefore, 5, PlayerID.NONE), Factory.makeField(xBefore, 6, PlayerID.NONE), Factory.makeField(xBefore, 7, PlayerID.NONE)));
        GameManager manager = LogicFactory.makeGameManager(board, game);
        ArrayList<Field> want = new ArrayList<>();
        Collections.addAll(want, Factory.makeField(xWant, 1, PlayerID.NONE), Factory.makeField(xWant, 2, PlayerID.NONE), Factory.makeField(xWant, 3, PlayerID.NONE), Factory.makeField(xWant, 4, PlayerID.NONE), Factory.makeField(xWant, 5, PlayerID.NONE), Factory.makeField(xWant, 6, PlayerID.NONE), Factory.makeField(xWant, 7, PlayerID.NONE));

        //act
        manager.executeMove(move, game.getActivePlayer());

        //assert
        List<Field> have = board.getHighlight();
        assertEquals(want, have);
    }

    @Test
    public void highlightMenuTest(){
        //arange
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1);
        FullBoard board = Factory.makeBoard();
        board.setHighlight(List.of(Factory.makeField(1, xBefore, PlayerID.NONE), Factory.makeField(2, xBefore, PlayerID.NONE), Factory.makeField(3, xBefore, PlayerID.NONE), Factory.makeField(4, xBefore, PlayerID.NONE), Factory.makeField(5, xBefore, PlayerID.NONE), Factory.makeField(6, xBefore, PlayerID.NONE), Factory.makeField(7, xBefore, PlayerID.NONE)));
        GameManager manager = LogicFactory.makeGameManager(board, game);
        ArrayList<Field> want = new ArrayList<>();
        Collections.addAll(want, Factory.makeField(xWant, 0, PlayerID.NONE));

        //act
        manager.executeMove(Move.UP, game.getActivePlayer());

        //assert
        List<Field> have = board.getHighlight();
        assertEquals(want, have);
    }
}
