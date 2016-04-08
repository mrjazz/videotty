Implementation of rendering pictures and videos in terminal
-----------------------------------------------------------


```

                         CC CCC
                        fCCCCCC
                         CCCCCC
                         ,CCCC
                           C

                                     @@@@@@@
                                   @@@@@@@@@@@
                                  @@@@@@@@@@@@@
                                  @@@@@@@@@@@@@
            CC@@@@@@@            @@@@       @@@@
            C@C@@@@@@@           @@@         @@@
            @C@@@@@@@@@         @@@           @@@ @@@@@@
           @@@@    C@@@@        @@@    @ @    @@@ @@@@@@
           @@@       @@@        @@O    @@     @@@ @@@@@
          @@@   @     @@@     @8@@    @@@@@    @@@
          @@@   @ @   @@@    @@@@@    @@@@     @@@
      @@@ @@    @@     @@ @@ @@@@@     @@@     @@@
     @@@@ @@   @@@@8   @@ @@@C@@@              @@@
     @@@@@@     @@@    @@i@@@@ @@     CCCCC    @@@
         @@     @@     @@@ @@@@@@     CCCCC    ;@@
         @@            @@@     @@               @@
         @@            @@@     @@               @@
         @@            @@      @@               @@
         @@            @@      @@@              @@
         @@            @@      @@@             @@@
         @@            @@       @@@            @@@
         @@@           @@       @@@@          @@@
          @@@         @@@        @@G @@@ ;@@@@@@
          @@  @@   @@@@@          @@@@@@@@@@ @@@@
           @@@@ @@@ @@@@         @@@@@@@@f @@@@@@@O
          @@@@@@   @@@@@@       @@@@@@@@   @@@@@@@@
         @@@@@@@  @@@@@@@@      @@@@@@@@   @@@@@@@@
          @@@@@    @@@@@@           1@@     .

```


In Example.java are a few examples of usage this library.

Displaying strings in coordinates:

```java
    Terminal terminal = new Terminal(System.out);
    terminal.echo("WWW", 50, 1);
    terminal.echo("BBB", 100, 2);
    terminal.render();
```

For example for displaying image/playing video:

```java
    try {
        QuickPlayer player = new QuickPlayer(FILE, 80);
        player.play();
    } catch (IOException e) {
        e.printStackTrace();
    }
```

Or more fancy syntax inside slower version of Player class:

```java
    VideoInputStream inputStream = new VideoInputStream(FILE);
    TerminalOutputStream outputStream = new TerminalOutputStream(
        inputStream.getVideoWidth(),
        inputStream.getVideoHeight(),
        terminalWidth, 40
    );
    VideoToVT100 converter = new VideoToVT100(inputStream, outputStream);
    converter.start();
```

An example of playing video in terminal:


<iframe width="649" height="360" src="https://www.youtube.com/embed/FRuA2x82uC0" frameborder="0" allowfullscreen></iframe>
