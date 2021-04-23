package edu.hm.se2.connect_four.csgles.datastore.mutable;

import edu.hm.se2.connect_four.csgles.datastore.Board;
import edu.hm.se2.connect_four.csgles.datastore.PlayerID;

/**
 * A full connect four game
 * @author Christoph Schwarz (schwarz4@hm.edu)
 * @author Georg Lang (glang@hm.edu)
 * @author Enno Scholz (enno.scholz@hm.edu)
 * @version 04-01-2021
 */

public class FullConnectFourGame extends AbstractConnectFourObservable implements FullGame{

    /**
     * A list of occupied fields.
     */
    private Board board;

    /**
     * The player that makes the next move.
     */
    private PlayerID currentPlayer;

    /**
     * The winner of the game.
     */
    private PlayerID gameWinner;

    /**
     * Amount of non-machine players.
     */
    private int humanPlayers;

    /**
     * The status of the game. Started or not started (in player select screen).
     */
    private boolean startedGame;


    public FullConnectFourGame(PlayerID startingPlayer, Board board) {
        this.board = board;
        this.currentPlayer = startingPlayer;
        this.gameWinner = PlayerID.NONE;
        this.humanPlayers = 1; //Default value for one player

    }

    @Override
    public void setActivePlayer(PlayerID activePlayer) {
        currentPlayer = activePlayer;
    }

    @Override
    public void setWinner(PlayerID winner) {
        gameWinner = winner;
    }

    @Override
    public void setIsStarted(boolean isStarted) {
        startedGame = isStarted;
    }

    @Override
    public void setPlayerCount(int playerCount) {
        humanPlayers = playerCount;
    }

    @Override
    public Board getBoard() { return board; }

    @Override
    public PlayerID getActivePlayer() {
        return currentPlayer;
    }

    @Override
    public PlayerID getWinner() {
        return gameWinner;
    }

    @Override
    public boolean isStarted() {
        return startedGame;
    }

    @Override
    public int getPLayerCount() {
        return humanPlayers;
    }

    public static FullGame make(PlayerID startingPlayer, Board board){
        return new FullConnectFourGame(startingPlayer, board);
    }
}
