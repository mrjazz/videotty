package com.sheremetov.videotty;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by denis on 4/1/2016.
 */
public class VideoToVT100 {

    final private VideoInputStream inputStream;
    final private TerminalOutputStream outputStream;

    public VideoToVT100(VideoInputStream input, TerminalOutputStream outputStream) {
        this.inputStream = input;
        this.outputStream = outputStream;
    }

    public void start() {
        int curByte;
        BufferedInputStream f = new BufferedInputStream(inputStream);
        try {
            while ((curByte = f.read()) >= 0) {
                outputStream.write(curByte);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
