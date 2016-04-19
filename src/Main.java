import GameStates.Menus.TitleScreen;
import GameStates.TestStates.Init;
import Handlers.Game;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Game game = new Game();

        // START LEVEL
        game.gameStateManager.addGame(new TitleScreen(game.gameStateManager));

        frame.add(game);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        game.run();
    }
}
