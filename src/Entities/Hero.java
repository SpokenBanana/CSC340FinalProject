package Entities;

import AssetManagers.Animation;
import Bullets.Bullet;
import Entities.Enemies.Enemy;
import Handlers.Game;
import com.oracle.deploy.update.UpdateCheck;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Hero extends Entity {
    public ArrayList<Bullet> bullets;
    public boolean isSliding;

    public Hero() {
        isSliding = false;
        position = new Rectangle(0,0,32,32);
        moveTo(32,32);
        sprites.addAnimation("main", new Animation("player.png", 3, 150, 32));
        sprites.setCurrent("main", 2, true);
        bullets = new ArrayList<>();
    }

    public void update() {
        super.update();
        if (isSliding) {
            if (colliding)
                isSliding = false;
        }

        if (Game.keys.isHeld(KeyEvent.VK_D) && !isSliding) {
            setDirection(Direction.Right);
        }
        else if (Game.keys.isHeld(KeyEvent.VK_A) && !isSliding) {
            setDirection(Direction.Left);
        }
        else if (Game.keys.isHeld(KeyEvent.VK_S) && !isSliding) {
            setDirection(Direction.Down);
        }
        else if (Game.keys.isHeld(KeyEvent.VK_W) && !isSliding) {
            setDirection(Direction.Up);
        }
        else {
            if (!isSliding)
                currentDirection = Direction.Standing;
        }

        if (Game.keys.isPressed(KeyEvent.VK_SPACE)) {
            Rectangle facing = getFacingBlock();
            blocks.forEach(x -> {
               if (x instanceof Enemy && x.getPosition().intersects(facing)) {
                   Enemy e = (Enemy) x;
                   e.hit(this.damage);
               }
            });
        }

        determineSprite();
        bullets.removeIf(b -> {
            b.update();
            for (MapEntity r : blocks) {
                if (r != this && r.getPosition().intersects(b.getPosition()))  {
                    if (r instanceof Enemy) {
                        Enemy e = (Enemy) r;
                        e.hit(this.damage);
                    }
                    return true;
                }
            }
            return false;
        });
    }

    public void draw(Graphics2D g) {
        super.draw(g);
        for (Bullet bullet : bullets) {
            bullet.draw(g);
        }
    }

    public Point shootInDirection(Direction dir) {
        switch (dir) {
            case Down:
                return new Point(this.position.x, this.position.y + this.position.height);
            case Left:
                return new Point(this.position.x - 1000, this.position.y);
            case Up:
                return new Point(this.position.x, this.position.y - 10);
            case Right:
                return new Point(this.position.x + this.position.width, this.position.y);
        }
        return null;
    }

    public void Shoot(Bullet b) {
        bullets.add(b);
    }

    public void determineSprite(){
        boolean still = currentDirection == Direction.Standing;
        switch (facingDirection) {
            case Left:
                sprites.setCurrent("main", 1, still);
                break;
            case Right:
                sprites.setCurrent("main", 2, still);
                break;
            case Down:
                sprites.setCurrent("main", 0, still);
                break;
            case Up:
                sprites.setCurrent("main", 3, still);
                break;
        }
    }

}
