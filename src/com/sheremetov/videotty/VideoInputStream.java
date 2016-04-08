package com.sheremetov.videotty;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by denis on 4/1/2016.
 */
public class VideoInputStream extends InputStream {

    private final InputStream input;
    private final MovieSize movieSize;

    public VideoInputStream(String movieFile) throws IOException {
        movieSize = (new Movie(movieFile)).getSize();

        String cmd = String.format("ffmpeg -i %s -f image2pipe -pix_fmt rgb24 -vcodec rawvideo -loglevel quiet -nostats -", movieFile);
        input = Runtime.getRuntime().exec(cmd).getInputStream();
    }

    public int getVideoWidth() {
        return movieSize.getWidth();
    }

    public int getVideoHeight() {
        return movieSize.getHeight();
    }

    @Override
    public int read() throws IOException {
        return input.read();
    }
}
