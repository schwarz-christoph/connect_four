/* (C) 2020, R. Schiedermeier, rs@cs.hm.edu
 * Private Java 14, Linux i386 4.18.20
 * april: AMD A4-9125 Radeon R3/2300 MHz, 2 Core(s), 7440 MByte RAM
 * HP 255 G7 Notebook PC (CND9060X2F)
 */
package edu.hm.cs.rs.se2.ui;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;

/** Fenster mit bunten Quadraten.
 * Tastendruecke loesen Events aus.
 * @author R. Schiedermeier, rs@cs.hm.edu
 * @version 2020-03-07
 * @version 2020-06-01 Redefiniertes shut schliesst das Fenster.
 */
class AWTFrame extends Frame implements UI {
    /** Kantenlaenge der Zellen, in denen die Quadrate liegen. */
    private static final int CELLPX = 32;

    /** Farben der Quadrate.
     * Erster Index von links nach rechts, zweiter von oben nach unten.
     */
    private final Color[][] content;

    /** Anzahl Quadrate senkrecht. */
    private final int height;

    /** Dekoration. */
    private Insets insets;

    /** Letztes Event.
     * x = keines.
     * volatile, damit der AWT-Thread nichts cached.
     */
    private volatile int lastSeen = 'x';

    /** Anzahl Quadrate quer. */
    private final int width;

    /** Ein neues Fenster.
     * @param width Anzahl Quadrate quer. Wenigstens 1.
     * @param height Anzahl Quadrate senkrecht. Wenigstens 1.
     */
    AWTFrame(int width, int height) {
        this.width = PixelGrid.requireRange(width, 1, Integer.MAX_VALUE);
        this.height = PixelGrid.requireRange(height, 1, Integer.MAX_VALUE);
        /** Fensterbreite netto. */
        final int frameWidth = CELLPX * width;
        /** Fensterhoehe netto. */
        final int frameHeight = CELLPX * height;
        content = new Color[width][height];
        addKeyListener(new KeyAdapter() {
            @Override public void keyTyped(KeyEvent keyEvent) {
                final char keyChar = keyEvent.getKeyChar();
                if(keyChar == '\n')
                    initWindow(frameWidth, frameHeight);
                else
                    synchronized(content) {
                    final int index = "daws ".indexOf(keyChar);
                    lastSeen = index < 0 ? 'x' : "rludm".charAt(index);
                    content.notifyAll();
                }
            }

        });
        addWindowListener(new WindowAdapter() {
            @Override public void windowClosing(final WindowEvent __) {
                dispose();
            }

        });

        initWindow(frameWidth, frameHeight);
    }

    @Override public void clear() {
        for(Color[] line: content)
            Arrays.fill(line, Color.BLACK);
    }

    @Override public int event(boolean wait) {
        if(wait)
            try {
            synchronized(content) {
                while(lastSeen == 'x')
                    content.wait();
            }
        }
        catch(InterruptedException interruptedException) {
            throw new RuntimeException(interruptedException);
        }
        final int result = lastSeen;
        lastSeen = 'x';
        return result;
    }

    @Override public int height() {
        return height;
    }

    @Override public void paint(Graphics graphics) {
        for(int line = 0; line < height(); line++)
            for(int column = 0; column < width(); column++)
                drawCell(graphics, line, column, content[column][line]);
    }

    @Override public void prompt(String text) {
        setTitle(text);
    }

    @Override public void render() {
        repaint();
    }

    @Override public void set(int x, int y, int color) {
        content[PixelGrid.requireLess(x, width())][PixelGrid.requireLess(y, height())] = new Color(color);
    }

    @Override public void shut() {
        dispose();
    }

    /** Callbackmethode zum Neuzeichnen des Fensters.
     * @param graphics Graphics context.
     */
    @Override public void update(Graphics graphics) {
        paint(graphics);
    }

    @Override public int width() {
        return width;
    }

    /** Malt ein Quadrat.
     * @param graphics Graphics context.
     * @param line Zeile.
     * @param column Spalte.
     * @param color Farbe.
     */
    private void drawCell(Graphics graphics, int line, int column, Color color) {
        graphics.setColor(color);
        final int x = insets.left + column * CELLPX;
        final int y = insets.top + line * CELLPX;
        final int size = CELLPX - 4;
        final int cornerRadius = size / 2;
        graphics.fillRoundRect(x, y, size, size, cornerRadius, cornerRadius);
    }

    /** Stellt die Groesse des Fensters passend zur Deko ein.
     * @param frameWidth Lichte Weite in Pixel.
     * @param frameHeight Lichte Hoehe.
     */
    private void initWindow(int frameWidth, int frameHeight) {
        pack();
        //setVisible(true);
        insets = getInsets();
        setVisible(false);
        setSize(frameWidth + insets.left + insets.right + 1,
                frameHeight + insets.bottom + insets.top + 1);
        setVisible(true);
        //repaint();
    }

}
