package GameStates;

import AssetManagers.Map;
import AssetManagers.SoundManager;
import Entities.Block;
import Entities.Enemies.Enemy;
import Entities.Enemies.Handy;
import Entities.Hero;
import Entities.LevelChanger;
import Entities.MapEntity;
import Entities.Obstacles.SlidingRock;
import Entities.items.Health;
import GameStates.GameLevels.MazeLevels;
import GameStates.Menus.PauseMenu;
import GameStates.Menus.TitleScreen;
import GameStates.Menus.WinState;
import Handlers.Game;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.events.EventException;
import org.w3c.dom.html.HTMLAnchorElement;
import sun.management.snmp.util.SnmpLoadedClassData;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class MapState extends GameState {
    protected ArrayList<MapEntity> blocks;
    protected LevelChanger changer;
    protected ArrayList<Enemy> enemies;
    protected ArrayList<Rectangle> slidingRegions;
    protected  Map map;
    protected SoundManager sound;
    protected Hero player;
    public boolean hastut;
    public String tut;

    public MapState(GameStateManager manger, String level, Hero player) {
        super(manger);
        hastut = false;
        this.player = player;
        blocks = new ArrayList<>();
        sound = new SoundManager();
        sound.addSound("complete", "126421__cabeeno-rossley__level-complete.wav");
        sound.playSound("complete");
        map = new Map(level);
        sound = new SoundManager();
        enemies = new ArrayList<>();
        extractBlocks();
        getProps();
        getSpawn();
        getSliding();
        spawnEnemies();
        getEnd();
        getRocks();
        getItems();
    }

    public MapState(GameStateManager manager, String level) {
        super(manager);
        blocks = new ArrayList<>();
        map = new Map(level);
        sound = new SoundManager();
        sound.addSound("complete", "126421__cabeeno-rossley__level-complete.wav");
        sound.playSound("complete");
        enemies = new ArrayList<>();
        extractBlocks();
        getSliding();
        getProps();
        getEnd();
    }
    final boolean[] slide = {false};

    public MapState(GameStateManager manager) {
        super(manager);
        blocks = new ArrayList<>();
        sound = new SoundManager();
    }

    public void changeLevel(String level) {
        if (level.equals("End")) {
            parentManager.clear();
            soundManager.clearAllSounds();
            parentManager.setGame(new WinState(parentManager));
            return;
        }
        sound.playSound("complete");
        sound.stopSound("music");
        map = new Map(level);
        blocks = new ArrayList<>();
        extractBlocks();
        getSpawn();
        getSliding();
        spawnEnemies();
        getProps();
        getEnd();
        getItems();
        if (hastut) {
            parentManager.addGame(new SpeechState(parentManager, this, tut));
            hastut = false;
        }
    }

    @Override
    public void update() {
        if (Game.keys.isPressed(KeyEvent.VK_ENTER)) {
            parentManager.addGame(new PauseMenu(parentManager));
            return;
        }
        if (this.player.getHealth() <= 0) {
            soundManager.clearAllSounds();
            while (!parentManager.gameStates.empty()) {
                ((MapState)parentManager.gameStates.peek()).soundManager.clearAllSounds();
                parentManager.deleteCurrentGame();
            }
            parentManager.setGame(new TitleScreen(parentManager));
        }
        slide[0] = false;
        slidingRegions.forEach(s -> {
            if (player.getPosition().intersects(s)){
                player.setDirection(player.getFacingDirection());
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

        if (changer != null && changer.didCross(player)) {
            if (changer.level.equals("Maze001")) {
                MazeLevels level = new MazeLevels(parentManager, changer.level, this.player);
                soundManager.clearAllSounds();
                parentManager.setGame(level);
                if (level.hastut) {
                    parentManager.addGame(new SpeechState(parentManager, level, level.tut));
                    level.hastut = false;
                }
            }
            else{
                changeLevel(changer.level);
            }
        }
    }

    @Override
    public void draw(Graphics2D g) {
        map.drawRest(g, 0);
        blocks.forEach(b -> b.draw(g));
    }
    public void extractBlocks() {
        blocks = new ArrayList<>();
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

    public void getEnd() {
        NodeList enders = map.getObject("End of Level");
        if (enders == null) return;
        Element ender = (Element) enders.item(0);
        changer = new LevelChanger(convertElementToRectangle(ender), ender.getAttribute("name"));
    }

    public void getItems() {
        NodeList itemsobj = map.getObject("Items");
        if (itemsobj == null) return;
        for (int i = 0; i < itemsobj.getLength(); i++) {
            Element item = (Element) itemsobj.item(i);
            if (item.getAttribute("name").equals("health")) {
                blocks.add(new Health(player, convertElementToRectangle(item)));
            }
        }
    }

    public void getRocks() {
        NodeList rocksobj = map.getObject("Boulder Spawn");
        if (rocksobj == null) return;
        for (int i = 0; i < rocksobj.getLength(); i++) {
            Element rock = (Element) rocksobj.item(i);
            SlidingRock r = new SlidingRock(this.player, convertElementToRectangle(rock));
            blocks.add(r);
            r.setBlocks(blocks);
        }
    }

    public void getProps() {
        NodeList props = map.getTags("properties");
        if (props == null) return;

        for (int i =0; i < props.getLength(); i++) {
            Element e = (Element) props.item(i);
            NodeList propList = e.getElementsByTagName("property");
            for (int j = 0; j < propList.getLength(); j++) {
                Element prop = (Element) propList.item(j);
                if (prop.getAttribute("name").equals("Music")) {
                    soundManager.addSound("music", prop.getAttribute("value"));
                    soundManager.playSound("music", true);
                }
                if (prop.getAttribute("name").equals("tut")) {
                    hastut = true;
                    tut = prop.getAttribute("value");
                }
            }
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
