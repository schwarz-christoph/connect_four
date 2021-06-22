package edu.hm.scholz.enno.connect_four.control;

import edu.hm.scholz.enno.connect_four.datastore.Game;
import edu.hm.scholz.enno.connect_four.datastore.PlayerID;
import edu.hm.scholz.enno.connect_four.logic.GameManager;
import edu.hm.scholz.enno.connect_four.logic.Move;

import java.util.List;

public class Bot implements Control{

    private final Game game;
    private final GameManager manager;
    private final PlayerID playerID;
    private boolean isRunning;

    public Bot(Game game, GameManager manager, PlayerID playerID){
        if(manager == null || playerID == null || playerID == PlayerID.NONE){
            throw new IllegalArgumentException("IllegalArgument for Bot");
        }

        this.game = game;
        this.manager = manager;
        this.playerID = playerID;
        this.isRunning = true;
    }

    @Override
    public boolean step() {
        final boolean stepSuccessful;
        if(isRunning){
            PlayerID activePlayerID = playerID;
            while (playerID == activePlayerID){
                final BotMove move = BotMove.getWeightedRandomMove();
                final List<Move> steps = BotMove.translate(move, game, playerID);

                for (final Move nextStep : steps) {
                    activePlayerID = manager.executeMove(nextStep);
                }
            }
            stepSuccessful = true;
        } else
            stepSuccessful = false;

        return stepSuccessful;
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
