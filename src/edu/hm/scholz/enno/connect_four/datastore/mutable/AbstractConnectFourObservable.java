package edu.hm.scholz.enno.connect_four.datastore.mutable;

import edu.hm.scholz.enno.connect_four.datastore.Observable;
import edu.hm.scholz.enno.connect_four.datastore.Board;
import edu.hm.scholz.enno.connect_four.datastore.Game;
import edu.hm.scholz.enno.connect_four.datastore.Observer;
import edu.hm.scholz.enno.connect_four.datastore.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * An abstract observable with methods to register and notify registered observers.
 *
 * @author Christoph Schwarz (schwarz4@hm.edu)
 * @author Georg Lang (glang@hm.edu)
 * @author Enno Scholz (enno.scholz@hm.edu)
 * @version 04-01-2021
 */

abstract class AbstractConnectFourObservable implements Observable {

    /**
     * A list of observers used to notify them.
     */
    private final List<Observer> observers;

    /**
     * The constructor of the observable.
     */
    AbstractConnectFourObservable() {
        this.observers = new ArrayList<>();
    }

    @Override
    public void register(Observer observer) {
        if(observer == null) {
            throw new IllegalArgumentException("Observer can't be null.");
        }

        observers.add(observer);
    }

    @Override
    public void notifyObservers(Board board, Game game, Player player1, Player player2) {
        if(game == null) {
            throw new IllegalArgumentException("Observable can't be null.");
        }
        for(Observer observer : observers) {
            observer.update(board, game, player1, player2);
        }
    }
}
