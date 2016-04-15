package GameStates.Menus;

import GameStates.GameState;
import GameStates.GameStateManager;
import Handlers.*;
import Handlers.Button;

import java.awt.*;

public class PauseMenu extends GameState{
    Handlers.Button resume;
    Handlers.Button quit;
    public PauseMenu(GameStateManager manager) {
        super(manager);
        resume = new Button(new Rectangle(400, 200, 350,60), "Resume");
        quit = new Button(new Rectangle(400, 300, 350,60), "Quit");
    }

    @Override
    public void update() {
        if (Game.mouseInput.didMouseClickOn(resume)) {
            parentManager.deleteCurrentGame();
        }
        else if (Game.mouseInput.didMouseClickOn(quit)){
            System.exit(0);
        }
    }

    @Override
    public void draw(Graphics2D g) {
        g.setFont(new Font("Quartz MS", Font.PLAIN, 50));
        g.setColor(Color.white);
        g.drawString("Pause", 500, 100);
        resume.draw(g);
        quit.draw(g);
    }
}
