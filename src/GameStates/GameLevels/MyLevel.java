package GameStates.GameLevels;

import Entities.Heroes.Wizard;
import GameStates.GameStateManager;
import GameStates.MapState;
import Handlers.GameLauncher;

import java.awt.*;

public class MyLevel extends MapState {

    public static void main(String[] args) {
        GameLauncher launcher = new GameLauncher();
        launcher.startGame(new MyLevel(launcher.getManager()));
    }

    public MyLevel(GameStateManager manager) {
        super(manager, "IcePuzzle001");
        player = new Wizard();
        getSpawn();
        spawnEnemies();
    }

    public void update() {
        super.update();
    }

    public void draw(Graphics2D g) {
        super.draw(g);
        map.drawRest(g, 2);
    }
}
