package edu.hm.scholz.enno.connect_four.logic;

import edu.hm.scholz.enno.connect_four.common.Settings;
import edu.hm.scholz.enno.connect_four.datastore.Field;
import edu.hm.scholz.enno.connect_four.datastore.PlayerID;
import edu.hm.scholz.enno.connect_four.datastore.mutable.Factory;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullBoard;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullGame;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


//TODO can not get FullBoard out of FullGame

class ExecuteMoveHandler {

    static void onEcexute(Move move, List<Field> currentHighlight, FullGame game){

        final Field fieldSelected = currentHighlight.get(0);
        if(fieldSelected.yCoordinate() == 0){
            //In Menu
            decideMenu(move, currentHighlight, game);
        }else {
            //In Matrix
            decideMatrix(move, currentHighlight, game);
        }

    }

    /**
     * decides in which direction the highlight has to move in the menu
     * @param move the move that was executed by the player
     * @param currentHighlight the current highlight
     * @param game the game
     */
    private static void decideMenu(Move move, List<Field> currentHighlight, FullGame game){
        final Field targetField = currentHighlight.get(0);
        final int targetFieldXCoordinate = targetField.xCoordinate();
        final int newXCoordinate;
        if(move == Move.RIGHT){
            newXCoordinate = (targetFieldXCoordinate + 1)%(Settings.fieldSize-1); // Calculate the new x coordinate for the new highlight in the menu
            createHighlight(newXCoordinate, game);
        }else if(move == Move.LEFT){
            newXCoordinate = (targetFieldXCoordinate - 1)%(Settings.fieldSize-1); // Calculate the new x coordinate for the new highlight in the menu
            createHighlight(newXCoordinate, game);
        }else if(move == Move.DOWN){
            createHighlight(targetFieldXCoordinate, game); // If the player goes from the menu in the matrix
        }else if(move == Move.CONFIRM){
             selectInMenu(targetFieldXCoordinate);//Select a joker, stop or restart the game
        }
    }

    /**
     * decides witch of the menu buttons is used
     * @param targetFieldXCoordinate the x coordinate of the selected field
     */
    private static void selectInMenu(int targetFieldXCoordinate){
        if(targetFieldXCoordinate < 2 || targetFieldXCoordinate > 5)
            selectJoker(targetFieldXCoordinate);
        else if(targetFieldXCoordinate == 3)
            end();
        else
            restart();

    }

    /**
     * decides witch of the joker is used
     * @param targetFieldXCoordinate the x coordinate of the selected field
     */
    private static void selectJoker(int targetFieldXCoordinate){
        if(targetFieldXCoordinate == 0 || targetFieldXCoordinate == 7)
            createBombJoker();
        else
            createDeleteJoker();
    }

    /**
     * decides in which direction the highlight has to move in the matrix
     * @param move the move that was executed by the player
     * @param currentHighlight the current highlight
     * @param game the game
     */
    private static void decideMatrix(Move move, List<Field> currentHighlight, FullGame game){

        //TODO if Joker is selected Bool to change Highlight only in the Joker menu

        final Field targetField = currentHighlight.get(0);
        final int targetFieldXCoordinate = targetField.xCoordinate();
        final int newXCoordinate;
        if(move == Move.RIGHT){
            newXCoordinate = (targetFieldXCoordinate + 1)%(Settings.fieldSize-1); // Calculate the new x coordinate for the new highlight
            createHighlight(newXCoordinate, game);
        }else if(move == Move.LEFT){
            newXCoordinate = (targetFieldXCoordinate - 1)%(Settings.fieldSize-1); // Calculate the new x coordinate for the new highlight
            createHighlight(newXCoordinate, game);
        }else if(move == Move.UP){
            createMenuHighlight(targetFieldXCoordinate, game); // If the player goes from the menu in the matrix
        }else if(move == Move.CONFIRM){
            //TODO what happens if the line is full
            createStone(currentHighlight, game); //Place a stone
        }
    }

    /**
     * creates a new highlight in the matrix
     * @param targetFieldXCoordinate the selected x coordinate of the new highlight
     * @param game the game
     */
    private static void createHighlight(int targetFieldXCoordinate, FullGame game) {
        final int menuYCoordinate = 0;
        List<Field> allFields = game.getBoard().getFields();
        List <Field> newHighlight = allFields.stream()
                .filter(n -> n.yCoordinate() != menuYCoordinate)
                .filter(n -> n.xCoordinate() == targetFieldXCoordinate)
                .collect(Collectors.toList());

        //TODO remove TypeCast
        FullBoard board = (FullBoard) game.getBoard();
        board.setHighlight(newHighlight);
    }

    /**
     * creates a new highlight in the menu
     * @param targetFieldXCoordinate the selected x coordinate of the new highlight
     * @param game the game
     */
    private static void createMenuHighlight(int targetFieldXCoordinate, FullGame game) {
        final int menuYCoordinate = 0;
        List<Field> allFields = game.getBoard().getFields();
        List <Field> newHighlight = allFields.stream()
                .filter(n -> n.yCoordinate() == menuYCoordinate)
                .filter(n -> n.xCoordinate() == targetFieldXCoordinate)
                .collect(Collectors.toList());

        //TODO remove TypeCast
        FullBoard board = (FullBoard) game.getBoard();
        board.setHighlight(newHighlight);
    }

    private static void createBombJokerHighlight(Field targetHighlight, FullGame game) {
        List<Field> allHighlights = game.getBoard().getFields();
        final List<Field> newHighlights;

        newHighlights = allHighlights.stream()
                //Filter everything thats in the radius
                .filter(field -> (Math.abs(field.xCoordinate() - targetHighlight.xCoordinate()) +
                        Math.abs(field.yCoordinate()) - targetHighlight.yCoordinate()) < 3)
                .collect(Collectors.toList());

        //TODO: Typecast ersetzen
        //TODO: Datastore updaten
        FullBoard board = (FullBoard) game.getBoard();
        board.setHighlight(newHighlights);
    }

    private static void createDeleteJokerHighlight(Field targetHighlight, FullGame game) {
        List<Field> allHighlights = game.getBoard().getFields();
        List<Field> newHighlights = null;

        //selected whole column
        if(targetHighlight.xCoordinate() > 8){
            newHighlights = allHighlights.stream()
                    .filter(field -> field.xCoordinate() == targetHighlight.xCoordinate())
                    .collect(Collectors.toList());

        //TODO: Check if user is left (y < 0)?
        //selected whole row
        }else if(targetHighlight.yCoordinate() > 8) {
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


        //TODO: Typecast ersetzen
        //TODO: Datastore updaten
        FullBoard board = (FullBoard) game.getBoard();
        board.setHighlight(newHighlights);
    }

    private static void createBombJoker() {
        //TODO Bombjoker Joker is only available not use only Highlight
    }

    private static void createDeleteJoker() {
        //TODO Delete Joker is only available not use only Highlight
    }

    private static void executeBombJoker(Field targetHighlight, FullGame game) {
        FullBoard board = (FullBoard) game.getBoard();
        List<Field> allFields = game.getBoard().getFields();
        List<Field> newAllFields = new ArrayList<>();


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
                .filter(field -> Math.abs(field.xCoordinate() - targetHighlight.xCoordinate()) == 1 )
                .filter(field -> field.yCoordinate() > targetHighlight.yCoordinate() + 1)
                .map(field -> Factory.makeField(field.xCoordinate(), field.yCoordinate() - 1, field.owner()))
                .collect(Collectors.toList());

        //Update everything on y Radius == 2
        newAllFields = newAllFields.stream()
                .filter(field -> Math.abs(field.xCoordinate() - targetHighlight.xCoordinate()) == 2 )
                .filter(field -> field.yCoordinate() > targetHighlight.yCoordinate())
                .map(field -> Factory.makeField(field.xCoordinate(), field.yCoordinate() - 1, field.owner()))
                .collect(Collectors.toList());

    }

    /**
     * executes the delete joker and removes an occupied stone from the board
     * @param targetHighlight stone that will be removed
     * @param game current game
     */
    private static void executeDeleteJoker(Field targetHighlight, FullGame game) {
        //TODO remove TypeCast
        FullBoard board = (FullBoard) game.getBoard();
        List<Field> allFields = game.getBoard().getFields();
        List<Field> newAllFields;

        //TODO: Datastore updaten
        //TODO: Fehler fixen
        //Selected a row
        if(targetHighlight.xCoordinate() > 8 || targetHighlight.xCoordinate() < 0){

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
        else if(targetHighlight.yCoordinate() > 8)
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

    private static void restart() {

    }

    private static void end() {
        System.exit(0);
    }
}
