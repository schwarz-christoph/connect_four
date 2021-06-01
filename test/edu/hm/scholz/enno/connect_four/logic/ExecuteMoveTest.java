package edu.hm.scholz.enno.connect_four.logic;

import edu.hm.scholz.enno.connect_four.datastore.Field;
import edu.hm.scholz.enno.connect_four.datastore.PlayerID;
import edu.hm.scholz.enno.connect_four.datastore.mutable.Factory;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullBoard;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullGame;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class ExecuteMoveTest {
    @Rule
    public Timeout globalTimeout = Timeout.millis(1_000);

    @Test
    public void executeMoveTest(){
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        game.setIsStarted(true);
        GameManager manager = LogicFactory.makeGameManager(board, game);

        board.setHighlight(List.of(Factory.makeField(1, 1, PlayerID.NONE),
                Factory.makeField(1, 2, PlayerID.NONE),
                Factory.makeField(1, 3, PlayerID.NONE),
                Factory.makeField(1, 4, PlayerID.NONE),
                Factory.makeField(1, 5, PlayerID.NONE),
                Factory.makeField(1, 6, PlayerID.NONE),
                Factory.makeField(1, 7, PlayerID.NONE)));

        ArrayList<Field> want = new ArrayList<>();
        Collections.addAll(want, Factory.makeField(1, 7, PlayerID.PLAYER_1));

        //act
        manager.executeMove(Move.CONFIRM);

        //assert
        List<Field> have = board.getFields();
        assertEquals(want, have);
    }

    @Test
    public void executeMoveGameNotStartedTest(){
        int xBefore = 1;

        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
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
        manager.executeMove(Move.CONFIRM);

        //assert
        assertFalse(manager.executeMove(Move.CONFIRM));
    }

    @Test
    public void executeMoveNotAllowedMoveTest(){
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        game.setIsStarted(true);
        GameManager manager = LogicFactory.makeGameManager(board, game);
        board.setHighlight(new ArrayList<>(Arrays.asList(Factory.makeField(3, 1, PlayerID.PLAYER_1))));

        //act, assert
        assertFalse(manager.executeMove(Move.DOWN));
    }

    @Test
    public void executeMoveAllowedMoveTest(){
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        game.setIsStarted(true);
        GameManager manager = LogicFactory.makeGameManager(board, game);
        board.setHighlight(new ArrayList<>(Arrays.asList(Factory.makeField(3, 1, PlayerID.PLAYER_1))));

        //assert
        assertTrue(manager.executeMove(Move.CONFIRM));
    }

    @Test
    public void playerInEndScreenNoPlayerWonTest(){
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        game.setIsStarted(true);
        GameManager manager = LogicFactory.makeGameManager(board, game);
        game.setActivePlayer(PlayerID.NONE); //Restart if no Player won the Game

        //act
        final boolean result = manager.executeMove(Move.DOWN);

        //assert
        assertFalse(result);
    }

    @Test
    public void playerInEndScreenRestartGameNoPlayerWonTest(){
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        game.setIsStarted(true);
        GameManager manager = LogicFactory.makeGameManager(board, game);
        game.setActivePlayer(PlayerID.NONE); //Restart if no Player won the Game
        game.setWinner(PlayerID.PLAYER_1); //Restart if one Player won the Game

        //act
        manager.executeMove(Move.CONFIRM);

        //assert
        List<Move> want = List.of(Move.CONFIRM, Move.RIGHT, Move.LEFT); //Moves in Player select screen
        List<Move> have = manager.getMoves(PlayerID.PLAYER_1);
        assertEquals(want, have);
    }

    @Test
    public void playerInEndScreenPlayerWonTest(){
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        game.setIsStarted(true);
        GameManager manager = LogicFactory.makeGameManager(board, game);
        game.setWinner(PlayerID.PLAYER_1); //Restart if one Player won the Game

        //act
        final boolean result = manager.executeMove(Move.DOWN);

        //assert
        assertFalse(result);
    }

    @Test
    public void playerInEndScreenRestartGamePlayerWonTest(){
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        game.setIsStarted(true);
        GameManager manager = LogicFactory.makeGameManager(board, game);
        game.setWinner(PlayerID.PLAYER_1); //Restart if one Player won the Game

        //act
        manager.executeMove(Move.CONFIRM);

        //assert
        List<Move> want = List.of(Move.CONFIRM, Move.RIGHT, Move.LEFT); //Moves in Player select screen
        List<Move> have = manager.getMoves(PlayerID.PLAYER_1);
        assertEquals(want, have);
    }

    @Test
    public void playerSelectTest(){
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        GameManager manager = LogicFactory.makeGameManager(board, game);


        //assert
        List<Move> want = List.of(Move.CONFIRM, Move.RIGHT, Move.LEFT); //Moves in Player select screen
        List<Move> have = manager.getMoves(PlayerID.PLAYER_1);
        assertEquals(want, have);
    }

    @Test
    public void playerSelectAddPlayerTest(){
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        GameManager manager = LogicFactory.makeGameManager(board, game);

        //act
        manager.executeMove(Move.RIGHT);

        //assert
        int want = 2;
        int have = game.getPLayerCount();
        assertEquals(want, have);
    }

    @Test
    public void playerSelectRemovePlayerTest(){
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        GameManager manager = LogicFactory.makeGameManager(board, game);

        game.setPlayerCount(2);

        //act
        manager.executeMove(Move.LEFT);

        //assert
        int want = 1;
        int have = game.getPLayerCount();
        assertEquals(want, have);
    }

    @Test
    public void playerSelectStartGameTest(){
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        GameManager manager = LogicFactory.makeGameManager(board, game);

        //act
        manager.executeMove(Move.CONFIRM);

        //assert
        boolean have = game.isStarted();
        assertTrue(have);
    }

    @Test
    public void playerSelectFalseMoveTest(){
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        GameManager manager = LogicFactory.makeGameManager(board, game);

        //act
        boolean result = manager.executeMove(Move.UP);

        //assert
        assertFalse(result);
    }

    @Test
    public void falseMoveInMatrixTest(){
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);

        game.setIsStarted(true);
        board.setHighlight(List.of(Factory.makeField(0,1, PlayerID.NONE),
                Factory.makeField(0,2, PlayerID.NONE),
                Factory.makeField(0,3, PlayerID.NONE),
                Factory.makeField(0,4, PlayerID.NONE),
                Factory.makeField(0,5, PlayerID.NONE),
                Factory.makeField(0,6, PlayerID.NONE),
                Factory.makeField(0,7, PlayerID.NONE)));

        GameManager manager = LogicFactory.makeGameManager(board, game);

        //act
        boolean result = manager.executeMove(Move.DOWN);

        //assert
        assertFalse(result);
    }

    @Test
    public void playerChangeTest(){
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        game.setIsStarted(true);
        GameManager manager = LogicFactory.makeGameManager(board, game);

        board.setHighlight(List.of(Factory.makeField(1, 1, PlayerID.NONE),
                Factory.makeField(1, 2, PlayerID.NONE),
                Factory.makeField(1, 3, PlayerID.NONE),
                Factory.makeField(1, 4, PlayerID.NONE),
                Factory.makeField(1, 5, PlayerID.NONE),
                Factory.makeField(1, 6, PlayerID.NONE),
                Factory.makeField(1, 7, PlayerID.NONE)));


        //act
        manager.executeMove(Move.CONFIRM);

        //assert
        PlayerID want = PlayerID.PLAYER_2;
        PlayerID have = game.getActivePlayer();
        assertEquals(want, have);
    }

}
