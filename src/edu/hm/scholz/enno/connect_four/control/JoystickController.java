package edu.hm.scholz.enno.connect_four.control;

import edu.hm.cs.rs.se2.ui.UI;
import edu.hm.scholz.enno.connect_four.datastore.PlayerID;
import edu.hm.scholz.enno.connect_four.logic.GameManager;
import edu.hm.scholz.enno.connect_four.logic.Move;

import java.util.Map;

/**
 * Implements the Controller for a player.
 */
public class JoystickController implements Control {

    /**
     * Integer value of the char move right.
     */
    static final int LETTER_R = 'r';

    /**
     * Integer value of the char left.
     */
    static final int LETTER_L = 'l';

    /**
     * Integer value of the char up.
     */
    static final int LETTER_U = 'u';

    /**
     * Integer value of the char down.
     */
    static final int LETTER_D = 'd';

    /**
     * Integer value of the char middle.
     */
    static final int LETTER_M = 'm';

    /**
     * Map of the Integer values to the correct move.
     */
    static final Map<Integer, Move> MOVE_CHAR_MAP = Map.of(LETTER_R, Move.RIGHT, LETTER_L, Move.LEFT, LETTER_U, Move.UP, LETTER_D, Move.DOWN, LETTER_M, Move.CONFIRM);

    /**
     * Manager of the current game.
     */
    private final GameManager manager;

    /**
     * Ui the controller is running on.
     */
    private final UI ui;

    /**
     * PlayerID of the current player.
     */
    private final PlayerID playerID;

    /**
     * Signals if the game is still running.
     */
    private boolean isRunning;

    /**
     * Constructor for the player Controller.
     * @param manager  The current manager of the game.
     * @param ui       The ui the game runs on.
     * @param playerID The playerID of the current player.
     */
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
                final Move move = MOVE_CHAR_MAP.get(moveCode);
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
