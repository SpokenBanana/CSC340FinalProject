package GameStates.TestStates;

import Entities.Hero;
import Entities.Heroes.Wizard;
import GameStates.GameStateManager;
import GameStates.MapState;
import Handlers.GameLauncher;

import java.awt.*;

public class SomeLevel extends MapState {
    public static void main(String[] args) {
        GameLauncher launcher = new GameLauncher();
        launcher.startGame(new SomeLevel(launcher.getManager()));
    }
    public SomeLevel(GameStateManager manger) {
        super(manger, "icelevel");
        player = new Wizard();
        getSpawn();
        spawnEnemies();
    }
    public void update(){
        super.update();
        // any other logic you want to add for the level as a whole goes here
    }
    public void draw(Graphics2D g) {
        super.draw(g);

        // anything more you want to draw goes here.

    }
}
