package edu.hm.scholz.enno.connect_four.datastore.mutable;

import edu.hm.scholz.enno.connect_four.datastore.ConnectFourField;
import edu.hm.scholz.enno.connect_four.datastore.Field;
import edu.hm.scholz.enno.connect_four.datastore.Board;
import edu.hm.scholz.enno.connect_four.datastore.PlayerID;

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
}
