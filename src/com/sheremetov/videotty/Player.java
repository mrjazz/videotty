package com.sheremetov.videotty;

import java.io.*;

/**
 * Created by denis on 3/25/2016.
 */
public class Player {

    public void play(String file, int terminalWidth) throws IOException {
        VideoInputStream inputStream = new VideoInputStream(file);
        TerminalOutputStream outputStream = new TerminalOutputStream(inputStream.getVideoWidth(), inputStream.getVideoHeight(), terminalWidth, 40);

        VideoToVT100 converter = new VideoToVT100(inputStream, outputStream);
        converter.start();
    }
}