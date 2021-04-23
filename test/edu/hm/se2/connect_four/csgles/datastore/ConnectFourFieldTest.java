package edu.hm.se2.connect_four.csgles.datastore;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static org.junit.Assert.*;

public class ConnectFourFieldTest {

    @Rule
    public Timeout globalTimeout = Timeout.millis(1_000);

    Field subjectUnderTestField = new ConnectFourField(1,2,PlayerID.PLAYER_1);

    @Test
    public void xCoordinate() {
        //assert
        assertEquals(1, subjectUnderTestField.xCoordinate());
    }

    @Test
    public void yCoordinate() {
        //assert
        assertEquals(2, subjectUnderTestField.yCoordinate());
    }

    @Test
    public void owner() {
        //assert
        assertEquals(PlayerID.PLAYER_1, subjectUnderTestField.owner());
    }
}