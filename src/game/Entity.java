package game;

import java.util.ArrayList;
import java.util.List;

import game.component.behavior.Behavior;
import game.component.collider.Collider;
import game.component.sprite.*;

public class Entity {

    private Sprite sprite;
    private Behavior behavior;
    private Collider collider;
    private GameWorld gameWorld;

    private double posX, posY, width, height;

    public Entity(Sprite s, Behavior b, Collider c, GameWorld g) {
        sprite = s;
        behavior = b;
        collider = c;
        gameWorld = g;

        posX = posY = 0;
        width = height = 64;

        sprite.setEntity(this);
        behavior.setEntity(this);
        collider.setEntity(this);
    }

    public void update() {
        behavior.act();
    }

    public void render() {
        sprite.render();
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
        return sprite;
    }
    
    public void swapSprite(Sprite s) {
    	sprite = s;
        sprite.setEntity(this);
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
