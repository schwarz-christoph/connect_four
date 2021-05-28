package edu.hm.scholz.enno.connect_four.logic;

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

public class GetMovesMenuTest {
    @Rule
    public Timeout globalTimeout = Timeout.millis(1_000);

    @Parameterized.Parameters(name = "Field on (X = {0} Y = {1}), Player: {2}, Want Moves: {3}")
    public static Iterable<Object[]> testCasesGetMovesMenuTest(){
        return Arrays.asList(new Object[][]{
                {0, 0, PlayerID.PLAYER_1,
                        new ArrayList<>(Arrays.asList(Move.RIGHT, Move.LEFT, Move.DOWN, Move.CONFIRM))},
                {0, 0, PlayerID.PLAYER_2,
                        new ArrayList<>(Arrays.asList(Move.RIGHT, Move.LEFT, Move.DOWN))},
                {1, 0, PlayerID.PLAYER_1,
                        new ArrayList<>(Arrays.asList(Move.RIGHT, Move.LEFT, Move.DOWN, Move.CONFIRM))},
                {1, 0, PlayerID.PLAYER_2,
                        new ArrayList<>(Arrays.asList(Move.RIGHT, Move.LEFT, Move.DOWN))},
                {2, 0, PlayerID.PLAYER_1,
                        new ArrayList<>(Arrays.asList(Move.RIGHT, Move.LEFT, Move.DOWN))},
                {3, 0, PlayerID.PLAYER_1,
                        new ArrayList<>(Arrays.asList(Move.RIGHT, Move.LEFT, Move.DOWN, Move.CONFIRM))},
                {4, 0, PlayerID.PLAYER_1,
                        new ArrayList<>(Arrays.asList(Move.RIGHT, Move.LEFT, Move.DOWN, Move.CONFIRM))},
                {5, 0, PlayerID.PLAYER_1,
                        new ArrayList<>(Arrays.asList(Move.RIGHT, Move.LEFT, Move.DOWN))},
                {6, 0, PlayerID.PLAYER_2,
                        new ArrayList<>(Arrays.asList(Move.RIGHT, Move.LEFT, Move.DOWN, Move.CONFIRM))},
                {6, 0, PlayerID.PLAYER_1,
                        new ArrayList<>(Arrays.asList(Move.RIGHT, Move.LEFT, Move.DOWN))},
                {7, 0, PlayerID.PLAYER_2,
                        new ArrayList<>(Arrays.asList(Move.RIGHT, Move.LEFT, Move.DOWN, Move.CONFIRM))},
                {6, 0, PlayerID.PLAYER_1,
                        new ArrayList<>(Arrays.asList(Move.RIGHT, Move.LEFT, Move.DOWN))},

        });


    }

    private final int xCoordinate;
    private final int yCoordinate;
    private final PlayerID owner;
    private final List<Move> possibleMoves;

    public GetMovesMenuTest(int xCoordinate, int yCoordinate, PlayerID owner, List<Move> possibleMoves){
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
        board.setHighlight(new ArrayList<>(Arrays.asList(Factory.makeField(xCoordinate, yCoordinate, owner))));

        List<Move> expectedMoves =possibleMoves;

        //act
        List<Move> actualMoves = manager.getMoves(owner);

        //assert
        assertEquals(expectedMoves, actualMoves);
    }


}
