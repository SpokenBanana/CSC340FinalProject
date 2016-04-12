package Entities.Obstacles;

import Entities.Entity;
import Entities.Hero;

import java.awt.*;

public class PushButton extends Entity {
    Hero player;
    boolean playerOn;
    boolean active;
    public PushButton(Hero player) {
        super();
        playerOn = false;
        active = false;
        this.player = player;
        position = new Rectangle(0,0, 32, 32);
        moveTo(32*2,32*6);
        sprites.addSprite("active", "button2.png");
        sprites.addSprite("unactive", "button.png");
        sprites.setCurrent("unactive");
    }

    @Override
    public boolean isDead() {
        return false;
    }

    public void update() {
        if (!playerOn && player.getPosition().intersects(position)) {
            sprites.setCurrent(active ? "active" : "unactive");
            active = !active;
            playerOn = true;
        }
        else if (playerOn && !player.getPosition().intersects(position)) {
            playerOn = false;
        }
    }

    public boolean isActive() {
        return sprites.getCurrent().equals("active");
    }
}
