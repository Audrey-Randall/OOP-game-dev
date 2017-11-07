package game.component.behavior;

import game.Entity;
import game.component.collider.TilemapCollider;
import main.Input;
import mote4.scenegraph.Window;

public class PlayerBehavior extends Behavior {
    private double velX, velY;
    private int jumpsLeft = 0;
       
    private int score = 0;
	
    public int getScore() {
    	return score;
    }
    
	public enum character {
		RACCOON(0),
		OPOSSUM(1),
		RAT(2);
		
        int index;
        character(int i) {
            index = i;
        }
		
	    private static character[] characterList = values();
	    public character next()
	    {
	        return characterList[(this.ordinal()+1) % characterList.length];
	    }
	}
	
	private character currentCharacter = character.RAT;
	
	private float[] characterStats = new float[3];
	
	private float initialHealth = 4;
	private float healthScaleFactor = 100;

    public PlayerBehavior() {
    	characterStats[character.RACCOON.index] = initialHealth;
    	characterStats[character.OPOSSUM.index] = initialHealth;
    	characterStats[character.RAT.index] = initialHealth;
    }
    
    public void switchCharacter() {
    	currentCharacter = currentCharacter.next();
    }
    
    public void adjustScore(int scoreIncrease) {
    	score += scoreIncrease;
    }
    

    public void tickHealth(character characterName, float healthDecrease) {
    	characterStats[characterName.index] -= healthDecrease;
    }
    
    public void boostHealth(character characterName, float healthIncrease) {
    	characterStats[characterName.index] += healthIncrease;
    }
    
    public character getCurrentCharacter( ) {
    	return currentCharacter;
    }
    
    @Override
    public void act() {
    	//Boosts health of unused characters and wears out character in use
    	tickHealth(currentCharacter, (float)Window.delta() / healthScaleFactor);
    	boostHealth(currentCharacter.next(), (float)Window.delta() / healthScaleFactor);
    	boostHealth(currentCharacter.next().next(), (float)Window.delta() / healthScaleFactor); 
    	
    	System.out.println(currentCharacter.toString() + ": " + characterStats[currentCharacter.index]);
    	System.out.println(currentCharacter.next().toString() + ": " + characterStats[currentCharacter.next().index]);
    	System.out.println(currentCharacter.next().next().toString() + ": " + characterStats[currentCharacter.next().next().index]);
    	
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
