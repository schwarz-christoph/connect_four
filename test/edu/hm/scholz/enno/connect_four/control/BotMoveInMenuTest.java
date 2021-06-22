package edu.hm.scholz.enno.connect_four.control;

import edu.hm.scholz.enno.connect_four.TestUtility;
import edu.hm.scholz.enno.connect_four.datastore.Game;
import edu.hm.scholz.enno.connect_four.datastore.PlayerID;
import edu.hm.scholz.enno.connect_four.datastore.mutable.Factory;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullBoard;
import edu.hm.scholz.enno.connect_four.logic.Move;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class BotMoveInMenuTest {

    @Test
    public void botInMenuTest_X0(){

        //arrange
        List<Move> result;
        List<Move> wantMoves = List.of(Move.DOWN, Move.LEFT, Move.LEFT, Move.CONFIRM);
        BotMove botMove = BotMove.BOT_COLUMN_0;
        FullBoard board = Factory.makeBoard();
        board.setHighlight(List.of(Factory.makeField(2, 0, PlayerID.NONE)));
        Game game = Factory.makeGame(PlayerID.PLAYER_1, board);

        //act
        result = BotMove.translate(botMove, game, PlayerID.PLAYER_1);

        //assert
        assertEquals(wantMoves, result);
    }

    @Test
    public void botInMenuTest_X7(){

        //arrange
        List<Move> result;
        List<Move> wantMoves = List.of(Move.DOWN, Move.RIGHT, Move.RIGHT, Move.RIGHT, Move.RIGHT, Move.RIGHT, Move.CONFIRM);
        BotMove botMove = BotMove.BOT_COLUMN_7;
        FullBoard board = Factory.makeBoard();
        board.setHighlight(List.of(Factory.makeField(2, 0, PlayerID.NONE)));
        Game game = Factory.makeGame(PlayerID.PLAYER_1, board);

        //act
        result = BotMove.translate(botMove, game, PlayerID.PLAYER_1);

        //assert
        assertEquals(wantMoves, result);
    }

    @Test
    public void botBombJokerPlayer_2Test(){

        //arrange
        List<Move> result;
        List<Move> wantMoves = List.of(Move.UP, Move.RIGHT, Move.RIGHT, Move.CONFIRM);
        String fieldString = "........" +
                ".....H.." +
                ".....H.." +
                ".....H.." +
                ".....H.." +
                ".....H.." +
                ".....H..";
        BotMove botMove = BotMove.BOT_BOMB_JOKER;
        FullBoard board = Factory.makeBoard();
        board.setHighlight(TestUtility.getHighlightedFieldList(fieldString));
        Game game = Factory.makeGame(PlayerID.PLAYER_1, board);

        //act
        result = BotMove.translate(botMove, game, PlayerID.PLAYER_1);

        //assert
        assertEquals(wantMoves, result);
    }

    @Test
    public void botDeleteJokerPlayer_2Test(){

        //arrange
        List<Move> result;
        List<Move> wantMoves = List.of(Move.UP, Move.RIGHT, Move.CONFIRM);
        String fieldString = "........" +
                ".....H.." +
                ".....H.." +
                ".....H.." +
                ".....H.." +
                ".....H.." +
                ".....H..";
        BotMove botMove = BotMove.BOT_BOMB_JOKER;
        FullBoard board = Factory.makeBoard();
        board.setHighlight(TestUtility.getHighlightedFieldList(fieldString));
        Game game = Factory.makeGame(PlayerID.PLAYER_1, board);

        //act
        result = BotMove.translate(botMove, game, PlayerID.PLAYER_1);

        //assert
        assertEquals(wantMoves, result);
    }
}
