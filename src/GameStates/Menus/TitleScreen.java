package GameStates.Menus;
import GameStates.GameState;
import GameStates.GameStateManager;
import GameStates.SpeechState;
import GameStates.TestStates.SomeLevel;
import Handlers.*;
import Handlers.Button;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class TitleScreen extends GameState {
    Rectangle play;


    BufferedImage title;

    public static void main(String[] args) {
        GameLauncher game = new GameLauncher();
        game.startGame(new TitleScreen(game.getManager()));
    }

    public TitleScreen(GameStateManager manager) {
        super(manager);
        play = new Rectangle(110, 540, 470, 212);
        try {
            title = ImageIO.read(new File("assets/Title Screen.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        if (Game.mouseInput.didMouseClickOn(play)) {
            SomeLevel level = new SomeLevel(parentManager);
            parentManager.setGame(level);
            if (level.hastut) {
                parentManager.addGame(new SpeechState(parentManager, level, level.tut));
                level.hastut = false;
            }
        }
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.drawImage(title, 0,0, null);
    }
}
