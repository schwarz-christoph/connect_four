package edu.hm.scholz.enno.connect_four.logic;

import edu.hm.scholz.enno.connect_four.TestUtility;
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

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DeleteJokerTest {
    @Rule
    public Timeout globalTimeout = Timeout.millis(1_000);

    @Test
    public void deleteJokerUsedTest() {
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_2, board);
        game.setIsStarted(true);
        board.setHighlight(List.of(Factory.makeField(6, 0, PlayerID.NONE))); //Delete Joker Player 2
        String boardState = "........" +
                "........" +
                "........" +
                "........" +
                "........" +
                "........" +
                "GB......";
        TestUtility.createBoardState(board, boardState);


        FullPlayer player1 = Factory.makePlayer(PlayerID.PLAYER_1);
        FullPlayer player2 = Factory.makePlayer(PlayerID.PLAYER_2);

        GameManager manager = LogicFactory.makeGameManager(board, game, player1, player2);

        //act
        manager.executeMove(Move.CONFIRM);
        manager.executeMove(Move.CONFIRM);
        //assert
        assertTrue(player2.isDeleteJokerUsed());
    }

    @Test
    public void deleteJokerUsedTestActiveJokerNull() {
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_2, board);
        game.setIsStarted(true);
        board.setHighlight(List.of(Factory.makeField(6, 0, PlayerID.NONE))); //Delete Joker Player 2
        String boardState = "........" +
                "........" +
                "........" +
                "........" +
                "........" +
                "........" +
                "GB......";
        TestUtility.createBoardState(board, boardState);


        FullPlayer player1 = Factory.makePlayer(PlayerID.PLAYER_1);
        FullPlayer player2 = Factory.makePlayer(PlayerID.PLAYER_2);

        GameManager manager = LogicFactory.makeGameManager(board, game, player1, player2);

        //act
        manager.executeMove(Move.CONFIRM);
        manager.executeMove(Move.CONFIRM);
        //assert
        PlayerActiveJoker want = PlayerActiveJoker.NONE;
        PlayerActiveJoker have = game.getActiveJoker();
        assertEquals(want, have);
    }

    @Test
    public void deleteJokerSingleFieldHighlightTest() {
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_2, board);
        game.setIsStarted(true);
        board.setHighlight(List.of(Factory.makeField(6, 0, PlayerID.NONE))); //Delete Joker Player 2
        String boardState = "........" +
                "........" +
                "........" +
                "........" +
                "........" +
                "........" +
                "GB......";
        TestUtility.createBoardState(board, boardState);
        List<Field> expectedHighlight = List.of(Factory.makeField(0, 1, PlayerID.NONE),
                Factory.makeField(0, 2, PlayerID.NONE),
                Factory.makeField(0, 3, PlayerID.NONE),
                Factory.makeField(0, 4, PlayerID.NONE),
                Factory.makeField(0, 5, PlayerID.NONE),
                Factory.makeField(0, 6, PlayerID.NONE),
                Factory.makeField(0, 7, PlayerID.NONE));

        GameManager manager = LogicFactory.makeGameManager(board, game);

        //act
        manager.executeMove(Move.CONFIRM);
        manager.executeMove(Move.CONFIRM);

        //assert
        assertEquals(expectedHighlight, board.getHighlight());
    }

    @Test
    public void deleteJokerSingleFieldBottomTest() {
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_2, board);
        game.setIsStarted(true);
        board.setHighlight(List.of(Factory.makeField(6, 0, PlayerID.NONE))); //Delete Joker Player 2
        String boardState = "........" +
                "........" +
                "........" +
                "........" +
                "........" +
                "........" +
                "GB......";
        TestUtility.createBoardState(board, boardState);
        String expectedBoardState = "........" +
                "........" +
                "........" +
                "........" +
                "........" +
                "........" +
                ".B......";
        List<Field> expectedFields = TestUtility.getOccupiedFieldList(expectedBoardState);

        GameManager manager = LogicFactory.makeGameManager(board, game);

        //act
        manager.executeMove(Move.CONFIRM);
        manager.executeMove(Move.CONFIRM);

        //assert
        assertTrue(board.getFields().containsAll(expectedFields));
    }

    @Test
    public void deleteJokerSingleFieldTopTest() {
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_2, board);
        game.setIsStarted(true);
        board.setHighlight(List.of(Factory.makeField(6, 0, PlayerID.NONE))); //Delete Joker Player 2
        String boardState = "B......." +
                "G......." +
                "G......." +
                "B......." +
                "B......." +
                "G......." +
                "GB......";
        TestUtility.createBoardState(board, boardState);
        String expectedBoardState = "........" +
                "G......." +
                "G......." +
                "B......." +
                "B......." +
                "G......." +
                "GB......";
        List<Field> expectedFields = TestUtility.getOccupiedFieldList(expectedBoardState);

        GameManager manager = LogicFactory.makeGameManager(board, game);

        //act
        manager.executeMove(Move.CONFIRM);
        board.setHighlight(List.of(Factory.makeField(0, 1, PlayerID.NONE)));
        manager.executeMove(Move.CONFIRM);

        //assert
        assertTrue(board.getFields().containsAll(expectedFields));
    }

    @Test
    public void deleteJokerSingleFieldBottomFallDownTest() {
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_2, board);
        game.setIsStarted(true);
        board.setHighlight(List.of(Factory.makeField(6, 0, PlayerID.NONE))); //Delete Joker Player 2
        String boardState = "........" +
                "........" +
                "G......." +
                "B......." +
                "B......." +
                "G......." +
                "GB......";
        TestUtility.createBoardState(board, boardState);
        String expectedBoardState = "........" +
                "........" +
                "........" +
                "G......." +
                "B......." +
                "B......." +
                "GB......";
        List<Field> expectedFields = TestUtility.getOccupiedFieldList(expectedBoardState);

        GameManager manager = LogicFactory.makeGameManager(board, game);

        //act
        manager.executeMove(Move.CONFIRM);
        manager.executeMove(Move.CONFIRM);

        //assert
        assertTrue(board.getFields().containsAll(expectedFields));
    }

    @Test
    public void deleteJokerSingleFieldMiddleFallDownTest() {
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_2, board);
        game.setIsStarted(true);
        board.setHighlight(List.of(Factory.makeField(6, 0, PlayerID.NONE))); //Delete Joker Player 2
        String boardState = "........" +
                "........" +
                "G......." +
                "B......." +
                "B......." +
                "G......." +
                "GB......";
        TestUtility.createBoardState(board, boardState);
        String expectedBoardState = "........" +
                "........" +
                "........" +
                "G......." +
                "B......." +
                "G......." +
                "GB......";
        List<Field> expectedFields = TestUtility.getOccupiedFieldList(expectedBoardState);

        GameManager manager = LogicFactory.makeGameManager(board, game);

        //act
        manager.executeMove(Move.CONFIRM);
        board.setHighlight(List.of(Factory.makeField(0, 5, PlayerID.NONE)));
        manager.executeMove(Move.CONFIRM);

        //assert
        assertTrue(board.getFields().containsAll(expectedFields));
    }

    @Test
    public void deleteJokerRowHighlightTest() {
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_2, board);
        game.setIsStarted(true);
        board.setHighlight(List.of(Factory.makeField(6, 0, PlayerID.NONE))); //Delete Joker Player 2
        String boardState = "........" +
                "........" +
                "........" +
                "........" +
                "........" +
                "........" +
                "GB......";
        TestUtility.createBoardState(board, boardState);
        List<Field> expectedHighlight = List.of(Factory.makeField(0, 1, PlayerID.NONE),
                Factory.makeField(0, 2, PlayerID.NONE),
                Factory.makeField(0, 3, PlayerID.NONE),
                Factory.makeField(0, 4, PlayerID.NONE),
                Factory.makeField(0, 5, PlayerID.NONE),
                Factory.makeField(0, 6, PlayerID.NONE),
                Factory.makeField(0, 7, PlayerID.NONE));

        GameManager manager = LogicFactory.makeGameManager(board, game);

        //act
        manager.executeMove(Move.CONFIRM);
        manager.executeMove(Move.LEFT);
        manager.executeMove(Move.CONFIRM);

        //assert
        assertEquals(expectedHighlight, board.getHighlight());
    }

    @Test
    public void deleteJokerRowBottomTest() {
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_2, board);
        game.setIsStarted(true);
        board.setHighlight(List.of(Factory.makeField(6, 0, PlayerID.NONE))); //Delete Joker Player 2
        String boardState = "........" +
                "........" +
                "........" +
                "........" +
                "........" +
                "........" +
                "GBGBGBGG";
        TestUtility.createBoardState(board, boardState);
        String expectedBoardState = "........" +
                "........" +
                "........" +
                "........" +
                "........" +
                "........" +
                "........";
        List<Field> expectedFields = TestUtility.getOccupiedFieldList(expectedBoardState);

        GameManager manager = LogicFactory.makeGameManager(board, game);

        //act
        manager.executeMove(Move.CONFIRM);
        manager.executeMove(Move.LEFT);
        manager.executeMove(Move.CONFIRM);

        //assert
        assertTrue(board.getFields().containsAll(expectedFields));
    }

    @Test
    public void deleteJokerRowBottomFallDownTest() {
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_2, board);
        game.setIsStarted(true);
        board.setHighlight(List.of(Factory.makeField(6, 0, PlayerID.NONE))); //Delete Joker Player 2
        String boardState = "........" +
                "........" +
                "........" +
                "........" +
                "........" +
                "GBGBGBGG" +
                "GBGBGBGG";
        TestUtility.createBoardState(board, boardState);
        String expectedBoardState = "........" +
                "........" +
                "........" +
                "........" +
                "........" +
                "........" +
                "GBGBGBGG";
        List<Field> expectedFields = TestUtility.getOccupiedFieldList(expectedBoardState);

        GameManager manager = LogicFactory.makeGameManager(board, game);

        //act
        manager.executeMove(Move.CONFIRM);
        manager.executeMove(Move.LEFT);
        manager.executeMove(Move.CONFIRM);

        //assert
        assertTrue(board.getFields().containsAll(expectedFields));
    }

    @Test
    public void deleteJokerRowMiddleTest() {
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_2, board);
        game.setIsStarted(true);
        board.setHighlight(List.of(Factory.makeField(6, 0, PlayerID.NONE))); //Delete Joker Player 2
        String boardState = "........" +
                "........" +
                "........" +
                "........" +
                "GBGGGBGB" +
                "GBGBGBGG" +
                "GBGBGBGG";
        TestUtility.createBoardState(board, boardState);
        String expectedBoardState = "........" +
                "........" +
                "........" +
                "........" +
                "........" +
                "GBGBGBGG" +
                "GBGBGBGG";
        List<Field> expectedFields = TestUtility.getOccupiedFieldList(expectedBoardState);

        GameManager manager = LogicFactory.makeGameManager(board, game);

        //act
        manager.executeMove(Move.CONFIRM);
        manager.executeMove(Move.LEFT);
        manager.executeMove(Move.UP);
        manager.executeMove(Move.UP);
        manager.executeMove(Move.CONFIRM);

        //assert
        assertTrue(board.getFields().containsAll(expectedFields));
    }

    @Test
    public void deleteJokerRowMiddleFallDownTest() {
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_2, board);
        game.setIsStarted(true);
        board.setHighlight(List.of(Factory.makeField(6, 0, PlayerID.NONE))); //Delete Joker Player 2
        String boardState = "........" +
                "........" +
                "........" +
                "GGGBBGGG" +
                "BBBGGBBB" +
                "BGGBBBGB" +
                "BBBGGBBB";
        TestUtility.createBoardState(board, boardState);
        String expectedBoardState = "........" +
                "........" +
                "........" +
                "........" +
                "GGGBBGGG" +
                "BGGBBBGB" +
                "BBBGGBBB";
        List<Field> expectedFields = TestUtility.getOccupiedFieldList(expectedBoardState);

        GameManager manager = LogicFactory.makeGameManager(board, game);

        //act
        manager.executeMove(Move.CONFIRM);
        manager.executeMove(Move.LEFT);
        manager.executeMove(Move.UP);
        manager.executeMove(Move.UP);
        manager.executeMove(Move.CONFIRM);

        //assert
        assertTrue(board.getFields().containsAll(expectedFields));
    }

    @Test
    public void deleteJokerRowTopTest() {
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_2, board);
        game.setIsStarted(true);
        board.setHighlight(List.of(Factory.makeField(6, 0, PlayerID.NONE))); //Delete Joker Player 2
        String boardState = "........" +
                "GGGBBGGG" +
                "BBBGGBBB" +
                "GGGBBGGG" +
                "BBBGGBBB" +
                "GGGBBGGG" +
                "BBBGGBBB";
        TestUtility.createBoardState(board, boardState);
        String expectedBoardState = "........" +
                "........" +
                "BBBGGBBB" +
                "GGGBBGGG" +
                "BBBGGBBB" +
                "GGGBBGGG" +
                "BBBGGBBB";
        List<Field> expectedFields = TestUtility.getOccupiedFieldList(expectedBoardState);

        GameManager manager = LogicFactory.makeGameManager(board, game);

        //act
        manager.executeMove(Move.CONFIRM);
        manager.executeMove(Move.LEFT);
        manager.executeMove(Move.UP);
        manager.executeMove(Move.UP);
        manager.executeMove(Move.UP);
        manager.executeMove(Move.UP);
        manager.executeMove(Move.UP);
        manager.executeMove(Move.UP);
        manager.executeMove(Move.UP);
        manager.executeMove(Move.CONFIRM);

        //assert
        assertTrue(board.getFields().containsAll(expectedFields));
    }

    @Test
    public void deleteJokerColumnHighlightTest() {
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_2, board);
        game.setIsStarted(true);
        board.setHighlight(List.of(Factory.makeField(6, 0, PlayerID.NONE))); //Delete Joker Player 2
        String boardState = "........" +
                "........" +
                "........" +
                "........" +
                "........" +
                "........" +
                "GB......";
        TestUtility.createBoardState(board, boardState);
        List<Field> expectedHighlight = List.of(Factory.makeField(0, 1, PlayerID.NONE),
                Factory.makeField(0, 2, PlayerID.NONE),
                Factory.makeField(0, 3, PlayerID.NONE),
                Factory.makeField(0, 4, PlayerID.NONE),
                Factory.makeField(0, 5, PlayerID.NONE),
                Factory.makeField(0, 6, PlayerID.NONE),
                Factory.makeField(0, 7, PlayerID.NONE));

        GameManager manager = LogicFactory.makeGameManager(board, game);

        //act
        manager.executeMove(Move.CONFIRM);
        manager.executeMove(Move.DOWN);
        manager.executeMove(Move.CONFIRM);

        //assert
        assertEquals(expectedHighlight, board.getHighlight());
    }


    @Test
    public void deleteJokerColumnLeftTest() {
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_2, board);
        game.setIsStarted(true);
        board.setHighlight(List.of(Factory.makeField(6, 0, PlayerID.NONE))); //Delete Joker Player 2
        String boardState = "........" +
                "GGGBBGGG" +
                "BBBGGBBB" +
                "GGGBBGGG" +
                "BBBGGBBB" +
                "GGGBBGGG" +
                "BBBGGBBB";
        TestUtility.createBoardState(board, boardState);
        String expectedBoardState = "........" +
                ".GGBBGGG" +
                ".BBGGBBB" +
                ".GGBBGGG" +
                ".BBGGBBB" +
                ".GGBBGGG" +
                ".BBGGBBB";
        List<Field> expectedFields = TestUtility.getOccupiedFieldList(expectedBoardState);

        GameManager manager = LogicFactory.makeGameManager(board, game);

        //act
        manager.executeMove(Move.CONFIRM);
        manager.executeMove(Move.DOWN);
        manager.executeMove(Move.CONFIRM);

        //assert
        assertTrue(board.getFields().containsAll(expectedFields));
    }

    @Test
    public void deleteJokerColumnMiddleTest() {
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_2, board);
        game.setIsStarted(true);
        board.setHighlight(List.of(Factory.makeField(6, 0, PlayerID.NONE))); //Delete Joker Player 2
        String boardState = "........" +
                "GGGBBGGG" +
                "BBBGGBBB" +
                "GGGBBGGG" +
                "BBBGGBBB" +
                "GGGBBGGG" +
                "BBBGGBBB";
        TestUtility.createBoardState(board, boardState);
        String expectedBoardState = "........" +
                "GGG.BGGG" +
                "BBB.GBBB" +
                "GGG.BGGG" +
                "BBB.GBBB" +
                "GGG.BGGG" +
                "BBB.GBBB";
        List<Field> expectedFields = TestUtility.getOccupiedFieldList(expectedBoardState);

        GameManager manager = LogicFactory.makeGameManager(board, game);

        //act
        manager.executeMove(Move.CONFIRM);
        manager.executeMove(Move.DOWN);
        manager.executeMove(Move.RIGHT);
        manager.executeMove(Move.RIGHT);
        manager.executeMove(Move.RIGHT);
        manager.executeMove(Move.CONFIRM);

        //assert
        assertTrue(board.getFields().containsAll(expectedFields));
    }

    @Test
    public void deleteJokerColumnRightTest() {
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_2, board);
        game.setIsStarted(true);
        board.setHighlight(List.of(Factory.makeField(6, 0, PlayerID.NONE))); //Delete Joker Player 2
        String boardState = "........" +
                "GGGBBGGG" +
                "BBBGGBBB" +
                "GGGBBGGG" +
                "BBBGGBBB" +
                "GGGBBGGG" +
                "BBBGGBBB";
        TestUtility.createBoardState(board, boardState);
        String expectedBoardState = "........" +
                "GGGBBGG." +
                "BBBGGBB." +
                "GGGBBGG." +
                "BBBGGBB." +
                "GGGBBGG." +
                "BBBGGBB.";
        List<Field> expectedFields = TestUtility.getOccupiedFieldList(expectedBoardState);

        GameManager manager = LogicFactory.makeGameManager(board, game);

        //act
        manager.executeMove(Move.CONFIRM);
        manager.executeMove(Move.DOWN);
        manager.executeMove(Move.RIGHT);
        manager.executeMove(Move.RIGHT);
        manager.executeMove(Move.RIGHT);
        manager.executeMove(Move.RIGHT);
        manager.executeMove(Move.RIGHT);
        manager.executeMove(Move.RIGHT);
        manager.executeMove(Move.RIGHT);
        manager.executeMove(Move.RIGHT);
        manager.executeMove(Move.CONFIRM);

        //assert
        assertTrue(board.getFields().containsAll(expectedFields));
    }

    @Test
    public void deleteJokerSingleFieldWinTest() {
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_2, board);
        game.setIsStarted(true);
        board.setHighlight(List.of(Factory.makeField(6, 0, PlayerID.NONE))); //Delete Joker Player 2
        String boardState = "........" +
                "........" +
                "........" +
                "........" +
                "........" +
                "BBBG...." +
                "GGGB....";
        TestUtility.createBoardState(board, boardState);

        PlayerID expectedWinner = PlayerID.PLAYER_1;

        GameManager manager = LogicFactory.makeGameManager(board, game);

        //act
        manager.executeMove(Move.CONFIRM);
        board.setHighlight(List.of(Factory.makeField(3, 7, PlayerID.NONE)));
        manager.executeMove(Move.CONFIRM);

        //assert
        assertEquals(expectedWinner, game.getWinner());
    }

    @Test
    public void deleteJokerRowWinTest() {
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_2, board);
        game.setIsStarted(true);
        board.setHighlight(List.of(Factory.makeField(6, 0, PlayerID.NONE))); //Delete Joker Player 2
        String boardState = "........" +
                "........" +
                "G......." +
                "G......." +
                "G......." +
                "BBBG...." +
                "GGGB....";
        TestUtility.createBoardState(board, boardState);

        PlayerID expectedWinner = PlayerID.PLAYER_1;

        GameManager manager = LogicFactory.makeGameManager(board, game);

        //act
        manager.executeMove(Move.CONFIRM);
        manager.executeMove(Move.LEFT);
        manager.executeMove(Move.UP);
        manager.executeMove(Move.CONFIRM);

        //assert
        assertEquals(expectedWinner, game.getWinner());
    }

    @Test
    public void deleteJokerSingleFieldTieTest() {
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_2, board);
        game.setIsStarted(true);
        board.setHighlight(List.of(Factory.makeField(6, 0, PlayerID.NONE))); //Delete Joker Player 2
        String boardState = "........" +
                "........" +
                "........" +
                "........" +
                "...B...." +
                "BBBG...." +
                "GGGB....";
        TestUtility.createBoardState(board, boardState);

        //activePlayer NONE means game is over
        PlayerID expectedActivePlayer = PlayerID.NONE;

        GameManager manager = LogicFactory.makeGameManager(board, game);

        //act
        manager.executeMove(Move.CONFIRM);
        board.setHighlight(List.of(Factory.makeField(3, 7, PlayerID.NONE)));
        manager.executeMove(Move.CONFIRM);

        //assert
        assertEquals(expectedActivePlayer, game.getActivePlayer());
    }

    @Test
    public void deleteJokerRowTieTest() {
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_2, board);
        game.setIsStarted(true);
        board.setHighlight(List.of(Factory.makeField(6, 0, PlayerID.NONE))); //Delete Joker Player 2
        String boardState = "........" +
                "........" +
                "GB......" +
                "GB......" +
                "GB......" +
                "BGBG...." +
                "GBGB....";
        TestUtility.createBoardState(board, boardState);

        PlayerID expectedWinner = PlayerID.NONE;

        GameManager manager = LogicFactory.makeGameManager(board, game);

        //act
        manager.executeMove(Move.CONFIRM);
        manager.executeMove(Move.LEFT);
        manager.executeMove(Move.UP);
        manager.executeMove(Move.CONFIRM);

        //assert
        assertEquals(expectedWinner, game.getWinner());
    }
}
