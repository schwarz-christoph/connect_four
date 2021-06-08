package edu.hm.scholz.enno.connect_four.logic;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({BombJokerTest.class,
        ConnectFourManagerConstructorTest.class,
        DeleteJokerHighlightTest.class,
        DeleteJokerTest.class,
        ExecuteMoveTest.class,
        ExecuteMoveWinningConditionsTest.class,
        GetMovesMenuTest.class,
        GetMovesPlayerSelectAndEndScreenTest.class,
        GetMovesRegularTest.class,
        HighlightTest.class,
        MenuTest.class,
        MoveTest.class})
public class ConnectFourManagerTest {
}