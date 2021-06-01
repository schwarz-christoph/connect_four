package edu.hm.scholz.enno.connect_four.logic;

import edu.hm.scholz.enno.connect_four.datastore.Field;
import edu.hm.scholz.enno.connect_four.datastore.PlayerActiveJoker;
import edu.hm.scholz.enno.connect_four.datastore.PlayerID;
import edu.hm.scholz.enno.connect_four.datastore.mutable.Factory;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullBoard;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullGame;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullPlayer;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * A execute handler witch handles most of the moves.
 *
 * @author Christoph Schwarz (schwarz4@hm.edu)
 * @author Georg Lang (glang@hm.edu)
 * @author Enno Scholz (enno.scholz@hm.edu)
 * @version 04-20-2021
 */

class ExecuteMoveHandler {

    public ExecuteMoveHandler() {
        throw new UnsupportedOperationException("Class only for static usage, do not create Objects.");
    }

    static void onExecute(Move move, List<Field> currentHighlight, FullGame game, FullBoard board, FullPlayer activePlayer) {
        final Field fieldSelected = currentHighlight.get(0);

        if (game.getActiveJoker() == PlayerActiveJoker.BOMB)
            //Player has active bomb joker
            createBombJoker(game, move, board, activePlayer);
        else if (game.getActiveJoker() == PlayerActiveJoker.DELETE)
            //Player has active delete joker
            createDeleteJoker(game, move, board, activePlayer);
        else
            if (fieldSelected.yCoordinate() == 0)
                //Player is in Menu
                decideMenu(move, currentHighlight, game, board, activePlayer);
            else
                //Player is in Matrix
                decideMatrix(move, currentHighlight, game, board);


    }

    /**
     * Decides in which direction the highlight has to move in the menu.
     *
     * @param move             The move that was executed by the player.
     * @param currentHighlight The current highlight.
     * @param game             The game.
     * @param board            The board.
     * @param activePlayer     The currently active player.
     */
    private static void decideMenu(Move move, List<Field> currentHighlight, FullGame game, FullBoard board, FullPlayer activePlayer) {
        final int targetFieldXCoordinate = currentHighlight.get(0).xCoordinate();
        final int newXCoordinate;
        if (move == Move.RIGHT) {
            newXCoordinate = fieldOverflowX(1, targetFieldXCoordinate); // Calculate the new x coordinate for the new highlight in the menu
            createMenuHighlight(newXCoordinate, board);
        } else if (move == Move.LEFT) {
            newXCoordinate = fieldOverflowX(-1, targetFieldXCoordinate); // Calculate the new x coordinate for the new highlight in the menu
            createMenuHighlight(newXCoordinate, board);
        } else if (move == Move.DOWN) {
            createHighlight(targetFieldXCoordinate, board); // If the player goes from the menu in the matrix
        } else
            //Can oly be Joker because the other two options are covered by the ConnectFourManager
            selectJoker(targetFieldXCoordinate, game, board, activePlayer);

    }

    /**
     * Decides witch of the joker is used.
     *
     * @param targetFieldXCoordinate the x coordinate of the selected field.
     * @param game                   the current running game.
     * @param board                  the current board the game is run on.
     * @param activePlayer           the currently active player.
     */
    private static void selectJoker(int targetFieldXCoordinate, FullGame game, FullBoard board, FullPlayer activePlayer) {

        final int player1BombJoker = 0;
        final int player2BombJoker = 7;

        if (targetFieldXCoordinate == player1BombJoker || targetFieldXCoordinate == player2BombJoker)
            createBombJoker(game, Move.CONFIRM, board, activePlayer);
        else
            createDeleteJoker(game, Move.CONFIRM, board, activePlayer);

    }

    /**
     * Decides in which direction the highlight has to move in the matrix.
     *
     * @param move             The move that was executed by the player.
     * @param currentHighlight The current highlight.
     * @param game             The game.
     * @param board            The board.
     */
    private static void decideMatrix(Move move, List<Field> currentHighlight, FullGame game, FullBoard board) {
        final int targetFieldXCoordinate = currentHighlight.get(0).xCoordinate();

        if (move == Move.RIGHT)
            createHighlight(fieldOverflowX(1, targetFieldXCoordinate), board);
        else if (move == Move.LEFT)
            createHighlight(fieldOverflowX(-1, targetFieldXCoordinate), board);
        else if (move == Move.UP)
            createMenuHighlight(targetFieldXCoordinate, board); // If the player goes from the menu in the matrix
        else
            decideConfirmMatrix(currentHighlight, game, board);

    }

    private static int fieldOverflowX(int adderX, int targetFieldXCoordinate) {
        //Player stands at 0 and then goes to the left to comes to rest at Fieldsize -1 or the other way around
        final int fieldSize = 8;
        return (targetFieldXCoordinate + adderX + fieldSize) % fieldSize;
    }

    /**
     * Decides if the move is valid. Because if the line is full no more stones could be placed
     *
     * @param board            the board the game runs on.
     * @param currentHighlight the current hightlight.
     * @param game             the active game.
     */
    private static void decideConfirmMatrix(List<Field> currentHighlight, FullGame game, FullBoard board) {

        createStone(currentHighlight, game, board); //Place a stone
        //Switch the player
        changePlayer(game);

    }

    /**
     * Creates a new column highlight in the matrix.
     *
     * @param targetFieldXCoordinate The selected x coordinate of the column highlight.
     * @param board                  The board.
     */
    private static void createHighlight(int targetFieldXCoordinate, FullBoard board) {
        final List<Field> newHighlight = new ArrayList<>();
        final int fieldSize = 8;
        //Start from 1 becuase on 0 is the Menu
        //Ends on fieldSize -1 because the Field is fieldSize long but wie start with the Menu by 0
        IntStream.range(1, fieldSize)
                .forEach(yCoordinate -> newHighlight.add(Factory.makeField(targetFieldXCoordinate, yCoordinate, PlayerID.NONE)));

        board.setHighlight(newHighlight);
    }

    /**
     * Creates a new highlight in the menu.
     *
     * @param targetFieldXCoordinate The selected x coordinate of the new highlight.
     * @param board                  The board.
     */
    private static void createMenuHighlight(int targetFieldXCoordinate, FullBoard board) {
        board.setHighlight(List.of(Factory.makeField(targetFieldXCoordinate, 0, PlayerID.NONE)));
    }

    /**
     * Creates a new bomb joker highlight in the matrix.
     *
     * @param xCoordinate the x Coordinate of the new Highlight
     * @param board       The board.
     */
    private static void createBombJokerHighlight(int xCoordinate, FullBoard board) {

        final Field targetHighlight = Factory.makeField(xCoordinate, 1, PlayerID.NONE);
        List<Field> bombJokerHighlight = new ArrayList<>(List.of(targetHighlight));

        final Field lowestFreeField = getLowestFreeField(targetHighlight, board);

        //add all fields on the same column to the highlight
        final List<Field> columnHighlight = getAllFieldsOnBoard().stream()
                .filter(field -> field.xCoordinate() == targetHighlight.xCoordinate())
                .collect(Collectors.toList());
        bombJokerHighlight.addAll(columnHighlight);

        //add all fields that are in the bomb radius
        final List<Field> bombRadius = getAllFieldsOnBoard().stream()
                .filter(field -> (Math.abs(field.xCoordinate() - lowestFreeField.xCoordinate())
                        + Math.abs(field.yCoordinate() - lowestFreeField.yCoordinate())) <= 2)
                .collect(Collectors.toList());
        bombJokerHighlight.addAll(bombRadius);

        //Removes duplicates that were in the column and the bomb radius
        bombJokerHighlight = bombJokerHighlight.stream()
                .distinct()
                .collect(Collectors.toList());

        board.setHighlight(List.copyOf(bombJokerHighlight));
    }

    /**
     * Creates a new delete joker highlight in the matrix.
     *
     * @param targetX The selected x coordinate to create a delete joker in.
     * @param targetY The selected y coordinate to create a delete joker in.
     * @param board   The board.
     */
    private static void createDeleteJokerHighlight(int targetX, int targetY, FullBoard board) {
        final List<Field> newHighlights;
        final int fieldSize = 8;


        if (targetX == -1 || targetX == fieldSize)
            //selected whole row
            newHighlights = getAllFieldsOnBoard().stream()
                    .filter(field -> field.yCoordinate() == targetY)
                    .collect(Collectors.toList());
        else if (targetY == 0 || targetY == fieldSize)
            //selected whole column
            newHighlights = getAllFieldsOnBoard().stream()
                    .filter(field -> field.xCoordinate() == targetX)
                    .collect(Collectors.toList());
        else
            //select only a single field
            newHighlights = getAllFieldsOnBoard().stream()
                    .filter(field -> field.xCoordinate() == targetX)
                    .filter(field -> field.yCoordinate() == targetY)
                    .collect(Collectors.toList());


        board.setHighlight(newHighlights);
    }

    private static void createDeleteJoker(FullGame game, Move move, FullBoard board, FullPlayer activePlayer) {
        final int fieldSize = 8;

        if (game.getActiveJoker() == PlayerActiveJoker.NONE) {
            //New in Joker
            game.setActiveJoker(PlayerActiveJoker.DELETE);
            createDeleteJokerHighlight(0, fieldSize - 1, board);
        } else {
            //Joker currently in use
            final List<Field> highlight = board.getHighlight();
            final boolean isColumnMultiHighlight;
            final boolean isRowMultiHighlight;
            if (highlight.size() > 1) {
                isColumnMultiHighlight =
                        highlight.stream().allMatch(field -> field.xCoordinate() == highlight.get(0).xCoordinate());
                isRowMultiHighlight =
                        highlight.stream().allMatch(field -> field.yCoordinate() == highlight.get(0).yCoordinate());
            } else {
                isColumnMultiHighlight = false;
                isRowMultiHighlight = false;
            }

            final Field targetField = highlight.get(0);

            if (move == Move.CONFIRM) {
                executeDeleteJoker(board);
                //Player has used his Joker
                activePlayer.useDeleteJoker();
                game.setActiveJoker(PlayerActiveJoker.NONE);
                //Change the Player
                changePlayer(game);
            } else if (move == Move.UP) {
                final int targetX;
                final int targetY;
                if (isRowMultiHighlight) {
                    targetX = -1;
                    targetY = Math.max(targetField.yCoordinate() - 1, 1);
                } else if (isColumnMultiHighlight) {
                    targetX = targetField.xCoordinate();
                    targetY = fieldSize - 1;
                } else {
                    targetX = targetField.xCoordinate();
                    targetY = targetField.yCoordinate() - 1;
                }
                createDeleteJokerHighlight(targetX, targetY, board);
            } else if (move == Move.DOWN) {
                final int targetX;
                final int targetY;
                if (isRowMultiHighlight) {
                    targetX = -1;
                    targetY = Math.min(targetField.yCoordinate() + 1, fieldSize - 1);
                } else if (isColumnMultiHighlight) {
                    targetX = targetField.xCoordinate();
                    targetY = 1;
                } else {
                    targetX = targetField.xCoordinate();
                    targetY = targetField.yCoordinate() + 1;
                }
                createDeleteJokerHighlight(targetX, targetY, board);
            } else if (move == Move.LEFT) {
                final int targetX;
                final int targetY;
                if (isRowMultiHighlight) {
                    targetX = fieldSize - 1;
                    targetY = targetField.yCoordinate();
                } else if (isColumnMultiHighlight) {
                    targetX = Math.max(targetField.xCoordinate() - 1, 0);
                    targetY = 0;
                } else {
                    targetX = targetField.xCoordinate() - 1;
                    targetY = targetField.yCoordinate();
                }
                createDeleteJokerHighlight(targetX, targetY, board);
            } else {
                //right
                final int targetX;
                final int targetY;
                if (isRowMultiHighlight) {
                    targetX = 0;
                    targetY = targetField.yCoordinate();
                } else if (isColumnMultiHighlight) {
                    targetX = Math.min(targetField.xCoordinate() + 1, fieldSize - 1);
                    targetY = 0;
                } else {
                    targetX = targetField.xCoordinate() + 1;
                    targetY = targetField.yCoordinate();
                }
                createDeleteJokerHighlight(targetX, targetY, board);
            }
        }
    }

    private static void createBombJoker(FullGame game, Move move, FullBoard board, FullPlayer activePlayer) {

        if (game.getActiveJoker() == PlayerActiveJoker.NONE) {
            //New in Joker
            game.setActiveJoker(PlayerActiveJoker.BOMB);
            createBombJokerHighlight(0, board);
        } else {
            //Joker currently in use
            final List<Field> highlight = game.getBoard().getHighlight();
            final Field targetField = highlight.get(0);

            if (move == Move.CONFIRM) {
                executeBombJoker(targetField, board);
                activePlayer.useBombJoker();
                changePlayer(game);
                board.setHighlight(List.of(targetField));

            } else if (move == Move.LEFT)
                createBombJokerHighlight(fieldOverflowX(-1, targetField.xCoordinate()), board);
            else
                //right
                createBombJokerHighlight(fieldOverflowX(1, targetField.xCoordinate()), board);

        }

    }

    private static void executeBombJoker(Field targetHighlight, FullBoard board) {

        final Field lowestFreeField = getLowestFreeField(targetHighlight, board);

        final List<Field> allFields = board.getFields().stream()
                .filter(field -> (Math.abs(field.xCoordinate() - lowestFreeField.xCoordinate())
                        + Math.abs(field.yCoordinate() - lowestFreeField.yCoordinate())) <= 2)
                .collect(Collectors.toList());

        allFields.forEach(board::removeStone);

        //get every field that need to be updated for radius1
        updateBombedFields(1, lowestFreeField, board);

        //get every field that need to be updated for radius 2
        updateBombedFields(2, lowestFreeField, board);
    }

    private static Field getLowestFreeField(Field targetHighlight, FullBoard board) {
        final Field highestOccupiedField = board.getFields().stream()
                .filter(field -> field.xCoordinate() == targetHighlight.xCoordinate())
                .min(Comparator.comparing(Field::yCoordinate)).orElse(null);


        final Field lowestFreeField;
        if (highestOccupiedField == null) {
            final int yGround = 7;
            lowestFreeField = Factory.makeField(targetHighlight.xCoordinate(), yGround, PlayerID.NONE);
        } else
            lowestFreeField = Factory.makeField(highestOccupiedField.xCoordinate(),
                    highestOccupiedField.yCoordinate() - 1, PlayerID.NONE);

        return lowestFreeField;
    }

    private static void updateBombedFields(int radius, Field bombCenter, FullBoard board) {


        final List<Field> fieldsToUpdate;

        //Get every stone which is in the radius and needs to be updated
        fieldsToUpdate = board.getFields().stream()
                .filter(field -> (Math.abs(field.xCoordinate() - bombCenter.xCoordinate()) == radius))
                .filter(field -> field.yCoordinate() < bombCenter.yCoordinate())
                .collect(Collectors.toList());

        //If list ist empty than no stones need to be updated, otherwise the lower stone that needs to be updated

        if (!fieldsToUpdate.isEmpty()) {

            final int fallSize;
            final Field lowestOccupied;

            lowestOccupied = fieldsToUpdate.stream()
                    .max(Comparator.comparing(Field::yCoordinate)).orElse(null);


            Field lowestFreeField = board.getFields().stream()
                    .filter(field -> (Math.abs(field.xCoordinate() - bombCenter.xCoordinate()) == radius))
                    .filter(field -> field.yCoordinate() > bombCenter.yCoordinate())
                    .min(Comparator.comparing(Field::yCoordinate)).orElse(null);

            if (lowestFreeField == null) {
                final int yGround = 7;
                fallSize = yGround - lowestOccupied.yCoordinate();
            } else {
                lowestFreeField = Factory.makeField(
                        lowestFreeField.xCoordinate(), lowestFreeField.yCoordinate() - 1, lowestFreeField.owner());

                fallSize = lowestFreeField.yCoordinate() - lowestOccupied.yCoordinate();
            }

            //Replace old stone positions with updated ones by fallsize
            fieldsToUpdate
                    .forEach(board::removeStone);
            fieldsToUpdate
                    .forEach(field -> board.placeStone(Factory
                            .makeField(field.xCoordinate(),
                                    field.yCoordinate() + fallSize, field.owner())));
        }
    }

    /**
     * executes the delete joker and removes an occupied stone from the board.
     *
     * @param board           that is being used
     */
    private static void executeDeleteJoker(FullBoard board) {
        final List<Field> stonesToRemove = board.getFields().stream()
                .filter(field -> board.getHighlight().stream().anyMatch(highlightField ->
                        field.xCoordinate() == highlightField.xCoordinate()
                                && field.yCoordinate() == highlightField.yCoordinate()))
                .collect(Collectors.toList());

        stonesToRemove.forEach(board::removeStone);

        final List<Field> stonesToMoveDown = board.getFields().stream()
                .filter(field -> stonesToRemove.stream().anyMatch(removedField ->
                        removedField.xCoordinate() == field.xCoordinate()
                                && removedField.yCoordinate() > field.yCoordinate()))
                .collect(Collectors.toList());

        stonesToMoveDown.forEach(board::removeStone);
        stonesToMoveDown.forEach(field -> board.placeStone(Factory.makeField(field.xCoordinate(),
                field.yCoordinate() + 1, field.owner())));

        createHighlight(0, board);
    }

    private static void createStone(List<Field> currentHighlight, FullGame game, FullBoard board) {
        final int targetXCoordinate = currentHighlight.get(0).xCoordinate();
        final int xCoordinate;
        final int yCoordinate;
        final int fieldSize = 8;

        final List<Field> allFields = board.getFields();

        if (allFields.isEmpty()) {
            xCoordinate = targetXCoordinate;
            yCoordinate = fieldSize - 1;
        } else {
            xCoordinate = targetXCoordinate;
            yCoordinate = allFields.stream()
                    .filter(n -> n.xCoordinate() == targetXCoordinate)
                    .min(Comparator.comparing(Field::yCoordinate))
                    .map(field -> field.yCoordinate() - 1)
                    .orElse(fieldSize - 1);
        }

        board.placeStone(Factory.makeField(xCoordinate, yCoordinate, game.getActivePlayer()));
    }

    private static void changePlayer(FullGame game) {
        game.setActivePlayer(game.getActivePlayer() == PlayerID.PLAYER_1 ? PlayerID.PLAYER_2 : PlayerID.PLAYER_1);
    }

    private static List<Field> getAllFieldsOnBoard() {
        final int fieldSize = 8;

        return IntStream.range(0, fieldSize * fieldSize)
                .mapToObj(field -> Factory.makeField(field / fieldSize, field % fieldSize, PlayerID.NONE))
                .filter(field -> field.yCoordinate() != 0).collect(Collectors.toList());
    }
}
