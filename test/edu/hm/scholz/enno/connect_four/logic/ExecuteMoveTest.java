package edu.hm.scholz.enno.connect_four.logic;

import edu.hm.scholz.enno.connect_four.TestUtility;
import edu.hm.scholz.enno.connect_four.datastore.Field;
import edu.hm.scholz.enno.connect_four.datastore.Player;
import edu.hm.scholz.enno.connect_four.datastore.PlayerID;
import edu.hm.scholz.enno.connect_four.datastore.mutable.Factory;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullBoard;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullGame;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullPlayer;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class ExecuteMoveTest {
//    @Rule
//    public Timeout globalTimeout = Timeout.millis(1_000);

    @Test(expected = UnsupportedOperationException.class)
    public void createExecuteMoveHandlerExceptionTest() {
        //arrange, act
        ExecuteMoveHandler executeMoveHandler = new ExecuteMoveHandler();
    }

    @Test
    public void executeMoveTest() {
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
    public void executeMoveGameNotStartedTest() {
        int xBefore = 1;

        //arrange
        PlayerID want = PlayerID.PLAYER_1;
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(want, board);
        GameManager manager = LogicFactory.makeGameManager(board, game);

        board.setHighlight(List.of(Factory.makeField(xBefore, 1, PlayerID.NONE),
                Factory.makeField(xBefore, 2, PlayerID.NONE),
                Factory.makeField(xBefore, 3, PlayerID.NONE),
                Factory.makeField(xBefore, 4, PlayerID.NONE),
                Factory.makeField(xBefore, 5, PlayerID.NONE),
                Factory.makeField(xBefore, 6, PlayerID.NONE),
                Factory.makeField(xBefore, 7, PlayerID.NONE)));

        //act
        PlayerID have = manager.executeMove(Move.CONFIRM);

        //assert
        assertEquals(want, have);
    }

    @Test
    public void executeMoveNotAllowedMoveTest() {
        //arrange
        PlayerID want = PlayerID.PLAYER_1;
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(want, board);
        game.setIsStarted(true);
        GameManager manager = LogicFactory.makeGameManager(board, game);
        board.setHighlight(new ArrayList<>(List.of(Factory.makeField(3, 1, PlayerID.PLAYER_1))));

        //act
        PlayerID have = manager.executeMove(Move.DOWN);

        //assert
        assertEquals(want, have);
    }

    @Test
    public void executeMoveAllowedMoveTest() {
        //arrange
        PlayerID want = PlayerID.PLAYER_2;
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        game.setIsStarted(true);
        GameManager manager = LogicFactory.makeGameManager(board, game);
        board.setHighlight(new ArrayList<>(List.of(Factory.makeField(3, 1, PlayerID.PLAYER_1))));

        //act
        PlayerID have = manager.executeMove(Move.CONFIRM);

        //assert
        assertEquals(want, have);
    }

    @Test
    public void playerInEndScreenNoPlayerWonTest() {
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        game.setIsStarted(true);
        GameManager manager = LogicFactory.makeGameManager(board, game);
        game.setActivePlayer(PlayerID.NONE); //Restart if no Player won the Game
        PlayerID want = PlayerID.NONE;

        //act
        final PlayerID have = manager.executeMove(Move.DOWN);

        //assert
        assertEquals(want, have);
    }

    @Test
    public void playerInEndScreenRestartGameNoPlayerWonTest() {
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
        List<Move> want = List.of(Move.CONFIRM); //Moves in regular game
        List<Move> have = manager.getMoves(PlayerID.PLAYER_1);
        assertEquals(want, have);
    }

    @Test
    public void playerInEndScreenPlayerWonTest() {
        //arrange
        PlayerID want = PlayerID.PLAYER_1;
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(want, board);
        game.setIsStarted(true);
        GameManager manager = LogicFactory.makeGameManager(board, game);
        game.setWinner(PlayerID.PLAYER_1); //Restart if one Player won the Game

        //act
        PlayerID have = manager.executeMove(Move.DOWN);

        //assert
        assertEquals(want, have);
    }

    @Test
    public void playerInEndScreenRestartGamePlayerWonTest() {
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        game.setIsStarted(true);
        GameManager manager = LogicFactory.makeGameManager(board, game);
        game.setWinner(PlayerID.PLAYER_1); //Restart if one Player won the Game
        game.setActivePlayer(PlayerID.NONE);

        //act
        manager.executeMove(Move.CONFIRM);

        //assert
        List<Move> want = List.of(Move.CONFIRM); //Moves in regular game
        List<Move> have = manager.getMoves(PlayerID.PLAYER_1);
        assertEquals(want, have);
    }

    @Test
    public void playerSelectTest() {
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
    public void playerSelectAddPlayerTest() {
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
    public void playerSelectRemovePlayerTest() {
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
    public void playerCreateStoneTest() {
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        GameManager manager = LogicFactory.makeGameManager(board, game);

        String afterCreateStone = "........" +
                                  "........" +
                                  "........" +
                                  "........" +
                                  "........" +
                                  "........" +
                                  "..G.....";
        List<Field> want = TestUtility.getOccupiedFieldList(afterCreateStone);

        //act
        manager.executeMove(Move.CONFIRM);
        manager.executeMove(Move.DOWN);
        manager.executeMove(Move.CONFIRM);
        List<Field> actual = board.getFields();

        //assert
        assertEquals(want, actual);
    }

    @Test
    public void playerSelectStartGameTest() {
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
    public void canExecuteMove() {
        //arrange
        PlayerID want = PlayerID.PLAYER_1;
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(want, board);
        GameManager manager = LogicFactory.makeGameManager(board, game);

        //act
        PlayerID have = manager.executeMove(Move.CONFIRM);

        //assert
        assertEquals(want, have);
    }

    @Test
    public void playerSelectFalseMoveTest() {
        //arrange
        PlayerID want = PlayerID.PLAYER_2;
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(want, board);
        GameManager manager = LogicFactory.makeGameManager(board, game);

        //act
        PlayerID have = manager.executeMove(Move.CONFIRM);

        //assert
        assertEquals(want, have);
    }

    @Test
    public void falseMoveInMatrixTest() {
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_2, board);
        PlayerID want = PlayerID.PLAYER_1;

        game.setIsStarted(true);
        board.setHighlight(List.of(Factory.makeField(0, 1, PlayerID.NONE),
                Factory.makeField(0, 2, PlayerID.NONE),
                Factory.makeField(0, 3, PlayerID.NONE),
                Factory.makeField(0, 4, PlayerID.NONE),
                Factory.makeField(0, 5, PlayerID.NONE),
                Factory.makeField(0, 6, PlayerID.NONE),
                Factory.makeField(0, 7, PlayerID.NONE)));

        GameManager manager = LogicFactory.makeGameManager(board, game);

        //act
        PlayerID have = manager.executeMove(Move.CONFIRM);

        //assert
        assertEquals(want, have);
    }

    @Test
    public void playerChangeTest() {
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

    @Test
    public void boardFullTest() {
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        String boardState = ".GGBBGGG" +
                "GGGBBGGG" +
                "BBBGGBBB" +
                "GGGBBGGG" +
                "BBBGGBBB" +
                "GGGBBGGG" +
                "BBBGGBBB";
        TestUtility.createBoardState(board, boardState);
        game.setIsStarted(true);
        GameManager manager = LogicFactory.makeGameManager(board, game);

        board.setHighlight(List.of(Factory.makeField(0, 1, PlayerID.NONE),
                Factory.makeField(0, 2, PlayerID.NONE),
                Factory.makeField(0, 3, PlayerID.NONE),
                Factory.makeField(0, 4, PlayerID.NONE),
                Factory.makeField(0, 5, PlayerID.NONE),
                Factory.makeField(0, 6, PlayerID.NONE),
                Factory.makeField(0, 7, PlayerID.NONE)));

        //act
        manager.executeMove(Move.CONFIRM);

        //assert
        PlayerID want = PlayerID.NONE;
        PlayerID have = game.getActivePlayer();
        assertEquals(want, have);
    }

    @Test
    public void playerDoubleSelectBombJokerTest() {
        //arrange
        FullBoard board = Factory.makeBoard();
        board.setHighlight(List.of(Factory.makeField(0, 0, PlayerID.NONE)));

        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        game.setIsStarted(true);

        FullPlayer player1 = Factory.makePlayer(PlayerID.PLAYER_1);
        player1.useBombJoker();
        FullPlayer player2 = Factory.makePlayer(PlayerID.PLAYER_2);

        GameManager manager = LogicFactory.makeGameManager(board, game, player1, player2);

        //act
        List<Move> have = manager.getMoves(game.getActivePlayer());

        //assert
        List<Move> want = List.of(Move.RIGHT, Move.LEFT, Move.DOWN);
        assertEquals(want, have);
    }

    @Test
    public void playerDoubleSelectDeleteJokerTest() {
        //arrange
        FullBoard board = Factory.makeBoard();
        board.setHighlight(List.of(Factory.makeField(1, 0, PlayerID.NONE)));

        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        game.setIsStarted(true);

        FullPlayer player1 = Factory.makePlayer(PlayerID.PLAYER_1);
        player1.useDeleteJoker();
        FullPlayer player2 = Factory.makePlayer(PlayerID.PLAYER_2);

        GameManager manager = LogicFactory.makeGameManager(board, game, player1, player2);

        //act
        List<Move> have = manager.getMoves(game.getActivePlayer());

        //assert
        List<Move> want = List.of(Move.RIGHT, Move.LEFT, Move.DOWN);
        assertEquals(want, have);
    }
}
