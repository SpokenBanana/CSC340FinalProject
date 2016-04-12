package Entities.Enemies;

import Entities.Hero;

public abstract class AI {
    Enemy enemy;

    public AI(Enemy e){
        enemy = e;
    }

    // called to determine movement
    public abstract void updateAI(Hero player);
}
