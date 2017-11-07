package game.component.behavior;

import game.Entity;
import game.GameWorld;
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
        GameWorld instance = GameWorld.getInstance();
        Entity player = instance.getPlayer();
        PlayerBehavior behavior = (PlayerBehavior) player.getBehavior();
        behavior.tickHealth(behavior.getCurrentCharacter(), 1);
    }
}
