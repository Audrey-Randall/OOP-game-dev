package game.component.behavior;

import game.Entity;
import game.GameWorld;

public class CoinBehavior extends Behavior {
	
    @Override
    public void setEntity(Entity e) {
        super.setEntity(e);
        entity.setSize(32,32);
    }
	
    @Override    
    public void act() {

    }

    @Override
    public void onCollide(Entity e) {
        entity.kill();
        //entity.moveTo(Math.random()*(640*3-64), Math.random()*(480-64));
        if (e.getBehavior() instanceof PlayerBehavior) {
        	//if (Window.time() > 3) {
	            PlayerBehavior playerBehavior = (PlayerBehavior)GameWorld.getInstance().getPlayer().getBehavior();
	        	playerBehavior.adjustScore(1);
	        	//playerBehavior.printStats();
        	//}
        }
    }
}
