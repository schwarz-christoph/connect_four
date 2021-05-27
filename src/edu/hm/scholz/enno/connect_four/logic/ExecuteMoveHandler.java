package edu.hm.scholz.enno.connect_four.logic;

import edu.hm.scholz.enno.connect_four.common.Settings;
import edu.hm.scholz.enno.connect_four.datastore.Field;
import edu.hm.scholz.enno.connect_four.datastore.PlayerActiveJoker;
import edu.hm.scholz.enno.connect_four.datastore.PlayerID;
import edu.hm.scholz.enno.connect_four.datastore.mutable.Factory;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullBoard;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullGame;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


//TODO can not get FullBoard out of FullGame
//TODO edit x overflow because -1%8 is -1 not 8!

class ExecuteMoveHandler {

    static void onEcexute(Move move, List<Field> currentHighlight, FullGame game, FullBoard board) {
        final Field fieldSelected = currentHighlight.get(0);

        if (game.getActiveJoker() == PlayerActiveJoker.BOMB) {
            //Player has active bomb joker
            createBombJoker(game, move, board);
        } else if (game.getActiveJoker() == PlayerActiveJoker.DELETE) {
            //Player has active delete joker
            createDeleteJoker(game, move, board);
        } else {
            if (fieldSelected.yCoordinate() == 0) {
                //Player is in Menu
                decideMenu(move, currentHighlight, game, board);
            } else {
                //Player is in Matrix
                decideMatrix(move, currentHighlight, game, board);
            }
        }
    }

    /**
     * Decides in which direction the highlight has to move in the menu.
     *
     * @param move             The move that was executed by the player.
     * @param currentHighlight The current highlight.
     * @param game             The game.
     * @param board            The board.
     */
    private static void decideMenu(Move move, List<Field> currentHighlight, FullGame game, FullBoard board) {
        final Field targetField = currentHighlight.get(0);
        final int targetFieldXCoordinate = targetField.xCoordinate();
        final int newXCoordinate;
        if (move == Move.RIGHT) {
            newXCoordinate = (targetFieldXCoordinate + 1) % Settings.fieldSize; // Calculate the new x coordinate for the new highlight in the menu
            createHighlight(newXCoordinate, board);
        } else if (move == Move.LEFT) {
            newXCoordinate = (targetFieldXCoordinate - 1) % Settings.fieldSize; // Calculate the new x coordinate for the new highlight in the menu
            createHighlight(newXCoordinate, board);
        } else if (move == Move.DOWN) {
            createHighlight(targetFieldXCoordinate, board); // If the player goes from the menu in the matrix
        } else if (move == Move.CONFIRM) {
            selectInMenu(targetFieldXCoordinate, game, board);//Select a joker, stop or restart the game
        }
    }

    /**
     * decides witch of the menu buttons is used
     *
     * @param targetFieldXCoordinate the x coordinate of the selected field
     */
    private static void selectInMenu(int targetFieldXCoordinate, FullGame game, FullBoard board) {
        if (targetFieldXCoordinate < 2 || targetFieldXCoordinate > 5) {
            selectJoker(targetFieldXCoordinate, game, board);
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
    private static void selectJoker(int targetFieldXCoordinate, FullGame game, FullBoard board) {
        if (targetFieldXCoordinate == 0 || targetFieldXCoordinate == 7) {
            createBombJoker(game, Move.CONFIRM, board);
        } else {
            createDeleteJoker(game, Move.CONFIRM, board);
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
    private static void decideMatrix(Move move, List<Field> currentHighlight, FullGame game, FullBoard board) {

        //TODO if Joker is selected Bool to change Highlight only in the Joker menu

        final Field targetField = currentHighlight.get(0);
        final int targetFieldXCoordinate = targetField.xCoordinate();
        final int newXCoordinate;
        if (move == Move.RIGHT) {

            createHighlight(fieldOverflowManager(1,0,targetField), board);
        } else if (move == Move.LEFT) {
            createHighlight(fieldOverflowManager(-1,0,targetField), board);
        } else if (move == Move.UP) {
            createMenuHighlight(targetFieldXCoordinate, board); // If the player goes from the menu in the matrix
        } else if (move == Move.CONFIRM) {
            decideConfirmMatrix(currentHighlight, game);
        }
    }

    private static int fieldOverflowManager(int adderX, int adderY, Field targetField){
        int newX;
        int endOfLine;

        if(adderX > 0){
            endOfLine = 0;
            newX = targetField.xCoordinate() + adderX;
        }else{
            //Because on 0 is the Menu which cant be accessed during the joker placement
            endOfLine = 1;
            newX = targetField.xCoordinate() + adderY;
        }

        if(newX < endOfLine){
            //Only can be -1
            //The left end
            newX = Settings.fieldSize - 1;
        }else if(newX >= Settings.fieldSize){
            //The right end
            newX = newX%Settings.fieldSize;
        }

        return newX;
    }

    /**
     * Decides if the move is valid. Because if the line is full no more stones could be placed
     *
     * @param currentHighlight the current hightlight
     * @param game             the active game
     */
    private static void decideConfirmMatrix(List<Field> currentHighlight, FullGame game) {
        //Higherst Field in the Row
        Field targetField = Factory.makeField(currentHighlight.get(0).xCoordinate(), 1, PlayerID.NONE);

        boolean isFull = currentHighlight.stream()
                .filter(field -> field.xCoordinate() == targetField.xCoordinate())
                .noneMatch(field -> field.yCoordinate() == targetField.yCoordinate());

        if (!isFull) {
            createStone(currentHighlight, game); //Place a stone
        }

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

        IntStream.range(1, Settings.fieldSize - 1)
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
        List<Field> newHighlight = new ArrayList<>();
        newHighlight.add(Factory.makeField(targetFieldXCoordinate, 0, PlayerID.NONE));
        board.setHighlight(newHighlight);
    }

    /**
     * Creates a new bomb joker highlight in the matrix.
     *
     * @param targetHighlight The selected field to create a bomb joker in
     * @param board           The board.
     */
    private static void createBombJokerHighlight(Field targetHighlight, FullBoard board) {
        //TODO Edit because we only get occupied Fields as highlight, but not sure

        List<Field> allHighlights = board.getFields();
        final List<Field> newHighlights;

        newHighlights = allHighlights.stream()
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
        //TODO Edit because we only get occupied Fields as highlight, but not sure

        List<Field> allHighlights = board.getFields();
        List<Field> newHighlights = null;

        //selected whole column
        if (targetHighlight.xCoordinate() > 8) {
            newHighlights = allHighlights.stream()
                    .filter(field -> field.xCoordinate() == targetHighlight.xCoordinate())
                    .collect(Collectors.toList());

            //TODO: Check if user is left (y < 0)?
            //selected whole row
        } else if (targetHighlight.yCoordinate() > 8) {
            allHighlights = allHighlights.stream()
                    .filter(field -> field.yCoordinate() == targetHighlight.yCoordinate())
                    .collect(Collectors.toList());
        }
        //select only a single field
        else {
            newHighlights = allHighlights.stream()
                    .filter(field -> field.xCoordinate() == targetHighlight.xCoordinate())
                    .filter(field -> field.yCoordinate() == targetHighlight.yCoordinate())
                    .collect(Collectors.toList());
        }

        board.setHighlight(newHighlights);
    }

    private static void createBombJoker(FullGame game, Move move, FullBoard board) {
        //TODO Bombjoker Joker is only available not use only Highlight
        if (game.getActiveJoker() == PlayerActiveJoker.NONE) {
            //New in Joker
            game.setActiveJoker(PlayerActiveJoker.BOMB);
            createBombJokerHighlight(Factory.makeField(0, 1, PlayerID.NONE), board);
        } else {
            //Joker currently in use
            final List<Field> highlight = game.getBoard().getHighlight();
            final Field targetField = highlight.get(0);

            if (move == Move.CONFIRM) {
                executeDeleteJoker(targetField, game);
            } else if (move == Move.UP) {
                createDeleteJokerHighlight(Factory.makeField(targetField.xCoordinate(),
                        fieldOverflowManager(0,1,targetField), targetField.owner()), board);
            } else if (move == Move.DOWN) {
                createDeleteJokerHighlight(Factory.makeField(targetField.xCoordinate(),
                        fieldOverflowManager(0,-1,targetField), targetField.owner()), board);
            } else if (move == Move.LEFT) {
                createDeleteJokerHighlight(Factory.makeField(fieldOverflowManager(-1,0,targetField),
                        targetField.yCoordinate(), targetField.owner()), board);
            } else {
                //right
                createDeleteJokerHighlight(Factory.makeField(fieldOverflowManager(1,0,targetField),
                        targetField.yCoordinate(), targetField.owner()), board);
            }
        }
    }

    private static void createDeleteJoker(FullGame game, Move move, FullBoard board) {
        //TODO Delete Joker is only available not use only Highlight
        if (game.getActiveJoker() == PlayerActiveJoker.NONE) {
            //New in Joker
            game.setActiveJoker(PlayerActiveJoker.DELETE);
            createDeleteJokerHighlight(Factory.makeField(0, 0, PlayerID.NONE), board);
        } else {
            //Joker currently in use
            final List<Field> highlight = game.getBoard().getHighlight();
            final Field targetField = highlight.get(0);

            if (move == Move.CONFIRM) {
                executeBombJoker(targetField, game);
            } else if (move == Move.UP) {
                createBombJokerHighlight(Factory.makeField(targetField.xCoordinate(),
                        fieldOverflowManager(0,1,targetField), targetField.owner()), board);
            } else if (move == Move.DOWN) {
                createBombJokerHighlight(Factory.makeField(targetField.xCoordinate(),
                        fieldOverflowManager(0,-1,targetField), targetField.owner()), board);
            } else if (move == Move.LEFT) {
                createBombJokerHighlight(Factory.makeField(fieldOverflowManager(-1,0,targetField),
                        targetField.yCoordinate(), targetField.owner()), board);
            } else {
                //right
                createBombJokerHighlight(Factory.makeField(fieldOverflowManager(1,0,targetField),
                        targetField.yCoordinate(), targetField.owner()), board);
            }
        }
    }

    private static void executeBombJoker(Field targetHighlight, FullGame game) {
        FullBoard board = (FullBoard) game.getBoard();
        List<Field> allFields = game.getBoard().getFields();
        List<Field> newAllFields;


        //TODO: Datastore updaten
        //TODO: AllFields fehler beseitigen
        //Remove all bombed fields
        newAllFields = allFields.stream()
                //filter everything thats not in the radius
                .filter(field -> field.xCoordinate() > targetHighlight.xCoordinate() + 2)
                .filter(field -> field.xCoordinate() < targetHighlight.xCoordinate() - 2)
                .filter(field -> field.yCoordinate() > targetHighlight.yCoordinate() + 2)
                .filter(field -> field.yCoordinate() < targetHighlight.yCoordinate() - 2)
                //filter corners away
                .filter(field -> (Math.abs(field.xCoordinate() - targetHighlight.xCoordinate()) +
                        Math.abs(field.yCoordinate()) - targetHighlight.yCoordinate()) > 3)
                .collect(Collectors.toList());

        //Update everything directly above
        newAllFields = newAllFields.stream()
                .filter(field -> field.xCoordinate() == targetHighlight.xCoordinate())
                .filter(field -> field.yCoordinate() > targetHighlight.yCoordinate() + 2)
                .map(field -> Factory.makeField(field.xCoordinate(), field.yCoordinate() - 1, field.owner()))
                .collect(Collectors.toList());

        //Update everything adjacent on left and right
        newAllFields = newAllFields.stream()
                .filter(field -> Math.abs(field.xCoordinate() - targetHighlight.xCoordinate()) == 1)
                .filter(field -> field.yCoordinate() > targetHighlight.yCoordinate() + 1)
                .map(field -> Factory.makeField(field.xCoordinate(), field.yCoordinate() - 1, field.owner()))
                .collect(Collectors.toList());

        //Update everything on y Radius == 2
        newAllFields = newAllFields.stream()
                .filter(field -> Math.abs(field.xCoordinate() - targetHighlight.xCoordinate()) == 2)
                .filter(field -> field.yCoordinate() > targetHighlight.yCoordinate())
                .map(field -> Factory.makeField(field.xCoordinate(), field.yCoordinate() - 1, field.owner()))
                .collect(Collectors.toList());

    }

    /**
     * executes the delete joker and removes an occupied stone from the board.
     *
     * @param targetHighlight stone that will be removed
     * @param game            current game
     */
    private static void executeDeleteJoker(Field targetHighlight, FullGame game) {
        //TODO remove TypeCast
        FullBoard board = (FullBoard) game.getBoard();
        List<Field> allFields = game.getBoard().getFields();
        List<Field> newAllFields;

        //TODO: Datastore updaten
        //TODO: Fehler fixen
        //Selected a row
        if (targetHighlight.xCoordinate() > 8 || targetHighlight.xCoordinate() < 0) {

            //Get all fields that need to be updated and make a new list with updated fields
            List<Field> updatedFields = new ArrayList<>();
            updatedFields = allFields.stream()
                    .filter(field -> field.yCoordinate() > targetHighlight.yCoordinate())
                    .map(field -> Factory
                            .makeField(field.xCoordinate(), field.yCoordinate() - 1, field.owner()))
                    .collect(Collectors.toList());

            //Remove all fields that are no longer up to date
            newAllFields = allFields.stream()
                    .filter(field -> field.yCoordinate() != targetHighlight.yCoordinate())
                    .collect(Collectors.toList());

            //Fill the matrix with the updated fields
            newAllFields = Stream.concat(newAllFields.stream(), updatedFields.stream())
                    .collect(Collectors.toList());
        }
        //Selected a column
        else if (targetHighlight.yCoordinate() > 8)
            newAllFields = allFields.stream()
                    .filter(field -> field.xCoordinate() != targetHighlight.xCoordinate())
                    .collect(Collectors.toList());
        else {
            List<Field> updatedFields = null;
            allFields.remove(targetHighlight);

            //Remove old fields
            newAllFields = allFields.stream()
                    .filter(field -> field.yCoordinate() > targetHighlight.yCoordinate())
                    .filter(field -> field.xCoordinate() != targetHighlight.xCoordinate())
                    .collect(Collectors.toList());

            //update field to new position
            updatedFields = allFields.stream()
                    .filter(field -> field.yCoordinate() > targetHighlight.yCoordinate())
                    .filter(field -> field.xCoordinate() == targetHighlight.xCoordinate())
                    .map(field -> Factory.makeField(field.xCoordinate(), field.yCoordinate() - 1, field.owner()))
                    .collect(Collectors.toList());

            newAllFields = Stream.concat(newAllFields.stream(), updatedFields.stream()).collect(Collectors.toList());
        }

    }

    private static void createStone(List<Field> currentHighlight, FullGame game) {
        //TODO remove typecast
        FullBoard board = (FullBoard) game.getBoard();
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

    private static void restart(FullGame game, FullBoard board) {
        game.setWinner(PlayerID.NONE);
        game.setIsStarted(false);
        game.setPlayerCount(1);
        game.setActivePlayer(PlayerID.PLAYER_1);

        board.setHighlight(new ArrayList<>());
        //Removes all occupied fields from the list, resetting it to an empty list
        board.getFields().stream().forEach(field -> board.removeStone(field));

    }

    private static void end() {
        System.exit(0);
    }
}
