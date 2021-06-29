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

import static org.junit.Assert.assertEquals;

import java.util.List;

@RunWith(Parameterized.class)
public class BotMoveTest {
    @Rule
    public Timeout globalTimeout = Timeout.millis(1_000);

    @Parameterized.Parameters(name = "Bot Move: {0} get List: {2}")
    public static Iterable<Object[]> testCasesBotMovesTest() {
        return List.of(new Object[][]{
                {BotMove.BOT_COLUMN_0, ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H",
                        List.of(Move.LEFT, Move.LEFT, Move.LEFT, Move.LEFT, Move.LEFT, Move.LEFT, Move.LEFT, Move.CONFIRM)},

                {BotMove.BOT_COLUMN_1, ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H",
                        List.of(Move.LEFT, Move.LEFT, Move.LEFT, Move.LEFT, Move.LEFT, Move.LEFT, Move.CONFIRM)},

                {BotMove.BOT_COLUMN_2, ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H",
                        List.of(Move.LEFT, Move.LEFT, Move.LEFT, Move.LEFT, Move.LEFT, Move.CONFIRM)},

                {BotMove.BOT_COLUMN_3, ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H",
                        List.of(Move.LEFT, Move.LEFT, Move.LEFT, Move.LEFT, Move.CONFIRM)},
                {BotMove.BOT_COLUMN_4, ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H",
                        List.of(Move.LEFT, Move.LEFT, Move.LEFT, Move.CONFIRM)},

                {BotMove.BOT_COLUMN_5, ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H",
                        List.of(Move.LEFT, Move.LEFT, Move.CONFIRM)},

                {BotMove.BOT_COLUMN_6, "........" +
                        ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H",
                        List.of(Move.LEFT, Move.CONFIRM)},

                {BotMove.BOT_COLUMN_7, "........" +
                        ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H",
                        List.of(Move.CONFIRM)},

                {BotMove.BOT_COLUMN_0, "H......." +
                        "H......." +
                        "H......." +
                        "H......." +
                        "H......." +
                        "H......." +
                        "H.......",
                        List.of(Move.CONFIRM)},

                {BotMove.BOT_COLUMN_1, "H......." +
                        "H......." +
                        "H......." +
                        "H......." +
                        "H......." +
                        "H......." +
                        "H.......",
                        List.of(Move.RIGHT, Move.CONFIRM)},

                {BotMove.BOT_COLUMN_2, "H......." +
                        "H......." +
                        "H......." +
                        "H......." +
                        "H......." +
                        "H......." +
                        "H.......",
                        List.of(Move.RIGHT, Move.RIGHT, Move.CONFIRM)},

                {BotMove.BOT_COLUMN_3, "H......." +
                        "H......." +
                        "H......." +
                        "H......." +
                        "H......." +
                        "H......." +
                        "H.......",
                        List.of(Move.RIGHT, Move.RIGHT, Move.RIGHT, Move.CONFIRM)},
                {BotMove.BOT_COLUMN_4, "H......." +
                        "H......." +
                        "H......." +
                        "H......." +
                        "H......." +
                        "H......." +
                        "H.......",
                        List.of(Move.RIGHT, Move.RIGHT, Move.RIGHT, Move.RIGHT, Move.CONFIRM)},

                {BotMove.BOT_COLUMN_5, "H......." +
                        "H......." +
                        "H......." +
                        "H......." +
                        "H......." +
                        "H......." +
                        "H.......",
                        List.of(Move.RIGHT, Move.RIGHT, Move.RIGHT, Move.RIGHT, Move.RIGHT, Move.CONFIRM)},

                {BotMove.BOT_COLUMN_6, "H......." +
                        "H......." +
                        "H......." +
                        "H......." +
                        "H......." +
                        "H......." +
                        "H.......",
                        List.of(Move.RIGHT, Move.RIGHT, Move.RIGHT, Move.RIGHT, Move.RIGHT, Move.RIGHT, Move.CONFIRM)},

                {BotMove.BOT_COLUMN_7, "H......." +
                        "H......." +
                        "H......." +
                        "H......." +
                        "H......." +
                        "H......." +
                        "H.......",
                        List.of(Move.RIGHT, Move.RIGHT, Move.RIGHT, Move.RIGHT, Move.RIGHT, Move.RIGHT, Move.RIGHT, Move.CONFIRM)}
        });
    }

    private final BotMove botMove;
    private final String fieldString;
    private final List<Move> wantMoves;

    public BotMoveTest(BotMove botMove, String fieldString, List<Move> wantMoves){
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
        assertEquals(wantMoves, result);
    }
}