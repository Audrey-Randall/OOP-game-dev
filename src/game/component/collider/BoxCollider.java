package game.component.collider;

import game.Entity;

import java.util.List;

public class BoxCollider extends Collider {

    public BoxCollider() {

    }

    @Override
    public void act(List<Entity> colliders) {
        for (Entity e : colliders)
            if (collidesWith(e.getCollider())) {
                entity.getBehavior().onCollide(e);
            }
    }

    @Override
    public boolean collidesWith(Collider c) {
        if (c == this)
            return false;
        if (c instanceof BoxCollider)
        {
            BoxCollider b = (BoxCollider)c;
            // standard aabb collision
            return (entity.posX()                 < b.entity.posX()+b.entity.width() &&
                    entity.posX()+entity.width()  > b.entity.posX() &&
                    entity.posY()                 < b.entity.posY()+b.entity.height() &&
                    entity.posY()+entity.height() > b.entity.posY());
        }
        else if (c instanceof TilemapCollider)
        {
            TilemapCollider t = (TilemapCollider) c;
            // TODO this only checks the four corners of the collider for collisions - this is not good!
            int posX = (int)(entity.posX()/t.tilemap.TILE_SIZE);
            int posY = (int)(entity.posY()/t.tilemap.TILE_SIZE);
            if ((int)entity.posX() < 0 || entity.posY() < 0 ||
                (int)entity.posX()+entity.width() >= t.tilemap.tiles.length*t.tilemap.TILE_SIZE ||
                entity.posY()+entity.height() >= t.tilemap.tiles[0].length*t.tilemap.TILE_SIZE)
                return true;
            if (t.tilemap.tiles[posX][posY] == 1)
                return true;
            int posX2 = (int)((entity.posX()+entity.width())/t.tilemap.TILE_SIZE);
            //posX2 = Math.min(t.tilemap.tiles.length, posX2);
            if (t.tilemap.tiles[posX2][posY] == 1)
                return true;
            int posY2 = (int)((entity.posY()+entity.height())/t.tilemap.TILE_SIZE);
            //posY2 = Math.min(t.tilemap.tiles[0].length, posY2);
            if (posY2 >= t.tilemap.tiles[0].length)
                return true;
            if (t.tilemap.tiles[posX][posY2] == 1)
                return true;
            if (t.tilemap.tiles[posX2][posY2] == 1)
                return true;
            return false;
        }
        else
            return false;
    }
}
