package game;

import java.util.ArrayList;
import java.util.List;

import game.component.behavior.Behavior;
import game.component.collider.Collider;
import game.component.sprite.*;

public class Entity {

    private List<Sprite> sprite = new ArrayList<Sprite>();
    private int numberOfSprites = 0;
    private int currentSprite = 0;
    private Behavior behavior;
    private Collider collider;
    private GameWorld gameWorld;

    private double posX, posY, width, height;

    public Entity(List<Sprite> s, Behavior b, Collider c, GameWorld g) {
        sprite = s;
        numberOfSprites = s.size();
        System.out.println("Number of Sprites: " + numberOfSprites);
        behavior = b;
        collider = c;
        gameWorld = g;

        posX = posY = 0;
        width = height = 64;

        sprite.get(0).setEntity(this);
        behavior.setEntity(this);
        collider.setEntity(this);
    }

    public void update() {
        behavior.act();
    }

    public void render() {
        sprite.get(currentSprite).render();
    }

    public void moveTo(double x, double y) {
        posX = x;
        posY = y;
    }
    public void move(double x, double y) {
        posX += x;
        posY += y;
    }

    public Sprite getSprite() {
    	System.out.println("getSprite");
        return sprite.get(currentSprite);
    }
    
    public void swapSprite() {
    	System.out.println("swapSprite");
    	currentSprite = (currentSprite + 1);
    	currentSprite = currentSprite % numberOfSprites;
    	System.out.println("Current Sprite: " + currentSprite);
    }
    
    public Behavior getBehavior() {
        return behavior;
    }
    public Collider getCollider() {
        return collider;
    }
    public GameWorld getGameWorld() {
        return gameWorld;
    }

    public double posX() { return posX; }
    public double posY() { return posY; }
    public double width() { return width; }
    public double height() { return height; }
}
