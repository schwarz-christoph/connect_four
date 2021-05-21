package edu.hm.scholz.enno.connect_four.datastore.mutable;

import edu.hm.scholz.enno.connect_four.datastore.PlayerID;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static org.junit.Assert.*;

public class FullConnectFourPlayerTest {

    @Rule
    public Timeout globalTimeout = Timeout.millis(1_000);

    @Test (expected = IllegalArgumentException.class)
    public void createPlayerIdIsNull(){
        //arrange, act
        final FullPlayer player = Factory.makePlayer(null);
    }

    @Test
    public void useBombJokerTest() {
        //arrange
        final FullPlayer player = Factory.makePlayer(PlayerID.PLAYER_1);

        //act
        player.useBombJoker();

        //assert
        assertTrue(player.isBombJokerUsed());
    }

    @Test
    public void bombJokerNotUsedTest() {
        //arrange
        final FullPlayer player = Factory.makePlayer(PlayerID.PLAYER_1);
        //assert
        assertFalse(player.isBombJokerUsed());
    }

    @Test
    public void useDeleteJokerTest() {
        //arrange
        final FullPlayer player = Factory.makePlayer(PlayerID.PLAYER_1);

        //act
        player.useDeleteJoker();

        //assert
        assertTrue(player.isDeleteJokerUsed());
    }

    @Test
    public void deleteJokerNotUsedTest() {
        //arrange
        final FullPlayer player = Factory.makePlayer(PlayerID.PLAYER_1);

        //assert
        assertFalse(player.isDeleteJokerUsed());
    }

    @Test
    public void getIdentifierTest() {
        //arrange
        final FullPlayer player = Factory.makePlayer(PlayerID.PLAYER_1);
        final PlayerID expectedPlayerID = PlayerID.PLAYER_1;

        //act
        PlayerID actualPlayerID = player.getIdentifier();

        //assert
        assertEquals(expectedPlayerID, actualPlayerID);
    }
}