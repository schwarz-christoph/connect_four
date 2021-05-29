package edu.hm.scholz.enno.connect_four.logic;

import edu.hm.scholz.enno.connect_four.common.Settings;
import edu.hm.scholz.enno.connect_four.datastore.Field;
import edu.hm.scholz.enno.connect_four.datastore.PlayerActiveJoker;
import edu.hm.scholz.enno.connect_four.datastore.PlayerID;
import edu.hm.scholz.enno.connect_four.datastore.mutable.Factory;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullBoard;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullGame;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullPlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//TODO Active the Observer and use them

class ExecuteMoveHandler {

    static boolean onEcexute(Move move, List<Field> currentHighlight, FullGame game, FullBoard board, FullPlayer player1, FullPlayer player2) {
        final boolean result;

        final Field fieldSelected = currentHighlight.get(0);

        FullPlayer activePlayer = game.getActivePlayer() == PlayerID.PLAYER_1? player1:player2;

        if (game.getActiveJoker() == PlayerActiveJoker.BOMB) {
            //Player has active bomb joker
            createBombJoker(game, move, board, activePlayer);
            result = true;
        } else if (game.getActiveJoker() == PlayerActiveJoker.DELETE) {
            //Player has active delete joker
            createDeleteJoker(game, move, board, activePlayer);
            result = true;
        } else {
            if (fieldSelected.yCoordinate() == 0) {
                //Player is in Menu
                result = decideMenu(move, currentHighlight, game, board, activePlayer);
            } else {
                //Player is in Matrix
                result = decideMatrix(move, currentHighlight, game, board);
            }
        }

        return result;
    }

    /**
     * Decides in which direction the highlight has to move in the menu.
     *
     * @param move             The move that was executed by the player.
     * @param currentHighlight The current highlight.
     * @param game             The game.
     * @param board            The board.
     */
    private static boolean decideMenu(Move move, List<Field> currentHighlight, FullGame game, FullBoard board, FullPlayer activePlayer) {
        final boolean result;

        final Field targetField = currentHighlight.get(0);
        final int targetFieldXCoordinate = targetField.xCoordinate();
        final int newXCoordinate;
        if (move == Move.RIGHT) {
            newXCoordinate = fieldOverflowX(1, targetFieldXCoordinate); // Calculate the new x coordinate for the new highlight in the menu
            createMenuHighlight(newXCoordinate, board);
            result = true;
        } else if (move == Move.LEFT) {
            newXCoordinate = fieldOverflowX(-1, targetFieldXCoordinate); // Calculate the new x coordinate for the new highlight in the menu
            createMenuHighlight(newXCoordinate, board);
            result = true;
        } else if (move == Move.DOWN) {
            createHighlight(targetFieldXCoordinate, board); // If the player goes from the menu in the matrix
            result = true;
        } else if (move == Move.CONFIRM) {
            selectInMenu(targetFieldXCoordinate, game, board, activePlayer);//Select a joker, stop or restart the game
            result = true;
        } else {
            result = false;
        }

        return result;
    }

    /**
     * decides witch of the menu buttons is used
     *
     * @param targetFieldXCoordinate the x coordinate of the selected field
     */
    private static void selectInMenu(int targetFieldXCoordinate, FullGame game, FullBoard board, FullPlayer activePlayer) {
        if (targetFieldXCoordinate < 2 || targetFieldXCoordinate > 5) {
            selectJoker(targetFieldXCoordinate, game, board, activePlayer);
        } else if (targetFieldXCoordinate == 3) {
            end();
        } else {
            restart(game, board);
        }

    }

    /**
     * decides witch of the joker is used
     *
     * @param targetFieldXCoordinate the x coordinate of the selected field
     */
    private static void selectJoker(int targetFieldXCoordinate, FullGame game, FullBoard board, FullPlayer activePlayer) {


        if (targetFieldXCoordinate == 0 || targetFieldXCoordinate == 7) {
            createBombJoker(game, Move.CONFIRM, board, activePlayer);
        } else {
            createDeleteJoker(game, Move.CONFIRM, board, activePlayer);
        }

    }

    /**
     * Decides in which direction the highlight has to move in the matrix.
     *
     * @param move             The move that was executed by the player.
     * @param currentHighlight The current highlight.
     * @param game             The game.
     * @param board            The board.
     */
    private static boolean decideMatrix(Move move, List<Field> currentHighlight, FullGame game, FullBoard board) {
        final boolean result;

        final Field targetField = currentHighlight.get(0);
        final int targetFieldXCoordinate = targetField.xCoordinate();
        if (move == Move.RIGHT) {
            createHighlight(fieldOverflowX(1, targetFieldXCoordinate), board);
            result = true;
        } else if (move == Move.LEFT) {
            createHighlight(fieldOverflowX(-1, targetFieldXCoordinate), board);
            result = true;
        } else if (move == Move.UP) {
            createMenuHighlight(targetFieldXCoordinate, board); // If the player goes from the menu in the matrix
            result = true;
        } else if (move == Move.CONFIRM) {
            result = decideConfirmMatrix(currentHighlight, game, board);
        } else {
            result = false;
        }
        return result;
    }

    private static int fieldOverflowX(int adderX, int targetFieldXCoordinate) {
        //Player stands at 0 and then goes to the left to comes to rest at Fieldsize -1 or the other way around
        return (targetFieldXCoordinate + adderX + Settings.fieldSize) % Settings.fieldSize;
    }

    private static int fieldOverflowY(int adderY, int targetFieldYCoordinate) {
        //Handle overflow in Y direction. Wraps from index 1 to index fieldSize-1 since index 0 is the menu
        return Math.max(targetFieldYCoordinate + adderY > 0 ? 1 : Settings.fieldSize - 1,
                (targetFieldYCoordinate + adderY + Settings.fieldSize) % Settings.fieldSize);
    }

    /**
     * Decides if the move is valid. Because if the line is full no more stones could be placed
     *
     * @param currentHighlight the current hightlight
     * @param game             the active game
     */
    private static boolean decideConfirmMatrix(List<Field> currentHighlight, FullGame game, FullBoard board) {
        boolean result = false;
        //Higherst Field in the Row
        Field targetField = Factory.makeField(currentHighlight.get(0).xCoordinate(), 1, PlayerID.NONE);

        boolean isFull = currentHighlight.stream()
                .filter(field -> field.xCoordinate() == targetField.xCoordinate())
                .noneMatch(field -> field.yCoordinate() == targetField.yCoordinate());

        if (!isFull) {
            createStone(currentHighlight, game, board); //Place a stone
            result = true;
            //Switch the player
            changePlayer(game);
        }
        return result;
    }

    /**
     * Creates a new column highlight in the matrix.
     *
     * @param targetFieldXCoordinate The selected x coordinate of the column highlight.
     * @param board                  The board.
     */
    private static void createHighlight(int targetFieldXCoordinate, FullBoard board) {
        List<Field> newHighlight = new ArrayList<>();
        //Start from 1 becuase on 0 is the Menu
        //Ends on fieldSize -1 because the Field is fieldSize long but wie start with the Menu by 0
        IntStream.range(1, Settings.fieldSize)
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
        board.setHighlight(Arrays.asList(Factory.makeField(targetFieldXCoordinate, 0, PlayerID.NONE)));
    }

    /**
     * Creates a new bomb joker highlight in the matrix.
     *
     * @param targetHighlight The selected field to create a bomb joker in
     * @param board           The board.
     */
    private static void createBombJokerHighlight(Field targetHighlight, FullBoard board) {

        final List<Field> newHighlights;
        newHighlights = getAllFieldsOnBoard().stream()
                //Filter everything thats in the radius
                .filter(field -> (Math.abs(field.xCoordinate() - targetHighlight.xCoordinate()) +
                        Math.abs(field.yCoordinate()) - targetHighlight.yCoordinate()) < 3)
                .collect(Collectors.toList());

        board.setHighlight(newHighlights);
    }

    /**
     * Creates a new delete joker highlight in the matrix.
     *
     * @param targetHighlight The selected field to create a delete joker in.
     * @param board           The board.
     */
    private static void createDeleteJokerHighlight(Field targetHighlight, FullBoard board) {

        List<Field> newHighlights;

        //selected whole row
        if (targetHighlight.xCoordinate() == -1 || targetHighlight.xCoordinate() == Settings.fieldSize) {
            newHighlights = board.getFields().stream()
                    .filter(field -> field.yCoordinate() == targetHighlight.yCoordinate())
                    .collect(Collectors.toList());

        //selected whole column
        } else if (targetHighlight.yCoordinate() == 0 || targetHighlight.yCoordinate() == Settings.fieldSize) {
            newHighlights = board.getFields().stream()
                    .filter(field -> field.xCoordinate() == targetHighlight.xCoordinate())
                    .collect(Collectors.toList());
        }

        //select only a single field
        else {
            newHighlights = board.getFields().stream()
                    .filter(field -> field.xCoordinate() == targetHighlight.xCoordinate())
                    .filter(field -> field.yCoordinate() == field.yCoordinate())
                    .collect(Collectors.toList());
        }

        board.setHighlight(newHighlights);
    }

    private static void createDeleteJoker(FullGame game, Move move, FullBoard board, FullPlayer activePlayer) {


        if (game.getActiveJoker() == PlayerActiveJoker.NONE) {
            //New in Joker
            game.setActiveJoker(PlayerActiveJoker.BOMB);
            createDeleteJokerHighlight(Factory.makeField(0, Settings.fieldSize - 1, PlayerID.NONE), board);
        } else {
            //Joker currently in use
            final List<Field> highlight = game.getBoard().getHighlight();
            final Field targetField = highlight.get(0);

            if (move == Move.CONFIRM) {
                executeDeleteJoker(targetField, board);
                //Player has used his Joker
                activePlayer.useDeleteJoker();
                //Change the Player
                changePlayer(game);
            } else if (move == Move.UP) {
                createDeleteJokerHighlight(Factory.makeField(targetField.xCoordinate(),
                        fieldOverflowY(1, targetField.yCoordinate()), targetField.owner()), board);
            } else if (move == Move.DOWN) {
                createDeleteJokerHighlight(Factory.makeField(targetField.xCoordinate(),
                        fieldOverflowY(-1, targetField.yCoordinate()), targetField.owner()), board);
            } else if (move == Move.LEFT) {
                createDeleteJokerHighlight(Factory.makeField(fieldOverflowX(-1, targetField.xCoordinate()),
                        targetField.yCoordinate(), targetField.owner()), board);
            } else {
                //right
                createDeleteJokerHighlight(Factory.makeField(fieldOverflowX(1, targetField.xCoordinate()),
                        targetField.yCoordinate(), targetField.owner()), board);
            }
        }

    }

    private static void createBombJoker(FullGame game, Move move, FullBoard board, FullPlayer activePlayer) {

        if (game.getActiveJoker() == PlayerActiveJoker.NONE) {
            //New in Joker
            game.setActiveJoker(PlayerActiveJoker.DELETE);
            createBombJokerHighlight(Factory.makeField(0, Settings.fieldSize - 1, PlayerID.NONE), board);
        } else {
            //Joker currently in use
            final List<Field> highlight = game.getBoard().getHighlight();
            final Field targetField = highlight.get(0);

            if (move == Move.CONFIRM) {
                executeBombJoker(targetField, board);
                activePlayer.useBombJoker();
                changePlayer(game);
            } else if (move == Move.UP) {
                createBombJokerHighlight(Factory.makeField(targetField.xCoordinate(),
                        fieldOverflowY(-1, targetField.yCoordinate()), targetField.owner()), board);
            } else if (move == Move.DOWN) {
                createBombJokerHighlight(Factory.makeField(targetField.xCoordinate(),
                        fieldOverflowY(-1, targetField.yCoordinate()), targetField.owner()), board);
            } else if (move == Move.LEFT) {
                createBombJokerHighlight(Factory.makeField(fieldOverflowX(-1, targetField.xCoordinate()),
                        targetField.yCoordinate(), targetField.owner()), board);
            } else {
                //right
                createBombJokerHighlight(Factory.makeField(fieldOverflowX(1, targetField.xCoordinate()),
                        targetField.yCoordinate(), targetField.owner()), board);
            }
        }

    }

    private static void executeBombJoker(Field targetHighlight, FullBoard board) {

        //Remove all bombed fields
        board.getHighlight().stream()
                .forEach(field -> board.removeStone(field));

        //get every field that need to be updated for radius1
        updateBombedFields(1, targetHighlight, board);

        //get every field that need to be updated for radius 2
        updateBombedFields(2, targetHighlight, board);
    }

    private static void updateBombedFields(int radius, Field targetHighlight, FullBoard board) {
        int fallSize;

        if (radius == 1) {
            fallSize = 3;
        } else {
            fallSize = 1;
        }

        List<Field> fieldsToUpdate;

        //Get every stone which is in the adjacent column and needs to be updated
        fieldsToUpdate = board.getFields().stream()
                .filter(field -> Math.abs(field.xCoordinate() - targetHighlight.xCoordinate()) == radius)
                .filter(field -> field.yCoordinate() > targetHighlight.yCoordinate())
                .collect(Collectors.toList());

        //Replace old stone positions with updated ones by fallsize
        fieldsToUpdate
                .forEach(board::removeStone);
        fieldsToUpdate
                .forEach(field -> board.placeStone(Factory
                        .makeField(targetHighlight.xCoordinate(),
                                targetHighlight.yCoordinate() - fallSize, field.owner())));
    }

    /**
     * executes the delete joker and removes an occupied stone from the board.
     *
     * @param targetHighlight stone that will be removed
     * @param board           that is being used
     */
    private static void executeDeleteJoker(Field targetHighlight, FullBoard board) {
        List<Field> fieldsToUpdate;

        //Remove highlighted Stones
        board.getHighlight().stream()
                .forEach(field -> board.removeStone(field));

        //Get all fields that need to be updated
        fieldsToUpdate = board.getHighlight().stream()
                .filter(field -> field.yCoordinate() > targetHighlight.yCoordinate())
                .collect(Collectors.toList());

        //Moves stones below
        fieldsToUpdate.stream()
                .forEach(field -> board.removeStone(field));
        fieldsToUpdate.stream()
                .forEach(field -> board.placeStone(Factory.makeField(targetHighlight.xCoordinate(),
                        targetHighlight.yCoordinate() - 1, targetHighlight.owner())));
    }

    private static void createStone(List<Field> currentHighlight, FullGame game, FullBoard board) {
        int targetXCoordinate = currentHighlight.get(0).xCoordinate();

        List<Field> allFields = board.getFields();

        Field lowestFiled = allFields.stream()
                .filter(n -> n.xCoordinate() == targetXCoordinate)
                .filter(n -> n.owner() == PlayerID.NONE)
                .min(Comparator.comparing(Field::yCoordinate))
                .orElseThrow(NullPointerException::new);

        //Changes the selected Field in the Board
        board.removeStone(lowestFiled);
        board.placeStone(Factory.makeField(lowestFiled.xCoordinate(), lowestFiled.yCoordinate(), game.getActivePlayer()));
    }

    private static void changePlayer(FullGame game) {
        game.setActivePlayer(game.getActivePlayer() == PlayerID.PLAYER_1 ? PlayerID.PLAYER_2 : PlayerID.PLAYER_1);
    }

    private static void restart(FullGame game, FullBoard board) {
        game.setWinner(PlayerID.NONE);
        game.setIsStarted(false);
        game.setPlayerCount(1);
        game.setActivePlayer(PlayerID.PLAYER_1);

        board.setHighlight(new ArrayList<>());
        //Removes all occupied fields from the list, resetting it to an empty list
        board.getFields().forEach(board::removeStone);

    }

    private static void end() {
        System.exit(0);
    }

    private static List<Field> getAllFieldsOnBoard() {
        return IntStream.range(0, Settings.fieldSize * Settings.fieldSize)
                .mapToObj(field -> Factory.makeField(field / Settings.fieldSize, field % Settings.fieldSize, PlayerID.NONE))
                .filter(field -> field.yCoordinate() != 0).collect(Collectors.toList());
    }
}
