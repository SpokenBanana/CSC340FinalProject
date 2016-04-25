package Entities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class HeroHUD {
    protected BufferedImage healthBar;
    protected BufferedImage blank;
    protected int startHealth;

    public HeroHUD(Hero hero){
        try {
            healthBar = ImageIO.read(new File("assets/healthbar.png"));
            blank = ImageIO.read(new File("assets/blank.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        startHealth = hero.health;
    }

    public void draw(Graphics2D g, Hero player) {
        g.setFont(new Font("Pericles", Font.PLAIN, 32));
        g.setColor(Color.white);

        float percent = player.getHealth() / (float) startHealth;

        g.drawString("Player", 50, 840);
        g.drawImage(blank, 50, 850, (int)(200 * percent), 40, Color.green, null);
        g.drawImage(healthBar, 50, 850, 200, 40, null);
    }
}
