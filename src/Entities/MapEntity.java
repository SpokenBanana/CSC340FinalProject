package Entities;

import java.awt.*;

public interface MapEntity {
    Rectangle getPosition();
    boolean isDead();
    void update();
    void draw(Graphics2D g);
}
