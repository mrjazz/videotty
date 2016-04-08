package com.sheremetov.videotty;

import java.io.*;

/**
 * Created by denis on 3/25/2016.
 */
public class Terminal {

    private StringBuilder result = new StringBuilder();

    final private OutputStream outputStream;

    public Terminal(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    private void reset() {
        result.setLength(0);
    }

    public void echo(String s, int x, int y) {
        result
                .append("\u001b")
                .append("\u001b[")
                .append(y + 1)
                .append(";")
                .append(x + 1)
                .append("f")
                .append(s)
        ;
    }

    public void render() {
        //System.out.print(result.toString());

        try {
            outputStream.write(result.toString().getBytes());
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        reset();
    }

}
