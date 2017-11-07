package game.component.behavior;

import game.Entity;
import game.component.collider.TilemapCollider;
import main.Input;
import mote4.scenegraph.Window;

public class PlayerBehavior extends Behavior {
	private enum character {
		RACCOON,
		OPOSSUM,
		RAT;
		
	    private static character[] characterList = values();
	    public character next()
	    {
	        return characterList[(this.ordinal()+1) % characterList.length];
	    }
	}
	private character currentCharacter = character.RAT;
    private double velX, velY;
    private int jumpsLeft = 0;
    private int score = 0;
    private float health = 4;

    public PlayerBehavior() {

    }
    
    public void switchCharacter() {
    	currentCharacter = currentCharacter.next();
    }
    
    public void adjustScore(int scoreIncrease) {
    	score += scoreIncrease;
    }
    
    public void tickHealth(float healthDecrease) {
    	health -= healthDecrease;
    }
    
    public void boostHealth(float healthIncrease) {
    	health += healthIncrease;
    }
    
    @Override
    public void act() {
    	tickHealth((float)Window.delta());
        if (Input.isKeyDown(Input.Key.RIGHT)) {
            if (velX < -.2)
                velX /= 2.5; // pivot directions fast
            else if (velX < 5)
                velX += .5;
        } else if (Input.isKeyDown(Input.Key.LEFT)) {
            if (velX > .2)
                velX /= 2.5;
            else if (velX > -5)
                velX -= .5;
        } else
            velX /= 1.5;

        entity.move(velX,0);
        // check collision in x direction
        for (Entity e : entity.getGameWorld().getEntities())
            if (e.getCollider() instanceof TilemapCollider) // TODO replace with some sort of solidity check
                if (entity.getCollider().collidesWith(e.getCollider())) {
                    entity.moveTo((int)entity.posX(),entity.posY());
                    do {
                        entity.move(-Math.signum(velX), 0);
                    }
                    while (entity.getCollider().collidesWith(e.getCollider()));
                    velX = 0;
                }

        velY += .65; // gravity
        if (jumpsLeft > 0 && Input.isKeyNew(Input.Key.UP)) {
            velY = -16;
            jumpsLeft--;
        }

        entity.move(0,velY);
        // check collision in y direction
        for (Entity e : entity.getGameWorld().getEntities())
            if (e.getCollider() instanceof TilemapCollider)
                if (entity.getCollider().collidesWith(e.getCollider())) {
                    entity.moveTo(entity.posX(),(int)entity.posY());
                    if (velY > 0)
                        jumpsLeft = 2;
                    do {
                        entity.move(0, -Math.signum(velY));
                    }
                    while (entity.getCollider().collidesWith(e.getCollider()));
                    velY = 0;
                }
    }

    @Override
    public void onCollide(Entity e) {

    }
}
