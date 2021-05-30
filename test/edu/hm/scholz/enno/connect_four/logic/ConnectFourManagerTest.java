package edu.hm.scholz.enno.connect_four.logic;


import edu.hm.scholz.enno.connect_four.datastore.PlayerID;
import edu.hm.scholz.enno.connect_four.datastore.mutable.Factory;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullBoard;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullGame;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullPlayer;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import java.awt.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({ExecuteMoveTest.class, GetMovesMenuTest.class, HighlightTest.class, GetMovesRegularTest.class,
        ExecuteMoveWinningConditionsTest.class, GetMovesPlayerSelectAndEndScreenTest.class, MenuTest.class,
        ConnectFourManagerConstructorTest.class})
public class ConnectFourManagerTest {
}