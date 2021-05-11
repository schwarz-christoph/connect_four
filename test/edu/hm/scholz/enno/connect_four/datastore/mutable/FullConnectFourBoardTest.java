package edu.hm.scholz.enno.connect_four.datastore.mutable;

import edu.hm.scholz.enno.connect_four.datastore.Field;
import edu.hm.scholz.enno.connect_four.datastore.PlayerID;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class FullConnectFourBoardTest {

    @Rule
    public Timeout globalTimeout = Timeout.millis(1_000);

    final FullBoard subjectUnderTestBoard = Factory.makeBoard();
    final PlayerID player1 = PlayerID.PLAYER_1;
    final PlayerID player2 = PlayerID.PLAYER_2;


    @Test
    public void placeSingleStoneTest() {
        //arrange
        final Field field_10 = Factory.makeField(1, 0, player1);
        final List<Field> expectedFields = new ArrayList<>(List.of(field_10));

        //act
        subjectUnderTestBoard.placeStone(field_10);

        //assert
        assertArrayEquals(expectedFields.toArray(), subjectUnderTestBoard.getFields().toArray());
    }

    @Test
    public void placeMultipleStonesTest() {
        //arrange
        final Field field_10 = Factory.makeField(1, 0, player1);
        final Field field_11 = Factory.makeField(1, 1, player2);
        final List<Field> expectedFields = new ArrayList<>(List.of(field_10, field_11));

        //act
        subjectUnderTestBoard.placeStone(field_10);
        subjectUnderTestBoard.placeStone(field_11);

        //assert
        assertArrayEquals(expectedFields.toArray(), subjectUnderTestBoard.getFields().toArray());
    }

    @Test
    public void removeStoneTest() {
        //arrange
        final Field field_10 = Factory.makeField(1, 0, player1);
        final Field field_11 = Factory.makeField(1, 1, player2);
        final List<Field> expectedFields = new ArrayList<>(List.of(field_11));

        //act
        subjectUnderTestBoard.placeStone(field_10);
        subjectUnderTestBoard.placeStone(field_11);
        subjectUnderTestBoard.removeStone(field_10);

        //assert
        assertArrayEquals(expectedFields.toArray(), subjectUnderTestBoard.getFields().toArray());
    }

    @Test
    public void highlightTest() {
        //arrange
        final Field highlightField_10 = Factory.makeField(1, 0, player1);
        final List<Field> expectedHighlight = new ArrayList<>(List.of(highlightField_10));

        //act
        subjectUnderTestBoard.setHighlight(expectedHighlight);

        //assert
        assertArrayEquals(expectedHighlight.toArray(), subjectUnderTestBoard.getHighlight().toArray());
    }
}