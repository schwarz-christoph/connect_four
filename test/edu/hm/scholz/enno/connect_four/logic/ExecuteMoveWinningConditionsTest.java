package edu.hm.scholz.enno.connect_four.logic;

import edu.hm.scholz.enno.connect_four.TestUtility;
import edu.hm.scholz.enno.connect_four.datastore.PlayerID;
import edu.hm.scholz.enno.connect_four.datastore.mutable.Factory;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullBoard;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullGame;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ExecuteMoveWinningConditionsTest {

    @Rule
    public Timeout globalTimeout = Timeout.millis(1_000);

    @Test
    public void executeMoveHorizontalRightTest() {

        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        game.setIsStarted(true);
        GameManager manager = LogicFactory.makeGameManager(board, game);


        board.setHighlight(List.of(Factory.makeField(3, 1, PlayerID.NONE),
                Factory.makeField(3, 2, PlayerID.NONE),
                Factory.makeField(3, 3, PlayerID.NONE),
                Factory.makeField(3, 4, PlayerID.NONE),
                Factory.makeField(3, 5, PlayerID.NONE),
                Factory.makeField(3, 6, PlayerID.NONE),
                Factory.makeField(3, 7, PlayerID.NONE)));

        //arrange stones
        String boardState = "........" +
                "........" +
                "........" +
                "........" +
                "........" +
                "........" +
                "GGG.....";
        TestUtility.createBoardState(board, boardState);
        PlayerID wantWinner = PlayerID.PLAYER_1;

        //act
        manager.executeMove(Move.CONFIRM);
        PlayerID actualWinner = game.getWinner();

        //assert
        assertEquals(wantWinner, actualWinner);
    }

    @Test
    public void executeMoveHorizontalRightTestPlayer2() {

        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_2, board);
        game.setIsStarted(true);
        GameManager manager = LogicFactory.makeGameManager(board, game);

        board.setHighlight(List.of(Factory.makeField(3, 1, PlayerID.NONE),
                Factory.makeField(3, 2, PlayerID.NONE),
                Factory.makeField(3, 3, PlayerID.NONE),
                Factory.makeField(3, 4, PlayerID.NONE),
                Factory.makeField(3, 5, PlayerID.NONE),
                Factory.makeField(3, 6, PlayerID.NONE),
                Factory.makeField(3, 7, PlayerID.NONE)));

        //arrange stones
        String boardState = "........" +
                "........" +
                "........" +
                "........" +
                "........" +
                "........" +
                "BBB.....";
        TestUtility.createBoardState(board, boardState);
        PlayerID wantWinner = PlayerID.PLAYER_2;

        //act
        manager.executeMove(Move.CONFIRM);
        PlayerID actualWinner = game.getWinner();

        //assert
        assertEquals(wantWinner, actualWinner);
    }

    @Test
    public void executeMoveHorizontalLeftTest() {

        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        game.setIsStarted(true);
        GameManager manager = LogicFactory.makeGameManager(board, game);

        board.setHighlight(List.of(Factory.makeField(0, 1, PlayerID.NONE),
                Factory.makeField(0, 2, PlayerID.NONE),
                Factory.makeField(0, 3, PlayerID.NONE),
                Factory.makeField(0, 4, PlayerID.NONE),
                Factory.makeField(0, 5, PlayerID.NONE),
                Factory.makeField(0, 6, PlayerID.NONE),
                Factory.makeField(0, 7, PlayerID.NONE)));

        //arrange stones
        String boardState = "........" +
                "........" +
                "........" +
                "........" +
                "........" +
                "........" +
                ".GGG....";
        TestUtility.createBoardState(board, boardState);
        PlayerID wantWinner = PlayerID.PLAYER_1;

        //act
        manager.executeMove(Move.CONFIRM);
        PlayerID actualWinner = game.getWinner();

        //assert
        assertEquals(wantWinner, actualWinner);
    }

    @Test
    public void executeMoveVerticalTest() {

        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        game.setIsStarted(true);
        GameManager manager = LogicFactory.makeGameManager(board, game);

        board.setHighlight(List.of(Factory.makeField(0, 1, PlayerID.NONE),
                Factory.makeField(0, 2, PlayerID.NONE),
                Factory.makeField(0, 3, PlayerID.NONE),
                Factory.makeField(0, 4, PlayerID.NONE),
                Factory.makeField(0, 5, PlayerID.NONE),
                Factory.makeField(0, 6, PlayerID.NONE),
                Factory.makeField(0, 7, PlayerID.NONE)));

        //arrange stones
        String boardState = "........" +
                "........" +
                "........" +
                "........" +
                "G......." +
                "G......." +
                "G.......";
        TestUtility.createBoardState(board, boardState);
        PlayerID wantWinner = PlayerID.PLAYER_1;

        //act
        manager.executeMove(Move.CONFIRM);
        PlayerID actualWinner = game.getWinner();

        //assert
        assertEquals(wantWinner, actualWinner);
    }

    @Test
    public void executeMoveDiagonalUpperRightTest() {

        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        game.setIsStarted(true);
        GameManager manager = LogicFactory.makeGameManager(board, game);

        board.setHighlight(List.of(Factory.makeField(3, 1, PlayerID.NONE),
                Factory.makeField(3, 2, PlayerID.NONE),
                Factory.makeField(3, 3, PlayerID.NONE),
                Factory.makeField(3, 4, PlayerID.NONE),
                Factory.makeField(3, 5, PlayerID.NONE),
                Factory.makeField(3, 6, PlayerID.NONE),
                Factory.makeField(3, 7, PlayerID.NONE)));

        //arrange stones
        String boardState = "........" +
                "........" +
                "........" +
                "........" +
                "..GB...." +
                ".GBB...." +
                "GBBB....";
        TestUtility.createBoardState(board, boardState);
        PlayerID wantWinner = PlayerID.PLAYER_1;

        //act
        manager.executeMove(Move.CONFIRM);
        PlayerID actualWinner = game.getWinner();

        //assert
        assertEquals(wantWinner, actualWinner);
    }

    @Test
    public void executeMoveDiagonalUpperRightTest2() {

        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        game.setIsStarted(true);
        GameManager manager = LogicFactory.makeGameManager(board, game);

        board.setHighlight(List.of(Factory.makeField(7, 1, PlayerID.NONE),
                Factory.makeField(7, 2, PlayerID.NONE),
                Factory.makeField(7, 3, PlayerID.NONE),
                Factory.makeField(7, 4, PlayerID.NONE),
                Factory.makeField(7, 5, PlayerID.NONE),
                Factory.makeField(7, 6, PlayerID.NONE),
                Factory.makeField(7, 7, PlayerID.NONE)));

        //arrange stones
        String boardState = "........" +
                "........" +
                "........" +
                "........" +
                "......GB" +
                ".....GBB" +
                "....GBBB";
        TestUtility.createBoardState(board, boardState);
        PlayerID wantWinner = PlayerID.PLAYER_1;

        //act
        manager.executeMove(Move.CONFIRM);
        PlayerID actualWinner = game.getWinner();

        //assert
        assertEquals(wantWinner, actualWinner);
    }

    @Test
    public void executeMoveDiagonalUpperRightTest3() {

        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        game.setIsStarted(true);
        GameManager manager = LogicFactory.makeGameManager(board, game);

        board.setHighlight(List.of(Factory.makeField(7, 1, PlayerID.NONE),
                Factory.makeField(7, 2, PlayerID.NONE),
                Factory.makeField(7, 3, PlayerID.NONE),
                Factory.makeField(7, 4, PlayerID.NONE),
                Factory.makeField(7, 5, PlayerID.NONE),
                Factory.makeField(7, 6, PlayerID.NONE),
                Factory.makeField(7, 7, PlayerID.NONE)));

        //arrange stones
        String boardState = "........" +
                "......GB" +
                ".....GBB" +
                "....GBGG" +
                "....GBGG" +
                "....BGBB" +
                "....BGBG";
        TestUtility.createBoardState(board, boardState);

        PlayerID wantWinner = PlayerID.PLAYER_1;

        //act
        manager.executeMove(Move.CONFIRM);
        PlayerID actualWinner = game.getWinner();

        //assert
        assertEquals(wantWinner, actualWinner);
    }

    @Test
    public void executeMoveDiagonalUpperRightTest4() {

        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        game.setIsStarted(true);
        GameManager manager = LogicFactory.makeGameManager(board, game);

        board.setHighlight(List.of(Factory.makeField(3, 1, PlayerID.NONE),
                Factory.makeField(3, 2, PlayerID.NONE),
                Factory.makeField(3, 3, PlayerID.NONE),
                Factory.makeField(3, 4, PlayerID.NONE),
                Factory.makeField(3, 5, PlayerID.NONE),
                Factory.makeField(3, 6, PlayerID.NONE),
                Factory.makeField(3, 7, PlayerID.NONE)));

        //arrange stones
        String boardState = "........" +
                "..GB...." +
                ".GBB...." +
                "GBGG...." +
                "GBGG...." +
                "BGBB...." +
                "BGBG....";
        TestUtility.createBoardState(board, boardState);

        PlayerID wantWinner = PlayerID.PLAYER_1;

        //act
        manager.executeMove(Move.CONFIRM);
        PlayerID actualWinner = game.getWinner();

        //assert
        assertEquals(wantWinner, actualWinner);
    }

    @Test
    public void executeMoveDiagonalUpperLeftTest() {

        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        game.setIsStarted(true);
        GameManager manager = LogicFactory.makeGameManager(board, game);

        board.setHighlight(List.of(Factory.makeField(0, 1, PlayerID.NONE),
                Factory.makeField(0, 2, PlayerID.NONE),
                Factory.makeField(0, 3, PlayerID.NONE),
                Factory.makeField(0, 4, PlayerID.NONE),
                Factory.makeField(0, 5, PlayerID.NONE),
                Factory.makeField(0, 6, PlayerID.NONE),
                Factory.makeField(0, 7, PlayerID.NONE)));

        //arrange stones
        String boardState = "........" +
                "........" +
                "........" +
                "........" +
                "BG......" +
                "BBG....." +
                "BBBG....";
        TestUtility.createBoardState(board, boardState);
        PlayerID wantWinner = PlayerID.PLAYER_1;

        //act
        manager.executeMove(Move.CONFIRM);
        PlayerID actualWinner = game.getWinner();

        //assert
        assertEquals(wantWinner, actualWinner);
    }

    @Test
    public void executeMoveDiagonalUpperLeftTest3() {

        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        game.setIsStarted(true);
        GameManager manager = LogicFactory.makeGameManager(board, game);

        board.setHighlight(List.of(Factory.makeField(0, 1, PlayerID.NONE),
                Factory.makeField(0, 2, PlayerID.NONE),
                Factory.makeField(0, 3, PlayerID.NONE),
                Factory.makeField(0, 4, PlayerID.NONE),
                Factory.makeField(0, 5, PlayerID.NONE),
                Factory.makeField(0, 6, PlayerID.NONE),
                Factory.makeField(0, 7, PlayerID.NONE)));

        //arrange stones
        String boardState = "........" +
                "BG......" +
                "BBG....." +
                "GGBG...." +
                "GGBG...." +
                "BBGB...." +
                "BBGB....";
        TestUtility.createBoardState(board, boardState);

        PlayerID wantWinner = PlayerID.PLAYER_1;

        //act
        manager.executeMove(Move.CONFIRM);
        PlayerID actualWinner = game.getWinner();

        //assert
        assertEquals(wantWinner, actualWinner);
    }

    @Test
    public void executeMoveDiagonalUpperLeftTest4() {

        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        game.setIsStarted(true);
        GameManager manager = LogicFactory.makeGameManager(board, game);

        board.setHighlight(List.of(Factory.makeField(4, 1, PlayerID.NONE),
                Factory.makeField(4, 2, PlayerID.NONE),
                Factory.makeField(4, 3, PlayerID.NONE),
                Factory.makeField(4, 4, PlayerID.NONE),
                Factory.makeField(4, 5, PlayerID.NONE),
                Factory.makeField(4, 6, PlayerID.NONE),
                Factory.makeField(4, 7, PlayerID.NONE)));

        //arrange stones
        String boardState = "........" +
                "....BG.." +
                "....BBG." +
                "....GGBG" +
                "....GGBG" +
                "....BBGB" +
                "....BBGB";
        TestUtility.createBoardState(board, boardState);
        PlayerID wantWinner = PlayerID.PLAYER_1;

        //act
        manager.executeMove(Move.CONFIRM);
        PlayerID actualWinner = game.getWinner();

        //assert
        assertEquals(wantWinner, actualWinner);
    }

    @Test
    public void executeMoveDiagonalUpperLeftTest5() {

        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        game.setIsStarted(true);
        GameManager manager = LogicFactory.makeGameManager(board, game);

        board.setHighlight(List.of(Factory.makeField(4, 1, PlayerID.NONE),
                Factory.makeField(4, 2, PlayerID.NONE),
                Factory.makeField(4, 3, PlayerID.NONE),
                Factory.makeField(4, 4, PlayerID.NONE),
                Factory.makeField(4, 5, PlayerID.NONE),
                Factory.makeField(4, 6, PlayerID.NONE),
                Factory.makeField(4, 7, PlayerID.NONE)));

        //arrange stones
        String boardState = "........" +
                "........" +
                "........" +
                "........" +
                "....BG.." +
                "....BBG." +
                "....BBBG";
        TestUtility.createBoardState(board, boardState);
        PlayerID wantWinner = PlayerID.PLAYER_1;

        //act
        manager.executeMove(Move.CONFIRM);
        PlayerID actualWinner = game.getWinner();

        //assert
        assertEquals(wantWinner, actualWinner);
    }
}
