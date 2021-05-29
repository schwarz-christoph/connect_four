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
public class ExecuteMoveTest {
    @Rule
    public Timeout globalTimeout = Timeout.millis(1_000);

    @Test
    public void executeMoveTest(){
        int xBefore = 1;

        //arrange
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1);
        game.setIsStarted(true);
        FullBoard board = Factory.makeBoard();
        GameManager manager = LogicFactory.makeGameManager(board, game);

        board.setHighlight(List.of(Factory.makeField(xBefore, 1, PlayerID.NONE),
                Factory.makeField(xBefore, 2, PlayerID.NONE),
                Factory.makeField(xBefore, 3, PlayerID.NONE),
                Factory.makeField(xBefore, 4, PlayerID.NONE),
                Factory.makeField(xBefore, 5, PlayerID.NONE),
                Factory.makeField(xBefore, 6, PlayerID.NONE),
                Factory.makeField(xBefore, 7, PlayerID.NONE)));
        ArrayList<Field> want = new ArrayList<>();
        Collections.addAll(want, Factory.makeField(xBefore, 0, PlayerID.PLAYER_1));
        List<Field> have = board.getFields();

        //act
        manager.executeMove(Move.CONFIRM, game.getActivePlayer());

        //assert
        assertEquals(want, have);
    }

    @Test
    public void executeMoveGameNotStartedTest(){
        int xBefore = 1;

        //arrange
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1);
        FullBoard board = Factory.makeBoard();
        GameManager manager = LogicFactory.makeGameManager(board, game);

        board.setHighlight(List.of(Factory.makeField(xBefore, 1, PlayerID.NONE),
                Factory.makeField(xBefore, 2, PlayerID.NONE),
                Factory.makeField(xBefore, 3, PlayerID.NONE),
                Factory.makeField(xBefore, 4, PlayerID.NONE),
                Factory.makeField(xBefore, 5, PlayerID.NONE),
                Factory.makeField(xBefore, 6, PlayerID.NONE),
                Factory.makeField(xBefore, 7, PlayerID.NONE)));
        ArrayList<Field> want = new ArrayList<>();
        Collections.addAll(want, Factory.makeField(xBefore, 0, PlayerID.PLAYER_1));
        List<Field> have = board.getFields();

        //act
        manager.executeMove(Move.CONFIRM, game.getActivePlayer());

        //assert
        assertFalse(manager.executeMove(Move.CONFIRM, game.getActivePlayer()));
    }

    @Test
    public void executeMoveNotAllowedMoveTest(){
        //arrange
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1);
        game.setIsStarted(true);
        FullBoard board = Factory.makeBoard();
        GameManager manager = LogicFactory.makeGameManager(board, game);
        board.setHighlight(new ArrayList<>(Arrays.asList(Factory.makeField(3, 1, PlayerID.PLAYER_1))));

        //act, assert
        assertFalse(manager.executeMove(Move.DOWN, game.getActivePlayer()));
    }

    @Test
    public void executeMoveAllowedMoveTest(){
        //arrange
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1);
        game.setIsStarted(true);
        FullBoard board = Factory.makeBoard();
        GameManager manager = LogicFactory.makeGameManager(board, game);
        board.setHighlight(new ArrayList<>(Arrays.asList(Factory.makeField(3, 1, PlayerID.PLAYER_1))));

        //act, assert
        assertFalse(manager.executeMove(Move.CONFIRM, game.getActivePlayer()));
    }

}
