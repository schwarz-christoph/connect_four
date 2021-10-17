/* (C) 2021, R. Schiedermeier, rs@cs.hm.edu
 * BellSoft Java 15.0.1, Linux i386 4.18.20
 * april: AMD A4-9125 Radeon R3/2300 MHz, 2 Core(s), 7440 MByte RAM
 * HP 255 G7 Notebook PC (CND9060X2F)
 */
package edu.hm.cs.rs.se2.ui;

import java.util.stream.IntStream;

/** Ein rechteckiges Raster mit bunten Pixeln.
 * @author R. Schiedermeier, rs@cs.hm.eduColor
 * @version 2021-02-26
 * @version 2021-06-02 Leere Defaultmethode shut() eingefuegt.
 */
public interface PixelGrid {
    /** Schaltet alle Pixel ab (schwarz).
     */
    void clear();

    /** Breite des Rasters.
     * @return Anzahl Pixel quer. Wenigstens 1.
     */
    int width();

    /** Hoehe des Rasters.
     * @return Anzahl Pixel senkrecht. Wenigstens 1.
     */
    int height();

    /** Gibt einem Pixel eine Farbe.
     * Wird eventuell erst nach einem Aufruf von render sichtbar.
     * @param x Horizontale Koordinate ab 0 = links.
     * @param y Vertikale Koordinate ab 0 = oben.
     * @param rgb Farbcode mit drei Primaerfarbenanteilen RRGGBB in den unteren drei Bytes
     * (B in Bits 0-7, G 8-15, R 16-23).
     * Jeder Anteil (RR, GG, BB) zwischen 0 = keiner und 255 = maximal.
     * Das obere Byte (Bits 24-31) spielt keine Rolle.
     * @see #render()
     */
    void set(int x, int y, int rgb);

    /** Faerbt alle Pixel auf einmal.
     * @param rgbs Array mit width * height Pixelfarben zeilenweise von oben nach unten,
     * jede Zeile von links nach rechts.
     */
    default void set(int[] rgbs) {
        IntStream.range(0, rgbs.length)
            .forEach(index -> set(index % width(), index / width(), rgbs[index]));
    }

    /** Stellt sicher, dass alle Pixel sichtbar sind.
     */
    default void render() {
    }

    /** Gibt alle Resourcen frei, die dieses Raster belegt. */
    default void shut() {
    }

    /** Stellt sicher, dass ein Wert wenigstes 0 ist und unter einer Grenze liegt.
     * @param value Irgend ein Wert.
     * @param stop Grenze, unter der value liegt muss.
     * @return Wert zwischen 0 und stop - 1.
     * @throws IllegalArgumentException wenn der Wert ausserhalb liegt.
     */
    static int requireLess(int value, int stop) {
        return requireRange(value, 0, stop);
    }

    /** Stellt sicher, dass ein Wert im vorgegebenen Bereich liegt.
     * @param value Irgend ein Wert.
     * @param min Kleinster erlaubter Wert.
     * @param max Grenze, unter der value liegt muss.
     * @return Wenigstens min und weniger als max.
     * @throws IllegalArgumentException wenn der Wert ausserhalb liegt.
     */
    static int requireRange(int value, int min, int max) {
        if(value < min)
            throw new IllegalArgumentException("at least %d required, got %d".formatted(min, value));
        if(value < max)
            return value;
        throw new IllegalArgumentException("less than %d required, got %d".formatted(max, value));
    }

}
