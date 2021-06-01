package edu.hm.scholz.enno.connect_four.logic;

import edu.hm.scholz.enno.connect_four.datastore.PlayerID;
import edu.hm.scholz.enno.connect_four.datastore.mutable.Factory;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullBoard;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullGame;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class GetMovesPlayerSelectAndEndScreenTest {
    @Rule
    public Timeout globalTimeout = Timeout.millis(1_000);

    @Test
    public void getMovesEndScreen(){
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        game.setIsStarted(true);
        game.setWinner(PlayerID.PLAYER_1);
        GameManager manager = LogicFactory.makeGameManager(board, game);

        List<Move> expectedMoves = new ArrayList<>(List.of(Move.CONFIRM));

        //act
        List<Move> actualMoves = manager.getMoves(PlayerID.PLAYER_1);

        //assert
        assertEquals(expectedMoves, actualMoves);
    }

    @Test
    public void getMovesPlayerSelect(){
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        game.setIsStarted(false);

        GameManager manager = LogicFactory.makeGameManager(board, game);

        List<Move> expectedMoves = new ArrayList<>(List.of(Move.CONFIRM, Move.RIGHT, Move.LEFT));

        //act
        List<Move> actualMoves = manager.getMoves(PlayerID.PLAYER_1);

        //assert
        assertEquals(expectedMoves, actualMoves);
    }
}
