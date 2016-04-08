package com.sheremetov.videotty;

/**
 * Created by denis on 3/28/2016.
 */
public class MovieSize {

    final private int width;
    final private int height;

    public MovieSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return String.format("%dx%d", width, height);
    }
}
