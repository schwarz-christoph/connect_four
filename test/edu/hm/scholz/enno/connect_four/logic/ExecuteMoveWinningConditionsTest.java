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
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ExecuteMoveWinningConditionsTest {
    @Rule
    public Timeout globalTimeout = Timeout.millis(1_000);

    public void executeMoveHorizontalRightTest(){

        //arrange
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1);
        game.setIsStarted(true);
        FullBoard board = Factory.makeBoard();
        GameManager manager = LogicFactory.makeGameManager(board, game);

        board.setHighlight(List.of(Factory.makeField(3, 1, PlayerID.PLAYER_1)));

        //arrange stones
        board.placeStone(Factory.makeField(0, 7, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(1, 7, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(2, 7, PlayerID.PLAYER_1));

        PlayerID wantWinner = PlayerID.PLAYER_1;

        //act
        manager.executeMove(Move.CONFIRM, game.getActivePlayer());
        PlayerID actualWinner = game.getWinner();

        //assert
        assertEquals(wantWinner, actualWinner);
    }

    public void executeMoveHorizontalLeftTest(){

        //arrange
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1);
        game.setIsStarted(true);
        FullBoard board = Factory.makeBoard();
        GameManager manager = LogicFactory.makeGameManager(board, game);

        board.setHighlight(List.of(Factory.makeField(0, 1, PlayerID.PLAYER_1)));

        //arrange stones
        board.placeStone(Factory.makeField(1, 7, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(2, 7, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(3, 7, PlayerID.PLAYER_1));

        PlayerID wantWinner = PlayerID.PLAYER_1;

        //act
        manager.executeMove(Move.CONFIRM, game.getActivePlayer());
        PlayerID actualWinner = game.getWinner();

        //assert
        assertEquals(wantWinner, actualWinner);
    }

    public void executeMoveVerticalTest(){

        //arrange
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1);
        game.setIsStarted(true);
        FullBoard board = Factory.makeBoard();
        GameManager manager = LogicFactory.makeGameManager(board, game);

        board.setHighlight(List.of(Factory.makeField(0, 1, PlayerID.PLAYER_1)));

        //arrange stones
        board.placeStone(Factory.makeField(0, 0, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(0, 1, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(0, 2, PlayerID.PLAYER_1));

        PlayerID wantWinner = PlayerID.PLAYER_1;

        //act
        manager.executeMove(Move.CONFIRM, game.getActivePlayer());
        PlayerID actualWinner = game.getWinner();

        //assert
        assertEquals(wantWinner, actualWinner);
    }

    public void executeMoveDiagonalUpperRightTest(){

        //arrange
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1);
        game.setIsStarted(true);
        FullBoard board = Factory.makeBoard();
        GameManager manager = LogicFactory.makeGameManager(board, game);

        board.setHighlight(List.of(Factory.makeField(3, 1, PlayerID.PLAYER_1)));

        //arrange stones
        board.placeStone(Factory.makeField(0, 0, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(1, 0, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(1, 1, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(2, 0, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(2, 1, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(2, 2, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(3, 0, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(3, 1, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(3, 2, PlayerID.PLAYER_2));

        PlayerID wantWinner = PlayerID.PLAYER_1;

        //act
        manager.executeMove(Move.CONFIRM, game.getActivePlayer());
        PlayerID actualWinner = game.getWinner();

        //assert
        assertEquals(wantWinner, actualWinner);
    }

    public void executeMoveDiagonalUpperLeftTest(){

        //arrange
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1);
        game.setIsStarted(true);
        FullBoard board = Factory.makeBoard();
        GameManager manager = LogicFactory.makeGameManager(board, game);

        board.setHighlight(List.of(Factory.makeField(0, 1, PlayerID.PLAYER_1)));

        //arrange stones
        board.placeStone(Factory.makeField(3, 0, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(2, 0, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(2, 1, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(1, 0, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(1, 1, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(1, 2, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(0, 0, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(0, 1, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(0, 2, PlayerID.PLAYER_2));

        PlayerID wantWinner = PlayerID.PLAYER_1;

        //act
        manager.executeMove(Move.CONFIRM, game.getActivePlayer());
        PlayerID actualWinner = game.getWinner();

        //assert
        assertEquals(wantWinner, actualWinner);
    }
}