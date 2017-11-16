package game.component.behavior;

import java.util.ArrayList;
import java.io.File;

import game.Entity;
import game.GameWorld;
import game.component.sprite.EmptySprite;
import game.component.sprite.Sprite;
import main.Input;

public class HatBehavior extends Behavior {
	private ArrayList<Sprite> hats;
	private int currentHat;
	private boolean changeSprite;
	public HatBehavior(){
		super();
		hats = new ArrayList<Sprite>();
		currentHat = 0;
		changeSprite = true;
	}
	
	private void changeTheSprite(){
		if (hats.size() == 0){
			entity.swapSprite(new EmptySprite());
		} else {
			entity.swapSprite(hats.get(currentHat));
		}
	}
    @Override
    public void act() {
    	Entity player = GameWorld.getInstance().getPlayer();
    	entity.moveTo(player.posX(), player.posY() - 60);
    	// Abuse of Unused Keys, probably?
    	if (Input.getInstance().isKeyNew(Input.Key.NO) && hats.size() > 0){
    		currentHat += 1;
    		if (currentHat >= hats.size()) currentHat = 0;
    		changeSprite = true;
    	} else if (Input.getInstance().isKeyNew(Input.Key.YES) && hats.size() > 0){
    		currentHat -= 1;
    		if (currentHat < 0) currentHat = hats.size()-1;
    		changeSprite = true;
    	}
    	if (changeSprite) changeTheSprite();
    	changeSprite = false;
    }
    @Override
    public void onCollide(Entity e) {
    	
    	
    }
    
    public void addHat(Sprite hat){
    	if (hats.contains(hat)) return;
    	hats.add(hat);
    	currentHat = hats.size()-1;
    	changeSprite = true;
    }
}