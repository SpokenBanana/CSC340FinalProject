package GameStates.GameLevels;

import Entities.Entity;
import Entities.Hero;
import GameStates.GameStateManager;
import GameStates.MapState;

import java.awt.*;

public class BattleMap extends MapState{
    Rectangle last;
    public BattleMap(GameStateManager manger, String level, Hero player, Rectangle lastpos) {
        super(manger, level, player);
        last = lastpos;
    }

    public void update() {
        super.update();
        enemies.removeIf(Entity::isDead);
        if (enemies.size() == 0){
            this.player.bullets.clear();
            player.moveTo(last.x, last.y);
            parentManager.deleteCurrentGame();
            ((MazeLevels) parentManager.gameStates.peek()).reset();
        }
    }
}
