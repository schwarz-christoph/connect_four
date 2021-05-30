package edu.hm.scholz.enno.connect_four.logic;

import edu.hm.scholz.enno.connect_four.datastore.Game;
import edu.hm.scholz.enno.connect_four.datastore.PlayerID;
import edu.hm.scholz.enno.connect_four.datastore.mutable.Factory;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullBoard;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullGame;
import edu.hm.scholz.enno.connect_four.datastore.Field;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;

public class bombJokerTest {

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
}
