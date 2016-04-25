package GameStates.TestStates;

import AssetManagers.Sound;
import Entities.Hero;
import Entities.Heroes.Wizard;
import Entities.Heroes.paladin;
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
        super(manger, "intro");
        player = new paladin();
        sound.addSound("main", "126428__cabeeno-rossley__toss-throw.wav");
        sound.playSound("main");
        getSpawn();
        getRocks();
        getItems();
        spawnEnemies();
    }

    public SomeLevel(GameStateManager manager, Hero hero) {
        super(manager, "intro");
        player = hero;
        sound.addSound("main", "126428__cabeeno-rossley__toss-throw.wav");
        sound.playSound("main");
        getSpawn();
        getRocks();
        getItems();
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
