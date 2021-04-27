package edu.hm.se2.connect_four.csgles.datastore;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static org.junit.Assert.*;

public class ConnectFourFieldTest {

    @Rule
    public Timeout globalTimeout = Timeout.millis(1_000);

    @Test
    public void xCoordinate() {
        //assert
        assertEquals(wantX, haveX);
    }

    @Test
    public void yCoordinate() {
        //arrange
        final int x = 1;
        final int wantY = 2;
        final Field subjectUnderTestField = new ConnectFourField(x, wantY, PlayerID.PLAYER_1);

        //act
        final int haveY = subjectUnderTestField.yCoordinate();

        //assert
        assertEquals(wantY, haveY);
    }

    @Test
    public void owner() {
        //arrange
        final int x = 1;
        final int y = 2;
        final PlayerID wantID = PlayerID.PLAYER_1;
        final Field subjectUnderTestField = new ConnectFourField(x, y, wantID);

        //act
        final PlayerID haveID = subjectUnderTestField.owner();

        //assert
        assertEquals(wantID, haveID);
    }
}