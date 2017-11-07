package game.component.behavior;

import game.Entity;
import game.GameWorld;
import game.component.sprite.StaticSprite;
import mote4.scenegraph.Window;
import mote4.util.texture.TextureMap;

public class FoodBehavior extends Behavior {
	public enum foodType {
		
		CHEESE(0),
		PEANUT(1),
		STRAWBERRY(2),
		TRASH(3);
		
        int index;
        foodType(int i) {
            index = i;
        }	
	}
	foodType food = foodType.TRASH;
	PlayerBehavior.character[] matchingFoodType = new PlayerBehavior.character[3];
	
	public FoodBehavior() {
		matchingFoodType[0] = PlayerBehavior.character.RAT;
		matchingFoodType[1] = PlayerBehavior.character.RACCOON;
		matchingFoodType[2] = PlayerBehavior.character.OPOSSUM;
	}
    @Override
    public void act() {}
    @Override
    public void onCollide(Entity e) {
    	if (e.getBehavior() instanceof PlayerBehavior) {
    		 PlayerBehavior playerBehavior = (PlayerBehavior)GameWorld.getInstance().getPlayer().getBehavior();
    		 if (food == foodType.TRASH) {
    			playerBehavior.boostHealth(playerBehavior.getCurrentCharacter(), (float) .5);
    		 	System.out.println("You got " + food.toString());
    		 }
    		 else
    			 if(matchingFoodType[food.index] == playerBehavior.getCurrentCharacter()) {
    				 playerBehavior.boostHealth(playerBehavior.getCurrentCharacter(), (float) 2);
    				 System.out.println("You got " + food.toString());
    			 }
    	}	
    }
    public void setFoodType(foodType f) {
    	
    	//entity.swapSprite(new StaticSprite(TextureMap.get("entity_trash")));
    	food = f;
    	System.out.println(food.toString());
    }
}
