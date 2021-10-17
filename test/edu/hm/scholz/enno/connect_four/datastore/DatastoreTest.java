package edu.hm.scholz.enno.connect_four.datastore;

import edu.hm.scholz.enno.connect_four.datastore.mutable.AbstractConnectFourObservableTest;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullConnectFourBoardTest;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullConnectFourGameTest;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullConnectFourPlayerTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({AbstractConnectFourObservableTest.class,
        FullConnectFourBoardTest.class,
        FullConnectFourGameTest.class,
        FullConnectFourPlayerTest.class,
        ConnectFourFieldTest.class})
public class DatastoreTest {
}
