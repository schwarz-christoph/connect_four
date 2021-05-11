package edu.hm.scholz.enno.connect_four.datastore.mutable;

import edu.hm.scholz.enno.connect_four.datastore.PlayerID;

/**
 * A full player.
 *
 * @author Christoph Schwarz (schwarz4@hm.edu)
 * @author Georg Lang (glang@hm.edu)
 * @author Enno Scholz (enno.scholz@hm.edu)
 * @version 04-01-2021
 */

final class FullConnectFourPlayer extends AbstractConnectFourObservable implements FullPlayer {

    /**
     * Whether the bomb joker has been used or not.
     */
    private boolean bombJokerUsed;

    /**
     * Whether the delete joker has been used or not.
     */
    private boolean deleteJokerUsed;

    /**
     * The ID of the Player.
     */
    private final PlayerID identifier;

    /**
     * The constructor for the Player.
     *
     * @param identifier The player ID.
     */
    FullConnectFourPlayer(PlayerID identifier) {
        this.identifier = identifier;
        this.bombJokerUsed = false;
    }

    @Override
    public void useBombJoker() {
        bombJokerUsed = true;
    }

    @Override
    public void useDeleteJoker() {
        deleteJokerUsed = true;
    }

    @Override
    public PlayerID getIdentifier() {
        return identifier;
    }

    @Override
    public boolean isBombJokerUsed() {
        return bombJokerUsed;
    }

    @Override
    public boolean isDeleteJokerUsed() {
        return deleteJokerUsed;
    }
}
