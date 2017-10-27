package game.component.behavior;

import game.Entity;

public class CoinBehavior extends Behavior {
    @Override
    public void act() {

    }

    @Override
    public void onCollide(Entity e) {
        entity.moveTo(Math.random()*(640-64), Math.random()*(480-64));
    }
}
