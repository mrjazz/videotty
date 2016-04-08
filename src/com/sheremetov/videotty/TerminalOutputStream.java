package com.sheremetov.videotty;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by denis on 4/1/2016.
 */
public class TerminalOutputStream extends OutputStream {

    private int HEIGHT;
    private int WIDTH;
    private int WIDTH_RATE;
    private int HEIGHT_RATE;

    final double CONTRAST_FACTOR = 2.95;

    final int BUFFER_SIZE;
    final byte[] buffer;

    int byteCounter = 0;
    int framesCount = 0;

    long curTime, lastFrame;
    double rate;

    final private Terminal terminal;

    final private Cache cache = new Cache();

    private final String[] CHARACTERS = {" ", ".", ",", ":", ";", "i", "1", "t", "f", "L", "C", "G", "O", "8", "@"};


    /**
     * @param videoWidth width of video
     * @param videoHeight height of video
     * @param terminalWidth width of terminal
     * @param terminalHeight not used for now but who knows?
     */
    public TerminalOutputStream(int videoWidth, int videoHeight, int terminalWidth, int terminalHeight) {
        WIDTH = videoWidth;
        HEIGHT = videoHeight;
        WIDTH_RATE = (int)Math.floor(WIDTH / terminalWidth);
        HEIGHT_RATE = (int)Math.floor(2 * (float)WIDTH_RATE);

        BUFFER_SIZE = WIDTH * HEIGHT * 3;

        buffer = new byte[BUFFER_SIZE];
        lastFrame = System.currentTimeMillis();

        terminal = new Terminal(System.out);
    }

    @Override
    public void write(int b) throws IOException {
        buffer[byteCounter++] = (byte)b;
        if (byteCounter == BUFFER_SIZE) {
            renderFrame();
        }
    }

    private void renderFrame() {
        // begin frame rate calculation
        framesCount++;
        curTime = System.currentTimeMillis();
        if (curTime - lastFrame > 1000) {
            rate = framesCount / ((curTime - lastFrame) / 1000);
            terminal.echo("___" + String.valueOf(rate) + "___", 145, 1);
            lastFrame = curTime;
            framesCount = 0;
        }
        // end frame rate calculation

        frameToAscii(buffer);
        byteCounter = 0; // reset buffer index
    }

    private int unsigned(byte x) {
        return x & 0xFF;
    }

    private void frameToAscii(byte[] data) {
        int line = 0;
        int x, y;
        double r, g, b, brightness;
        StringBuilder ascii = new StringBuilder();

        for (y = 0; y < HEIGHT; y += HEIGHT_RATE) {
            ascii.setLength(0);
            for (x = 0; x < WIDTH; x += WIDTH_RATE) {
                int offset = (y * WIDTH + x) * 3;
                r = Math.max(0, Math.min(CONTRAST_FACTOR * (unsigned(data[offset]) - 128) + 128, 255));
                g = (long) Math.max(0, Math.min(CONTRAST_FACTOR * (unsigned(data[offset + 1]) - 128) + 128, 255));
                b = (long) Math.max(0, Math.min(CONTRAST_FACTOR * (unsigned(data[offset + 2]) - 128) + 128, 255));
                brightness = 1 - (0.299 * r + 0.587 * g + 0.114 * b) / 255;

                ascii.append(CHARACTERS[((int) Math.round(brightness * 14))]);
            }

            String lineValue = ascii.toString();
            if (!cache.isExists(line, lineValue)) {
                terminal.echo(lineValue, 0, line);
                cache.hitLine(line, lineValue);
            }
            line++;
        }

        terminal.render();
    }
}