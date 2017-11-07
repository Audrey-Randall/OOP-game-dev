package game.component.behavior;

import game.Entity;
import game.GameWorld;
import mote4.scenegraph.Window;

public class EnemyBehavior extends Behavior {
	

	
	float PENALTY_SCALE_FACTOR = 5;
	
	float sin = 0;
    @Override
    public void act() {
    	entity.moveTo((int)entity.posX(),entity.posY() + Math.sin(sin) / 5);
    	sin += Window.delta() * 2;
    }

    @Override
    public void onCollide(Entity e) {
    	if (e.getBehavior() instanceof PlayerBehavior) {
    		if (Window.time() > 3) {
    		    PlayerBehavior playerBehavior = (PlayerBehavior)GameWorld.getInstance().getPlayer().getBehavior();
		        playerBehavior.tickHealth(playerBehavior.getCurrentCharacter(), (float)Window.delta() /  PENALTY_SCALE_FACTOR);
		        System.out.println("HIT!");
		        playerBehavior.printStats();
    		}
    	}
    }
}
