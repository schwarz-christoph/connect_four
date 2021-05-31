package edu.hm.scholz.enno.connect_four.logic;


import edu.hm.scholz.enno.connect_four.datastore.Field;
import edu.hm.scholz.enno.connect_four.datastore.PlayerID;
import edu.hm.scholz.enno.connect_four.datastore.mutable.Factory;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullBoard;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullGame;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import java.util.List;

import static org.junit.Assert.*;

public class DeleteJokerHighlightTest {
    @Rule
    public Timeout globalTimeout = Timeout.millis(1_000);

    @Test
    public void deleteJokerInitialHighlightTest(){
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_2, board);
        game.setIsStarted(true);
        board.setHighlight(List.of(Factory.makeField(6, 0, PlayerID.NONE))); //Delete Joker Player 2

        GameManager manager = LogicFactory.makeGameManager(board, game);

        //act
        manager.executeMove(Move.CONFIRM);

        //assert
        List<Field> want = List.of(Factory.makeField(0, 7, PlayerID.NONE));
        List<Field> have = board.getHighlight();

        assertEquals(want, have);
    }

    @Test
    public void deleteJokerInitialHighlightMoveTest(){
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_2, board);
        game.setIsStarted(true);
        board.setHighlight(List.of(Factory.makeField(6, 0, PlayerID.NONE))); //Delete Joker Player 2

        GameManager manager = LogicFactory.makeGameManager(board, game);

        //act
        manager.executeMove(Move.CONFIRM);
        manager.executeMove(Move.UP);
        manager.executeMove(Move.UP);
        manager.executeMove(Move.LEFT);
        manager.executeMove(Move.RIGHT);
        manager.executeMove(Move.DOWN);

        //assert
        List<Field> want = List.of(Factory.makeField(0, 6, PlayerID.NONE));
        List<Field> have = board.getHighlight();

        assertEquals(want, have);
    }

    @Test
    public void deleteJokerHighlightRowTest(){
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_2, board);
        game.setIsStarted(true);
        board.setHighlight(List.of(Factory.makeField(6, 0, PlayerID.NONE))); //Delete Joker Player 2

        GameManager manager = LogicFactory.makeGameManager(board, game);

        //act
        manager.executeMove(Move.CONFIRM);
        manager.executeMove(Move.LEFT);

        //assert
        List<Field> want = List.of(Factory.makeField(0, 7, PlayerID.NONE),
                Factory.makeField(1, 7, PlayerID.NONE),
                Factory.makeField(2, 7, PlayerID.NONE),
                Factory.makeField(3, 7, PlayerID.NONE),
                Factory.makeField(4, 7, PlayerID.NONE),
                Factory.makeField(5, 7, PlayerID.NONE),
                Factory.makeField(6, 7, PlayerID.NONE),
                Factory.makeField(7, 7, PlayerID.NONE));
        List<Field> have = board.getHighlight();

        assertEquals(want, have);
    }

    @Test
    public void deleteJokerHighlightMoveRowUpTest(){
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_2, board);
        game.setIsStarted(true);
        board.setHighlight(List.of(Factory.makeField(6, 0, PlayerID.NONE))); //Delete Joker Player 2

        GameManager manager = LogicFactory.makeGameManager(board, game);

        //act
        manager.executeMove(Move.CONFIRM);
        manager.executeMove(Move.LEFT);
        manager.executeMove(Move.UP);

        //assert
        List<Field> want = List.of(Factory.makeField(0, 6, PlayerID.NONE),
                Factory.makeField(1, 6, PlayerID.NONE),
                Factory.makeField(2, 6, PlayerID.NONE),
                Factory.makeField(3, 6, PlayerID.NONE),
                Factory.makeField(4, 6, PlayerID.NONE),
                Factory.makeField(5, 6, PlayerID.NONE),
                Factory.makeField(6, 6, PlayerID.NONE),
                Factory.makeField(7, 6, PlayerID.NONE));
        List<Field> have = board.getHighlight();

        assertEquals(want, have);
    }

    @Test
    public void deleteJokerHighlightRowNoMoveDownTest(){
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_2, board);
        game.setIsStarted(true);
        board.setHighlight(List.of(Factory.makeField(6, 0, PlayerID.NONE))); //Delete Joker Player 2

        GameManager manager = LogicFactory.makeGameManager(board, game);

        //act
        manager.executeMove(Move.CONFIRM);
        manager.executeMove(Move.LEFT);
        manager.executeMove(Move.DOWN);

        //assert
        List<Field> want = List.of(Factory.makeField(0, 7, PlayerID.NONE),
                Factory.makeField(1, 7, PlayerID.NONE),
                Factory.makeField(2, 7, PlayerID.NONE),
                Factory.makeField(3, 7, PlayerID.NONE),
                Factory.makeField(4, 7, PlayerID.NONE),
                Factory.makeField(5, 7, PlayerID.NONE),
                Factory.makeField(6, 7, PlayerID.NONE),
                Factory.makeField(7, 7, PlayerID.NONE));
        List<Field> have = board.getHighlight();

        assertEquals(want, have);
    }

    @Test
    public void deleteJokerHighlightRowWrapLeftTest(){
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_2, board);
        game.setIsStarted(true);
        board.setHighlight(List.of(Factory.makeField(6, 0, PlayerID.NONE))); //Delete Joker Player 2

        GameManager manager = LogicFactory.makeGameManager(board, game);

        //act
        manager.executeMove(Move.CONFIRM);
        manager.executeMove(Move.LEFT);
        manager.executeMove(Move.LEFT);

        //assert
        List<Field> want = List.of(Factory.makeField(7, 7, PlayerID.NONE));
        List<Field> have = board.getHighlight();

        assertEquals(want, have);
    }

    @Test
    public void deleteJokerHighlightRowWrapRightTest(){
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_2, board);
        game.setIsStarted(true);
        board.setHighlight(List.of(Factory.makeField(6, 0, PlayerID.NONE))); //Delete Joker Player 2

        GameManager manager = LogicFactory.makeGameManager(board, game);

        //act
        manager.executeMove(Move.CONFIRM);
        manager.executeMove(Move.LEFT);
        manager.executeMove(Move.RIGHT);

        //assert
        List<Field> want = List.of(Factory.makeField(0, 7, PlayerID.NONE));
        List<Field> have = board.getHighlight();

        assertEquals(want, have);
    }

    @Test
    public void deleteJokerHighlightMoveRightTest(){
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_2, board);
        game.setIsStarted(true);
        board.setHighlight(List.of(Factory.makeField(6, 0, PlayerID.NONE))); //Delete Joker Player 2
        GameManager manager = LogicFactory.makeGameManager(board, game);

        //act
        manager.executeMove(Move.CONFIRM);
        manager.executeMove(Move.RIGHT);

        //assert
        List<Field> want = List.of(Factory.makeField(1, 7, PlayerID.NONE));
        List<Field> have = board.getHighlight();

        assertEquals(want, have);
    }

    @Test
    public void deleteJokerHighlightRowRightSideMoveUpTest(){
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_2, board);
        game.setIsStarted(true);
        board.setHighlight(List.of(Factory.makeField(6, 0, PlayerID.NONE))); //Delete Joker Player 2
        GameManager manager = LogicFactory.makeGameManager(board, game);

        //act
        manager.executeMove(Move.CONFIRM);
        manager.executeMove(Move.RIGHT);
        manager.executeMove(Move.RIGHT);
        manager.executeMove(Move.RIGHT);
        manager.executeMove(Move.RIGHT);
        manager.executeMove(Move.RIGHT);
        manager.executeMove(Move.RIGHT);
        manager.executeMove(Move.RIGHT);
        manager.executeMove(Move.RIGHT);
        manager.executeMove(Move.UP);

        //assert
        List<Field> want = List.of(Factory.makeField(0, 6, PlayerID.NONE),
                Factory.makeField(1, 6, PlayerID.NONE),
                Factory.makeField(2, 6, PlayerID.NONE),
                Factory.makeField(3, 6, PlayerID.NONE),
                Factory.makeField(4, 6, PlayerID.NONE),
                Factory.makeField(5, 6, PlayerID.NONE),
                Factory.makeField(6, 6, PlayerID.NONE),
                Factory.makeField(7, 6, PlayerID.NONE));
        List<Field> have = board.getHighlight();

        assertEquals(want, have);
    }

    @Test
    public void deleteJokerHighlightRowRightSideNoMoveDownTest(){
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_2, board);
        game.setIsStarted(true);
        board.setHighlight(List.of(Factory.makeField(6, 0, PlayerID.NONE))); //Delete Joker Player 2
        GameManager manager = LogicFactory.makeGameManager(board, game);

        //act
        manager.executeMove(Move.CONFIRM);
        manager.executeMove(Move.RIGHT);
        manager.executeMove(Move.RIGHT);
        manager.executeMove(Move.RIGHT);
        manager.executeMove(Move.RIGHT);
        manager.executeMove(Move.RIGHT);
        manager.executeMove(Move.RIGHT);
        manager.executeMove(Move.RIGHT);
        manager.executeMove(Move.RIGHT);
        manager.executeMove(Move.DOWN);

        //assert
        List<Field> want = List.of(Factory.makeField(0, 7, PlayerID.NONE),
                Factory.makeField(1, 7, PlayerID.NONE),
                Factory.makeField(2, 7, PlayerID.NONE),
                Factory.makeField(3, 7, PlayerID.NONE),
                Factory.makeField(4, 7, PlayerID.NONE),
                Factory.makeField(5, 7, PlayerID.NONE),
                Factory.makeField(6, 7, PlayerID.NONE),
                Factory.makeField(7, 7, PlayerID.NONE));
        List<Field> have = board.getHighlight();

        assertEquals(want, have);
    }

    @Test
    public void deleteJokerHighlightRowRightSideMoveUpWrapLeftTest(){
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_2, board);
        game.setIsStarted(true);
        board.setHighlight(List.of(Factory.makeField(6, 0, PlayerID.NONE))); //Delete Joker Player 2
        GameManager manager = LogicFactory.makeGameManager(board, game);

        //act
        manager.executeMove(Move.CONFIRM);
        manager.executeMove(Move.RIGHT);
        manager.executeMove(Move.RIGHT);
        manager.executeMove(Move.RIGHT);
        manager.executeMove(Move.RIGHT);
        manager.executeMove(Move.RIGHT);
        manager.executeMove(Move.RIGHT);
        manager.executeMove(Move.RIGHT);
        manager.executeMove(Move.RIGHT);
        manager.executeMove(Move.UP);
        manager.executeMove(Move.LEFT);

        //assert
        List<Field> want = List.of(Factory.makeField(7, 6, PlayerID.NONE));
        List<Field> have = board.getHighlight();

        assertEquals(want, have);
    }

    @Test
    public void deleteJokerHighlightTopRowNoMoveUpTest(){
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_2, board);
        game.setIsStarted(true);
        board.setHighlight(List.of(Factory.makeField(6, 0, PlayerID.NONE))); //Delete Joker Player 2
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

        //assert
        List<Field> want = List.of(Factory.makeField(0, 1, PlayerID.NONE),
                Factory.makeField(1, 1, PlayerID.NONE),
                Factory.makeField(2, 1, PlayerID.NONE),
                Factory.makeField(3, 1, PlayerID.NONE),
                Factory.makeField(4, 1, PlayerID.NONE),
                Factory.makeField(5, 1, PlayerID.NONE),
                Factory.makeField(6, 1, PlayerID.NONE),
                Factory.makeField(7, 1, PlayerID.NONE));
        List<Field> have = board.getHighlight();

        assertEquals(want, have);
    }

    @Test
    public void deleteJokerHighlightColumnTest(){
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_2, board);
        game.setIsStarted(true);
        board.setHighlight(List.of(Factory.makeField(6, 0, PlayerID.NONE))); //Delete Joker Player 2

        GameManager manager = LogicFactory.makeGameManager(board, game);

        //act
        manager.executeMove(Move.CONFIRM);
        manager.executeMove(Move.DOWN);

        //assert
        List<Field> want = List.of(Factory.makeField(0, 1, PlayerID.NONE),
                Factory.makeField(0, 2, PlayerID.NONE),
                Factory.makeField(0, 3, PlayerID.NONE),
                Factory.makeField(0, 4, PlayerID.NONE),
                Factory.makeField(0, 5, PlayerID.NONE),
                Factory.makeField(0, 6, PlayerID.NONE),
                Factory.makeField(0, 7, PlayerID.NONE));
        List<Field> have = board.getHighlight();

        assertEquals(want, have);
    }

    @Test
    public void deleteJokerHighlightColumnMoveRightTest(){
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_2, board);
        game.setIsStarted(true);
        board.setHighlight(List.of(Factory.makeField(6, 0, PlayerID.NONE))); //Delete Joker Player 2

        GameManager manager = LogicFactory.makeGameManager(board, game);

        //act
        manager.executeMove(Move.CONFIRM);
        manager.executeMove(Move.DOWN);
        manager.executeMove(Move.RIGHT);

        //assert
        List<Field> want = List.of(Factory.makeField(1, 1, PlayerID.NONE),
                Factory.makeField(1, 2, PlayerID.NONE),
                Factory.makeField(1, 3, PlayerID.NONE),
                Factory.makeField(1, 4, PlayerID.NONE),
                Factory.makeField(1, 5, PlayerID.NONE),
                Factory.makeField(1, 6, PlayerID.NONE),
                Factory.makeField(1, 7, PlayerID.NONE));
        List<Field> have = board.getHighlight();

        assertEquals(want, have);
    }

    @Test
    public void deleteJokerHighlightColumnNoMoveLeftTest(){
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_2, board);
        game.setIsStarted(true);
        board.setHighlight(List.of(Factory.makeField(6, 0, PlayerID.NONE))); //Delete Joker Player 2

        GameManager manager = LogicFactory.makeGameManager(board, game);

        //act
        manager.executeMove(Move.CONFIRM);
        manager.executeMove(Move.DOWN);
        manager.executeMove(Move.LEFT);

        //assert
        List<Field> want = List.of(Factory.makeField(0, 1, PlayerID.NONE),
                Factory.makeField(0, 2, PlayerID.NONE),
                Factory.makeField(0, 3, PlayerID.NONE),
                Factory.makeField(0, 4, PlayerID.NONE),
                Factory.makeField(0, 5, PlayerID.NONE),
                Factory.makeField(0, 6, PlayerID.NONE),
                Factory.makeField(0, 7, PlayerID.NONE));
        List<Field> have = board.getHighlight();

        assertEquals(want, have);
    }

    @Test
    public void deleteJokerHighlightColumnWrapDownTest(){
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_2, board);
        game.setIsStarted(true);
        board.setHighlight(List.of(Factory.makeField(6, 0, PlayerID.NONE))); //Delete Joker Player 2

        GameManager manager = LogicFactory.makeGameManager(board, game);

        //act
        manager.executeMove(Move.CONFIRM);
        manager.executeMove(Move.DOWN);
        manager.executeMove(Move.DOWN);

        //assert
        List<Field> want = List.of(Factory.makeField(0, 1, PlayerID.NONE));
        List<Field> have = board.getHighlight();

        assertEquals(want, have);
    }

    @Test
    public void deleteJokerHighlightColumnWrapUpTest(){
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_2, board);
        game.setIsStarted(true);
        board.setHighlight(List.of(Factory.makeField(6, 0, PlayerID.NONE))); //Delete Joker Player 2

        GameManager manager = LogicFactory.makeGameManager(board, game);

        //act
        manager.executeMove(Move.CONFIRM);
        manager.executeMove(Move.DOWN);
        manager.executeMove(Move.UP);

        //assert
        List<Field> want = List.of(Factory.makeField(0, 7, PlayerID.NONE));
        List<Field> have = board.getHighlight();

        assertEquals(want, have);
    }

    @Test
    public void deleteJokerHighlightRightSideColumnTest(){
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_2, board);
        game.setIsStarted(true);
        board.setHighlight(List.of(Factory.makeField(6, 0, PlayerID.NONE))); //Delete Joker Player 2

        GameManager manager = LogicFactory.makeGameManager(board, game);

        //act
        manager.executeMove(Move.CONFIRM);
        board.setHighlight(List.of(Factory.makeField(7, 7, PlayerID.NONE)));
        manager.executeMove(Move.DOWN);

        //assert
        List<Field> want = List.of(Factory.makeField(7, 1, PlayerID.NONE),
                Factory.makeField(7, 2, PlayerID.NONE),
                Factory.makeField(7, 3, PlayerID.NONE),
                Factory.makeField(7, 4, PlayerID.NONE),
                Factory.makeField(7, 5, PlayerID.NONE),
                Factory.makeField(7, 6, PlayerID.NONE),
                Factory.makeField(7, 7, PlayerID.NONE));
        List<Field> have = board.getHighlight();

        assertEquals(want, have);
    }

    @Test
    public void deleteJokerHighlightColumnNoMoveRightTest(){
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_2, board);
        game.setIsStarted(true);
        board.setHighlight(List.of(Factory.makeField(6, 0, PlayerID.NONE))); //Delete Joker Player 2

        GameManager manager = LogicFactory.makeGameManager(board, game);

        //act
        manager.executeMove(Move.CONFIRM);
        board.setHighlight(List.of(Factory.makeField(7, 7, PlayerID.NONE)));
        manager.executeMove(Move.DOWN);
        manager.executeMove(Move.RIGHT);

        //assert
        List<Field> want = List.of(Factory.makeField(7, 1, PlayerID.NONE),
                Factory.makeField(7, 2, PlayerID.NONE),
                Factory.makeField(7, 3, PlayerID.NONE),
                Factory.makeField(7, 4, PlayerID.NONE),
                Factory.makeField(7, 5, PlayerID.NONE),
                Factory.makeField(7, 6, PlayerID.NONE),
                Factory.makeField(7, 7, PlayerID.NONE));
        List<Field> have = board.getHighlight();

        assertEquals(want, have);
    }
}
