package com.sheremetov.videotty;

import java.util.HashMap;

/**
 * Created by denis on 3/26/2016.
 */
public class Cache {
    private HashMap<Integer, String> lines = new HashMap<Integer, String>();

    public void hitLine(int line, String value) {
        lines.put(line, value);
    }

    public boolean isExists(int line, String value) {
        String exists = lines.get(line);
        return exists != null && exists.equals(value);
    }
}
