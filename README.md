# CSC340FinalProject
Final project for CSC 340 (software engineering)

# Adding a new Hero

* Create a new class in Entities/Hero 
* Make it extend Hero
* make sure it's main sprite is called "main"
* start adding your own features!

Example:
```Java
public class Knight extends Hero {
  public Knight() {
    super(); // make sure this is here!!
    // seems complicated but its really not! Just copy and paste this, 
    // but change "pathToSprite.png" to the sprite sheet you want to use. Look in assets/
    this.sprites.addAnimtion("main", new Animation("pathToSprite.png", 3, 150, 32)); 
    
    // now you can set the rest of the Hero's properties here!
    // look in the Hero class and Entity class to look at what you can change
  }
  
  public void update() {
    super.update(); // always have this too!
    // this method is called 60 times per second, all extra functionality or abilities you want to add goes here!
  }
  
  public void draw() {
    super.draw(); // always have this too!
    // add anything extra you want to draw around your player too
    // player location is found by getPosition() for a bounding rectangle of the player,
    // or getPosition().getLocation() for a Point object of the location.
  }
  
}
```

# Testing what you added

So you created your own Hero and want to see it in action, here is how:   
* Create a new class in GameSates/TestStates
* Make it extend MapState
* MapState has three constructors, choose to implement the constructor with one argument
* the first line in the constructor should be "super(manager, "level") where level is the map you want to load
* Create your hero and run a launcher to start your code

I know that is a lot so here is an example:

```Java
package GameStates.TestStates;

import Entities.Hero;
import Entities.Heroes.Wizard;
import GameStates.GameStateManager;
import GameStates.MapState;
import Handlers.GameLauncher;

public class SomeLevel extends MapState {
    public static void main(String[] args) {
      // this is code to start your game 
        GameLauncher launcher = new GameLauncher();
        
        // starts the game with your level
        launcher.startGame(new SomeLevel(launcher.getManager()));
    }
    
    public SomeLevel(GameStateManager manger) {
        super(manger, "icelevel");
        // or new Knight();, whatever you created
        player = new Wizard();
        
        // make sure to remember this
        getSpawn();
        // if you want enemies, add this line of code too, most maps have enemies in them
        spawnEnemies();
    }
    public void update(){
        super.update();
        // any other logic you want to add for the level as a whole goes here
    }
    public void draw(Graphics2D g) {
        super.draw(g);
        
        // anything more you want to draw goes here.
        
    }
}
```
This code is the game just for reference. You can also look at the Wizard class to see how custom actions can be implemented.
With this alone you can see your work on the screen! Just make sure you right click your code, then click "run file" to run this main method.

All of the assets are in a folder called assets. So look in there for sprites and maps. 


Any questions just email me and I'll help!
