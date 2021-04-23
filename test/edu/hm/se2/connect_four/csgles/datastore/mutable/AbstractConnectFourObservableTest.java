package edu.hm.se2.connect_four.csgles.datastore.mutable;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static org.junit.Assert.*;

public class AbstractConnectFourObservableTest {

    @Rule
    public Timeout globalTimeout = Timeout.millis(1_000);

    FullBoard subjectUnderTestBoard = FullConnectFourBoard.make();

    @Test
    public void notifyObserversTest() {
        //arrange
        //act
        //assert
    }

    @Test
    public void testNotifyObserversTest() {
        //arrange
        //act
        //assert
    }

    @Test
    public void testNotifyObservers1Test() {
        //arrange
        //act
        //assert
    }
}