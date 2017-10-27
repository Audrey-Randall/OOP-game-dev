package game.component.collider;

import game.Entity;

import java.util.List;

public class TilemapCollider extends Collider {

    @Override
    public void act(List<Entity> colliders) {

    }
    @Override
    public boolean collidesWith(Collider c, double offsetX, double offsetY) {
        return false;
    }
}
