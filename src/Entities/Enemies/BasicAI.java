package Entities.Enemies;

import Entities.Entity;
import Entities.Hero;

public class BasicAI extends AI{
    int moved;
    public BasicAI(Enemy e) {
        super(e);
        moved = 60;
    }

    @Override
    public void updateAI(Hero player) {
        if (player.distance(enemy.getPosition()) < 128*128) {
            // chase player
            enemy.setDirection(enemy.face(player.getPosition()));
            if (enemy.getFacingRect().intersects(player.getPosition())) {
                player.hit(10);
            }
        }
        else {
            if (enemy.getFacingDirection() != Entity.Direction.Left && enemy.getFacingDirection() != Entity.Direction.Right)
                enemy.setDirection(Entity.Direction.Right);
            moved--;
            if (moved <= 0) {
                moved = 60;
                enemy.setDirection(enemy.getOppositeDirectionOf(enemy.getFacingDirection()));
            }
        }
    }
}
