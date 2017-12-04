package game.component.collider;

import game.Entity;
import main.Tilemap;

import java.util.List;

public class TilemapCollider extends Collider {

    public final Tilemap tilemap;

    public TilemapCollider(Tilemap t) {
        tilemap = t;
        solid = true;
    }

    @Override
    public void act(List<Entity> colliders) {

    }
    @Override
    public boolean collidesWith(Collider c) {
        return false;
    }
}
