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
    public void bombJokerHighlightTest(){
        //arrange
        FullBoard board = Factory.makeBoard();
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        GameManager manager = LogicFactory.makeGameManager(board, game);
        game.setIsStarted(true);

        board.placeStone(Factory.makeField(1, 2, PlayerID.PLAYER_1));
        board.placeStone(Factory.makeField(1, 3, PlayerID.PLAYER_2));
        board.placeStone(Factory.makeField(1, 4, PlayerID.PLAYER_2));

        board.setHighlight(List.of(Factory.makeField(0, 0, PlayerID.PLAYER_1)));
        manager.executeMove(Move.CONFIRM);
        manager.executeMove(Move.RIGHT);
        manager.executeMove(Move.CONFIRM);

        List<Field> want = List.of(Factory.makeField(3, 3, PlayerID.PLAYER_1));

        //act
        manager.executeMove(Move.CONFIRM);
        List<Field> actual = board.getFields();

        //assert
        assertEquals(want, actual);
    }
}
