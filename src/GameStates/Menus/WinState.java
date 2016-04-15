package GameStates.Menus;

import GameStates.GameState;
import GameStates.GameStateManager;
import Handlers.*;
import Handlers.Button;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class WinState extends GameState{

    BufferedImage win;
    Handlers.Button play;
    public WinState(GameStateManager manager) {
        super(manager);
        play = new Button(new Rectangle(300, 300, 300, 60), "Return to title screen");
        soundManager.addSound("yay", "fan.wav");
        soundManager.playSound("yay");
        try {
            win = ImageIO.read(new File("assets/end game screen.png"));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        if (Game.mouseInput.didMouseClickOn(play)){
            parentManager.setGame(new TitleScreen(parentManager));
        }
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(win, 0,0,null);
        play.draw(g);
    }
}
