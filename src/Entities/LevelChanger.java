package Entities;

import java.awt.*;

public class LevelChanger extends Rectangle {
    public String level;

    public LevelChanger(Rectangle position, String level) {
        super(position);
        this.level = level;
    }

    public boolean didCross(Hero hero) {
        return hero.getPosition().intersects(this);
    }
}
