package Handlers;

import GameStates.GameState;
import GameStates.GameStateManager;

import javax.swing.*;
import java.awt.*;

/**
 * The main handler of the game, it controls all the game states and make sure everything gets drawn
 * and updated.
 */
public class Game extends JPanel{

    public GameStateManager gameStateManager;
    public static KeyInput keys;
    public static MouseInput mouseInput;

    // some soundManager are universal, like background music, and so big that it would take too long to
    // keep loading over and over again, those are stored here for anyone to play.

    public Game() {
        keys = new KeyInput();
        mouseInput = new MouseInput();
        setPreferredSize(new Dimension(1280, 960));
        gameStateManager = new GameStateManager();
        setFocusable(true);
        addKeyListener(keys);
        addMouseListener(mouseInput);
        addMouseMotionListener(mouseInput);
//        gameStateManager.addGame(new Menu(gameStateManager, mouseInput, keyInput));
    }

    public Game(GameState game) {
        super();
        gameStateManager.addGame(game);
    }


    public void run(){
        while (true) {
            gameStateManager.update();
            repaint();
            keys.update();
            mouseInput.update();
            try {
                // This acts like a mini "pause" so we can see some of the action.
                Thread.sleep(1000/60);
            } catch (Exception e) {
                e.printStackTrace(); //print the error that occurred
            }
        }
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.black);
        g2.fillRect(0,0, getWidth(), getHeight());
        gameStateManager.draw(g2);
    }

}
