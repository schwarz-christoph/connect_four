/* (C) 2021, R. Schiedermeier, rs@cs.hm.edu
 * BellSoft Java 15.0.1, Armbian 5.67
 * 4b: ARMv8 64 bit, 1400 GHz, 6 Core(s), 4052 MByte RAM
 * RockPi 4B
 */
package edu.hm.cs.rs.se2.ui;

import java.io.IOException;
import java.io.PrintWriter;

/** UI, das Ausgaben auf System.out schreibt und Eingabe von System.in liest.
 * @author R. Schiedermeier, rs@cs.hm.edu
 * @version 2021-02-27
 */
public class ConsoleUI extends BaseLetterGrid implements UI {
    /** Neues UI.
     * @param width Anzahl Zeichen quer.
     * @param height Anzahl Zeilen senkrecht.
     */
    public ConsoleUI(int width, int height) {
        super(new PrintWriter(System.out, true), width, height);
    }

    @Override public int event(boolean wait) {
        if(!wait)
            throw new IllegalArgumentException("cannot read keyboard async");
        int key;
        do
            try {
                key = System.in.read();
            } catch(IOException ioException) {
                throw new RuntimeException(ioException);
            }
        while("lrudm".indexOf(key) < 0);
        return key;
    }

    @Override public void prompt(String text) {
        System.out.println(text);
    }
}
