package GameStates.Menus;

import Entities.Hero;
import Entities.Heroes.Wizard;
import Entities.Heroes.paladin;
import GameStates.GameState;
import GameStates.GameStateManager;
import GameStates.SpeechState;
import GameStates.TestStates.SomeLevel;
import Handlers.Game;
import Handlers.GameLauncher;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class CharacterSelection extends GameState {

    public static void main(String[] args) {
        GameLauncher g = new GameLauncher();
        g.startGame(new CharacterSelection(g.getManager()));
    }

    public ArrayList<Selection> heroes;
    public BufferedImage blank;

    class Selection extends Rectangle {
        public Hero hero;
        public boolean hovered;
        public Selection(Rectangle r, Hero hero) {
            super(r);
            hero.moveTo(x, y);
            this.hero = hero;
        }

        public void draw(Graphics2D g) {
            g.drawImage(blank, x, y, width, height, hovered ? over : notHovered, null);
            hero.draw(g);
            g.setFont(gameFont);
            g.drawString(hero.name, x + 10, y + 50);
            g.drawString(hero.description, x + 10, y + 150);
        }
    }
    public Font gameFont;
    public Color over, notHovered;
    public CharacterSelection(GameStateManager manager) {
        super(manager);
        try {
            blank = ImageIO.read(new File("assets/blank.png"));
        } catch (Exception e) {}
        over = new Color(10,110,40);
        notHovered = new Color(10,70, 150);
        gameFont = new Font("sans serif", Font.PLAIN, 20);
        heroes = new ArrayList<>();
        heroes.add(new Selection(new Rectangle(40, 100, 700, 200), new paladin()));
        heroes.add(new Selection(new Rectangle(40, 350, 700, 200), new Wizard()));
    }

    @Override
    public void update() {
        heroes.forEach(e -> {
            e.hovered = Game.mouseInput.isMouseOver(e);
            if (Game.mouseInput.didMouseClickOn(e)) {
                // parentManager.setGame(new SomeLevel(parentManager, e.hero));
                SomeLevel level = new SomeLevel(parentManager, e.hero);
                parentManager.setGame(level);
                if (level.hastut) {
                    parentManager.addGame(new SpeechState(parentManager, level, level.tut));
                    level.hastut = false;
                }
            }
        });

    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.white);
        g.setFont(gameFont);
        g.drawString("Select a character", 500, 30);
        heroes.forEach(e -> e.draw(g));
    }
}
