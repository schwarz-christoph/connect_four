package edu.hm.scholz.enno.connect_four;

import edu.hm.scholz.enno.connect_four.control.ControlTests;
import edu.hm.scholz.enno.connect_four.datastore.DatastoreTest;
import edu.hm.scholz.enno.connect_four.logic.ConnectFourManagerTest;
import edu.hm.scholz.enno.connect_four.view.ViewTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ControlTests.class, DatastoreTest.class, ConnectFourManagerTest.class, ViewTest.class})
public class AllConnectFourTests {
}
