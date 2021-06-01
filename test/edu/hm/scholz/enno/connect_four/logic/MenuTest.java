package edu.hm.scholz.enno.connect_four.logic;

import edu.hm.scholz.enno.connect_four.datastore.Field;
import edu.hm.scholz.enno.connect_four.datastore.PlayerActiveJoker;
import edu.hm.scholz.enno.connect_four.datastore.PlayerID;
import edu.hm.scholz.enno.connect_four.datastore.mutable.Factory;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullBoard;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullGame;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullPlayer;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MenuTest{
    @Rule
    public Timeout globalTimeout = Timeout.millis(1_000);

    @Test
    public void deleteJokerSelectTest(){
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        game.setIsStarted(true);
        board.setHighlight(List.of(Factory.makeField(1, 0, PlayerID.NONE)));


        FullPlayer player1 = edu.hm.scholz.enno.connect_four.datastore.mutable.Factory.makePlayer(PlayerID.PLAYER_1);
        FullPlayer player2 = edu.hm.scholz.enno.connect_four.datastore.mutable.Factory.makePlayer(PlayerID.PLAYER_2);
        GameManager manager = LogicFactory.makeGameManager(board, game, player1, player2);

        //act
        manager.executeMove(Move.CONFIRM);

        //assert
        PlayerActiveJoker want = PlayerActiveJoker.DELETE;
        PlayerActiveJoker have = game.getActiveJoker();

        assertEquals(want, have);
    }

    @Test
    public void bombJokerSelectTest(){
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        game.setIsStarted(true);
        board.setHighlight(List.of(Factory.makeField(0, 0, PlayerID.NONE)));


        FullPlayer player1 = edu.hm.scholz.enno.connect_four.datastore.mutable.Factory.makePlayer(PlayerID.PLAYER_1);
        FullPlayer player2 = edu.hm.scholz.enno.connect_four.datastore.mutable.Factory.makePlayer(PlayerID.PLAYER_2);
        GameManager manager = LogicFactory.makeGameManager(board, game, player1, player2);

        //act
        manager.executeMove(Move.CONFIRM);

        //assert
        PlayerActiveJoker want = PlayerActiveJoker.BOMB;
        PlayerActiveJoker have = game.getActiveJoker();

        assertEquals(want, have);
    }

    @Test
    public void deleteJokerSelectPlayer1ExecutePlayer2Test(){
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_2, board);
        game.setIsStarted(true);
        board.setHighlight(List.of(Factory.makeField(1, 0, PlayerID.NONE)));


        FullPlayer player1 = edu.hm.scholz.enno.connect_four.datastore.mutable.Factory.makePlayer(PlayerID.PLAYER_1);
        FullPlayer player2 = edu.hm.scholz.enno.connect_four.datastore.mutable.Factory.makePlayer(PlayerID.PLAYER_2);
        GameManager manager = LogicFactory.makeGameManager(board, game, player1, player2);

        //act
        manager.executeMove(Move.CONFIRM);

        //assert
        PlayerActiveJoker want = PlayerActiveJoker.NONE;
        PlayerActiveJoker have = game.getActiveJoker();

        assertEquals(want, have);
    }

    @Test
    public void bombJokerSelectPlayer1ExecutePlayer2Test(){
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_2, board);
        game.setIsStarted(true);
        board.setHighlight(List.of(Factory.makeField(0, 0, PlayerID.NONE)));


        FullPlayer player1 = edu.hm.scholz.enno.connect_four.datastore.mutable.Factory.makePlayer(PlayerID.PLAYER_1);
        FullPlayer player2 = edu.hm.scholz.enno.connect_four.datastore.mutable.Factory.makePlayer(PlayerID.PLAYER_2);
        GameManager manager = LogicFactory.makeGameManager(board, game, player1, player2);

        //act
        manager.executeMove(Move.CONFIRM);

        //assert
        PlayerActiveJoker want = PlayerActiveJoker.NONE;
        PlayerActiveJoker have = game.getActiveJoker();

        assertEquals(want, have);
    }

    @Test
    public void deleteJokerSelectPlayer2Test(){
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_2, board);
        game.setIsStarted(true);
        board.setHighlight(List.of(Factory.makeField(6, 0, PlayerID.NONE)));


        FullPlayer player1 = edu.hm.scholz.enno.connect_four.datastore.mutable.Factory.makePlayer(PlayerID.PLAYER_1);
        FullPlayer player2 = edu.hm.scholz.enno.connect_four.datastore.mutable.Factory.makePlayer(PlayerID.PLAYER_2);
        GameManager manager = LogicFactory.makeGameManager(board, game, player1, player2);

        //act
        manager.executeMove(Move.CONFIRM);

        //assert
        PlayerActiveJoker want = PlayerActiveJoker.DELETE;
        PlayerActiveJoker have = game.getActiveJoker();

        assertEquals(want, have);
    }

    @Test
    public void bombJokerSelectPlayer2Test(){
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_2, board);
        game.setIsStarted(true);
        board.setHighlight(List.of(Factory.makeField(7, 0, PlayerID.NONE)));


        FullPlayer player1 = edu.hm.scholz.enno.connect_four.datastore.mutable.Factory.makePlayer(PlayerID.PLAYER_1);
        FullPlayer player2 = edu.hm.scholz.enno.connect_four.datastore.mutable.Factory.makePlayer(PlayerID.PLAYER_2);
        GameManager manager = LogicFactory.makeGameManager(board, game, player1, player2);

        //act
        manager.executeMove(Move.CONFIRM);

        //assert
        PlayerActiveJoker want = PlayerActiveJoker.BOMB;
        PlayerActiveJoker have = game.getActiveJoker();

        assertEquals(want, have);
    }

    @Test
    public void deleteJokerSelectPlayer2ExecutePlayer1Test(){
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        game.setIsStarted(true);
        board.setHighlight(List.of(Factory.makeField(7, 0, PlayerID.NONE)));


        FullPlayer player1 = edu.hm.scholz.enno.connect_four.datastore.mutable.Factory.makePlayer(PlayerID.PLAYER_1);
        FullPlayer player2 = edu.hm.scholz.enno.connect_four.datastore.mutable.Factory.makePlayer(PlayerID.PLAYER_2);
        GameManager manager = LogicFactory.makeGameManager(board, game, player1, player2);

        //act
        manager.executeMove(Move.CONFIRM);

        //assert
        PlayerActiveJoker want = PlayerActiveJoker.NONE;
        PlayerActiveJoker have = game.getActiveJoker();

        assertEquals(want, have);
    }

    @Test
    public void bombJokerSelectPlayer2ExecutePlayer1Test(){
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        game.setIsStarted(true);
        board.setHighlight(List.of(Factory.makeField(7, 0, PlayerID.NONE)));


        FullPlayer player1 = edu.hm.scholz.enno.connect_four.datastore.mutable.Factory.makePlayer(PlayerID.PLAYER_1);
        FullPlayer player2 = edu.hm.scholz.enno.connect_four.datastore.mutable.Factory.makePlayer(PlayerID.PLAYER_2);
        GameManager manager = LogicFactory.makeGameManager(board, game, player1, player2);

        //act
        manager.executeMove(Move.CONFIRM);

        //assert
        PlayerActiveJoker want = PlayerActiveJoker.NONE;
        PlayerActiveJoker have = game.getActiveJoker();

        assertEquals(want, have);
    }

    @Test
    public void restartTest(){
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        game.setIsStarted(true);
        board.setHighlight(List.of(Factory.makeField(4, 0, PlayerID.NONE)));

        GameManager manager = LogicFactory.makeGameManager(board, game);

        //act
        manager.executeMove(Move.CONFIRM);

        //assert
        List<Move> want = new ArrayList<>(List.of(Move.CONFIRM ,Move.RIGHT, Move.LEFT)); //Moves from Player select screen
        List<Move> have = manager.getMoves(PlayerID.PLAYER_1);
        assertEquals(want, have);
    }

    @Test
    public void endTest(){
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        game.setIsStarted(true);
        board.setHighlight(List.of(Factory.makeField(3, 0, PlayerID.NONE)));

        GameManager manager = LogicFactory.makeGameManager(board, game);

        //act
        manager.executeMove(Move.CONFIRM);

        //assert
        List<Move> want = new ArrayList<>(List.of(Move.CONFIRM)); //Moves from Player select screen
        List<Move> have = manager.getMoves(PlayerID.PLAYER_1);
        assertEquals(want, have);
    }

    @Test
    public void endWinningPlayerTest(){
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        game.setIsStarted(true);
        board.setHighlight(List.of(Factory.makeField(3, 0, PlayerID.NONE)));

        GameManager manager = LogicFactory.makeGameManager(board, game);

        //act
        manager.executeMove(Move.CONFIRM);

        //assert
        PlayerID want = PlayerID.NONE; //Moves from Player select screen
        PlayerID have = game.getWinner();
        assertEquals(want, have);
    }
}
