package edu.hm.scholz.enno.connect_four.logic;

import edu.hm.scholz.enno.connect_four.datastore.PlayerID;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullBoard;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullGame;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullPlayer;

public interface LogicFactory {
    /**
     * Make a GameManager.
     * @param board the board that is being played on
     * @param game the Game which needs the rules
     * @return the logic of the game with two independent players
     */
    static GameManager makeGameManager(FullBoard board, FullGame game) {

        final FullPlayer player1 = edu.hm.scholz.enno.connect_four.datastore.mutable.Factory.makePlayer(PlayerID.PLAYER_1);

        final FullPlayer player2 = edu.hm.scholz.enno.connect_four.datastore.mutable.Factory.makePlayer(PlayerID.PLAYER_2);

        return new ConnectFourManager(board, game, player1, player2);

    }

    /**
     * Creates a game manager.
     * @param board the board of the game
     * @param game the game
     * @param player1 the first player
     * @param player2 the second player
     * @return the logic of the connect for game
     */
    static GameManager makeGameManager(FullBoard board, FullGame game, FullPlayer player1, FullPlayer player2) {
        return new ConnectFourManager(board, game, player1, player2);

    }
}
