package edu.hm.scholz.enno.connect_four.datastore.mutable;

import edu.hm.scholz.enno.connect_four.datastore.Board;
import edu.hm.scholz.enno.connect_four.datastore.Game;
import edu.hm.scholz.enno.connect_four.datastore.PlayerActiveJoker;
import edu.hm.scholz.enno.connect_four.datastore.PlayerID;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static org.junit.Assert.*;

public class FullConnectFourGameTest {

    @Rule
    public Timeout globalTimeout = Timeout.millis(1_000);

    final FullGame subjectUnderTestGame = Factory.makeGame(PlayerID.PLAYER_1);

    @Test (expected = IllegalArgumentException.class)
    public void createGameNullPlayerArgumentTest() {
        //arrange, act
        final Board board = Factory.makeBoard();
        final Game game = Factory.makeGame(null, board);
    }

    @Test (expected = IllegalArgumentException.class)
    public void createGameNullBoardTest() {
        //arrange
        final Game game = Factory.makeGame(PlayerID.PLAYER_1, null);
    }


    @Test
    public void setActivePlayerTest() {
        //arrange
        final PlayerID want = PlayerID.PLAYER_2;

        //act
        subjectUnderTestGame.setActivePlayer(PlayerID.PLAYER_2);

        //assert
        assertEquals(want, subjectUnderTestGame.getActivePlayer());
    }

    @Test (expected = IllegalArgumentException.class)
    public void setActivePlayerNullTest() {
        //arrange
        final FullBoard board = Factory.makeBoard();
        final FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        game.setActivePlayer(null);
    }

    @Test
    public void setWinnerTest() {
        //arrange
        final PlayerID want = PlayerID.PLAYER_2;

        //act
        subjectUnderTestGame.setWinner(PlayerID.PLAYER_2);

        //assert
        assertEquals(want, subjectUnderTestGame.getWinner());
    }

    @Test (expected = IllegalArgumentException.class)
    public void setWinnerNullTest() {
        //arrange
        final PlayerID want = PlayerID.PLAYER_2;

        //act
        subjectUnderTestGame.setWinner(null);
    }

    @Test
    public void setIsStartedTest() {
        //arrange
        final boolean want = true;
        subjectUnderTestGame.setIsStarted(want);

        //assert
        assertEquals(want, subjectUnderTestGame.isStarted());
    }

    @Test
    public void setIsNotStartedTest() {
        //arrange
        final boolean want = false;
        subjectUnderTestGame.setIsStarted(want);

        //assert
        assertEquals(want, subjectUnderTestGame.isStarted());
    }

    @Test
    public void setPlayerCountTest() {
        //arrange;
        final int want = 1;

        //act
        subjectUnderTestGame.setPlayerCount(1);

        //assert
        assertEquals(want, subjectUnderTestGame.getPLayerCount());
    }


    @Test (expected = IllegalArgumentException.class)
    public void setPlayerCountLessThanNullTest() {
        //arrange;
        final int want = 1;

        //act
        subjectUnderTestGame.setPlayerCount(0);

    }

    @Test (expected = IllegalArgumentException.class)
    public void setPlayerCountMoreThanMaxPlayerCountTest() {
        //arrange;
        final int want = 1;
        //act
        subjectUnderTestGame.setPlayerCount(3);

    }

    @Test
    public void getBoardTest() {
        //arrange
        final Board board = Factory.makeBoard();
        final Game game = Factory.makeGame(PlayerID.PLAYER_1, board);
        //assert
        assertEquals(board, game.getBoard());
    }

    @Test
    public void setActiveJokerTest(){
        //arrange
        final FullBoard board = Factory.makeBoard();
        final FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        //act
        game.setActiveJoker(PlayerActiveJoker.DELETE);

        //assert
        PlayerActiveJoker want = PlayerActiveJoker.DELETE;
        PlayerActiveJoker have = game.getActiveJoker();
        assertEquals(want, have);
    }
}