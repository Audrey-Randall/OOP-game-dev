package game.component.behavior;

import game.Entity;
import game.EntityFactory;
import game.EntityFactory.EntityType;
import game.GameWorld;
import game.component.collider.TilemapCollider;
import game.component.sprite.PlayerSprite;
import main.Input;
import mote4.scenegraph.Window;

public class PlayerBehavior extends Behavior {
	double printCounter = 0;
    private double velX, velY;
    private boolean facingRight = true; 
    private boolean isMoving = false; 
    private boolean isFalling = false; 
    private int jumpsLeft = 0;
    private Entity hat;
    double GRAVITY = .65;
    float RACCOON_SPEED = (float)1.1;
    float RAT_SPEED = (float)1.5;
    float OPOSSUM_SPEED = (float).8;
    float JUMP_HEIGHT = 16;
    private boolean[] isDead = new boolean[3];
    
    public boolean getIsCharacterDead(int characterIndex) {
    	return isDead[characterIndex];
    }
    
    public void setIsCharacterDead(int characterIndex, boolean dead) {
    	isDead[characterIndex] = dead;
    }
       
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
	
	public class CharacterInfo {
		float health = INITIAL_HEALTH;
		double timer = 10;
		float speed;
		PlayerBehavior.character character;
		
		public CharacterInfo(PlayerBehavior.character characterType) {
			character = characterType;
		}
		
		
		private void increaseHealth(float update) {
			health = Math.min(health + update, MAX_HEALTH);
			if (health <= 0) {
				setHealth(0);
				setTimer(0);
				setIsCharacterDead(character.index, true);
			}
		}
		
		private void decreaseHealth(float update) {
			if (!getIsCharacterDead(character.index))
				health -= update;
			if (health <= 0) {
				setHealth(0);
				setTimer(0);
				setIsCharacterDead(character.index, true);
			}
		}
		
		private void setHealth(float update) {
			health = update;
			if (health <= 0) {
				health = 0;
				setTimer(0);
				setIsCharacterDead(character.index, true);
			}
		}
		
		private float getHealth() {
			return health;
		}
		
		private void updateTimer(double update) { //Considered "alive" if timer is greater than 10
			timer += update;
			if (timer >= 10)
				setIsCharacterDead(character.index, false);
		}
		
		private void setTimer(double update) {
			timer = update;
			if (timer >= 10)
				setIsCharacterDead(character.index, false);
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

	private CharacterInfo[] characterStats = new CharacterInfo[3];
	
	private float INITIAL_HEALTH = 4;
	private float MAX_HEALTH = 5;
	private float HEALTH_SCALE_FACTOR = 100;

    public PlayerBehavior() {
    	CharacterInfo raccoon = new CharacterInfo(character.RACCOON);
    	CharacterInfo opossum = new CharacterInfo(character.OPOSSUM);
    	CharacterInfo rat = new CharacterInfo(character.RAT);

    	raccoon.setSpeed(RACCOON_SPEED);
    	opossum.setSpeed(OPOSSUM_SPEED);
    	rat.setSpeed(RAT_SPEED);
    	
    	hat = null;
    	
    	characterStats[character.RACCOON.index] = raccoon;
    	characterStats[character.OPOSSUM.index] = opossum;
    	characterStats[character.RAT.index] = rat;
    	
    	RaccoonBehavior raccoonB = new RaccoonBehavior();
    	PossumBehavior possumB = new PossumBehavior();    	
    	RatBehavior ratB = new RatBehavior();
    	
        behaviorList[character.RACCOON.index] = () -> raccoonB.claw();
        behaviorList[character.OPOSSUM.index] = () -> possumB.playDead();
        behaviorList[character.RAT.index] = () -> ratB.scurry();
        
        setIsCharacterDead(character.RACCOON.index, false);
        setIsCharacterDead(character.RAT.index, false);
        setIsCharacterDead(character.OPOSSUM.index, false);

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
    }
    
    public void adjustScore(int scoreIncrease) {
    	score += scoreIncrease;
    }
    

    public void tickHealth(character characterName, float healthDecrease) {
    	characterStats[characterName.index].decreaseHealth(healthDecrease);
    }
    
    public void boostHealth(character characterName, float healthIncrease) {
    	characterStats[characterName.index].increaseHealth(healthIncrease);
    }
    
    public character getCurrentCharacter( ) {
    	return currentCharacter;
    }
    
    @Override
    public void act() {
    	if (hat == null){
    		hat = new EntityFactory(entity.getGameWorld()).getEntity(EntityType.HAT);
    	}
    	if(isDead[0] == true && isDead[1] ==  true && isDead[2] == true)
    		Window.destroy();
    	
    	printCounter += Window.delta();
    	
    	//Boosts health of unused characters and wears out character in use
    	tickHealth(currentCharacter, (float)Window.delta() / HEALTH_SCALE_FACTOR);
    	boostHealth(currentCharacter.next(), (float)Window.delta() / HEALTH_SCALE_FACTOR);
    	boostHealth(currentCharacter.next().next(), (float)Window.delta() / HEALTH_SCALE_FACTOR);
    	
    	characterStats[0].updateTimer(Window.delta());
    	characterStats[1].updateTimer(Window.delta());
    	characterStats[2].updateTimer(Window.delta());
    	
    	if (printCounter > 3) {
    		printStats();
	    	printCounter = 0;
    	}
    	
    	 if (Input.getInstance().isKeyNew(Input.Key.DOWN)) {
    		 switchCharacter();
    	 }
    	
    	 if (Input.getInstance().isKeyNew(Input.Key.BACKSPACE)) {
    		 if (!getIsCharacterDead(currentCharacter.index))
    			 performSpecialBehavior();
    		 else {
         		System.out.print(currentCharacter.toString());
         		System.out.printf(" dead. WaitTime: %.2f%n", 10 - (float)(characterStats[currentCharacter.index].getTimer()) );
         	}
    	 }
    	 
        if (Input.getInstance().isKeyDown(Input.Key.RIGHT)) {
        	 if (!getIsCharacterDead(currentCharacter.index)) {
        		facingRight = true; 
        		isMoving = true; 
	            if (velX < -.2)
	                velX = (velX / 2.5) * characterStats[currentCharacter.index].getSpeed(); // pivot directions fast
	            else if (velX < 5)
	                velX = (velX + .5) * characterStats[currentCharacter.index].getSpeed();
        	}
        	else {
        		System.out.print(currentCharacter.toString());
        		System.out.printf(" dead. WaitTime: %.2f%n", 10 - (float)(characterStats[currentCharacter.index].getTimer()) );
        	}
        } 
        else if (Input.getInstance().isKeyDown(Input.Key.LEFT)) {
        	 if (!getIsCharacterDead(currentCharacter.index)) {
        		facingRight = false; 
        		isMoving = true; 
	            if (velX > .2)
	                velX = (velX / 2.5) * characterStats[currentCharacter.index].getSpeed();
	            else if (velX > -5)
	                velX = (velX - .5) * characterStats[currentCharacter.index].getSpeed();
        	}
        	else {
        		System.out.print(currentCharacter.toString());
        		System.out.printf(" dead. WaitTime: %.2f%n", 10 - (float)(characterStats[currentCharacter.index].getTimer()) );
        	}
        } 
        else {
        	isMoving = false;
            velX = (velX / 1.5);
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

        velY += GRAVITY; // gravity
        if (jumpsLeft > 0 && Input.getInstance().isKeyNew(Input.Key.UP)) {
        	 if (!getIsCharacterDead(currentCharacter.index)) {
	            velY = -JUMP_HEIGHT;
	            jumpsLeft--;
        	}
        	else {
        		System.out.print(currentCharacter.toString());
        		System.out.printf(" dead. WaitTime: %.2f%n", 10 - (float)(characterStats[currentCharacter.index].getTimer()) );
        	}
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
        hat.getBehavior().act();
    }
    
    public void printStats() {
    	System.out.printf(currentCharacter.toString() + ": %.2f%n", characterStats[currentCharacter.index].getHealth());
    	System.out.printf(currentCharacter.next().toString() + ": %.2f%n", characterStats[currentCharacter.next().index].getHealth());
    	System.out.printf(currentCharacter.next().next().toString() + ": %.2f%n", characterStats[currentCharacter.next().next().index].getHealth());
    	System.out.println("Score: " + score);
    }
    public double[] getPlayerInfo() {
    	return new double[] {
			characterStats[character.RAT.index].getHealth(),
			characterStats[character.RACCOON.index].getHealth(),
			characterStats[character.OPOSSUM.index].getHealth(),
			score
		};
	}

    @Override
    public void onCollide(Entity e) {
    	if (e.getBehavior().getClass().getSimpleName().equals("CosmeticBehavior")){
    		
    	}
    }
    
    public Entity getHat(){
    	return hat;
    }
}
