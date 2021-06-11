package edu.hm.scholz.enno.connect_four.control;

import edu.hm.cs.rs.se2.ui.UI;
import edu.hm.scholz.enno.connect_four.datastore.PlayerID;
import edu.hm.scholz.enno.connect_four.logic.GameManager;
import edu.hm.scholz.enno.connect_four.view.View;

public class JoystickController implements Control{

    final GameManager manager;
    final UI ui;
    final PlayerID playerID;

    public JoystickController(GameManager manager, UI ui, PlayerID playerID) {
        if(manager == null || ui == null || playerID == PlayerID.NONE || playerID == null)
            throw new IllegalArgumentException("IllegalArgument for JoystickController");

        this.manager = manager;
        this.ui = ui;
        this.playerID = playerID;
    }

    @Override
    public boolean running() {
        throw new UnsupportedOperationException("Ned benutzen du depp");
    }

    @Override
    public void step() {

    }

    @Override
    public void close() {

    }
}
