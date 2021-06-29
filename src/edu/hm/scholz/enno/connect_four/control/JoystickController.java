package edu.hm.scholz.enno.connect_four.control;

import edu.hm.cs.rs.se2.ui.UI;
import edu.hm.scholz.enno.connect_four.datastore.Game;
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
     * The game.
     */
    private final Game game;

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
     *
     * @param manager  The current manager of the game.
     * @param ui       The ui the game runs on.
     * @param game     The current game.
     * @param playerID The playerID of the current player.
     */
    public JoystickController(Game game, GameManager manager, UI ui, PlayerID playerID) {
        isNotNull(game);
        isNotNull(manager);
        isNotNull(ui);
        isNotNull(playerID);

        if (playerID == PlayerID.NONE)
            throw new IllegalArgumentException("IllegalArgument for JoystickController");

        this.game = game;
        this.manager = manager;
        this.ui = ui;
        this.playerID = playerID;
        this.isRunning = true;
    }

    private void isNotNull(Object object){
        if(object == null)
            throw new IllegalArgumentException("IllegalArgument for JoystickController");
    }

    @Override
    public boolean running() {
        return isRunning;
    }

    @Override
    public void step() {
        if (isRunning) {
            final boolean gameStatus = game.isStarted();
            PlayerID activePlayer = playerID;

            while (gameStatus == game.isStarted() && playerID == activePlayer)
                activePlayer = executeSingleMove();
        }
    }

    @Override
    public void close() {
        isRunning = false;
    }

    /**
     * Waits for an input event and executes the corresponding move.
     *
     * @return The active player after executing the move.
     */
    private PlayerID executeSingleMove() {
        final int moveCode = ui.event(true);
        final Move move = MOVE_CHAR_MAP.get(moveCode);
        return manager.executeMove(move);
    }
}
