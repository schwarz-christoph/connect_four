package edu.hm.scholz.enno.connect_four.view;

public enum Colors {
    PLAYER_1(0, 100, 0),
    PLAYER_2(0, 0, 100),
    DELETE_JOKER(0, 200, 200),
    BOMB_JOKER(200, 110, 0),
    END_GAME(100, 0, 0),
    RESTART_GAME(200, 200, 0),
    HIGHLIGHT(255, 255, 255),
    EMPTY(0, 0, 0);

    private final int red;
    private final int green;
    private final int blue;
    //color offset for highlighted/greyed out fields
    private static final int OFFSET = 50;

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

    public int getRGBCode(boolean isHighlighted, boolean isGreyed) {
        final int offSetMultiplier;
        if(isHighlighted)
            offSetMultiplier = 1;
        else {
            if(isGreyed)
                offSetMultiplier = -1;
            else
                offSetMultiplier = 0;
        }
        final int colorOffset = OFFSET * offSetMultiplier;

        final int adjustedRed = Math.min(Math.max(getRed() + colorOffset, 0), 255);
        final int adjustedGreen = Math.min(Math.max(getGreen() + colorOffset, 0), 255);
        final int adjustedBlue = Math.min(Math.max(getBlue() + colorOffset, 0), 255);

        return (adjustedRed << 16) + (adjustedGreen << 8) + adjustedBlue;
    }
}
