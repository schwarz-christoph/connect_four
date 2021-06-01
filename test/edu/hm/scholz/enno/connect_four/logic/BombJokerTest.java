package edu.hm.scholz.enno.connect_four.logic;

import edu.hm.scholz.enno.connect_four.TestUtility;
import edu.hm.scholz.enno.connect_four.datastore.Game;
import edu.hm.scholz.enno.connect_four.datastore.PlayerID;
import edu.hm.scholz.enno.connect_four.datastore.mutable.Factory;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullBoard;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullGame;
import edu.hm.scholz.enno.connect_four.datastore.Field;
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

        board.placeStone(Factory.makeField(1, 2, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(1, 3, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(1, 4, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(1, 5, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(1, 6, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(1, 7, PlayerID.PLAYER_2));

        board.setHighlight(List.of(Factory.makeField(0, 0, PlayerID.PLAYER_1)));
        manager.executeMove(Move.CONFIRM);
        manager.executeMove(Move.RIGHT);
        manager.executeMove(Move.LEFT);
        manager.executeMove(Move.RIGHT);

        List<Field> want = List.of(Factory.makeField(1, 4, PlayerID.PLAYER_2),
                Factory.makeField(1, 5, PlayerID.PLAYER_1),
                Factory.makeField(1, 6, PlayerID.PLAYER_2),
                Factory.makeField(1, 7, PlayerID.PLAYER_2));

        //act
        manager.executeMove(Move.CONFIRM);
        List<Field> actual = board.getFields();

        //assert
        assertEquals(want, actual);
    }

    @Test
    public void bombJokerHighlightTestHorizontalFilled(){
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        GameManager manager = LogicFactory.makeGameManager(board, game);
        game.setIsStarted(true);

        board.placeStone(Factory.makeField(0, 7, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(1, 7, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(2, 7, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(3, 7, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(4, 7, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(5, 7, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(6, 7, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(7, 7, PlayerID.PLAYER_1));

        board.setHighlight(List.of(Factory.makeField(0, 0, PlayerID.PLAYER_1)));
        manager.executeMove(Move.CONFIRM);
        manager.executeMove(Move.RIGHT);
        manager.executeMove(Move.RIGHT);
        manager.executeMove(Move.RIGHT);

        List<Field> want = List.of(Factory.makeField(0, 7, PlayerID.PLAYER_1),
                Factory.makeField(1, 7, PlayerID.PLAYER_2),
                Factory.makeField(5, 7, PlayerID.PLAYER_2),
                Factory.makeField(6, 7, PlayerID.PLAYER_2),
                Factory.makeField(7, 7, PlayerID.PLAYER_1));

        //act
        manager.executeMove(Move.CONFIRM);
        List<Field> actual = board.getFields();

        //assert
        assertEquals(want, actual);
    }

    @Test
    public void bombJokerHighlightTestAllSidesFilled(){
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        GameManager manager = LogicFactory.makeGameManager(board, game);
        game.setIsStarted(true);

        board.placeStone(Factory.makeField(0, 7, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(1, 7, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(2, 7, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(3, 7, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(4, 7, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(5, 7, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(6, 7, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(7, 7, PlayerID.PLAYER_2));

        board.placeStone(Factory.makeField(1, 6, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(2, 6, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(3, 6, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(4, 6, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(5, 6, PlayerID.PLAYER_2));

        board.placeStone(Factory.makeField(1, 5, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(2, 5, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(4, 5, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(5, 5, PlayerID.PLAYER_2));

        board.placeStone(Factory.makeField(1, 4, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(2, 4, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(4, 4, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(5, 4, PlayerID.PLAYER_1));

        board.setHighlight(List.of(Factory.makeField(0, 0, PlayerID.PLAYER_1)));
        manager.executeMove(Move.CONFIRM);
        manager.executeMove(Move.RIGHT);
        manager.executeMove(Move.RIGHT);
        manager.executeMove(Move.RIGHT);

        List<Field> want = List.of(
                Factory.makeField(0, 7, PlayerID.PLAYER_1),
                Factory.makeField(1, 7, PlayerID.PLAYER_2),
                Factory.makeField(2, 7, PlayerID.PLAYER_1),
                Factory.makeField(4, 7, PlayerID.PLAYER_1),
                Factory.makeField(5, 7, PlayerID.PLAYER_2),
                Factory.makeField(6, 7, PlayerID.PLAYER_1),
                Factory.makeField(7, 7, PlayerID.PLAYER_2),
                Factory.makeField(1, 6, PlayerID.PLAYER_2),
                Factory.makeField(5, 6, PlayerID.PLAYER_2),
                Factory.makeField(1, 5, PlayerID.PLAYER_1),

                Factory.makeField(5, 5, PlayerID.PLAYER_1));




        //act
        manager.executeMove(Move.CONFIRM);
        List<Field> actual = board.getFields();

        //assert
        assertEquals(want, actual);
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
    public void bombJokerTestNoOverflow() {
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        GameManager manager = LogicFactory.makeGameManager(board, game);
        game.setIsStarted(true);

        board.setHighlight(List.of(Factory.makeField(0, 0, PlayerID.PLAYER_1)));
        manager.executeMove(Move.CONFIRM);

        board.placeStone(Factory.makeField(0, 7, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(7, 7, PlayerID.PLAYER_1));

        List<Field> want = new ArrayList<>(List.of(Factory.makeField(7, 7, PlayerID.PLAYER_1)));
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

        board.placeStone(Factory.makeField(2, 7, PlayerID.PLAYER_1));

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

        board.placeStone(Factory.makeField(2, 7, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(2, 6, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(2, 5, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(2, 4, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(2, 3, PlayerID.PLAYER_1));

        board.placeStone(Factory.makeField(4, 7, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(4, 6, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(4, 5, PlayerID.PLAYER_1));

        board.placeStone(Factory.makeField(6, 7, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(6, 6, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(6, 5, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(6, 4, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(6, 3, PlayerID.PLAYER_2));

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
        List<Field> actual = board.getHighlight();

        //assert
        List<Field> have = board.getHighlight();
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
        List<Field> actual = board.getHighlight();

        //assert
        List<Field> have = board.getHighlight();
        assertEquals(want, have);
    }
}
