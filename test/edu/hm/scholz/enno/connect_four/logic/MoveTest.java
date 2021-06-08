package edu.hm.scholz.enno.connect_four.logic;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import static org.junit.Assert.*;

public class MoveTest {
    @Rule
    public Timeout globalTimeout = Timeout.millis(1_000);

    @Test
    public void moveUPLetterTest(){
        //arrange
        char want = 'U';

        //act
        char have = Move.UP.getMoveName();

        //assert
        assertEquals(want, have);
    }

    @Test
    public void moveDOWNLetterTest(){
        //arrange
        char want = 'D';

        //act
        char have = Move.DOWN.getMoveName();

        //assert
        assertEquals(want, have);
    }

    @Test
    public void moveRIGHTLetterTest(){
        //arrange
        char want = 'R';

        //act
        char have = Move.RIGHT.getMoveName();

        //assert
        assertEquals(want, have);
    }

    @Test
    public void moveLEFTLetterTest(){
        //arrange
        char want = 'L';

        //act
        char have = Move.LEFT.getMoveName();

        //assert
        assertEquals(want, have);
    }

    @Test
    public void moveCONFIRMLetterTest(){
        //arrange
        char want = 'C';

        //act
        char have = Move.CONFIRM.getMoveName();

        //assert
        assertEquals(want, have);
    }

    @Test
    public void moveUPToStringLetterTest(){
        //arrange
        String want = "Select field above";

        //act
        String have = Move.UP.toString();

        //assert
        assertEquals(want, have);
    }

    @Test
    public void moveDOWNToStringLetterTest(){
        //arrange
        String want = "Select field below";

        //act
        String have = Move.DOWN.toString();

        //assert
        assertEquals(want, have);
    }

    @Test
    public void moveLEFTToStringLetterTest(){
        //arrange
        String want = "Select left field";

        //act
        String have = Move.LEFT.toString();

        //assert
        assertEquals(want, have);
    }

    @Test
    public void moveRIGHTToStringLetterTest(){
        //arrange
        String want = "Select right field";

        //act
        String have = Move.RIGHT.toString();

        //assert
        assertEquals(want, have);
    }

    @Test
    public void moveCONFIRMToStringLetterTest(){
        //arrange
        String want = "Confirm selected field";

        //act
        String have = Move.CONFIRM.toString();

        //assert
        assertEquals(want, have);
    }
}
