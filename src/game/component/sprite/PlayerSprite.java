package game.component.sprite;

import game.Entity;
import game.component.behavior.PlayerBehavior;
import mote4.util.texture.TextureMap;

public class PlayerSprite extends Sprite {

    private final AnimatedSprite[] SPRITES;
    private int index;

    public PlayerSprite() {
        SPRITES = new AnimatedSprite[] {
        		new AnimatedSprite(TextureMap.get("entity_rat"),  2,1,2,15),
                new AnimatedSprite(TextureMap.get("entity_possum"),  2,1,2,15),
                new AnimatedSprite(TextureMap.get("entity_raccoon"),  2,1,2,15)
        };
        index = 0;
    }

    @Override
    public void setEntity(Entity e) {
        for (AnimatedSprite s : SPRITES)
            s.setEntity(e);
        entity = e;
    }

    @Override
    public void render() {
        SPRITES[index].render();
        if (entity.getBehavior().getClass().getName().equals("game.component.behavior.PlayerBehavior")){
        	PlayerBehavior pb = (PlayerBehavior)(entity.getBehavior());
        	pb.getHat().getSprite().render();
        }
    }

    public void setSprite(PlayerBehavior.character c) {
        index = c.getIndex();
    }
}
