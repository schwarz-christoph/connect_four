/* (C) 2021, R. Schiedermeier, rs@cs.hm.edu
 * BellSoft Java 16, Linux i386 4.19.79
 * bluna: Intel Core i7-5600U/2600 MHz, 4 Core(s), 15904 MByte RAM
 * Dell Inc. Latitude E7250 (D58CN32)
 */
package edu.hm.cs.rs.se2.ui;

import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/** Steuerung eines Raspberry Pi SenseHAT, Emulator oder Hardware.
 * Braucht das Pythonprogramm.
 * @author R. Schiedermeier, rs@cs.hm.edu
 * @version 2021-03-12
 * @version 2021-06-01 sendMessage ist synchronized an this.
 * @version 2021-06-01 Redefiniertes shut schliesst die Socketstreams.
 * @version 2021-06-08 Zwei parallele Verbindungen zu Joystick und LEDs.
 */
public class SenseHAT implements Closeable, UI {
    /** Hostname des Raspi mit dem SenseHAT. */
    public static final String HOST = "127.0.0.2";

    /** Default-Port des Servers. */
    public static final int PORT = 29_144;

    /** Kantenlaenge des LED-Rasters. */
    private static final int LEDS = 8;

    /** Anzahl Zeichen, die der Raspi maximal schickt. */
    private static final int MAX_RESPONSE_LENGTH = 8;

    /** Bitmaske der niederen 24 Bit, in denen RGB liegt. */
    private static final int RGB_BITMASK = 0xFFFFFF;

    /** Verbindung fuer Events vom Joystick. */
    private final Netlink joystick;

    /** Verbindung zur Steuerung der LEDs. */
    private final Netlink leds;

    /** Neuer Adapter.
     */
    public SenseHAT() {
        this(HOST, PORT);
    }

    /** Neue Steuerung.
     * @param hostname IP-Adresse oder Hostname eines Raspberry Pi.
     * @param port Port des Servers.
     */
    public SenseHAT(String hostname, int port) {
        try {
            leds = new Netlink(new Socket(hostname, port));
            joystick = new Netlink(new Socket(hostname, port));
        }
        catch(IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }

    @Override public void clear() {
        set(new int[LEDS * LEDS]);
    }

    @Override public void close() {
        joystick.send("q");
    }

    @Override public int event(boolean wait) {
        return joystick.send(wait ? "w" : "e");
    }

    @Override public int height() {
        return LEDS;
    }

    @Override public void set(int x, int y, int rgb) {
        leds.send("p%d%d%06X%n".formatted(
            PixelGrid.requireLess(x, LEDS),
            PixelGrid.requireLess(y, LEDS),
            rgb & RGB_BITMASK));
    }

    @Override public void set(int[] rgbs) {
        if(rgbs.length != LEDS * LEDS)
            throw new IllegalArgumentException("invalid length: " + rgbs.length);
        leds.send(IntStream.of(rgbs).map(rgb -> rgb & RGB_BITMASK)
            .mapToObj("%06X"::formatted)
            .collect(Collectors.joining("", "g", "\n")));
    }

    @Override public void shut() {
        try {
            leds.close();
            joystick.close();
        }
        catch(IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }

    @Override public int width() {
        return LEDS;
    }

    /** Eine offene Socketverbindung zum Pythonserver auf dem Raspi.
     * @param up Socketstream zum Lesen von Antworten.
     * @param down Socketstream zum Schreiben von Kommandos.
     */
    private record Netlink(InputStream up, OutputStream down) implements Closeable {
        Netlink(Socket socket) throws IOException {
            this(socket.getInputStream(), socket.getOutputStream());
        }

        @Override
        public void close() throws IOException {
            up.close();
            down.close();
        }

        /** Schickt ein Kommando zum Raspi und holt die Antwort.
         * @param string Kommando.
         * @return Erstes Zeichen der Antwort, wenn es nicht "0" ist.
         * Zweites Zeichen der Antwort, wenn das erste 0 ist.
         */
        private synchronized int send(String string) {
            try {
                down.write(string.getBytes());
                final int[] buffer = new int[MAX_RESPONSE_LENGTH];
                final int received = receive(buffer);
                if(buffer[0] != '0')
                    throw new IOException("response code: " + buffer[0]);
                return received > 1 ? buffer[1] : 0;
            }
            catch(IOException ioException) {
                throw new RuntimeException(ioException);
            }
        }

        /** Liest eine Zeile vom Raspi.
         * @param buffer Array, das die Zeichen der Antwort aufnimmt.
         * @return Anzahl gelesener Zeichen.
         */
        private int receive(int[] buffer) {
            try {
                int offset = 0;
                int bite = up.read();
                while(bite >= 0 && bite != '\n') {
                    buffer[offset++] = bite;
                    bite = up.read();
                }
                if(bite < 0)
                    throw new EOFException();
                return offset;
            }
            catch(IOException ioException) {
                throw new RuntimeException(ioException);
            }
        }

    }

}
