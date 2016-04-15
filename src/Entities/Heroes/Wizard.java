package Entities.Heroes;

import AssetManagers.SoundManager;
import Bullets.FireBall;
import Entities.Hero;
import Handlers.Game;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Wizard extends Hero {
    int length;
    int duration;
    SoundManager soundManager;
    public Wizard() {
        super();
        length = 0;
        soundManager = new SoundManager();
        soundManager.addSound("shoot", "126423__cabeeno-rossley__shoot-laser.wav");
        duration = 15;
        this.damage = 50;
        this.velocity = 10 ;
        setDirection(Direction.Right);
    }

    public void update() {
        super.update();

        if (Game.keys.isPressed(KeyEvent.VK_F)) {
            length = duration;
            Shoot(new FireBall(new Rectangle(this.position), Game.mouseInput.getMouseLocation()));
            soundManager.playSound("shoot");
        }

        if (length > 0){
            length--;
        }
    }

}
