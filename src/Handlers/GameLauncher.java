package Handlers;

import GameStates.GameState;
import GameStates.GameStateManager;
import GameStates.TestStates.Init;
import Handlers.Game;

import javax.swing.*;

public class GameLauncher {
    JFrame frame;
    Game game;
    public GameLauncher() {
        frame = new JFrame("Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game = new Game();
        frame.add(game);
        frame.pack();
        frame.setLocationRelativeTo(null);
    }

    public GameStateManager getManager() {
        return game.gameStateManager;
    }

    public void startGame(GameState gamestate){
        game.gameStateManager.addGame(gamestate);
        frame.setVisible(true);
        game.run();
    }
}
