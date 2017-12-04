package game.component.behavior;

import game.Entity;
import game.GameWorld;
import game.component.behavior.PlayerBehavior.character;
import mote4.scenegraph.Window;

public class EnemyBehavior extends Behavior {
	
	public enum movementType {
		NONE(0),
		HORIZONTAL(1),
		VERTICAL(2);
		
        private int index;
        movementType(int i) {
            index = i;
        }
	    public int getIndex() { return index; }
	}
	
	movementType moveBehavior = movementType.VERTICAL;
	
	float PENALTY_SCALE_FACTOR = 1;
	
	float sin = 0;
    @Override
    public void act() {
    	if (moveBehavior == movementType.NONE) {
	    	entity.moveTo((int)entity.posX(),entity.posY() + Math.sin(sin) / 5);
    	}
    	else if (moveBehavior == movementType.HORIZONTAL) {
    		entity.moveTo((int)entity.posX() + Math.sin(sin),entity.posY());
    	}
    	else {
    		entity.moveTo((int)entity.posX(),entity.posY() + Math.sin(sin));
    	}
    	
    	sin += Window.delta() * 2;
    }

    @Override
    public void onCollide(Entity e) {
    	if (e.getBehavior() instanceof PlayerBehavior) {
    		if (Window.time() > 3) {
    		    PlayerBehavior playerBehavior = (PlayerBehavior)GameWorld.getInstance().getPlayer().getBehavior();
		        playerBehavior.tickHealth(playerBehavior.getCurrentCharacter(), (float)Window.delta() /  PENALTY_SCALE_FACTOR);
		        playerBehavior.printStats();
    		}
    	}
    }
}
