/* (C) 2021, R. Schiedermeier, rs@cs.hm.edu
 * BellSoft Java 15.0.1, Linux i386 4.18.20
 * april: AMD A4-9125 Radeon R3/2300 MHz, 2 Core(s), 7440 MByte RAM
 * HP 255 G7 Notebook PC (CND9060X2F)
 */

package edu.hm.cs.rs.se2.ui;

import java.util.List;

/** Verwendet ANSI-Escapesequenzen zur Malen von farbigen Bloecken.
 * @author R. Schiedermeier, rs@cs.hm.edu
 * @version 2021-02-27
 */
public class AnsiTerminal extends ConsoleUI {
    /** Liste von Farben in der Reihenfolge der ANSI-Farbecodes. */
    private static final List<Integer> COLORS = List.of(0,
                                                        0xFF0000,
                                                        0xFF00,
                                                        0xFFFF00,
                                                        0xFF,
                                                        0xFF00FF,
                                                        0xFFFF,
                                                        0xFFFFFF);

    /** Farbcode von Schwarz. Die anderen Farben folgen direkt danach. */
    private static final int ANSI_COLOR_CODEBASE = 40;

    /** Pixel als ANSI-Farbcodes. */
    private int[][] content = new int[width()][height()];

    public AnsiTerminal(int width, int height) {
        super(width, height);
    }

    @Override public final void clear() {
        escape("J");
        content = new int[width()][height()];
    }

    @Override public void set(int x, int y, int color) {
        content[PixelGrid.requireLess(x, width())]
                [PixelGrid.requireLess(y, height())] = COLORS.indexOf(color);
    }

    @Override public void render() {
        for(int line = 0; line < height(); line++) {
            escape((line + 1) + ";0H"); // Cursor an den Zeilenanfang
            for(int column = 0; column < width(); column++) {
                escape((content[column][line] + ANSI_COLOR_CODEBASE) + ";1m  "); // Zwei bunte Blanks
            }
            escape("0m");
            escape("0K"); // Rest der Zeile loeschen
        }
        escape((height() + 1) + ";0H"); // Cursor unter das Pixelfeld
        escape("0J"); // Rest loeschen
        printWriter().flush();
    }

    /** Gibt eine Escape-Sequenz aus.
     * @param string Zeichen nach Escape und [.
     */
    private void escape(String string) {
        printWriter().print("\u001B[");
        printWriter().print(string);

    }
}
