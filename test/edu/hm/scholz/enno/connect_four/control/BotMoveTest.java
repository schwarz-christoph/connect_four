package edu.hm.scholz.enno.connect_four.control;

import edu.hm.scholz.enno.connect_four.logic.Move;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

@RunWith(Parameterized.class)
public class BotMoveTest {
//    @Rule
//    public Timeout globalTimeout = Timeout.millis(1_000);

    @Parameterized.Parameters(name = "Bot Move: {0} get List: {2}")
    public static Iterable<Object[]> testCasesBotMovesTest() {
        return List.of(new Object[][]{
                {BotMove.BOT_COLUMN_0, "........" +
                        ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H",
                        List.of(Move.LEFT, Move.LEFT, Move.LEFT, Move.LEFT, Move.LEFT, Move.LEFT, Move.LEFT)},

                {BotMove.BOT_COLUMN_1, "........" +
                        ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H",
                        List.of(Move.LEFT, Move.LEFT, Move.LEFT, Move.LEFT, Move.LEFT, Move.LEFT)},

                {BotMove.BOT_COLUMN_2, "........" +
                        ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H",
                        List.of(Move.LEFT, Move.LEFT, Move.LEFT, Move.LEFT, Move.LEFT)},

                {BotMove.BOT_COLUMN_3, "........" +
                        ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H",
                        List.of(Move.LEFT, Move.LEFT, Move.LEFT, Move.LEFT)},
                {BotMove.BOT_COLUMN_4, "........" +
                        ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H",
                        List.of(Move.LEFT, Move.LEFT, Move.LEFT)},

                {BotMove.BOT_COLUMN_5, "........" +
                        ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H",
                        List.of(Move.LEFT, Move.LEFT)},

                {BotMove.BOT_COLUMN_6, "........" +
                        ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H",
                        List.of(Move.LEFT)},

                {BotMove.BOT_COLUMN_7, ".......H" +
                        "........" +
                        "........" +
                        "........" +
                        "........" +
                        "........" +
                        "........",
                        List.of(Move.DOWN)},

                {BotMove.BOT_COLUMN_0, "H......." +
                        "........" +
                        "........" +
                        "........" +
                        "........" +
                        "........" +
                        "........",
                        List.of(Move.DOWN)},

                {BotMove.BOT_COLUMN_1, "........" +
                        "H......." +
                        "H......." +
                        "H......." +
                        "H......." +
                        "H......." +
                        "H.......",
                        List.of(Move.RIGHT)},

                {BotMove.BOT_COLUMN_2, "........" +
                        "H......." +
                        "H......." +
                        "H......." +
                        "H......." +
                        "H......." +
                        "H.......",
                        List.of(Move.RIGHT, Move.RIGHT)},

                {BotMove.BOT_COLUMN_3, "........" +
                        "H......." +
                        "H......." +
                        "H......." +
                        "H......." +
                        "H......." +
                        "H.......",
                        List.of(Move.RIGHT, Move.RIGHT, Move.RIGHT)},
                {BotMove.BOT_COLUMN_4, "........" +
                        "H......." +
                        "H......." +
                        "H......." +
                        "H......." +
                        "H......." +
                        "H.......",
                        List.of(Move.RIGHT, Move.RIGHT, Move.RIGHT, Move.RIGHT)},

                {BotMove.BOT_COLUMN_5, "........" +
                        "H......." +
                        "H......." +
                        "H......." +
                        "H......." +
                        "H......." +
                        "H.......",
                        List.of(Move.RIGHT, Move.RIGHT, Move.RIGHT, Move.RIGHT, Move.RIGHT)},

                {BotMove.BOT_COLUMN_6, "........" +
                        "H......." +
                        "H......." +
                        "H......." +
                        "H......." +
                        "H......." +
                        "H.......",
                        List.of(Move.RIGHT, Move.RIGHT, Move.RIGHT, Move.RIGHT, Move.RIGHT, Move.RIGHT)},

                {BotMove.BOT_COLUMN_7, "........" +
                        "H......." +
                        "H......." +
                        "H......." +
                        "H......." +
                        "H......." +
                        "H.......",
                        List.of(Move.RIGHT, Move.RIGHT, Move.RIGHT, Move.RIGHT, Move.RIGHT, Move.RIGHT, Move.RIGHT)},

                {BotMove.BOT_BOMB_JOKER, "........" +
                        ".....H.." +
                        ".....H.." +
                        ".....H.." +
                        ".....H.." +
                        ".....H.." +
                        ".....H..",
                        List.of(Move.UP, Move.LEFT, Move.LEFT, Move.LEFT, Move.LEFT, Move.LEFT)},

                {BotMove.BOT_DELETE_JOKER, "........" +
                        ".....H.." +
                        ".....H.." +
                        ".....H.." +
                        ".....H.." +
                        ".....H.." +
                        ".....H..",
                        List.of(Move.UP, Move.LEFT, Move.LEFT, Move.LEFT, Move.LEFT)},
        });
    }

    private final BotMove move;
    private final String field;
    private final List<Move> resultMoves;

    public BotMoveTest(BotMove move, String field, List<Move> resultMoves){
        this.move = move;
        this.field = field;
        this.resultMoves = resultMoves;
    }

    @Test
    public void translate() {



    }
}