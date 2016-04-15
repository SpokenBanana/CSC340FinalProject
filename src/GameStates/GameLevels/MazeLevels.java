package GameStates.GameLevels;

import Entities.Hero;
import Entities.Heroes.Wizard;
import Entities.MapEntity;
import GameStates.GameStateManager;
import GameStates.MapState;
import Handlers.Game;
import Handlers.GameLauncher;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

public class MazeLevels extends MapState {
    public static void main(String[] args) {
        GameLauncher launcher = new GameLauncher();
        launcher.startGame(new MazeLevels(launcher.getManager(), "Maze001", new Wizard()));
    }
    Random random;
    public MazeLevels(GameStateManager manger, String level, Hero player) {
        super(manger, level, player);
        random = new Random();
    }

    public void reset() {
        player.setBlocks(blocks);
    }

    public void update() {
        super.update();
        if (Game.keys.isHeld(KeyEvent.VK_A) ||
            Game.keys.isHeld(KeyEvent.VK_D) ||
            Game.keys.isHeld(KeyEvent.VK_S) ||
            Game.keys.isHeld(KeyEvent.VK_W)) {
            if (random.nextInt(1000) <= 1) {
                // battle!
                Rectangle pos = new Rectangle(this.player.getPosition());
                pos.x -= pos.x % 16;
                pos.y -= pos.y % 16;
                parentManager.addGame(new BattleMap(parentManager, "BattleMap001", this.player, pos));
            }
        }
    }

    public void draw(Graphics2D g ){
        super.draw(g);
    }
}
