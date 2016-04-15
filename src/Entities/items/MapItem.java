package Entities.items;

import Entities.Hero;
import Entities.MapEntity;

import java.awt.*;

public abstract class MapItem implements MapEntity {
    Hero player;
    public MapItem(Hero player) {
        this.player = player;
    }

    public abstract void update();
    public abstract void draw(Graphics2D g);
}
