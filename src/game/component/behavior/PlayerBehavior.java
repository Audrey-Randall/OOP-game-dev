package game.component.behavior;

import game.Entity;
import game.component.collider.TilemapCollider;
import main.Input;

public class PlayerBehavior extends Behavior {

    private double velX, velY;

    public PlayerBehavior() {

    }

    @Override
    public void act() {

        if (Input.isKeyDown(Input.Key.RIGHT)) {
            if (velX < -.1)
                velX /= 3;
            else if (velX < 4)
                velX++;
        } else if (Input.isKeyDown(Input.Key.LEFT)) {
            if (velX > .1)
                velX /= 3;
            else if (velX > -4)
                velX--;
        } else
            velX /= 2;

        // check collision here
        // with velocity offset
        for (Entity e : entity.getGameWorld().getEntities())
            if (e.getCollider() instanceof TilemapCollider)
            if (entity.getCollider().collidesWith(e.getCollider(), velX, 0))
                velX = 0;

        velY += .6; // gravity
        if (Input.isKeyNew(Input.Key.UP))
            velY = -15;

        // check collision here
        // with velocity offset
        for (Entity e : entity.getGameWorld().getEntities())
            if (e.getCollider() instanceof TilemapCollider)
            if (entity.getCollider().collidesWith(e.getCollider(), velX, velY))
                velY = 0;

        entity.move(velX, velY);
    }

    @Override
    public void onCollide(Entity e) {

    }
}
