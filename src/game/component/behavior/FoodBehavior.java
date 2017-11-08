package game.component.behavior;

import java.util.Hashtable;

import game.Entity;
import game.GameWorld;
import game.component.sprite.StaticSprite;
import mote4.scenegraph.Window;
import mote4.util.texture.TextureMap;

public class FoodBehavior extends Behavior {
	public enum foodType {
		TRASH(0),
		CHEESE(1),
		PEANUT(2),
		STRAWBERRY(3),
		;
		
        int index;
        foodType(int i) {
            index = i;
        }	
	}
	foodType food = foodType.TRASH;
	Hashtable matchingFoodType = new Hashtable();
	//PlayerBehavior.character[] matchingFoodType = new PlayerBehavior.character[3];
	
	public FoodBehavior() {
		matchingFoodType.put(foodType.CHEESE, PlayerBehavior.character.RAT);
		matchingFoodType.put(foodType.PEANUT, PlayerBehavior.character.RACCOON);
		matchingFoodType.put(foodType.STRAWBERRY, PlayerBehavior.character.OPOSSUM);
	}
    @Override
    public void act() {}
    @Override
    public void onCollide(Entity e) {
    	if (e.getBehavior() instanceof PlayerBehavior) {
    		 PlayerBehavior playerBehavior = (PlayerBehavior)GameWorld.getInstance().getPlayer().getBehavior();
    		 if (food == foodType.TRASH) {
    			playerBehavior.boostHealth(playerBehavior.getCurrentCharacter(), (float) .5);
    			entity.kill();
    		 }
    		 else {
    			 if(matchingFoodType.get(food) == playerBehavior.getCurrentCharacter()) {
    				 playerBehavior.boostHealth(playerBehavior.getCurrentCharacter(), (float) 2);
    				 entity.kill();
    			 }
    		 }
    		 
    	}	
    }
    public void setFoodType(foodType f) {
    	
    	//entity.swapSprite(new StaticSprite(TextureMap.get("entity_trash")));
    	StaticSprite s = (StaticSprite)entity.getSprite();
    	s.setSprite(f.index);
    	food = f;
    }
}
