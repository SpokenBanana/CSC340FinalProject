package AssetManagers;


import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

/**
    This will take the data from the various JSON files that represent a tiled map and be able
    to turn into a map object. This will allows us to get the objects specified in the JSON files as well
    as be able to render the map it describes.
 */
public class Map {
    private ArrayList<int[][]> layers;
    private Element mapData;


    // to simply things, we will use only one tiles-sheet (21x23)
    private BufferedImage tileSheet;
    private ArrayList<TileSet> tileSets;


    public Map(String filename) {
        layers = new ArrayList<int[][]>();
        File file = new File("assets/" + filename + ".tmx");
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

            mapData = builder.parse(file).getDocumentElement();

            tileSheet = ImageIO.read(new File("assets/terrain.png"));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("could not load the file.");
        }
        extractLayers();
        getTileSets();
    }
    public void drawRest(Graphics2D g, int index) {
        for (int i = index; i < layers.size(); i++) {
            draw(g, i);
        }
    }
    /**
        This will draw a layer of the map which the user will specify. The layers start at 0
     */
    public void draw(Graphics2D g, int layer) {
        if (layer < layers.size())
            drawLayer(g, layers.get(layer));
    }
    private void drawLayer(Graphics2D g, int[][] layer) {
        int SOURCE_TILE_SIZE = 32, TILE_COLUMNS = 21, TILE_SIZE = 32;

        for (int i = 0; i < layer.length; i++) {
            for (int j = 0; j < layer[0].length; j++) {
                int id = layer[i][j];
                if (layer[i][j] == -1) continue;

                // j represents how far along we are in a row, which is our x

                int x = j * TILE_SIZE;

                // i represents how far along we are in a columns, which is our y
                int y = i * TILE_SIZE;

                // we only want to draw one part of the tile sheet (the specified tile) so that means
                // we have to find a way to interpret the data. The tile id corresponds to where the tile is placed
                // on the tile sheet (top left corner = 0, to right of that tile = 1, and so on; left to right,
                // top to bottom. So that means if we take the width of each tile (which are squares) multiply it by
                // the mod of the the id by the amount of tiles in a rows is how far along it is on the x-axis,
                // and the id divided by the amount of rows is how far along it is on the y-axis.

                for (TileSet set : tileSets) {
                    if (set.inBoud(layer[i][j])) {
                        tileSheet = set.image;
                        id = set.getIndex(id);
                        TILE_COLUMNS = set.getColumns();
                        break;
                    }
                }

                int sourceX = SOURCE_TILE_SIZE * (id % TILE_COLUMNS);
                int sourceY = SOURCE_TILE_SIZE * (id / TILE_COLUMNS);
                g.drawImage(tileSheet, x, y, x + TILE_SIZE, y + TILE_SIZE,
                            sourceX, sourceY,sourceX + SOURCE_TILE_SIZE, sourceY + SOURCE_TILE_SIZE, null);
            }
        }
    }
    /**
        retrieves an object from the JSON file. Objects in the JSON are meant to represent many things and
        helps make levels easier to lay out.
     */
    public NodeList getObject(String key) {
        NodeList objectArray = mapData.getElementsByTagName("objectgroup");
        for (int i = 0; i < objectArray.getLength(); i++) {
            Element holder = (Element) objectArray.item(i);
            NodeList item = holder.getElementsByTagName("object");
            if (holder.getAttribute("name").equals(key)) {
                return item;
            }
        }
        return null;
    }

    public NodeList getTags(String tag) {
        return mapData.getElementsByTagName(tag);
    }

    public void getTileSets() {
        tileSets = new ArrayList<>();
        NodeList objects = mapData.getElementsByTagName("tileset");
        for (int i = 0; i < objects.getLength(); i++) {
            Element set = (Element) objects.item(i);
            int width = Integer.parseInt(set.getAttribute("tilewidth"));
            int fgid = Integer.parseInt(set.getAttribute("firstgid"));
            Element tset = (Element) set.getElementsByTagName("image").item(0);
            String fullImagePath = tset.getAttribute("source");
            int lastIndex = fullImagePath.lastIndexOf("/")+1;
            String imagePath;
            if (lastIndex == 0) {
                imagePath = fullImagePath;
            }
            else {
                imagePath = fullImagePath.substring(lastIndex);
            }
            TileSet s = new TileSet(imagePath, width, fgid);
            if (tileSets.size() != 0) {
                tileSets.get(tileSets.size()-1).setBound(fgid);
            }
            tileSets.add(s);
        }
    }

    /**
     * returns the object at the defined key
     * @param key they key to the object
     * @return the object that corresponds to key
     */
    public NodeList get(String key) {
        return mapData.getElementsByTagName(key);
    }
    /**
        This will read the Map object and take all the data from each tile layer and
        parse it so that we get the information we need (the tile id's) in the format we want (ArrayList<int[][]>).
     */
    private void extractLayers() {
        NodeList objects = mapData.getElementsByTagName("data");

        // an iterator is sort of a helper that helps us iterate through a collection
        for (int  k = 0; k < objects.getLength(); k++) {

            Element layer = (Element) objects.item(k);

            // from the attribute "type" we check if it is a tile layer, if so we add it to layers
            // data contains the tile numbers of the map as they are placed
            NodeList tileData = layer.getElementsByTagName("tile");

            // the attributes height and width contain the amount of tiles on each dimension
            int mapHeight = Integer.parseInt(mapData.getAttribute("height"));
            int mapWidth = Integer.parseInt(mapData.getAttribute("width"));

            // we will save the data in a 2 dimensional array to simplify rendering
            // because now we can say tiles[0][1] and understand the indexes as coordinates
            int[][] tiles = new int[mapHeight][mapWidth];
            for (int i = 0; i < mapHeight; i++){
                for (int j = 0; j < mapWidth; j++){
                    Element tile = (Element) tileData.item((i * mapWidth) + j);
                    tiles[i][j] = Integer.parseInt(tile.getAttribute("gid")) - 1;
                }
            }
            layers.add(tiles);
        }
    }
}
