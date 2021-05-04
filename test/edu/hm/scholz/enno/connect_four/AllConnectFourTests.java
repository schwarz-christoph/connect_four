package edu.hm.scholz.enno.connect_four;

import edu.hm.scholz.enno.connect_four.datastore.ConnectFourFieldTest;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullConnectFourBoardTest;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullConnectFourGameTest;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullConnectFourPlayerTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({FullConnectFourBoardTest.class, FullConnectFourGameTest.class, FullConnectFourPlayerTest.class, ConnectFourFieldTest.class})
public class AllConnectFourTests {
}
