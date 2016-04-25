package Entities.Heroes;

import AssetManagers.Animation;
import Entities.Hero;
import Entities.HeroHUD;

import java.awt.*;

public class paladin extends Hero {
    public paladin(){
        super();
        this.name = "Paladin";
        this.description = "Health: 200, Damage: 50, Very strong, only melee attacks (With Space bar)";
        this.sprites.addAnimation("main", new Animation("Paladin.png", 3, 150, 32));
        this.health =startHealth= 200;
        this.damage=50;
        this.hud = new HeroHUD(this);
    }

    public void update() {
        super.update();
    }
    public void draw(Graphics2D g) {
        super.draw(g);
    }
}