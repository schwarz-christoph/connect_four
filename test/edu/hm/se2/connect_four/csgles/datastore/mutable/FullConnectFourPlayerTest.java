package edu.hm.se2.connect_four.csgles.datastore.mutable;

import edu.hm.se2.connect_four.csgles.datastore.PlayerID;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static org.junit.Assert.*;

public class FullConnectFourPlayerTest {

    @Rule
    public Timeout globalTimeout = Timeout.millis(1_000);

    FullBoard board = FullConnectFourBoard.make();
    FullGame subjectUnderTestGame = FullConnectFourGame.make(PlayerID.PLAYER_1, board);

    @Test
    public void useBombJokerTest() {
        //arrange
        final FullPlayer player = FullConnectFourPlayer.make(PlayerID.PLAYER_1);

        //act
        player.useBombJoker();

        //assert
        assertTrue(player.isBombJokerUsed());
    }

    @Test
    public void useDeleteJokerTest() {
        //arrange
        FullPlayer player = FullConnectFourPlayer.make(PlayerID.PLAYER_1);

        //act
        player.useDeleteJoker();

        //assert
        assertFalse(player.isDeleteJokerUsed());
    }

    @Test
    public void deleteJokerNotUsedTest() {
        //arrange
        final FullPlayer player = FullConnectFourPlayer.make(PlayerID.PLAYER_1);

        //assert
        assertFalse(player.isDeleteJokerUsed());
    }

    @Test
    public void getIdentifierTest() {
        //arrange
        FullPlayer player = FullConnectFourPlayer.make(PlayerID.PLAYER_1);
        PlayerID expectedPlayerID = PlayerID.PLAYER_1;

        //act
        PlayerID actualPlayerID = player.getIdentifier();

        //assert
        assertEquals(expectedPlayerID, actualPlayerID);
    }
}