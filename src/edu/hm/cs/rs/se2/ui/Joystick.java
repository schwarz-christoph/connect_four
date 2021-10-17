/* (C) 2021, R. Schiedermeier, rs@cs.hm.edu
 * BellSoft Java 16, Linux i386 4.19.79
 * bluna: Intel Core i7-5600U/2600 MHz, 4 Core(s), 15904 MByte RAM
 * Dell Inc. Latitude E7250 (D58CN32)
 */
package edu.hm.cs.rs.se2.ui;

/** Ein Joystick, der Events produziert.
 * @author R. Schiedermeier, rs@cs.hm.edu
 * @version 2021-03-15
 */
public interface Joystick extends AutoCloseable {
    /** Liefert ein Event.
     * @param wait true = auf das naechste Event warten.
     * false = sofort zurueck kehren und das letzte Event liefern,
     * wenn es eines gab.
     * @return Ein Buchstabe r, l, u, d, m fuer Events links, rechts, oben, unten, Mitte.
     * x beim Aufruf mit false, wenn es vorher kein Event gab.
     */
    int event(boolean wait);

    /** Gibt einen Hinweis darauf, was der Benutzer tun soll.
     * @param text Hinweistext.
     */
    default void prompt(String text) {
    }

    /** Beendet diesen Joystick.
     */
    @Override default void close() {
    }

}
