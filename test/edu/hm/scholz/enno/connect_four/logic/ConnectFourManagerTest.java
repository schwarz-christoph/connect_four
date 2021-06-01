package edu.hm.scholz.enno.connect_four.logic;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({ExecuteMoveTest.class,
        GetMovesMenuTest.class,
        HighlightTest.class,
        GetMovesRegularTest.class,
        ExecuteMoveWinningConditionsTest.class,
        GetMovesPlayerSelectAndEndScreenTest.class,
        MenuTest.class,
        bombJokerTest.class,
        ConnectFourManagerConstructorTest.class,
        DeleteJokerHighlightTest.class,
        MoveTest.class})
public class ConnectFourManagerTest {
}