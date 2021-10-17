/* (C) 2021, R. Schiedermeier, rs@cs.hm.edu
 * BellSoft Java 16, Linux i386 4.19.79
 * bluna: Intel Core i7-5600U/2600 MHz, 4 Core(s), 15904 MByte RAM
 * Dell Inc. Latitude E7250 (D58CN32)
 */

package edu.hm.cs.rs.se2.ui;

/** Kombination von Pixelgrid zur Ausgabe mit Joystick zur Eingabe.
 * @author R. Schiedermeier, rs@cs.hm.edu
 * @version 2021-03-16
 */
public interface UI extends PixelGrid, Joystick {
    /** Factory fuer UI-Objekte.
     * @return Ein UI-Objekt.
     */
    static UI make() {
        return make(System.getProperty("userInterface", "console"),
                    Integer.parseInt(System.getProperty("size", "8")));
    }

    /** Factory fuer UI-Objekte.
     * @param uiType Typ des UI:
     * console = Text-Ein- und Ausgabe via Terminal und Tastatur;
     * awt = Fenster mit Tasten WASD, Blank
     * hat = SenseHAT auf einem Raspberry Pi; Falls verfuegbar Hardware, ansonsten Emulator.
     * @param gridsize Kantenlaenge des Pixelrasters. Ignoriert vom SenseHAT-UI.
     * @return Ein UI-Objekt.
     * @throws IllegalArgumentException wenn uiType keinen zulaessigen Wert hat.
     */
    static UI make(String uiType, int gridsize) {
        return switch(uiType) {
            case "console" -> new ConsoleUI(gridsize, gridsize);
            case "awt" -> new AWTFrame(gridsize, gridsize);
            case "ansi" -> new AnsiTerminal(gridsize, gridsize);
            case "hat" -> new SenseHAT(System.getProperty("hostname", "localhost"), SenseHAT.PORT);
            default -> throw new IllegalArgumentException("unknown UI type");
        };
    }

    /** Demo- und Testprogramm.
     * Zeichnet bunte Pixel in die Ecken des Rasters.
     * Bewegt ein weisses Pixel mit dem Joystick.
     * @param args Kommandozeilenargumente: keine.
     */
    @SuppressWarnings({"checkstyle:magicnumber", "checkstyle:innerassignment"})
    static void main(String... args) {
        try(UI ui = make()) {
            ui.clear();
            System.out.println("red@(0,0), green@(0,7), blue@(7,7), yellow@(7,0)");
            ui.set(0, 0, 0xFF0000);
            ui.set(0, 7, 0x00FF00);
            ui.set(7, 7, 0x0000FF);
            ui.set(7, 0, 0xFFFF00);
            char direction;
            int posx = 3;
            int posy = 3;
            do {
                ui.set(posx, posy, 0xFFFFFF);
                ui.render();

                // wait for joystick event
                direction = (char)ui.event(true);
                ui.set(posx, posy, 0);
                switch(direction) {
                    case 'l' -> posx = Math.max(0, posx - 1);
                    case 'r' -> posx = Math.min(7, posx + 1);
                    case 'u' -> posy = Math.max(0, posy - 1);
                    case 'd' -> posy = Math.min(7, posy + 1);
                }
                System.out.printf("%c --> (%d,%d)%n", direction, posx, posy);
            }
            while(direction != 'm');
            ui.clear();
        }
    }
}
