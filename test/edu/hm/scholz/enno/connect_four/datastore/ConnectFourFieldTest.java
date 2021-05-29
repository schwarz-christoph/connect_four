package edu.hm.scholz.enno.connect_four.datastore;

import edu.hm.scholz.enno.connect_four.common.Settings;
import edu.hm.scholz.enno.connect_four.datastore.mutable.Factory;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static org.junit.Assert.*;

public class ConnectFourFieldTest {

    @Rule
    public Timeout globalTimeout = Timeout.millis(1_000);

    @Test
    public void xCoordinateTest() {
        //arrange
        final int wantX = 1;
        final int y = 1;
        final Field subjectUnderTestField = Factory.makeField(wantX, y, PlayerID.PLAYER_1);

        //act
        final int haveX = subjectUnderTestField.xCoordinate();

        //assert
        assertEquals(wantX, haveX);
    }

    @Test (expected = IllegalArgumentException.class)
    public void xCoordinateOutOfBoundsNegativeTest() {
        //arrange
        final int x = -1;
        final int y = 1;
        final Field subjectUnderTestField = Factory.makeField(x, y, PlayerID.PLAYER_1);
    }

    @Test (expected = IllegalArgumentException.class)
    public void xCoordinateOutOfBoundsTest() {
        //arrange
        final int x = Settings.fieldSize;
        final int y = 1;
        final Field subjectUnderTestField = Factory.makeField(x, y, PlayerID.PLAYER_1);
    }

    @Test
    public void yCoordinateTest() {
        //arrange
        final int x = 1;
        final int wantY = 2;
        final Field subjectUnderTestField = Factory.makeField(x, wantY, PlayerID.PLAYER_1);

        //act
        final int haveY = subjectUnderTestField.yCoordinate();

        //assert
        assertEquals(wantY, haveY);
    }

    @Test (expected = IllegalArgumentException.class)
    public void yCoordinateOutOfBoundsNegativeTest() {
        //arrange
        final int x = 1;
        final int y = -1;
        final Field subjectUnderTestField = Factory.makeField(x, y, PlayerID.PLAYER_1);
    }

    @Test (expected = IllegalArgumentException.class)
    public void yCoordinateOutOfBoundsTest() {
        //arrange
        final int x = 1;
        final int y = Settings.fieldSize;
        final Field subjectUnderTestField = Factory.makeField(x, y, PlayerID.PLAYER_1);
    }

    @Test
    public void ownerTest() {
        //arrange
        final int x = 1;
        final int y = 2;
        final PlayerID wantID = PlayerID.PLAYER_1;
        final Field subjectUnderTestField = Factory.makeField(x, y, wantID);

        //act
        final PlayerID haveID = subjectUnderTestField.owner();

        //assert
        assertEquals(wantID, haveID);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ownerNullTest() {
        //arrange
        final int x = 1;
        final int y = 2;
        final Field subjectUnderTestField = Factory.makeField(x, y, null);
    }

    @Test
    public void equalsTestTrue() {
        //arrange
        final int x = 1;
        final int y = 2;
        final PlayerID wantID = PlayerID.PLAYER_1;
        final Field subjectUnderTestField = Factory.makeField(x, y, wantID);
        final Field secondField = Factory.makeField(x, y, wantID);

        //assert
        assertTrue(subjectUnderTestField.equals(secondField));
    }

    @Test
    public void equalsTestFalse() {
        //arrange
        final int x = 1;
        final int y = 2;
        final int secondX = 5;
        final PlayerID wantID = PlayerID.PLAYER_1;
        final Field subjectUnderTestField = Factory.makeField(x, y, wantID);
        final Field secondField = Factory.makeField(secondX, y, wantID);

        //assert
        assertFalse(subjectUnderTestField.equals(secondField));
    }
}