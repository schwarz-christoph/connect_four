/* (C) 2021, R. Schiedermeier, rs@cs.hm.edu
 * BellSoft Java 15.0.1, Linux i386 4.18.20
 * april: AMD A4-9125 Radeon R3/2300 MHz, 2 Core(s), 7440 MByte RAM
 * HP 255 G7 Notebook PC (CND9060X2F)
 */

package edu.hm.cs.rs.se2.ui;

import java.io.PrintWriter;
import java.util.Map;

/** Darstellung in Textform.
 * @author R. Schiedermeier, rs@cs.hm.edu
 * @version 2021-02-27
 */
abstract class BaseLetterGrid implements PixelGrid {
    /** Abbildung von ein paar Farben auf Buchstaben.*/
    private static final Map<Integer, Character> COLOR_LETTERS = Map.of(0, '.',
                                                                        0xFF, 'B',
                                                                        0xFF00, 'G',
                                                                        0xFFFF, 'C',
                                                                        0xFF0000, 'R',
                                                                        0xFF00FF, 'M',
                                                                        0xFFFFFF, 'W',
                                                                        0xFFFF00, 'Y');

    /** Anzahl Spalten. */
    private final int width;

    /** Anzahl Zeilen. */
    private final int height;

    /** Verkettete Zeilen von oben nach unten. */
    private String content;

    /** Wohin mit der Textausgabe. */
    private final PrintWriter printWriter;

    BaseLetterGrid(PrintWriter printWriter, int width, int height) {
        this.printWriter = printWriter;
        this.width = width;
        this.height = height;
        clear();
    }

    @Override public void clear() {
        content = (".".repeat(width()) + '\n').repeat(height());
    }

    @Override public void set(int x, int y, int color) {
        final char letter = COLOR_LETTERS.get(color);
        final int charIndex = PixelGrid.requireLess(y, height())*(width() + 1)
                + PixelGrid.requireLess(x, width());
        content = content.substring(0, charIndex) + letter + content.substring(charIndex + 1);
    }

    @Override public void render() {
        printWriter.print(content);
        printWriter.flush();
    }

    @Override public int width() {
        return width;
    }

    @Override public int height() {
        return height;
    }

    PrintWriter printWriter() {
        return printWriter;
    }

}
