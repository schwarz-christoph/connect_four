package edu.hm.scholz.enno.connect_four.control;

import edu.hm.cs.rs.se2.ui.UI;
import edu.hm.scholz.enno.connect_four.datastore.PlayerID;
import edu.hm.scholz.enno.connect_four.logic.GameManager;
import edu.hm.scholz.enno.connect_four.logic.Move;

import java.util.Map;

public class JoystickController implements Control {

    private final GameManager manager;
    private final UI ui;
    private final PlayerID playerID;
    private boolean isRunning;

    static final int letterR = 'r';
    static final int letterL = 'l';
    static final int letterU = 'u';
    static final int letterD = 'd';
    static final int letterM = 'm';

    static final Map<Integer, Move> moveCharMap = Map.of(letterR, Move.RIGHT, letterL, Move.LEFT, letterU, Move.UP, letterD, Move.DOWN, letterM, Move.CONFIRM);

    public JoystickController(GameManager manager, UI ui, PlayerID playerID) {
        if(manager == null || ui == null || playerID == PlayerID.NONE || playerID == null)
            throw new IllegalArgumentException("IllegalArgument for JoystickController");

        this.manager = manager;
        this.ui = ui;
        this.playerID = playerID;
        this.isRunning = true;
    }

    @Override
    public boolean running() {
        return isRunning;
    }

    @Override
    public boolean step() {
        final boolean stepSuccessful;
        if(isRunning) {
            PlayerID activePlayer = playerID;
            while(playerID == activePlayer) {
                final int moveCode = ui.event(true);
                final Move move = moveCharMap.get(moveCode);
                activePlayer = manager.executeMove(move);
            }
            stepSuccessful = true;
        } else {
            stepSuccessful = false;
        }

        return stepSuccessful;
    }

    @Override
    public void close() {
        isRunning = false;
    }
}
