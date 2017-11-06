package game;

import game.component.behavior.*;
import game.component.collider.*;
import game.component.sprite.*;
import main.Tilemap;
import mote4.util.texture.TextureMap;

public class EntityFactory {

    public enum EntityType {
        PLAYER,
        COIN,
        TILEMAP,
        ENEMY;
    }

    private GameWorld world;

    public EntityFactory(GameWorld w) {
        world = w;
    }

    public Entity getEntity(EntityType type) {
        switch (type) {
            case PLAYER:
                Entity e = new Entity(
                    new StaticSprite(TextureMap.get("entity_rat")),
                    new PlayerBehavior(),
                    new BoxCollider(),
                    world);
                world.setPlayer(e);
                return e;
            case COIN:
                return new Entity(
                    new AnimatedSprite(TextureMap.get("entity_coin"),16,2,21,3),
                    new CoinBehavior(),
                    new BoxCollider(),
                    world);
            case TILEMAP:
                Tilemap t = new Tilemap();
                return new Entity(
                    new TilemapSprite(TextureMap.get("tileset"), t),
                    new EmptyBehavior(),
                    new TilemapCollider(t),
                    world);
            case ENEMY:
            	Entity enemy = new Entity(
            			new StaticSprite(TextureMap.get("entity_enemy")),
            			new EnemyBehavior(),
            			new BoxCollider(),
            			world
            			);
            	return enemy;
            default:
                throw new IllegalArgumentException("Invalid entity type!");
        }
    }
}
