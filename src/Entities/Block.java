package Entities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * These are the "walls" in the game. I created this in mind that I may want to have some "Walls" behave
 * differently
 */
public class Block extends Rectangle implements MapEntity {
    private boolean isDoor;
    private BufferedImage sprite;
    public Block(Rectangle rectangle) {
        super(rectangle);
    }

    /**
     * Makes the block now a door
     */
    public void setAsDoor() {
        isDoor = true;
        try {
            sprite = ImageIO.read(new File("Assets/Sprites/items/door.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean ignore() {
        return false;
    }

    /**
     * Lets us know if the block is a door or not
     * @return true if is door
     */
    public boolean isDoor() {
        return isDoor;
    }
    /**
     * If this block is a door, then we want to be able to draw it so the player can see it is a door
     * @param g the "brush" component we draw with
     */
    public void draw(Graphics2D g) {
        g.drawImage(sprite, x, y, width, height, null);
    }

    @Override
    public Rectangle getPosition() {
        return this;
    }

    @Override
    public boolean isDead() {
        return false;
    }

    @Override
    public void update() { }
}
