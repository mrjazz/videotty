package com.sheremetov.videotty;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by denis on 3/25/2016.
 */
public class QuickTerminal {

    private StringBuilder result = new StringBuilder();

    private void reset() {
        result.setLength(0);
    }

    public void echo(String s, int x, int y) {
        result
                .append('\u001b')
                .append("\u001b[")
                .append(y + 1)
                .append(";")
                .append(x + 1)
                .append("f")
                .append(s)
        ;
    }

    public void render() {
        System.out.print(result.toString());
        reset();
    }
}
