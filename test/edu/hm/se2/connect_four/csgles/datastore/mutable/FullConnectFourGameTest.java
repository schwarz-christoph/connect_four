package edu.hm.se2.connect_four.csgles.datastore.mutable;

import edu.hm.se2.connect_four.csgles.datastore.PlayerID;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static org.junit.Assert.*;

public class FullConnectFourGameTest {

    @Rule
    public Timeout globalTimeout = Timeout.millis(1_000);

    final FullGame subjectUnderTestGame = Factory.makeGame(PlayerID.PLAYER_1);

    @Test
    public void setActivePlayerTest() {
        //arrange
        PlayerID want = PlayerID.PLAYER_2;

        //act
        subjectUnderTestGame.setActivePlayer(PlayerID.PLAYER_2);

        //assert
        assertEquals(want, subjectUnderTestGame.getActivePlayer());
    }

    @Test
    public void setWinnerTest() {
        //arrange
        PlayerID want = PlayerID.PLAYER_2;

        //act
        subjectUnderTestGame.setWinner(PlayerID.PLAYER_2);

        //assert
        assertEquals(want, subjectUnderTestGame.getWinner());
    }

    @Test
    public void setIsStartedTest() {
        //arrange
        boolean want = true;
        subjectUnderTestGame.setIsStarted(true);

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

    @Test
    public void getBoardTest() {

        //assert
        assertEquals(board, subjectUnderTestGame.getBoard());
    }
}