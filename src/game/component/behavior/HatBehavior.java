package game.component.behavior;

import game.Entity;
import game.GameWorld;

public class HatBehavior extends Behavior {
    @Override
    public void act() {
    	Entity player = GameWorld.getInstance().getPlayer();
    	entity.moveTo(player.posX(), player.posY() - 60);
    }
    @Override
    public void onCollide(Entity e) {
    	
    	
    }
}