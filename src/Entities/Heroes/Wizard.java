package Entities.Heroes;

import Bullets.FireBall;
import Entities.Hero;
import Handlers.Game;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Wizard extends Hero {
    int length;
    int duration;
    public Wizard() {
        super();
        length = 0;
        duration = 15;
        this.damage = 50;
        this.velocity = 10 ;
        setDirection(Direction.Right);
        this.sprites.addSprite("fight", "ButterflyLeft.png");
    }

    public void update() {
        super.update();

        if (Game.keys.isPressed(KeyEvent.VK_F)) {
            this.sprites.setCurrent("fight");
            length = duration;
            Shoot(new FireBall(new Rectangle(this.position), Game.mouseInput.getMouseLocation()));
        }

        if (length > 0){
            this.sprites.setCurrent("fight");
            length--;
        }
    }

}
