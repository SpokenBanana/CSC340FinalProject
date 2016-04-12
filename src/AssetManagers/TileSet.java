package AssetManagers;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;

public class TileSet {
    protected int id;
    protected int size;
    protected int columns;
    protected int next;
    public BufferedImage image;

    public TileSet(String image, int tileSize, int firstGid) {
        try {
            this.image = ImageIO.read(new File("assets/" + image));
        } catch (Exception e) {
            e.printStackTrace();
        }
        size = tileSize;
        id = firstGid-1;
        next = -1;
        columns = this.image.getWidth() / size;
    }

    public int getIndex(int index) {
        if (id == 0) return index;
        return index - id;
    }

    public void setBound(int bound) {
        next = bound-1;
    }

    public boolean inBoud(int index) {
        return id <= index && next == -1 || id <= index && index < next;
    }

    public int getSize() {
        return size;
    }

    public int getColumns() {
        return columns;
    }
}
