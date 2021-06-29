package edu.hm.scholz.enno.connect_four.control;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({BotMoveInMenuTest.class,
        BotMoveJokerTest.class,
        BotMoveTest.class,
        BotTheoriesTest.class,
        BotPreconditionTest.class})
public class ControlTests {
}
