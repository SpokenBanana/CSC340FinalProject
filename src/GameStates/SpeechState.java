package GameStates;

import AssetManagers.SoundManager;
import Handlers.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;

public class SpeechState extends GameState {
    private final char PAUSE = '`';
    private String currentScreenText;
    private String conversation;
    private GameState prev;
    private BufferedImage textBox;
    private int position;

    public SpeechState(GameStateManager manager, GameState prev, String convo) {
        super(manager);
        conversation = convo;
        position = 0;
        currentScreenText = "";
        this.prev = prev;
        soundManager = new SoundManager();
        soundManager.addSound("confirm", "confirm.wav");
        try {
            textBox = ImageIO.read(new File("assets/textbox.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        if (position  == conversation.length()) {
            if (Game.keys.isPressed(KeyEvent.VK_SPACE)) {
                soundManager.playSound("confirm");
                parentManager.deleteCurrentGame();
            }
        }
        else {
            if (conversation.charAt(position) == PAUSE && Game.keys.isPressed(KeyEvent.VK_SPACE)){
                currentScreenText = "";
                position++;
                soundManager.playSound("confirm");
            }
            else if (conversation.charAt(position) != PAUSE){
                currentScreenText += conversation.charAt(position++);
            }
        }
    }

    @Override
    public void draw(Graphics2D g) {
        prev.draw(g);
        g.setFont(new Font("Sans serif", Font.PLAIN, 20));
        g.drawImage(textBox, 0, 800, 1280, 160, null);
        g.setColor(Color.white);
        g.drawString(currentScreenText, 40, 850);
    }
}
