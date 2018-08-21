package Main;

import Main.Game;

public class Launcher {

    public static void main(String[] args) {
        Game game = new Game("Test Game", 1500, 1000);
        game.start();
    }
}
