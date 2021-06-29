package edu.hm.scholz.enno.connect_four.control;

import edu.hm.scholz.enno.connect_four.TestUtility;
import edu.hm.scholz.enno.connect_four.datastore.Game;
import edu.hm.scholz.enno.connect_four.datastore.PlayerID;
import edu.hm.scholz.enno.connect_four.datastore.mutable.Factory;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullBoard;
import edu.hm.scholz.enno.connect_four.logic.Move;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class BotMoveJokerTest {

    @Rule
    public Timeout globalTimeout = Timeout.millis(1_000);

    @Parameterized.Parameters(name = "Bot Move: {0} get List: {2}")
    public static Iterable<Object[]> testCasesBotMovesTest() {
        return List.of(new Object[][]{
                {BotMove.BOT_BOMB_JOKER, "........" +
                        ".....H.." +
                        ".....H.." +
                        ".....H.." +
                        ".....H.." +
                        ".....H.." +
                        ".....H..",
                        List.of(Move.UP, Move.LEFT, Move.LEFT, Move.LEFT, Move.LEFT, Move.LEFT, Move.CONFIRM)},

                {BotMove.BOT_DELETE_JOKER, "........" +
                        ".....H.." +
                        ".....H.." +
                        ".....H.." +
                        ".....H.." +
                        ".....H.." +
                        ".....H..",
                        List.of(Move.UP, Move.LEFT, Move.LEFT, Move.LEFT, Move.LEFT, Move.CONFIRM)}
        });
    }


    private final BotMove botMove;
    private final String fieldString;
    private final List<Move> wantMoves;

    public BotMoveJokerTest(BotMove botMove, String fieldString, List<Move> wantMoves) {
        this.botMove = botMove;
        this.fieldString = fieldString;
        this.wantMoves = wantMoves;
    }

    @Test
    public void translateTest() {

        //arrange
        List<Move> result;
        FullBoard board = Factory.makeBoard();
        board.setHighlight(TestUtility.getHighlightedFieldList(fieldString));
        Game game = Factory.makeGame(PlayerID.PLAYER_1, board);

        //act
        result = BotMove.translate(botMove, game, PlayerID.PLAYER_1);

        //assert
        List<Move> resultMovesToJoker = result.stream().limit(wantMoves.size()).collect(Collectors.toList());
        assertEquals(wantMoves, resultMovesToJoker);
    }

    @Test
    public void translateEndConfirmTest() {

        //arrange
        List<Move> result;
        FullBoard board = Factory.makeBoard();
        board.setHighlight(TestUtility.getHighlightedFieldList(fieldString));
        Game game = Factory.makeGame(PlayerID.PLAYER_1, board);

        //act
        result = BotMove.translate(botMove, game, PlayerID.PLAYER_1);

        //assert
        assertEquals(Move.CONFIRM, result.get(result.size() - 1));
    }
}
