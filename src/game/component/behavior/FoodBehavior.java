package game.component.behavior;

import game.Entity;
import game.GameWorld;
import mote4.scenegraph.Window;

public class FoodBehavior extends Behavior {
	public enum foodType {
		TRASH(0),
		CHEESE(1),
		PEANUT(2),
		STRAWBERRY(3);
		
        int index;
        foodType(int i) {
            index = i;
        }	
	}
	
	foodType food = foodType.TRASH;
	
    @Override
    public void act() {}
    @Override
    public void onCollide(Entity e) {
    	if (e.getBehavior() instanceof PlayerBehavior) {
    		 PlayerBehavior playerBehavior = (PlayerBehavior)GameWorld.getInstance().getPlayer().getBehavior();
    		 playerBehavior.boostHealth(playerBehavior.getCurrentCharacter(), (float)(.5 + ((food.index == 0) ? 2 : 0)));
    	}	
    }
    public void setFoodType(int foodTypeIndex) {
    	food.index = foodTypeIndex;
    }
}
