package edu.hm.scholz.enno.connect_four.datastore.mutable;

import edu.hm.scholz.enno.connect_four.common.Settings;
import edu.hm.scholz.enno.connect_four.datastore.Board;
import edu.hm.scholz.enno.connect_four.datastore.PlayerActiveJoker;
import edu.hm.scholz.enno.connect_four.datastore.PlayerID;

/**
 * A full connect four game.
 *
 * @author Christoph Schwarz (schwarz4@hm.edu)
 * @author Georg Lang (glang@hm.edu)
 * @author Enno Scholz (enno.scholz@hm.edu)
 * @version 04-01-2021
 */

final class FullConnectFourGame extends AbstractConnectFourObservable implements FullGame {

    /**
     * A list of occupied fields.
     */
    private final Board board;

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

    /**
     * Status of joker usage. True when joker is being used, otherwise false.
     */
    private PlayerActiveJoker isJokerInUse;

    /**
     * Make a Game.
     *
     * @param startingPlayer Player who starts.
     * @param board          The Board of the Game.
     */
    FullConnectFourGame(PlayerID startingPlayer, Board board) {
        if (board == null) {
            throw new IllegalArgumentException("Board can't be null.");
        }

        if(startingPlayer == null){
            throw new IllegalArgumentException("Player can't be null.");
        }

        this.board = board;
        this.currentPlayer = startingPlayer;
        this.gameWinner = PlayerID.NONE;
        this.humanPlayers = 1; //Default value for one player
    }

    /**
     *
     * @param activePlayer player to be set to active
     */
    @Override
    public void setActivePlayer(PlayerID activePlayer) {
        if (activePlayer == null){
            throw new NullPointerException("activePlayer cant be null");
        }

        currentPlayer = activePlayer;
    }

    @Override
    public void setWinner(PlayerID winner) {
        if (winner == null){
            throw new NullPointerException("winner cant be null");
        }

        gameWinner = winner;
    }

    @Override
    public void setIsStarted(boolean isStarted) {
        startedGame = isStarted;
    }

    @Override
    public void setPlayerCount(int playerCount) {
        if(playerCount < 0 || playerCount > Settings.maxPlayerCount){
            throw new IllegalArgumentException("Player Count must be between 1 and maxPlayerCount");
        }

        humanPlayers = playerCount;
    }

    @Override
    public void setActiveJoker(PlayerActiveJoker isJokerInUse) {
        this.isJokerInUse = isJokerInUse;
    }

    @Override
    public Board getBoard() {
        return board;
    }

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

    @Override
    public PlayerActiveJoker getActiveJoker() {
        return isJokerInUse;
    }
}
