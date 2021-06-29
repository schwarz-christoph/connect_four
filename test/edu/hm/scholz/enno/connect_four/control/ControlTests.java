package edu.hm.scholz.enno.connect_four.control;

import edu.hm.scholz.enno.connect_four.logic.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({BotMoveInMenuTest.class,
        BotMoveTest.class,
        BotTheoriesTest.class})
public class ControlTests {
}
