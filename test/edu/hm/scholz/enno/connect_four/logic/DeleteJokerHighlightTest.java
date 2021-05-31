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
    public void deleteJokerHighlightTest(){

        //arange
        FullGame game = Factory.makeGame(PlayerID.PLAYER_2);
        game.setIsStarted(true);
        FullBoard board = Factory.makeBoard();
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
    public void deleteJokerHighlightRowTest(){

        //arange
        FullGame game = Factory.makeGame(PlayerID.PLAYER_2);
        game.setIsStarted(true);
        FullBoard board = Factory.makeBoard();
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
                Factory.makeField(5, 7, PlayerID.NONE),
                Factory.makeField(6, 7, PlayerID.NONE),
                Factory.makeField(7, 7, PlayerID.NONE));
        List<Field> have = board.getHighlight();


        assertEquals(want, have);

    }

    @Test
    public void deleteJokerHighlightRow2Test(){

        //arange
        FullGame game = Factory.makeGame(PlayerID.PLAYER_2);
        game.setIsStarted(true);
        FullBoard board = Factory.makeBoard();
        board.setHighlight(List.of(Factory.makeField(6, 0, PlayerID.NONE))); //Delete Joker Player 2

        GameManager manager = LogicFactory.makeGameManager(board, game);

        //act
        manager.executeMove(Move.CONFIRM);
        manager.executeMove(Move.LEFT);
        manager.executeMove(Move.UP);

        //assert
        List<Field> want = List.of(Factory.makeField(0, 6, PlayerID.NONE),
                Factory.makeField(1, 6, PlayerID.NONE),
                Factory.makeField(3, 6, PlayerID.NONE),
                Factory.makeField(4, 6, PlayerID.NONE),
                Factory.makeField(5, 6, PlayerID.NONE),
                Factory.makeField(5, 6, PlayerID.NONE),
                Factory.makeField(6, 6, PlayerID.NONE),
                Factory.makeField(7, 6, PlayerID.NONE));
        List<Field> have = board.getHighlight();

        assertEquals(want, have);

    }

    @Test
    public void deleteJokerHighlightRow3Test(){

        //arange
        FullGame game = Factory.makeGame(PlayerID.PLAYER_2);
        game.setIsStarted(true);
        FullBoard board = Factory.makeBoard();
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
                Factory.makeField(5, 7, PlayerID.NONE),
                Factory.makeField(6, 7, PlayerID.NONE),
                Factory.makeField(7, 7, PlayerID.NONE));
        List<Field> have = board.getHighlight();


        assertEquals(want, have);

    }

    @Test
    public void deleteJokerHighlightRow4Test(){

        //arange
        FullGame game = Factory.makeGame(PlayerID.PLAYER_2);
        game.setIsStarted(true);
        FullBoard board = Factory.makeBoard();
        board.setHighlight(List.of(Factory.makeField(6, 0, PlayerID.NONE))); //Delete Joker Player 2

        GameManager manager = LogicFactory.makeGameManager(board, game);

        //act
        manager.executeMove(Move.CONFIRM);
        manager.executeMove(Move.LEFT);
        manager.executeMove(Move.UP);
        manager.executeMove(Move.LEFT);

        //assert
        List<Field> want = List.of(Factory.makeField(0, 6, PlayerID.NONE));
        List<Field> have = board.getHighlight();


        assertEquals(want, have);

    }

    @Test
    public void deleteJokerHighlightRow5Test(){

        //arange
        FullGame game = Factory.makeGame(PlayerID.PLAYER_2);
        game.setIsStarted(true);
        FullBoard board = Factory.makeBoard();
        board.setHighlight(List.of(Factory.makeField(6, 0, PlayerID.NONE))); //Delete Joker Player 2
        GameManager manager = LogicFactory.makeGameManager(board, game);

        //act
        manager.executeMove(Move.CONFIRM);
        manager.executeMove(Move.RIGHT);

        //assert
        List<Field> want = List.of(Factory.makeField(7, 7, PlayerID.NONE),
                Factory.makeField(1, 7, PlayerID.NONE),
                Factory.makeField(2, 7, PlayerID.NONE),
                Factory.makeField(3, 7, PlayerID.NONE),
                Factory.makeField(4, 7, PlayerID.NONE),
                Factory.makeField(5, 7, PlayerID.NONE),
                Factory.makeField(5, 7, PlayerID.NONE),
                Factory.makeField(6, 7, PlayerID.NONE),
                Factory.makeField(7, 7, PlayerID.NONE));
        List<Field> have = board.getHighlight();


        assertEquals(want, have);

    }

    @Test
    public void deleteJokerHighlightRow6Test(){

        //arange
        FullGame game = Factory.makeGame(PlayerID.PLAYER_2);
        game.setIsStarted(true);
        FullBoard board = Factory.makeBoard();
        board.setHighlight(List.of(Factory.makeField(6, 0, PlayerID.NONE))); //Delete Joker Player 2
        GameManager manager = LogicFactory.makeGameManager(board, game);

        //act
        manager.executeMove(Move.CONFIRM);
        manager.executeMove(Move.RIGHT);
        manager.executeMove(Move.UP);
        manager.executeMove(Move.RIGHT);

        //assert
        List<Field> want = List.of(Factory.makeField(7, 6, PlayerID.NONE));
        List<Field> have = board.getHighlight();


        assertEquals(want, have);

    }

    @Test
    public void deleteJokerHighlightRow7Test(){

        //arange
        FullGame game = Factory.makeGame(PlayerID.PLAYER_2);
        game.setIsStarted(true);
        FullBoard board = Factory.makeBoard();
        board.setHighlight(List.of(Factory.makeField(6, 0, PlayerID.NONE))); //Delete Joker Player 2
        GameManager manager = LogicFactory.makeGameManager(board, game);

        //act
        manager.executeMove(Move.CONFIRM);
        manager.executeMove(Move.RIGHT);
        manager.executeMove(Move.UP);
        manager.executeMove(Move.LEFT);

        //assert
        List<Field> want = List.of(Factory.makeField(7, 6, PlayerID.NONE));
        List<Field> have = board.getHighlight();


        assertEquals(want, have);

    }

    @Test
    public void deleteJokerHighlightColumnTest(){

        //arange
        FullGame game = Factory.makeGame(PlayerID.PLAYER_2);
        game.setIsStarted(true);
        FullBoard board = Factory.makeBoard();
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
    public void deleteJokerHighlightColumn2Test(){

        //arange
        FullGame game = Factory.makeGame(PlayerID.PLAYER_2);
        game.setIsStarted(true);
        FullBoard board = Factory.makeBoard();
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
    public void deleteJokerHighlightColumn3Test(){

        //arange
        FullGame game = Factory.makeGame(PlayerID.PLAYER_2);
        game.setIsStarted(true);
        FullBoard board = Factory.makeBoard();
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
    public void deleteJokerHighlightColumn4Test(){

        //arange
        FullGame game = Factory.makeGame(PlayerID.PLAYER_2);
        game.setIsStarted(true);
        FullBoard board = Factory.makeBoard();
        board.setHighlight(List.of(Factory.makeField(6, 0, PlayerID.NONE))); //Delete Joker Player 2

        GameManager manager = LogicFactory.makeGameManager(board, game);

        //act
        manager.executeMove(Move.CONFIRM);
        manager.executeMove(Move.DOWN);
        manager.executeMove(Move.RIGHT);
        manager.executeMove(Move.DOWN);

        //assert
        List<Field> want = List.of(Factory.makeField(1, 1, PlayerID.NONE));
        List<Field> have = board.getHighlight();


        assertEquals(want, have);

    }

    @Test
    public void deleteJokerHighlightColumn5Test(){

        //arange
        FullGame game = Factory.makeGame(PlayerID.PLAYER_2);
        game.setIsStarted(true);
        FullBoard board = Factory.makeBoard();
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
    public void deleteJokerHighlightColumn6Test(){

        //arange
        FullGame game = Factory.makeGame(PlayerID.PLAYER_2);
        game.setIsStarted(true);
        FullBoard board = Factory.makeBoard();
        board.setHighlight(List.of(Factory.makeField(6, 0, PlayerID.NONE))); //Delete Joker Player 2

        GameManager manager = LogicFactory.makeGameManager(board, game);

        //act
        manager.executeMove(Move.CONFIRM);
        manager.executeMove(Move.DOWN);
        manager.executeMove(Move.RIGHT);
        manager.executeMove(Move.UP);

        //assert
        List<Field> want = List.of(Factory.makeField(1, 1, PlayerID.NONE));
        List<Field> have = board.getHighlight();


        assertEquals(want, have);

    }
}
