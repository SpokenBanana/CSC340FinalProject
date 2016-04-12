package GameStates.TestStates;

import Entities.Enemies.Handy;
import Entities.Hero;
import Entities.Obstacles.*;
import Entities.Obstacles.PushButton;
import GameStates.GameStateManager;
import GameStates.MapState;

import java.awt.*;

public class Init extends MapState {
    private Hero hero;
    private Handy handy;
    SlidingRock rock;
    PushButton button;

    public Init(GameStateManager manager) {
        super(manager, "practice");
        hero = new Hero();
        handy = new Handy(hero);
        rock = new SlidingRock(hero);
        button = new PushButton(hero);

        handy.setBlocks(blocks);
        rock.setBlocks(blocks);
        blocks.add(hero);
        blocks.add(rock);
        blocks.add(handy);

        hero.setBlocks(blocks);
    }

    @Override
    public void update() {
        hero.update();
        handy.update();
        button.update();
        rock.update();
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.white);
        map.draw(g, 0);
        button.draw(g);
        hero.draw(g);
        handy.draw(g);
        rock.draw(g);
        map.draw(g, 1);
    }
}
