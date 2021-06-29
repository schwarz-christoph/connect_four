package edu.hm.scholz.enno.connect_four.view;

import edu.hm.scholz.enno.connect_four.TestUtility;
import edu.hm.scholz.enno.connect_four.datastore.Game;
import edu.hm.scholz.enno.connect_four.datastore.PlayerID;
import edu.hm.scholz.enno.connect_four.datastore.mutable.Factory;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullBoard;
import org.junit.Test;

import static org.junit.Assert.*;

public class ValidatingViewTest {

    @Test(expected = IllegalArgumentException.class)
    public void preconditionValidatingViewTest() {
        //arrange
        Game game = Factory.makeGame(PlayerID.PLAYER_1);
        ValidatingView view = new ValidatingView(null);
    }

    @Test
    public void invalidWinnerTest() {
        //arrange
        FullBoard board = Factory.makeBoard();
        Game game = Factory.makeGame(PlayerID.PLAYER_1, board);
        ValidatingView view = new ValidatingView(game);

        String boardState = "........" +
                "........" +
                "........" +
                "........" +
                "........" +
                "........" +
                "GGGG....";
        TestUtility.createBoardState(board, boardState);
        //manipulate board after game is marked as won -> validate should fail
        String boardStateManipulated = "........" +
                "........" +
                "........" +
                "........" +
                "........" +
                "........" +
                ".GGG....";
        TestUtility.createBoardState(board, boardStateManipulated);

        //act, assert
        assertFalse(view.validateGame());
    }
}