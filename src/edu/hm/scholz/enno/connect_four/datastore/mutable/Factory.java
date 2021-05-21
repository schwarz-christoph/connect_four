package edu.hm.scholz.enno.connect_four.datastore.mutable;

import edu.hm.scholz.enno.connect_four.datastore.ConnectFourField;
import edu.hm.scholz.enno.connect_four.datastore.Field;
import edu.hm.scholz.enno.connect_four.datastore.Board;
import edu.hm.scholz.enno.connect_four.datastore.PlayerID;
import edu.hm.scholz.enno.connect_four.logic.ConnectFourManager;
import edu.hm.scholz.enno.connect_four.logic.GameManager;
import edu.hm.scholz.enno.connect_four.logic.Move;

import java.util.List;

/**
 * Connect_Four Factory.
 *
 * @author Christoph Schwarz (schwarz4@hm.edu)
 * @author Georg Lang (glang@hm.edu)
 * @author Enno Scholz (enno.scholz@hm.edu)
 * @version 04-01-2021
 */

public interface Factory {

    /**
     * Make a Board without fields and without highlight.
     *
     * @return The created Board.
     */
    static FullBoard makeBoard() {
        return new FullConnectFourBoard();
    }

    /**
     * Make a field with the provided fields.
     *
     * @param xCoordinate The x coordinate of the field.
     * @param yCoordinate The y coordinate of the field.
     * @param owner       The owner of the field.
     * @return The created Field.
     */
    static Field makeField(int xCoordinate, int yCoordinate, PlayerID owner) {
        return new ConnectFourField(xCoordinate, yCoordinate, owner);
    }

    /**
     * Make a Game.
     *
     * @param startingPlayer Player who starts.
     * @return The created Game.
     */
    static FullGame makeGame(PlayerID startingPlayer) {
        final FullBoard board = Factory.makeBoard();
        return new FullConnectFourGame(startingPlayer, board);
    }

    /**
     * Make a Game.
     *
     * @param startingPlayer Player who starts.
     * @param board          Board of the game.
     * @return The created Game.
     */
    static FullGame makeGame(PlayerID startingPlayer, Board board) {
        return new FullConnectFourGame(startingPlayer, board);
    }


    /**
     * Make a Player.
     *
     * @param identifier The Identifier of the Player.
     * @return The created Player.
     */
    static FullPlayer makePlayer(PlayerID identifier) {
        return new FullConnectFourPlayer(identifier);
    }

    /**
     * Make a Logic
     *
     * @param game the Game witch needs the rules
     * @return the logic of the game with two independent players
     */
    static GameManager makeGameManager(FullGame game) {

        FullPlayer player1 = Factory.makePlayer(PlayerID.PLAYER_1);

        FullPlayer player2 = Factory.makePlayer(PlayerID.PLAYER_2);

        return new ConnectFourManager(game, player1, player2);

    }

}
