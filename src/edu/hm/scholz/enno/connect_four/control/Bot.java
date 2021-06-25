package edu.hm.scholz.enno.connect_four.control;

import edu.hm.scholz.enno.connect_four.datastore.Game;
import edu.hm.scholz.enno.connect_four.datastore.PlayerID;
import edu.hm.scholz.enno.connect_four.logic.GameManager;
import edu.hm.scholz.enno.connect_four.logic.Move;

import java.util.List;

/**
 * Class that implements the Controller for the bots.
 * Determines their move random based on weights.
 */
public class Bot implements Control {

    /**
     * The current running game.
     */

    private final Game game;

    /**
     * The current Manager of the game.
     */
    private final GameManager manager;

    /**
     * The playerID of the bot.
     */
    private final PlayerID playerID;

    /**
     * Signals if the game is running or not.
     */
    private boolean isRunning;

    /**
     * Constructor of the bot.
     *
     * @param game     The current running game.
     * @param manager  The current manager of the game.
     * @param playerID The ID of the bot.
     * @throws IllegalArgumentException if a parameter is not viable.
     */
    public Bot(Game game, GameManager manager, PlayerID playerID) {
        if (manager == null || playerID == null || playerID == PlayerID.NONE)
            throw new IllegalArgumentException("IllegalArgument for Bot");
        

        this.game = game;
        this.manager = manager;
        this.playerID = playerID;
        this.isRunning = true;
    }

    /**
     * Tries to execute every step the bot is doing, when its his current turn.
     *
     * @return if the step was executed successfully.
     */
    @Override
    public void step() {
        if (isRunning) {
            if (game.isStarted()) {
                PlayerID activePlayerID = playerID;
                while (playerID == activePlayerID && game.getActivePlayer() != PlayerID.NONE) {
                    final BotMove move = BotMove.getWeightedRandomMove();
                    final List<Move> steps = BotMove.translate(move, game, playerID);

                    for (final Move nextStep : steps) {
                        activePlayerID = manager.executeMove(nextStep);
                    }
                }
            } else
                manager.executeMove(Move.CONFIRM);
        }
    }

    @Override
    public void close() {
        isRunning = false;
    }

    @Override
    public boolean running() {
        return isRunning;
    }
}
