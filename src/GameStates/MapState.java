package GameStates;

import AssetManagers.Map;
import AssetManagers.SoundManager;
import Entities.Block;
import Entities.Enemies.Enemy;
import Entities.Enemies.Handy;
import Entities.Hero;
import Entities.MapEntity;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.html.HTMLAnchorElement;

import java.awt.*;
import java.util.ArrayList;

public class MapState extends GameState {
    protected ArrayList<MapEntity> blocks;
    protected ArrayList<Enemy> enemies;
    protected ArrayList<Rectangle> slidingRegions;
    protected  Map map;
    protected SoundManager sound;
    protected Hero player;

    public MapState(GameStateManager manger, String level, Hero player) {
        super(manger);
        this.player = player;
        blocks = new ArrayList<>();
        map = new Map(level);
        sound = new SoundManager();
        enemies = new ArrayList<>();
        extractBlocks();
        getSliding();
    }

    public MapState(GameStateManager manager, String level) {
        super(manager);
        blocks = new ArrayList<>();
        map = new Map(level);
        sound = new SoundManager();
        enemies = new ArrayList<>();
        extractBlocks();
        getSliding();
    }
    final boolean[] slide = {false};

    public MapState(GameStateManager manager) {
        super(manager);
        blocks = new ArrayList<>();
        sound = new SoundManager();
    }

    public void changeLevel(String level) {
        map = new Map(level);
        blocks = new ArrayList<>();
        extractBlocks();
        getSliding();
        getSpawn();
        spawnEnemies();
    }

    @Override
    public void update() {
        slide[0] = false;
        slidingRegions.forEach(s -> {
            if (player.getPosition().intersects(s)){
                player.isSliding = true;
                slide[0] = true;
            }

        });

        if (!slide[0])
            player.isSliding = false;

        blocks.removeIf(b -> {
            b.update();
            return b.isDead();
        });
    }

    @Override
    public void draw(Graphics2D g) {
        map.draw(g, 0);
        map.draw(g, 1);
        blocks.forEach(b -> b.draw(g));
    }
    private void extractBlocks() {
        // I named the objects I want as walls "walls" in the JSON files.
        NodeList wallObjects = map.getObject("walls");

        // this map has no walls, no need to continue
        if (wallObjects == null) {
            wallObjects = map.getObject("Walls");
            if (wallObjects == null){
                wallObjects = map.getObject("Wall");
                if (wallObjects == null)
                    return;
            }
        }

        // objects contain an array of objects defined under the "objects" property.

        for (int i = 0; i < wallObjects.getLength(); i++) {
            Element wall = (Element) wallObjects.item(i);

            // we convert the object to a JSONObject to retrieve the properties we want.
            Block block = new Block(convertElementToRectangle(wall));

            // if this property even exists, we know it was meant to be a door
            NodeList properties = wall.getElementsByTagName("properties");

            blocks.add(block);
        }
    }

    public void getSpawn() {
        blocks.add(player);
        player.setBlocks(blocks);
        NodeList spawnObject = map.getObject("Player spawn");
        if (spawnObject == null){
            spawnObject = map.getObject("Initial Spawn");
            if (spawnObject == null)
                return;
        }
        Element spawn = (Element) spawnObject.item(0);
        int x = (int) Double.parseDouble(spawn.getAttribute("x"));
        int y = (int) Double.parseDouble(spawn.getAttribute("y"));
        player.moveTo(x, y);
    }

    public void spawnEnemies() {
        enemies = new ArrayList<>();
        NodeList enemiesobjects = map.getObject("Enemy Spawn");
        if (enemiesobjects == null)
            return;
        for (int i = 0; i < enemiesobjects.getLength(); i++) {
            Element e = (Element) enemiesobjects.item(i);
            Handy handy = new Handy(player);
            handy.setBlocks(blocks);
            blocks.add(handy);
            int x = (int) Double.parseDouble(e.getAttribute("x"));
            int y = (int) Double.parseDouble(e.getAttribute("y"));
            handy.moveTo(x, y);
            enemies.add(handy);
        }
    }

    public void getSliding() {
        slidingRegions = new ArrayList<>();
        NodeList sliders = map.getObject("Sliding ice");
        if (sliders == null)
            return;

        for (int i = 0; i < sliders.getLength(); i++){
            Element slide = (Element) sliders.item(i);
            slidingRegions.add(convertElementToRectangle(slide));
        }
    }

    private Rectangle convertElementToRectangle(Element toRectangle) {
        // JSONObject's get() method returns the value in a generic Object-type. We expect the
        // property "x" to contain a numerical value, so to convert it to a numerical value we must
        // first convert it to a string with Object's toString() method and then we can convert it back
        // to an integer with Integer.parseInt(). We do this for all properties we expect a numerical value.
        int x = (int) Double.parseDouble(toRectangle.getAttribute("x"));
        int y = (int) Double.parseDouble(toRectangle.getAttribute("y"));
        int width = (int) Double.parseDouble(toRectangle.getAttribute("width"));
        int height = (int) Double.parseDouble(toRectangle.getAttribute("height"));
        return new Rectangle(x, y, width, height);
    }
}
