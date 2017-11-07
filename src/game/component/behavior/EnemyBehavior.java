package game.component.behavior;

import game.Entity;
import mote4.scenegraph.Window;

public class EnemyBehavior extends Behavior {
	float sin = 0;
    @Override
    public void act() {
    	entity.moveTo((int)entity.posX(),entity.posY() + Math.sin(sin) / 5);
    	sin += Window.delta() * 2;
    }

    @Override
    public void onCollide(Entity e) {
        entity.moveTo(Math.random()*(640-64), Math.random()*(480-64));
    }
}
