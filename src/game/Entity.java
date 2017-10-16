package game;

import game.component.behavior.Behavior;
import game.component.collider.Collider;
import game.component.sprite.Sprite;

public class Entity {

    private Sprite sprite;
    private Behavior behavior;
    private Collider collider;

    private double posX, posY;

    public Entity(Sprite s, Behavior b, Collider c) {
        sprite = s;
        behavior = b;
        collider = c;

        posX = posY = 0;
    }

    public void update() {
        collider.update(this);
        behavior.update(this);
    }

    public void render() {
        sprite.update(this);
    }

    public double posX() { return posX; }
    public double posY() { return posY; }
    public void move(double x, double y) {
        posX += x;
        posY += y;
    }
}
