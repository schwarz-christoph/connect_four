package edu.hm.se2.connect_four.csgles.datastore.mutable;

import edu.hm.se2.connect_four.csgles.datastore.PlayerID;

/**
 * A full player.
 *
 * @author Christoph Schwarz (schwarz4@hm.edu)
 * @author Georg Lang (glang@hm.edu)
 * @author Enno Scholz (enno.scholz@hm.edu)
 * @version 04-01-2021
 */

public class FullConnectFourPlayer extends AbstractConnectFourObservable implements FullPlayer {

    /**
     * Whether the bomb joker has been used or not.
     */
    private boolean isBombJokerAvailable;

    /**
     * Whether the delete joker has been used or not.
     */
    private boolean isDeleteJokerAvailable;

    /**
     * The ID of the Player.
     */
    private final PlayerID identifier;

    /**
     * The constructor for the Player.
     *
     * @param identifier The player ID.
     */
    public FullConnectFourPlayer(PlayerID identifier) {
        this.identifier = identifier;
        this.isBombJokerAvailable = false;
    }

    @Override
    public void useBombJoker() {
        isBombJokerAvailable = false;
    }

    @Override
    public void useDeleteJoker() {
        isDeleteJokerAvailable = false;
    }

    @Override
    public PlayerID getIdentifier() {
        return identifier;
    }

    @Override
    public boolean isBombJokerUsed() {
        return isBombJokerAvailable;
    }

    @Override
    public boolean isDeleteJokerUsed() {
        return isDeleteJokerAvailable;
    }
}
