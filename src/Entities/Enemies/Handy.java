package Entities.Enemies;

import Entities.Hero;

import java.awt.*;

public class Handy extends Enemy {
    public Handy(Hero hero) {
        super(hero);
        this.hero = hero;
        position = new Rectangle(0,0,64,64);
        moveTo(96, 32);
        sprites.addSprite("main", "enemy.png");
        sprites.setCurrent("main");
    }
}
