package edu.hm.scholz.enno.connect_four.datastore.mutable;

import edu.hm.scholz.enno.connect_four.datastore.Field;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A full board.
 *
 * @author Christoph Schwarz (schwarz4@hm.edu)
 * @author Georg Lang (glang@hm.edu)
 * @author Enno Scholz (enno.scholz@hm.edu)
 * @version 04-01-2021
 */

final class FullConnectFourBoard extends AbstractConnectFourObservable implements FullBoard {

    /**
     * A list of occupied fields.
     */
    private final List<Field> allOccupiedFields;
    /**
     * A list of currently highlighted fields.
     */
    private List<Field> highlight;

    /**
     * Make a Board without fields and without highlight.
     */
    FullConnectFourBoard() {
        this.allOccupiedFields = new ArrayList<>();
        this.highlight = new ArrayList<>();
    }

    @Override
    public void placeStone(Field field) {
        if(field == null) {
            throw new IllegalArgumentException("Field cant be null");
        }

        allOccupiedFields.add(field);
    }

    @Override
    public void removeStone(Field field) {
        allOccupiedFields.remove(field);
    }

    @Override
    public void setHighlight(List<Field> fields) {
        if(fields == null) {
            throw new IllegalArgumentException("Highlight cant be null");
        }

        highlight = fields;
    }

    @Override
    public List<Field> getFields() {
        return Collections.unmodifiableList(allOccupiedFields);
    }

    @Override
    public List<Field> getHighlight() {
        return Collections.unmodifiableList(highlight);
    }
}
