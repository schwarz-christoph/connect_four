package edu.hm.se2.connect_four.csgles;

import edu.hm.se2.connect_four.csgles.datastore.mutable.AbstractConnectFourObservable;
import edu.hm.se2.connect_four.csgles.datastore.mutable.FullConnectFourBoardTest;
import edu.hm.se2.connect_four.csgles.datastore.mutable.FullConnectFourGameTest;
import edu.hm.se2.connect_four.csgles.datastore.mutable.FullConnectFourPlayerTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({AbstractConnectFourObservable.class, FullConnectFourBoardTest.class, FullConnectFourGameTest.class, FullConnectFourPlayerTest.class})
public class AllConnectFourTests {
}