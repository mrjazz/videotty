package com.sheremetov.videotty;

import java.io.*;
import java.nio.file.Files;

/**
 * Created by denis on 3/25/2016.
 */
public class QuickPlayer {

    private final int HEIGHT;
    private final int WIDTH;

    private final String FILE;

    private final int WIDTH_RATE;
    private final int HEIGHT_RATE;

    final double CONTRAST_FACTOR = 2.95;

    final private QuickTerminal terminal = new QuickTerminal();
    final private Cache cache = new Cache();;

    private final String[] CHARACTERS = {" ", ".", ",", ":", ";", "i", "1", "t", "f", "L", "C", "G", "O", "8", "@"};

    public QuickPlayer(String file, int terminalWidth) throws IOException {
        FILE = file;
        Movie movie = new Movie(file);
        MovieSize movieSize = movie.getSize();
        WIDTH = movieSize.getWidth();
        HEIGHT = movieSize.getHeight();
        WIDTH_RATE = (int)Math.floor(WIDTH / terminalWidth);
        HEIGHT_RATE = (int)Math.floor(((float)HEIGHT / (float)WIDTH) * (float)WIDTH_RATE * 2);

        System.out.println(WIDTH_RATE + " " + HEIGHT_RATE);

    }

    public void play() throws IOException {
        /*try {
            readStream(new FileInputStream("data1.bin"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/
        String cmd = String.format("ffmpeg -i %s -f image2pipe -pix_fmt rgb24 -vcodec rawvideo -loglevel quiet -nostats -", FILE);
        try {
            Process p = Runtime.getRuntime().exec(cmd);
            readStream(p.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readStream(InputStream stream) {
        final int bufferSize = WIDTH * HEIGHT * 3;
        BufferedInputStream f = new BufferedInputStream(stream);

        byte[] buffer = new byte[bufferSize];

        int byteCounter = 0;
        int framesCount = 0;
        double rate;
        int curByte;
        long curTime, lastFrame = System.currentTimeMillis();

        try {
            while (true) {
                curByte = f.read();
                if (curByte < 0) break;
                buffer[byteCounter++] = (byte)curByte;
                if (byteCounter == bufferSize) {
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

//                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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