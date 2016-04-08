package com.sheremetov.videotty;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Example {

    public static final String MOVIE_FILE = "badapple.mp4";
    public static final String IMAGE_FILE = "pair.jpeg";

    public static void main(String[] args) {
        //playMovieFast();

        //playMovieSlow();

        //displayMovieDimensions();

        displayPicture();

        //displayTextAtCoords();
    }

    private static void displayPicture() {
        try {
            QuickPlayer player = new QuickPlayer(IMAGE_FILE, 80);
            player.play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void displayTextAtCoords() {
        Terminal terminal = new Terminal(System.out);
        terminal.echo("WWW", 50, 1);
        terminal.echo("BBB", 100, 2);
        terminal.render();
    }

    private static void displayMovieDimensions() {
        Movie movie = new Movie(MOVIE_FILE);
        try {
            MovieSize movieSize = movie.getSize();
            System.out.println(movieSize.getWidth() + "x" + movieSize.getHeight());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void playMovieFast() {
        try {
            QuickPlayer player = new QuickPlayer(MOVIE_FILE, 80);
            player.play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void playMovieSlow() {
        try {
            Player player = new Player();
            player.play(MOVIE_FILE, 80);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}