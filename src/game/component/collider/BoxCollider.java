package game.component.collider;

import game.Entity;

import java.util.List;

public class BoxCollider extends Collider {

    public BoxCollider() {

    }

    @Override
    public void act(List<Entity> colliders) {
        for (Entity e : colliders)
            if (collidesWith(e.getCollider(), 0,0)) {
                entity.getBehavior().onCollide(e);
            }
    }

    @Override
    public boolean collidesWith(Collider c, double offsetX, double offsetY) {
        if (c == this)
            return false;
        if (c instanceof BoxCollider) {
            BoxCollider b = (BoxCollider)c;

            if ((entity.posX()+offsetX < b.entity.posX()+b.entity.width() &&
                 entity.posX()+offsetX+entity.width() > b.entity.posX()) &&
                (entity.posY()+offsetY < b.entity.posY()+b.entity.height() &&
                 entity.posY()+offsetY+entity.height() > b.entity.posY()))
            {
                return true;
            }
        } else if (c instanceof TilemapCollider) {
            return (entity.posY()+offsetY+entity.height() > 480-32);
        }
        return false;
    }
}
