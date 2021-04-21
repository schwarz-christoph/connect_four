package edu.hm.se2.connect_four.csgles.datastore.mutable;

import edu.hm.se2.connect_four.csgles.datastore.Field;
import edu.hm.se2.connect_four.csgles.datastore.PlayerID;

import java.util.Collections;
import java.util.List;

/**
 * A full board
 * @author Christoph Schwarz (schwarz4@hm.edu)
 * @author Georg Lang (glang@hm.edu)
 * @author Enno Scholz (enno.scholz@hm.edu)
 * @version 04-01-2021
 */

public class FullConnectFourBoard implements FullBoard {

    /**
     * A list of occupied fields.
     */
    private List<Field> allOccupiedFields;
    /**
     * A list of currently highlighted fields.
     */
    private List<Field> highlight;

    public FullConnectFourBoard(PlayerID none, FullConnectFourBoard fullConnectFourBoard, int i) {
        this.allOccupiedFields = Collections.emptyList();
        this.highlight = Collections.emptyList();
    }

    @Override
    public void placeStone(Field field) {
        allOccupiedFields.remove(field);
    }

    @Override
    public void removeStone(Field field) {
        allOccupiedFields.add(field);
    }

    @Override
    public void setHighlight(List<Field> fields) {
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
