package edu.hm.scholz.enno.connect_four.logic;

import edu.hm.scholz.enno.connect_four.TestUtility;
import edu.hm.scholz.enno.connect_four.datastore.*;
import edu.hm.scholz.enno.connect_four.datastore.mutable.Factory;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullBoard;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullGame;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullPlayer;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class BombJokerTest {

    @Test
    public void bombJokerHighlightTestOnlyColumnFilled(){
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        GameManager manager = LogicFactory.makeGameManager(board, game);
        game.setIsStarted(true);

        String beforeBomb = "........" +
                            "G......." +
                            "G......." +
                            "B......." +
                            "G......." +
                            "B......." +
                            "B.......";
        TestUtility.createBoardState(board, beforeBomb);

        board.setHighlight(List.of(Factory.makeField(0, 0, PlayerID.PLAYER_1)));
        manager.executeMove(Move.CONFIRM);
        manager.executeMove(Move.RIGHT);
        manager.executeMove(Move.LEFT);

        String afterBomb = "........" +
                           "........" +
                           "........" +
                           "B......." +
                           "G......." +
                           "B......." +
                           "B.......";
        List<Field> want = TestUtility.getOccupiedFieldList(afterBomb);

        //act
        manager.executeMove(Move.CONFIRM);
        List<Field> actual = board.getFields();

        //assert
        assertTrue(TestUtility.equalsListFields(actual, want));
    }

    @Test
    public void bombJokerHighlightTestHorizontalFilled(){
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        GameManager manager = LogicFactory.makeGameManager(board, game);
        game.setIsStarted(true);

        String beforeBomb = "........" +
                            "........" +
                            "........" +
                            "........" +
                            "........" +
                            "........" +
                            "GBBGBBBG";
        TestUtility.createBoardState(board, beforeBomb);

        String afterBomb = "........" +
                           "........" +
                           "........" +
                           "........" +
                           "........" +
                           "........" +
                           "GB..BBBG";
        List<Field> want = TestUtility.getOccupiedFieldList(afterBomb);

        board.setHighlight(List.of(Factory.makeField(0, 0, PlayerID.PLAYER_1)));
        manager.executeMove(Move.CONFIRM);
        manager.executeMove(Move.RIGHT);
        manager.executeMove(Move.RIGHT);
        manager.executeMove(Move.RIGHT);

        //act
        manager.executeMove(Move.CONFIRM);
        List<Field> actual = board.getFields();

        //assert
        assertTrue(want.containsAll(actual));
    }

    @Test
    public void bombJokerHighlightTestAllSidesFilled(){
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        GameManager manager = LogicFactory.makeGameManager(board, game);
        game.setIsStarted(true);

        String beforeBomb = "........" +
                            "........" +
                            "........" +
                            ".GB.BG.." +
                            ".BB.BB.." +
                            ".BBBGB.." +
                            "GBGBGBGB";
        TestUtility.createBoardState(board, beforeBomb);

        board.setHighlight(List.of(Factory.makeField(0, 0, PlayerID.PLAYER_1)));
        manager.executeMove(Move.CONFIRM);
        manager.executeMove(Move.RIGHT);
        manager.executeMove(Move.RIGHT);
        manager.executeMove(Move.RIGHT);

        String afterBomb = "........" +
                           "........" +
                           "........" +
                           "........" +
                           ".G...G.." +
                           ".B...B.." +
                           "GBG.GBGB";
        List<Field> want = TestUtility.getOccupiedFieldList(afterBomb);

        //act
        manager.executeMove(Move.CONFIRM);
        List<Field> actual = board.getFields();

        //assert
        assertTrue(want.containsAll(actual));
    }

    @Test
    public void bombJokerEmptyBoardTest() {
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        GameManager manager = LogicFactory.makeGameManager(board, game);
        game.setIsStarted(true);

        board.setHighlight(List.of(Factory.makeField(0, 0, PlayerID.PLAYER_1)));
        manager.executeMove(Move.CONFIRM);
        manager.executeMove(Move.RIGHT);
        manager.executeMove(Move.LEFT);
        manager.executeMove(Move.RIGHT);

        //act
        manager.executeMove(Move.CONFIRM);
        List<Field> actual = board.getFields();

        //assert
        assertTrue(actual.isEmpty());
    }

    @Test
    public void bombJokerGetMoveTest() {
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        GameManager manager = LogicFactory.makeGameManager(board, game);
        game.setIsStarted(true);

        board.setHighlight(List.of(Factory.makeField(0, 0, PlayerID.PLAYER_1)));
        manager.executeMove(Move.CONFIRM);

        //act
        List<Move> have = manager.getMoves(PlayerID.PLAYER_1);

        //assert
        List<Move> want = List.of(Move.CONFIRM, Move.RIGHT, Move.LEFT);
        assertEquals(want, have);
    }

    @Test
    public void bombJokerTestNoOverflow() {
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        GameManager manager = LogicFactory.makeGameManager(board, game);
        game.setIsStarted(true);

        board.setHighlight(List.of(Factory.makeField(0, 0, PlayerID.PLAYER_1)));
        manager.executeMove(Move.CONFIRM);

        String beforeBomb = "........" +
                            "........" +
                            "........" +
                            "........" +
                            "........" +
                            "........" +
                            "G......G";
        TestUtility.createBoardState(board, beforeBomb);

        String afterBomb = "........" +
                            "........" +
                            "........" +
                            "........" +
                            "........" +
                            "........" +
                            ".......G";
        List<Field> want = TestUtility.getOccupiedFieldList(afterBomb);

        //act
        manager.executeMove(Move.CONFIRM);
        List<Field> actual = board.getFields();

        //assert
        assertEquals(want, actual);
    }

    @Test
    public void bombJokerOneStoneTest() {
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        GameManager manager = LogicFactory.makeGameManager(board, game);
        game.setIsStarted(true);

        board.setHighlight(List.of(Factory.makeField(0, 0, PlayerID.PLAYER_1)));
        manager.executeMove(Move.CONFIRM);
        manager.executeMove(Move.RIGHT);
        manager.executeMove(Move.RIGHT);

        String beforeBomb = "........" +
                            "........" +
                            "........" +
                            "........" +
                            "........" +
                            "........" +
                            "..G.....";
        TestUtility.createBoardState(board, beforeBomb);
        //act
        manager.executeMove(Move.CONFIRM);
        List<Field> actual = board.getFields();

        //assert
        assertTrue(actual.isEmpty());
    }

    @Test
    public void bombJokerLeadsToWinTest() {
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        GameManager manager = LogicFactory.makeGameManager(board, game);
        game.setIsStarted(true);

        board.setHighlight(List.of(Factory.makeField(0, 0, PlayerID.PLAYER_1)));
        manager.executeMove(Move.CONFIRM);
        manager.executeMove(Move.RIGHT);
        manager.executeMove(Move.RIGHT);
        manager.executeMove(Move.RIGHT);
        manager.executeMove(Move.RIGHT);

        String boardState = "........" +
                            "........" +
                            "..G....." +
                            "..B....." +
                            "..G.G..." +
                            "..G.G..." +
                            "..G.G...";

        board.placeStone(Factory.makeField(2, 7, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(2, 6, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(2, 5, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(2, 4, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(2, 3, PlayerID.PLAYER_1));

        board.placeStone(Factory.makeField(4, 7, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(4, 6, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(4, 5, PlayerID.PLAYER_1));

        PlayerID want = PlayerID.PLAYER_1;

        //act
        manager.executeMove(Move.CONFIRM);
        PlayerID actual = game.getWinner();

        //assert
        assertEquals(want, actual);
    }

    @Test
    public void bombJokerLeadsToWinBothPlayersTest() {
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        GameManager manager = LogicFactory.makeGameManager(board, game);
        game.setIsStarted(true);

        board.setHighlight(List.of(Factory.makeField(0, 0, PlayerID.PLAYER_1)));
        manager.executeMove(Move.CONFIRM);
        manager.executeMove(Move.RIGHT);
        manager.executeMove(Move.RIGHT);
        manager.executeMove(Move.RIGHT);
        manager.executeMove(Move.RIGHT);

        String boardState = "........" +
                            "........" +
                            "..G...B." +
                            "..B...G." +
                            "..G.G.B." +
                            "..G.G.B." +
                            "..G.G.B.";

        TestUtility.createBoardState(board, boardState);

        PlayerID want = PlayerID.NONE;

        //act
        manager.executeMove(Move.CONFIRM);
        PlayerID actual = game.getWinner();

        //assert
        assertEquals(want, actual);
    }

    @Test
    public void bombJokerUpdateToGround() {
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        GameManager manager = LogicFactory.makeGameManager(board, game);
        game.setIsStarted(true);

        board.setHighlight(List.of(Factory.makeField(0, 0, PlayerID.PLAYER_1)));
        manager.executeMove(Move.CONFIRM);
        manager.executeMove(Move.RIGHT);
        manager.executeMove(Move.RIGHT);

        String boardState = "........"
                          +("........")
                          +("........")
                          +(".B......")
                          +(".G......")
                          +(".G......")
                          +(".GG.....");


        TestUtility.createBoardState(board, boardState);

        List<Field> want = new ArrayList<>(List.of(
                Factory.makeField(1, 7, PlayerID.PLAYER_2)
        ));

        //act
        manager.executeMove(Move.CONFIRM);
        List<Field> actual = game.getBoard().getFields();

        //assert
        assertEquals(want, actual);
    }

    @Test
    public void highlightAfterBombUse() {
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        GameManager manager = LogicFactory.makeGameManager(board, game);
        game.setIsStarted(true);

        board.setHighlight(List.of(Factory.makeField(0, 0, PlayerID.NONE)));
        manager.executeMove(Move.CONFIRM);
        manager.executeMove(Move.CONFIRM);

        List<Field> want = List.of(Factory.makeField(0, 1, PlayerID.NONE));

        //act
        List<Field> have = board.getHighlight();

        //assert
        assertEquals(want, have);
    }

    @Test
    public void highlightAfterBombUseSecondColumn() {
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        GameManager manager = LogicFactory.makeGameManager(board, game);
        game.setIsStarted(true);

        board.setHighlight(List.of(Factory.makeField(0, 0, PlayerID.NONE)));
        manager.executeMove(Move.CONFIRM);
        manager.executeMove(Move.RIGHT);
        manager.executeMove(Move.RIGHT);
        manager.executeMove(Move.RIGHT);
        manager.executeMove(Move.CONFIRM);

        List<Field> want = List.of(Factory.makeField(3, 1, PlayerID.NONE));

        //act
        List<Field> have = board.getHighlight();

        //assert
        assertEquals(want, have);
    }

    @Test
    public void highlightDuringBombUse() {
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        GameManager manager = LogicFactory.makeGameManager(board, game);

        //Start the game by selecting player 1
        manager.executeMove(Move.CONFIRM);

        String boardState =
                          "........"
                        + "........"
                        + "........"
                        + "..B....."
                        + "..B....."
                        + "..G....."
                        + "..G.....";

        TestUtility.createBoardState(board, boardState);

        manager.executeMove(Move.LEFT);
        manager.executeMove(Move.LEFT);
        manager.executeMove(Move.CONFIRM);
        manager.executeMove(Move.RIGHT);
        manager.executeMove(Move.RIGHT);

        String highlightArea =
                "..H....." +
                ".HHH...." +
                "HHHHH..." +
                ".HHH...." +
                "..H....." +
                "..H....." +
                "..H.....";

        List<Field> want = TestUtility.getHighlightedFieldList(highlightArea);

        //act
        List<Field> actual = board.getHighlight();

        //assert
        assertTrue(TestUtility.equalsListFields(actual, want));
    }

    @Test
    public void TestPlayerChangeAfterBombUse() {
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        GameManager manager = LogicFactory.makeGameManager(board, game);

        //Start the game by selecting player 1
        manager.executeMove(Move.CONFIRM);

        manager.executeMove(Move.LEFT);
        manager.executeMove(Move.LEFT);
        manager.executeMove(Move.CONFIRM);
        manager.executeMove(Move.CONFIRM);

        PlayerID want = PlayerID.PLAYER_2;

        //act
        PlayerID actual = game.getActivePlayer();

        //assert
        assertEquals(want, actual);
    }

    @Test
    public void TestBombJokerIsNotAvailableAfterUse() {
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        FullPlayer player1 = Factory.makePlayer(PlayerID.PLAYER_1);
        FullPlayer player2 = Factory.makePlayer(PlayerID.PLAYER_2);
        GameManager manager = LogicFactory.makeGameManager(board, game, player1, player2);

        //Start the game by selecting player 1
        manager.executeMove(Move.CONFIRM);

        manager.executeMove(Move.LEFT);
        manager.executeMove(Move.LEFT);
        manager.executeMove(Move.CONFIRM);
        manager.executeMove(Move.CONFIRM);

        PlayerID want = PlayerID.PLAYER_2;

        //act
        PlayerID actual = game.getActivePlayer();

        //assert
        assertTrue(player1.isBombJokerUsed());
    }

    @Test
    public void TestBombJokerLeftBorder(){
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        GameManager manager = LogicFactory.makeGameManager(board, game);

        String beforeBomb = "........" +
                            "........" +
                            "........" +
                            "........" +
                            ".BB....." +
                            ".BGG...." +
                            "GGBGB,..";
        TestUtility.createBoardState(board, beforeBomb);

        //Start the game by selecting player 1
        manager.executeMove(Move.CONFIRM);

        manager.executeMove(Move.LEFT);
        manager.executeMove(Move.LEFT);
        manager.executeMove(Move.CONFIRM);
        manager.executeMove(Move.CONFIRM);

        String afterBomb = "........" +
                "........" +
                "........" +
                "........" +
                "........" +
                "..BG...." +
                "..BGB,..";
        List<Field> want = TestUtility.getOccupiedFieldList(afterBomb);

        //act
        List<Field> actual = TestUtility.getOccupiedFieldList(afterBomb);

        assertEquals(want, actual);
    }

    @Test
    public void TestBombJokerLeftBottomCorner(){
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        GameManager manager = LogicFactory.makeGameManager(board, game);

        String beforeBomb = "........" +
                            "........" +
                            "........" +
                            "........" +
                            ".BB....." +
                            ".BGG...." +
                            ".GBGB,..";
        TestUtility.createBoardState(board, beforeBomb);

        //Start the game by selecting player 1
        manager.executeMove(Move.CONFIRM);

        manager.executeMove(Move.LEFT);
        manager.executeMove(Move.LEFT);
        manager.executeMove(Move.CONFIRM);
        manager.executeMove(Move.CONFIRM);

        String afterBomb = "........" +
                           "........" +
                           "........" +
                           "........" +
                           "........" +
                           "..BG...." +
                           "..BGB,..";
        List<Field> want = TestUtility.getOccupiedFieldList(afterBomb);

        //act
        List<Field> actual = TestUtility.getOccupiedFieldList(afterBomb);

        assertEquals(want, actual);
    }

    @Test
    public void TestBombJokerLeftTopCorner(){
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        GameManager manager = LogicFactory.makeGameManager(board, game);

        String beforeBomb = "........" +
                            "GGG....." +
                            "GGG....." +
                            "BBB....." +
                            "GGG....." +
                            "GGG....." +
                            "GGG.....";
        TestUtility.createBoardState(board, beforeBomb);

        //Start the game by selecting player 1
        manager.executeMove(Move.CONFIRM);

        manager.executeMove(Move.LEFT);
        manager.executeMove(Move.LEFT);
        manager.executeMove(Move.CONFIRM);
        manager.executeMove(Move.CONFIRM);

        String afterBomb = "........" +
                           "..G....." +
                           ".GG....." +
                           "BBB....." +
                           "GGG....." +
                           "GGG....." +
                           "GGG.....";
        List<Field> want = TestUtility.getOccupiedFieldList(afterBomb);

        //act
        List<Field> actual = TestUtility.getOccupiedFieldList(afterBomb);

        assertEquals(want, actual);
    }

    @Test
    public void bombJokerUsedTest(){
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        FullPlayer player1 = Factory.makePlayer(PlayerID.PLAYER_1);
        FullPlayer player2 = Factory.makePlayer(PlayerID.PLAYER_2);

        GameManager manager = LogicFactory.makeGameManager(board, game, player1, player2);

        String beforeBomb = "........" +
                "GGG....." +
                "GGG....." +
                "BBB....." +
                "GGG....." +
                "GGG....." +
                "GGG.....";
        TestUtility.createBoardState(board, beforeBomb);

        //Start the game by selecting player 1
        manager.executeMove(Move.CONFIRM);

        manager.executeMove(Move.LEFT);
        manager.executeMove(Move.LEFT);
        manager.executeMove(Move.CONFIRM);
        manager.executeMove(Move.CONFIRM);

        String afterBomb = "........" +
                "..G....." +
                ".GG....." +
                "BBB....." +
                "GGG....." +
                "GGG....." +
                "GGG.....";
        //act
        assertTrue(player1.isBombJokerUsed());
    }

    @Test
    public void bombJokerChangePlayerTest(){
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        FullPlayer player1 = Factory.makePlayer(PlayerID.PLAYER_1);
        FullPlayer player2 = Factory.makePlayer(PlayerID.PLAYER_2);

        GameManager manager = LogicFactory.makeGameManager(board, game, player1, player2);

        String beforeBomb = "........" +
                "GGG....." +
                "GGG....." +
                "BBB....." +
                "GGG....." +
                "GGG....." +
                "GGG.....";
        TestUtility.createBoardState(board, beforeBomb);

        //Start the game by selecting player 1
        manager.executeMove(Move.CONFIRM);

        manager.executeMove(Move.LEFT);
        manager.executeMove(Move.LEFT);
        manager.executeMove(Move.CONFIRM);
        manager.executeMove(Move.CONFIRM);

        String afterBomb = "........" +
                "..G....." +
                ".GG....." +
                "BBB....." +
                "GGG....." +
                "GGG....." +
                "GGG.....";
        //act
        PlayerID wantNextPlayer = PlayerID.PLAYER_2;
        PlayerID haveNextPlayer = game.getActivePlayer();
        assertEquals(wantNextPlayer, haveNextPlayer);
    }

    @Test
    public void bombJokerUsedTestActiveJokerNull(){
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        FullPlayer player1 = Factory.makePlayer(PlayerID.PLAYER_1);
        FullPlayer player2 = Factory.makePlayer(PlayerID.PLAYER_2);

        GameManager manager = LogicFactory.makeGameManager(board, game, player1, player2);

        String beforeBomb = "........" +
                "GGG....." +
                "GGG....." +
                "BBB....." +
                "GGG....." +
                "GGG....." +
                "GGG.....";
        TestUtility.createBoardState(board, beforeBomb);

        //Start the game by selecting player 1
        manager.executeMove(Move.CONFIRM);

        manager.executeMove(Move.LEFT);
        manager.executeMove(Move.LEFT);
        manager.executeMove(Move.CONFIRM);
        manager.executeMove(Move.CONFIRM);

        //act
        PlayerActiveJoker want = PlayerActiveJoker.NONE;
        PlayerActiveJoker have = game.getActiveJoker();
        assertEquals(want, have);
    }

}
