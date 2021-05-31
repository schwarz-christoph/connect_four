package edu.hm.scholz.enno.connect_four.logic;

import edu.hm.scholz.enno.connect_four.datastore.PlayerID;
import edu.hm.scholz.enno.connect_four.datastore.mutable.Factory;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullBoard;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullGame;
import static org.junit.Assert.*;
import org.junit.Test;

import java.util.List;

public class RestartEndTest {

    @Test
    public void endTest(){

    }

    @Test
    public void RestartTestIsStarted(){
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        GameManager manager = LogicFactory.makeGameManager(board, game);

        game.setIsStarted(true);
        game.setActivePlayer(PlayerID.PLAYER_2);
        game.setWinner(PlayerID.PLAYER_2);
        game.setPlayerCount(2);

        board.placeStone(Factory.makeField(0, 0, PlayerID.PLAYER_1));

        board.setHighlight(List.of(Factory.makeField(4, 0, PlayerID.NONE)));
        manager.executeMove(Move.CONFIRM);

        //act, assert
        assertFalse(game.isStarted());
    }

    @Test
    public void RestartTestActivePlayer(){
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        GameManager manager = LogicFactory.makeGameManager(board, game);

        game.setIsStarted(true);
        game.setActivePlayer(PlayerID.PLAYER_2);
        game.setWinner(PlayerID.PLAYER_2);
        game.setPlayerCount(2);

        board.placeStone(Factory.makeField(0, 0, PlayerID.PLAYER_1));

        board.setHighlight(List.of(Factory.makeField(4, 0, PlayerID.NONE)));
        manager.executeMove(Move.CONFIRM);

        PlayerID want = PlayerID.PLAYER_1;

        //act
        PlayerID actual = game.getActivePlayer();

        //assert
        assertEquals(want, actual);
    }

    @Test
    public void RestartTestWinner(){
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        GameManager manager = LogicFactory.makeGameManager(board, game);

        game.setIsStarted(true);
        game.setActivePlayer(PlayerID.PLAYER_2);
        game.setWinner(PlayerID.PLAYER_2);
        game.setPlayerCount(2);

        board.placeStone(Factory.makeField(0, 0, PlayerID.PLAYER_1));

        board.setHighlight(List.of(Factory.makeField(4, 0, PlayerID.NONE)));
        manager.executeMove(Move.CONFIRM);

        PlayerID want = PlayerID.NONE;

        //act
        PlayerID actual = game.getWinner();

        //assert
        assertEquals(want, actual);
    }

    @Test
    public void RestartTestPlayerCount(){
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        GameManager manager = LogicFactory.makeGameManager(board, game);

        game.setIsStarted(true);
        game.setActivePlayer(PlayerID.PLAYER_2);
        game.setWinner(PlayerID.PLAYER_2);
        game.setPlayerCount(2);

        board.placeStone(Factory.makeField(0, 0, PlayerID.PLAYER_1));

        board.setHighlight(List.of(Factory.makeField(4, 0, PlayerID.NONE)));
        manager.executeMove(Move.CONFIRM);

        int want = 1;

        //act
        int actual = game.getPLayerCount();

        //assert
        assertEquals(want, actual);
    }

    @Test
    public void RestartTestHighlight(){
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        GameManager manager = LogicFactory.makeGameManager(board, game);

        game.setIsStarted(true);
        game.setActivePlayer(PlayerID.PLAYER_2);
        game.setWinner(PlayerID.PLAYER_2);
        game.setPlayerCount(2);

        board.placeStone(Factory.makeField(0, 0, PlayerID.PLAYER_1));

        board.setHighlight(List.of(Factory.makeField(4, 0, PlayerID.NONE)));
        manager.executeMove(Move.CONFIRM);


        //assert
        assertTrue(board.getHighlight().isEmpty());
    }
    @Test
    public void RestartTestFields(){
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        GameManager manager = LogicFactory.makeGameManager(board, game);

        game.setIsStarted(true);
        game.setActivePlayer(PlayerID.PLAYER_2);
        game.setWinner(PlayerID.PLAYER_2);
        game.setPlayerCount(2);

        board.placeStone(Factory.makeField(0, 0, PlayerID.PLAYER_1));

        board.setHighlight(List.of(Factory.makeField(4, 0, PlayerID.NONE)));
        manager.executeMove(Move.CONFIRM);


        //assert
        assertTrue(board.getFields().isEmpty());
    }

}
