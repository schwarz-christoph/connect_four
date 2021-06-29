package edu.hm.scholz.enno.connect_four.control;

import edu.hm.scholz.enno.connect_four.datastore.Game;
import edu.hm.scholz.enno.connect_four.datastore.PlayerID;
import edu.hm.scholz.enno.connect_four.datastore.mutable.Factory;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullBoard;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullGame;
import edu.hm.scholz.enno.connect_four.logic.GameManager;
import edu.hm.scholz.enno.connect_four.logic.LogicFactory;
import org.junit.Test;

public class BotPreconditionTest {

    @Test (expected = IllegalArgumentException.class)
    public void botManagerNullTest() {

        //act
        Game game = Factory.makeGame(PlayerID.PLAYER_1);
        Bot testBot = new Bot(game, null, PlayerID.PLAYER_1);
    }

    @Test (expected = IllegalArgumentException.class)
    public void botPlayerIDNullTest() {

        //act
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1);
        FullBoard board = Factory.makeBoard();
        GameManager manager = LogicFactory.makeGameManager(board, game);
        Bot testBot = new Bot(game, manager, null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void botGameNullTest() {

        //act
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1);
        FullBoard board = Factory.makeBoard();
        GameManager manager = LogicFactory.makeGameManager(board, game);
        Bot testBot = new Bot(null, manager, PlayerID.PLAYER_1);
    }
}
