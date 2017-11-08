package game.component.behavior;

import game.Entity;
import game.component.collider.TilemapCollider;
import game.component.sprite.AnimatedSprite;
import game.component.sprite.PlayerSprite;
import game.component.sprite.StaticSprite;
import main.Input;
import mote4.scenegraph.Window;
import mote4.util.texture.TextureMap;

public class PlayerBehavior extends Behavior {
	double printCounter = 0;
    private double velX, velY;
    private boolean facingRight = true;
    private boolean isMoving = false;
    private boolean isFalling = false;
    private int jumpsLeft = 0;
       
    private int score = 0;
	
    public int getScore() {
    	return score;
    }
    
	public enum character {
		RAT(0),
		OPOSSUM(1),
		RACCOON(2);
		
        private int index;
        character(int i) {
            index = i;
        }
		
	    private static character[] characterList = values();
	    public character next()
	    {
	        return characterList[(this.ordinal()+1) % characterList.length];
	    }
	    public int getIndex() { return index; }
	}
	
	private character currentCharacter = character.RAT;
	
	class characterInfo {
		float health = INITIAL_HEALTH;
		double timer = 7;
		float speed;
		
		private void updateHealth(float update) {
			health += update;
		}
		
		private void overrideHealth(float update) {
			health = update;
		}
		
		private float getHealth() {
			return health;
		}
		
		private void updateTimer(double update) {
			timer += update;
		}
		
		private void overrideTimer(double update) {
			timer = update;
		}
		
		private double getTimer() {
			return timer;
		}
		
		private void setSpeed(float s) {
			speed = s;
		}
		
		private float getSpeed() {
			return speed;
		}
		
	}

	private float[] characterStats = new float[3];
	
	private float INITIAL_HEALTH = 4;
	private float HEALTH_SCALE_FACTOR = 100;

    public PlayerBehavior() {
    	characterStats[character.RACCOON.index] = INITIAL_HEALTH;
    	characterStats[character.OPOSSUM.index] = INITIAL_HEALTH;
    	characterStats[character.RAT.index] = INITIAL_HEALTH;
    	
        behaviorList[character.RACCOON.index] = new SpecialBehaviors() { public void behavior() { raccoon.claw(); } };
        behaviorList[character.OPOSSUM.index] = new SpecialBehaviors() { public void behavior() { possum.playDead(); } };
        behaviorList[character.RAT.index] = new SpecialBehaviors() { public void behavior() { rat.scurry(); } };
    }
    
    public interface SpecialBehaviors {
    	void behavior();
    }
    
    class RatBehavior {
    	void scurry() {
    		System.out.println("Rat Special Behavior");
    	}
    }
    
    class PossumBehavior {
    	void playDead() {
    		System.out.println("Possum Special Behavior");
    	}
    }
    
    class RaccoonBehavior {
    	void claw() {
    		System.out.println("Raccoon Special Behavior");
    	}
    }
    
    RatBehavior rat = new RatBehavior();
    PossumBehavior possum = new PossumBehavior();
    RaccoonBehavior raccoon = new RaccoonBehavior();

    SpecialBehaviors[] behaviorList = new SpecialBehaviors[3]; 


    
    public void performSpecialBehavior() {
    	behaviorList[currentCharacter.index].behavior();
    }
    
    public void climb() {
    	
    }
    
    public void switchCharacter() {
    	currentCharacter = currentCharacter.next();
        PlayerSprite s = (PlayerSprite)entity.getSprite();
        s.setSprite(currentCharacter);
    	//entity.swapSprite(new AnimatedSprite(TextureMap.get("entity_possum"),  2,1,2,15));
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
    	printCounter += Window.delta();
    	
    	//Boosts health of unused characters and wears out character in use
    	tickHealth(currentCharacter, (float)Window.delta() / HEALTH_SCALE_FACTOR);
    	boostHealth(currentCharacter.next(), (float)Window.delta() / HEALTH_SCALE_FACTOR);
    	boostHealth(currentCharacter.next().next(), (float)Window.delta() / HEALTH_SCALE_FACTOR);
    	
    	if (printCounter > 3) {
    		printStats();
	    	printCounter = 0;
    	}
    	
    	 if (Input.isKeyNew(Input.Key.DOWN)) {
    		 switchCharacter();
    	 }
    	
    	 if (Input.isKeyNew(Input.Key.BACKSPACE)) {
    		 performSpecialBehavior();
    	 }
    	 
        if (Input.isKeyDown(Input.Key.RIGHT)) {
        	facingRight = true;
        	isMoving = true;
            if (velX < -.2)
                velX /= 2.5; // pivot directions fast
            else if (velX < 5)
                velX += .5;
        } else if (Input.isKeyDown(Input.Key.LEFT)) {
        	facingRight = false;
        	isMoving = true;
            if (velX > .2)
                velX /= 2.5;
            else if (velX > -5)
                velX -= .5;
        } else{
        	isMoving = false;
            velX /= 1.5;
        }

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
    
    public void printStats() {
    	System.out.println(currentCharacter.toString() + ": " + characterStats[currentCharacter.index]);
    	System.out.println(currentCharacter.next().toString() + ": " + characterStats[currentCharacter.next().index]);
    	System.out.println(currentCharacter.next().next().toString() + ": " + characterStats[currentCharacter.next().next().index]);
    	System.out.println("Score: " + score);
    }

    @Override
    public void onCollide(Entity e) {

    }
}
