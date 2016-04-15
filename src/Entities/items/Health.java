package Entities.items;

import Entities.Hero;
import org.w3c.dom.css.Rect;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Health extends MapItem {

    Rectangle position;
    boolean done;

    BufferedImage heart;

    public Health(Hero player, Rectangle position) {
        super(player);
        this.position = position;
        done = false;
        try{
            heart = ImageIO.read(new File("assets/health.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Rectangle getPosition() {
        return position;
    }

    @Override
    public boolean isDead() {
        return done;
    }

    @Override
    public boolean ignore() {
        return true;
    }

    @Override
    public void update() {
        if (player.getPosition().intersects(position)) {
            done = true;
            player.recover(25);
        }
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(heart, position.x, position.y, position.width, position.height, null);
    }
}
