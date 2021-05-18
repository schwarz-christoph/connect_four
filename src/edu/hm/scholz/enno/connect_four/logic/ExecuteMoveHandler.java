package edu.hm.scholz.enno.connect_four.logic;

import edu.hm.scholz.enno.connect_four.datastore.Field;
import edu.hm.scholz.enno.connect_four.datastore.Game;
import edu.hm.scholz.enno.connect_four.datastore.PlayerID;
import edu.hm.scholz.enno.connect_four.datastore.mutable.Factory;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullBoard;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullGame;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


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
            newXCoordinate = (targetFieldXCoordinate + 1)%7; // Calculate the new x coordinate for the new highlight in the menu
            createHighlight(newXCoordinate, game);
        }else if(move == Move.LEFT){
            newXCoordinate = (targetFieldXCoordinate - 1)%7; // Calculate the new x coordinate for the new highlight in the menu
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
        final Field targetField = currentHighlight.get(0);
        final int targetFieldXCoordinate = targetField.xCoordinate();
        final int newXCoordinate;
        if(move == Move.RIGHT){
            newXCoordinate = (targetFieldXCoordinate + 1)%7; // Calculate the new x coordinate for the new highlight
            createHighlight(newXCoordinate, game);
        }else if(move == Move.LEFT){
            newXCoordinate = (targetFieldXCoordinate - 1)%7; // Calculate the new x coordinate for the new highlight
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

    private static void createBombJokerHighlight(Field targetHighlight) {

    }

    private static void createDeleteJokerHighlight(Field targetHighlight) {

    }

    private static void createBombJoker() {

    }

    private static void createDeleteJoker() {

    }

    private static void executeBombJoker(Field targetHighlight, FullGame game) {
        FullBoard board = (FullBoard) game.getBoard();
        List<Field> allFields = game.getBoard().getFields();


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

        if(targetHighlight.owner() != PlayerID.NONE){
            allFields.remove(targetHighlight);

            //Get all field that need to be updated
            List<Field> fieldsToUpdate = allFields.stream()
                    .filter(next -> next.xCoordinate() == targetHighlight.xCoordinate())
                    .collect(Collectors.toList());

            //Remove all fields that are no longer up to date
            allFields.stream()
                    .filter(next -> next.xCoordinate() == targetHighlight.xCoordinate())
                    .filter(next -> next.yCoordinate() > targetHighlight.yCoordinate())
                    .map(next -> allFields.remove(next));

            //Move Fields to new position
            allFields.stream()
                    .filter(n -> n.xCoordinate() == targetHighlight.xCoordinate())
                    .map(n -> Factory.makeField(n.xCoordinate(), n.yCoordinate() - 1, n.owner()))
                    .collect(Collectors.toList());

            //add new field on top
            allFields.add(Factory.makeField(targetHighlight.xCoordinate(), 7, PlayerID.NONE));
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

    }
}
