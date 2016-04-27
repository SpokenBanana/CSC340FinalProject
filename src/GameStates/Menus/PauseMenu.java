package GameStates.Menus;

import GameStates.GameState;
import GameStates.GameStateManager;
import GameStates.MapState;
import Handlers.*;
import Handlers.Button;

import java.awt.*;

public class PauseMenu extends GameState{
    Handlers.Button resume;
    Handlers.Button quit;
    Handlers.Button toMenu;
    public PauseMenu(GameStateManager manager) {
        super(manager);
        resume = new Button(new Rectangle(400, 200, 350,60), "Resume");
        quit = new Button(new Rectangle(400, 300, 350,60), "Quit");
        toMenu = new Button(new Rectangle(400, 400, 350, 60), "To Main Menu");
    }

    @Override
    public void update() {
        if (Game.mouseInput.didMouseClickOn(resume)) {
            parentManager.deleteCurrentGame();
        }
        else if (Game.mouseInput.didMouseClickOn(quit)){
            System.exit(0);
        }
        else if (Game.mouseInput.didMouseClickOn(toMenu)) {
            parentManager.deleteCurrentGame();
            ((MapState)parentManager.gameStates.peek()).soundManager.clearAllSounds();
            parentManager.clear();
            parentManager.setGame(new TitleScreen(parentManager));
        }
    }

    @Override
    public void draw(Graphics2D g) {
        g.setFont(new Font("Quartz MS", Font.PLAIN, 50));
        g.setColor(Color.white);
        g.drawString("Pause", 500, 100);
        resume.draw(g);
        quit.draw(g);
        toMenu.draw(g);
    }
}
