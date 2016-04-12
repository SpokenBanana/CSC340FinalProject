package Bullets;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;

/**
 * This bullet is used by the Ghost, pretty much acts the same with a different sprite
 */
public class FireBall extends Bullet {
    public FireBall(Rectangle location, Point target) {
        super(location, target);
        try {
            sprite = ImageIO.read(new File("assets/fireball.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
