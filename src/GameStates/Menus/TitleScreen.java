package GameStates.Menus;
import GameStates.GameState;
import GameStates.GameStateManager;
import GameStates.TestStates.SomeLevel;
import Handlers.*;
import Handlers.Button;

import java.awt.*;

public class TitleScreen extends GameState {
    Handlers.Button play;


    public static void main(String[] args) {
        GameLauncher game = new GameLauncher();
        game.startGame(new TitleScreen(game.getManager()));
    }

    public TitleScreen(GameStateManager manager) {
        super(manager);
        play = new Handlers.Button(new Rectangle(100, 200, 200, 40), "Start game");
    }

    @Override
    public void update() {
        if (Game.mouseInput.didMouseClickOn(play)) {
            parentManager.setGame(new SomeLevel(parentManager));
        }
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Quartz MS", Font.PLAIN, 84));
        g.drawString("Game over", 100, 100);
        play.draw(g);
    }
}
