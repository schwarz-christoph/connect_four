package edu.hm.scholz.enno.connect_four.logic;

import edu.hm.scholz.enno.connect_four.datastore.Field;
import edu.hm.scholz.enno.connect_four.datastore.Player;
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

public class GetMovesTest {
    @Rule
    public Timeout globalTimeout = Timeout.millis(1_000);

    @Parameterized.Parameters(name = "Field from Y = {0} with Move.{1} to Y = {2}")
    public static Iterable<Object[]> testCasesGetMovesTest(){
        return Arrays.asList(new Object[][]{
                {0, 0, PlayerID.PLAYER_1,
                        new ArrayList<>(Arrays.asList(Move.RIGHT, Move.LEFT, Move.CONFIRM, Move.CONFIRM))},
                {}
        });


    }

    private final int xCoordinate;
    private final int yCoordinate;
    private final PlayerID owner;
    private final List<Move> possibleMoves;

    public GetMovesTest(int xCoordinate, int yCoordinate, PlayerID owner, List<Move> possibleMoves){
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.owner = owner;
        this.possibleMoves = possibleMoves;
    }

    @Test
    public void getMovesInMenuTestAsPlayer_1() {
        //arrange
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1);
        FullBoard board = Factory.makeBoard();
        GameManager manager = LogicFactory.makeGameManager(board, game);
        board.setHighlight(new ArrayList<>(Arrays.asList(Factory.makeField(0, 0, PlayerID.PLAYER_1))));

        List<Move> expectedMoves = new ArrayList<>(Arrays.asList(Move.RIGHT, Move.LEFT, Move.DOWN, Move.CONFIRM));

        //act
        List<Move> actualMoves = manager.getMoves(PlayerID.PLAYER_1);

        //assert
        assertEquals(expectedMoves, actualMoves);
    }


}
