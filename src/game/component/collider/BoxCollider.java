package game.component.collider;

import game.Entity;

import java.util.List;

public class BoxCollider extends Collider {

    public BoxCollider() {
    }
    
    public BoxCollider(boolean sol, boolean brea){
    	this();
    	solid = sol;
    	breakable = brea;
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
            //Slightly better! (Chance)
            int posX = (int)(entity.posX()/t.tilemap.TILE_SIZE);
            int posY = (int)((entity.posY()-1)/t.tilemap.TILE_SIZE);
            int posX2 = (int)(((entity.posX()+entity.width()-1))/t.tilemap.TILE_SIZE);
            int posY2 = (int)((entity.posY()+entity.height())/t.tilemap.TILE_SIZE);
            if (posX < 0 || posY < 0 || posX2 >= t.tilemap.TILES.length)
            	return true;
            for (int i = posX; i <= posX2 && i < t.tilemap.TILES.length; i++){
            	for (int j = posY; j <= posY2 && j < t.tilemap.TILES[0].length; j++){
            		if (t.tilemap.TILES[i][j] == 1 || t.tilemap.TILES[i][j] >= 19)
            		    return true;
            	}
            }
            return false;
        }
        else
            return false;
    }
}
