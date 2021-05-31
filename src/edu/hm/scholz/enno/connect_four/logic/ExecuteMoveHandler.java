package edu.hm.scholz.enno.connect_four.logic;

import edu.hm.scholz.enno.connect_four.common.Settings;
import edu.hm.scholz.enno.connect_four.datastore.Field;
import edu.hm.scholz.enno.connect_four.datastore.PlayerActiveJoker;
import edu.hm.scholz.enno.connect_four.datastore.PlayerID;
import edu.hm.scholz.enno.connect_four.datastore.mutable.Factory;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullBoard;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullGame;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullPlayer;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class ExecuteMoveHandler {

    static boolean onEcexute(Move move, List<Field> currentHighlight, FullGame game, FullBoard board, FullPlayer player1, FullPlayer player2) {
        final boolean result;

        final Field fieldSelected = currentHighlight.get(0);

        final FullPlayer activePlayer = game.getActivePlayer() == PlayerID.PLAYER_1? player1:player2;

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
            //Can oly be Joker because the other two options are covered by the ConnectFourManager
            selectJoker(targetFieldXCoordinate, game, board, activePlayer);
            result = true;
        } else {
            result = false;
        }

        return result;
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
     * @param xCoordinate the x Coordinate of the new Highlight
     * @param board       The board.
     */
    private static void createBombJokerHighlight(int xCoordinate, FullBoard board) {

        Field targetHighlight = Factory.makeField(xCoordinate, 1, PlayerID.NONE);
        Field lowestFreeField = getLowestFreeField(targetHighlight, board);

        Set<Field> columnHighlight = getAllFieldsOnBoard().stream()
                .filter(field -> field.xCoordinate() == targetHighlight.xCoordinate())
                .collect(Collectors.toSet());

        Set<Field> bombJokerHighlight = getAllFieldsOnBoard().stream()
                .filter(field -> (Math.abs(field.xCoordinate() - lowestFreeField.xCoordinate())
                        + Math.abs(field.yCoordinate() - lowestFreeField.yCoordinate())) <= 2)
                .collect(Collectors.toSet());
        bombJokerHighlight.addAll(columnHighlight);

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

        if (targetX == -1 || targetX == Settings.fieldSize) {
            //selected whole row
            newHighlights = getAllFieldsOnBoard().stream()
                    .filter(field -> field.yCoordinate() == targetY)
                    .collect(Collectors.toList());
        } else if (targetY == 0 || targetY == Settings.fieldSize) {
            //selected whole column
            newHighlights = getAllFieldsOnBoard().stream()
                    .filter(field -> field.xCoordinate() == targetX)
                    .collect(Collectors.toList());
        } else {
            //select only a single field
            newHighlights = getAllFieldsOnBoard().stream()
                    .filter(field -> field.xCoordinate() == targetX)
                    .filter(field -> field.yCoordinate() == targetY)
                    .collect(Collectors.toList());
        }

        board.setHighlight(newHighlights);
    }

    private static void createDeleteJoker(FullGame game, Move move, FullBoard board, FullPlayer activePlayer) {
        if (game.getActiveJoker() == PlayerActiveJoker.NONE) {
            //New in Joker
            game.setActiveJoker(PlayerActiveJoker.DELETE);
            createDeleteJokerHighlight(0, Settings.fieldSize - 1, board);
        } else {
            //Joker currently in use
            final List<Field> highlight = game.getBoard().getHighlight();
            final boolean isColumnMultiHighlight;
            final boolean isRowMultiHighlight;
            if(highlight.size() > 1) {
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
                executeDeleteJoker(targetField, board);
                //Player has used his Joker
                activePlayer.useDeleteJoker();
                game.setActiveJoker(PlayerActiveJoker.NONE);
                //Change the Player
                changePlayer(game);
            } else if (move == Move.UP) {
                final int targetX;
                final int targetY;
                if(isRowMultiHighlight) {
                    targetX = -1;
                    targetY = Math.max(targetField.yCoordinate() - 1, 1);
                } else if(isColumnMultiHighlight) {
                    targetX = Settings.fieldSize - 1;
                    targetY = targetField.yCoordinate() - 1;
                } else {
                    targetX = targetField.xCoordinate();
                    targetY = targetField.yCoordinate() - 1;
                }
                createDeleteJokerHighlight(targetX, targetY, board);
            } else if (move == Move.DOWN) {
                final int targetX;
                final int targetY;
                if(isRowMultiHighlight) {
                    targetX = -1;
                    targetY = Math.min(targetField.yCoordinate() + 1, Settings.fieldSize - 1);
                } else if(isColumnMultiHighlight) {
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
                if(isRowMultiHighlight) {
                    targetX = Settings.fieldSize - 1;
                    targetY = targetField.yCoordinate();
                } else if(isColumnMultiHighlight) {
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
                if(isRowMultiHighlight) {
                    targetX = 0;
                    targetY = targetField.yCoordinate();
                } else if(isColumnMultiHighlight) {
                    targetX = Math.min(targetField.xCoordinate() + 1, Settings.fieldSize - 1);
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
            } else if (move == Move.LEFT) {
                createBombJokerHighlight(fieldOverflowX(-1, targetField.xCoordinate()), board);
            } else {
                //right
                createBombJokerHighlight(fieldOverflowX(1, targetField.xCoordinate()), board);
            }
        }

    }

    private static void executeBombJoker(Field targetHighlight, FullBoard board) {

        Field lowestFreeField = getLowestFreeField(targetHighlight, board);

        List<Field> allFields = board.getFields().stream()
                .filter(field -> (Math.abs(field.xCoordinate() - lowestFreeField.xCoordinate())
                        + Math.abs(field.yCoordinate() - lowestFreeField.yCoordinate())) <= 2)
                .collect(Collectors.toList());

        allFields.forEach(board::removeStone);

        //get every field that need to be updated for radius1
        updateBombedFields(1, lowestFreeField, board);

        //get every field that need to be updated for radius 2
        updateBombedFields(2, lowestFreeField, board);
    }

    private static Field getLowestFreeField(Field targetHighlight, FullBoard board){
        Field highestOccupiedField = board.getFields().stream()
                .filter(field -> field.xCoordinate() == targetHighlight.xCoordinate())
                .min(Comparator.comparing(Field::yCoordinate)).orElse(null);

        Field lowestFreeField = Factory.makeField(highestOccupiedField.xCoordinate(),
                highestOccupiedField.yCoordinate() - 1, PlayerID.NONE);

        return lowestFreeField;
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
                .filter(field -> (Math.abs(field.xCoordinate() - targetHighlight.xCoordinate()) == radius))
                .filter(field -> field.yCoordinate() < targetHighlight.yCoordinate())
                .collect(Collectors.toList());

        //Replace old stone positions with updated ones by fallsize
        fieldsToUpdate
                .forEach(board::removeStone);
        fieldsToUpdate
                .forEach(field -> board.placeStone(Factory
                        .makeField(field.xCoordinate(),
                                field.yCoordinate() + fallSize, field.owner())));
    }

    /**
     * executes the delete joker and removes an occupied stone from the board.
     *
     * @param targetHighlight stone that will be removed
     * @param board           that is being used
     */
    private static void executeDeleteJoker(Field targetHighlight, FullBoard board) {
        List<Field> stonesToRemove = board.getFields().stream()
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


        List<Field> allFields = board.getFields();

        if(allFields.isEmpty()){
            xCoordinate = targetXCoordinate;
            yCoordinate = Settings.fieldSize - 1;
        }else {
            xCoordinate = targetXCoordinate;
            yCoordinate = allFields.stream()
                    .filter(n -> n.xCoordinate() == targetXCoordinate)
                    .min(Comparator.comparing(Field::yCoordinate))
                    .map(field -> field.yCoordinate() - 1)
                    .orElse(Settings.fieldSize - 1);
        }

        board.placeStone(Factory.makeField(xCoordinate, yCoordinate, game.getActivePlayer()));
    }

    private static void changePlayer(FullGame game) {
        game.setActivePlayer(game.getActivePlayer() == PlayerID.PLAYER_1 ? PlayerID.PLAYER_2 : PlayerID.PLAYER_1);
    }

    private static List<Field> getAllFieldsOnBoard() {
        return IntStream.range(0, Settings.fieldSize * Settings.fieldSize)
                .mapToObj(field -> Factory.makeField(field / Settings.fieldSize, field % Settings.fieldSize, PlayerID.NONE))
                .filter(field -> field.yCoordinate() != 0).collect(Collectors.toList());
    }
}
