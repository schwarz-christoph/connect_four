package edu.hm.scholz.enno.connect_four.control;

import edu.hm.scholz.enno.connect_four.control.BotTheoriesTest;
import org.junit.Test;
import org.junit.experimental.ParallelComputer;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import static org.junit.Assert.assertEquals;

public class BotTheoriesTestMultiThread {
    @Test
    public void runParallel() {
        Result resutl = JUnitCore.runClasses(ParallelComputer.methods(),
                BotTheoriesTest.class);
        assertEquals(resutl.getFailures().toString(),
                0,
                resutl.getFailureCount());
    }
}
