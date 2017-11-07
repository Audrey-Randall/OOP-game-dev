package game.component.behavior;

import game.Entity;
import game.GameWorld;
import mote4.scenegraph.Window;

public class CoinBehavior extends Behavior {
	

	
    @Override    
    public void act() {

    }

    @Override
    public void onCollide(Entity e) {
        entity.moveTo(Math.random()*(640-64), Math.random()*(480-64));
        if (e.getBehavior() instanceof PlayerBehavior) {
        	if (Window.time() > 3) {
	            PlayerBehavior playerBehavior = (PlayerBehavior)GameWorld.getInstance().getPlayer().getBehavior();
	        	playerBehavior.adjustScore(1);
	        	playerBehavior.printStats();
        	}
        }
    }
}
