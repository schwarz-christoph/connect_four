package edu.hm.scholz.enno.connect_four.control;

import edu.hm.scholz.enno.connect_four.logic.Move;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class BotMovesTest {
//    @Rule
//    public Timeout globalTimeout = Timeout.millis(1_000);

    @Parameterized.Parameters(name = "Bot Move: {0} get List: {2}")
    public static Iterable<Object[]> testCasesBotMovesTest() {
        return List.of(new Object[][]{
                {BotMoves.BOT_COLUMN_0, "........" +
                        ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H",
                        List.of(Move.LEFT, Move.LEFT, Move.LEFT, Move.LEFT, Move.LEFT, Move.LEFT, Move.LEFT)},

                {BotMoves.BOT_COLUMN_1, "........" +
                        ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H",
                        List.of(Move.LEFT, Move.LEFT, Move.LEFT, Move.LEFT, Move.LEFT, Move.LEFT)},

                {BotMoves.BOT_COLUMN_2, "........" +
                        ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H",
                        List.of(Move.LEFT, Move.LEFT, Move.LEFT, Move.LEFT, Move.LEFT)},

                {BotMoves.BOT_COLUMN_3, "........" +
                        ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H",
                        List.of(Move.LEFT, Move.LEFT, Move.LEFT, Move.LEFT)},
                {BotMoves.BOT_COLUMN_4, "........" +
                        ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H",
                        List.of(Move.LEFT, Move.LEFT, Move.LEFT)},

                {BotMoves.BOT_COLUMN_5, "........" +
                        ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H",
                        List.of(Move.LEFT, Move.LEFT)},

                {BotMoves.BOT_COLUMN_6, "........" +
                        ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H" +
                        ".......H",
                        List.of(Move.LEFT)},

                {BotMoves.BOT_COLUMN_7, ".......H" +
                        "........" +
                        "........" +
                        "........" +
                        "........" +
                        "........" +
                        "........",
                        List.of(Move.DOWN)},

                {BotMoves.BOT_COLUMN_0, "H......." +
                        "........" +
                        "........" +
                        "........" +
                        "........" +
                        "........" +
                        "........",
                        List.of(Move.DOWN)},

                {BotMoves.BOT_COLUMN_1, "........" +
                        "H......." +
                        "H......." +
                        "H......." +
                        "H......." +
                        "H......." +
                        "H.......",
                        List.of(Move.RIGHT)},

                {BotMoves.BOT_COLUMN_2, "........" +
                        "H......." +
                        "H......." +
                        "H......." +
                        "H......." +
                        "H......." +
                        "H.......",
                        List.of(Move.RIGHT, Move.RIGHT)},

                {BotMoves.BOT_COLUMN_3, "........" +
                        "H......." +
                        "H......." +
                        "H......." +
                        "H......." +
                        "H......." +
                        "H.......",
                        List.of(Move.RIGHT, Move.RIGHT, Move.RIGHT)},
                {BotMoves.BOT_COLUMN_4, "........" +
                        "H......." +
                        "H......." +
                        "H......." +
                        "H......." +
                        "H......." +
                        "H.......",
                        List.of(Move.RIGHT, Move.RIGHT, Move.RIGHT, Move.RIGHT)},

                {BotMoves.BOT_COLUMN_5, "........" +
                        "H......." +
                        "H......." +
                        "H......." +
                        "H......." +
                        "H......." +
                        "H.......",
                        List.of(Move.RIGHT, Move.RIGHT, Move.RIGHT, Move.RIGHT, Move.RIGHT)},

                {BotMoves.BOT_COLUMN_6, "........" +
                        "H......." +
                        "H......." +
                        "H......." +
                        "H......." +
                        "H......." +
                        "H.......",
                        List.of(Move.RIGHT, Move.RIGHT, Move.RIGHT, Move.RIGHT, Move.RIGHT, Move.RIGHT)},

                {BotMoves.BOT_COLUMN_7, "........" +
                        "H......." +
                        "H......." +
                        "H......." +
                        "H......." +
                        "H......." +
                        "H.......",
                        List.of(Move.RIGHT, Move.RIGHT, Move.RIGHT, Move.RIGHT, Move.RIGHT, Move.RIGHT, Move.RIGHT)},

                {BotMoves.BOT_BOMB_JOKER, "........" +
                        ".....H.." +
                        ".....H.." +
                        ".....H.." +
                        ".....H.." +
                        ".....H.." +
                        ".....H..",
                        List.of(Move.UP, Move.LEFT, Move.LEFT, Move.LEFT, Move.LEFT, Move.LEFT)},

                {BotMoves.BOT_DELETE_JOKER, "........" +
                        ".....H.." +
                        ".....H.." +
                        ".....H.." +
                        ".....H.." +
                        ".....H.." +
                        ".....H..",
                        List.of(Move.UP, Move.LEFT, Move.LEFT, Move.LEFT, Move.LEFT)},
        });
    }

    @Test
    public void translate() {


    }
}