package Entities.Enemies;

import Entities.Block;
import Entities.Entity;
import Entities.Hero;

import java.awt.*;

public abstract class Enemy extends Entity {
    protected Hero hero;
    protected AI ai;

    public Enemy(Hero hero) {
        super();
        this.velocity = 5;
        ai = new BasicAI(this);
    }

    public void update(){
        super.update();
        ai.updateAI(hero);
        if (health <= 0) {
            dead = true;
        }
    }

}
