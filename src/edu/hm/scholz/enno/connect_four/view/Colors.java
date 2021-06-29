package edu.hm.scholz.enno.connect_four.view;

/**
 * Implements the colors for the menu and players.
 */
public enum Colors {
    PLAYER_1(0, 100, 0),
    PLAYER_2(0, 0, 100),
    DELETE_JOKER(0, 200, 200),
    BOMB_JOKER(200, 110, 0),
    END_GAME(100, 0, 0),
    RESTART_GAME(200, 200, 0),
    HIGHLIGHT(255, 255, 255),
    EMPTY(0, 0, 0);

    /**
     * color offset for highlighted/greyed out fields.
     */
    private static final int OFFSET = 50;

    /**
     * Integer value for color red.
     */
    private final int red;

    /**
     * Integer value for color green.
     */
    private final int green;

    /**
     * Integer value for color blue.
     */
    private final int blue;

    /**
     * Constructor for the colors.
     * @param red   Integer value for red.
     * @param green Integer value for green.
     * @param blue  Integer value for blue.
     */
    Colors(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    /**
     * Returns the RGB Value based on if something is highlighted.
     *
     * @param isHighlighted if something.
     * @param isGreyed      signals if something is going to be deleted by a joker.
     * @return corresponding rgb value.
     */
    public int getRGBCode(boolean isHighlighted, boolean isGreyed) {
        final int offSetMultiplier;
        if (isHighlighted)
            offSetMultiplier = 1;
        else {
            if (isGreyed)
                offSetMultiplier = -1;
            else
                offSetMultiplier = 0;
        }
        final int colorOffset = OFFSET * offSetMultiplier;

        final int adjustedRed = Math.min(Math.max(getRed() + colorOffset, 0), 255);
        final int adjustedGreen = Math.min(Math.max(getGreen() + colorOffset, 0), 255);
        final int adjustedBlue = Math.min(Math.max(getBlue() + colorOffset, 0), 255);

        final int rgbShiftRed = 16;
        final int rgbShiftGreen = 8;

        return (adjustedRed << rgbShiftRed) + (adjustedGreen << rgbShiftGreen) + adjustedBlue;
    }
}
