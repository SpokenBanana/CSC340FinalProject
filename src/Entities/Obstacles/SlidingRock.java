package Entities.Obstacles;

import Entities.Block;
import Entities.Entity;
import Entities.Hero;
import Entities.MapEntity;

import java.awt.*;

public class SlidingRock extends Entity {
    public Hero player;
    public SlidingRock(Hero hero, Rectangle position) {
        super();
        player = hero;
        this.position = position;
        moveTo(position.x, position.y);
        sprites.addSprite("main", "rock.png");
        sprites.setCurrent("main");
    }

    public void update() {
        super.update();
        if (this.player.getFacingRect().intersects(position) &&
                this.player.getCurrentDirection() != Direction.Standing) {
            slide(player.getFacingDirection());
        }
        else
            currentDirection = Direction.Standing;
    }

    public void slide(Direction direction) {
        setDirection(direction);
    }

    public boolean collisionCondition(MapEntity block, Rectangle newPositon){
        return block != this && block != player && block.getPosition().intersects(newPositon);
    }
}
