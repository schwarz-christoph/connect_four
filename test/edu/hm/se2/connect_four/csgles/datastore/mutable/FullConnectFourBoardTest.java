package edu.hm.se2.connect_four.csgles.datastore.mutable;

import edu.hm.se2.connect_four.csgles.datastore.ConnectFourField;
import edu.hm.se2.connect_four.csgles.datastore.Field;
import edu.hm.se2.connect_four.csgles.datastore.PlayerID;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class FullConnectFourBoardTest {

    @Rule
    public Timeout globalTimeout = Timeout.millis(1_000);

    FullBoard subjectUnderTestBoard = FullConnectFourBoard.make();
    PlayerID player1 = PlayerID.PLAYER_1;
    PlayerID player2 = PlayerID.PLAYER_2;


    @Test
    public void placeSingleStoneTest() {
        //arrange
        Field field_10 = ConnectFourField.make(1, 0, player1);
        List<Field> expectedFields = new ArrayList<>(List.of(field_10));

        //act
        subjectUnderTestBoard.placeStone(field_10);

        //assert
        assertArrayEquals(expectedFields.toArray(), subjectUnderTestBoard.getFields().toArray());
    }

    @Test
    public void placeMultipleStonesTest() {
        //arrange
        Field field_10 = ConnectFourField.make(1, 0, player1);
        Field field_11 = ConnectFourField.make(1, 1, player2);
        List<Field> expectedFields = new ArrayList<>(List.of(field_10, field_11));

        //act
        subjectUnderTestBoard.placeStone(field_10);
        subjectUnderTestBoard.placeStone(field_11);

        //assert
        assertArrayEquals(expectedFields.toArray(), subjectUnderTestBoard.getFields().toArray());
    }

    @Test
    public void removeStoneTest() {
        //arrange
        Field field_10 = ConnectFourField.make(1, 0, player1);
        Field field_11 = ConnectFourField.make(1, 1, player2);
        List<Field> expectedFields = new ArrayList<>(List.of(field_11));

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
        Field highlightField_10 = ConnectFourField.make(1, 0, player1);
        List<Field> expectedHighlight = new ArrayList<>(List.of(highlightField_10));

        //act
        subjectUnderTestBoard.setHighlight(expectedHighlight);

        //assert
        assertArrayEquals(expectedHighlight.toArray(), subjectUnderTestBoard.getHighlight().toArray());
    }
}