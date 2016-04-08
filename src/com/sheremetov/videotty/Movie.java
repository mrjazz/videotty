package com.sheremetov.videotty;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by denis on 3/28/2016.
 */
public class Movie {

    final private String FILE;

    public Movie(String file) {
        FILE = file;
    }

    public MovieSize getSize() throws IOException {
        final String cmd = String.format("ffmpeg -i %s", FILE);

        Process p = Runtime.getRuntime().exec(cmd);
        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getErrorStream()));

        Pattern pattern = Pattern.compile("(\\d{2,})x(\\d{2,})");
        Matcher matcher;

        String s;
        while ((s = reader.readLine()) != null) {
            matcher = pattern.matcher(s);
            if (matcher.find()) {
                return new MovieSize(Integer.valueOf(matcher.group(1)), Integer.valueOf(matcher.group(2)));
            }
        }

        return null;
    }
}
