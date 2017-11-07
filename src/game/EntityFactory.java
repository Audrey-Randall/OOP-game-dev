package game;

import java.util.ArrayList;
import java.util.List;

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
        ENEMY,
        HAT,
        COSMETIC;
    }

    private GameWorld world;

    public EntityFactory(GameWorld w) {
        world = w;
    }

    public Entity getEntity(EntityType type) {
    	
        switch (type) {
            case PLAYER:
            	List<Sprite> characterSprites = new ArrayList<Sprite>();
            	AnimatedSprite s1 = new AnimatedSprite(TextureMap.get("entity_rat"),  2,1,2,15);
            	AnimatedSprite s2 = new AnimatedSprite(TextureMap.get("entity_possum"),  2,1,2,15);
            	characterSprites.add(s1);
            	characterSprites.add(s2);
                Entity e = new Entity(characterSprites,
                    new PlayerBehavior(),
                    new BoxCollider(),
                    world);
                world.setPlayer(e);
                return e;
            case COIN:
            	List<Sprite> coinSprite = new ArrayList<Sprite>();
            	AnimatedSprite c = new AnimatedSprite(TextureMap.get("entity_coin"),16,2,21,3);
            	coinSprite.add(c);
                return new Entity(
                    coinSprite,
                    new CoinBehavior(),
                    new BoxCollider(),
                    world);
            case TILEMAP:
                Tilemap t = new Tilemap();
                List<Sprite> tileSprite = new ArrayList<Sprite>();
                TilemapSprite tile = new TilemapSprite(TextureMap.get("tileset"), t);
                tileSprite.add(tile);
                return new Entity(
                    tileSprite,
                    new EmptyBehavior(),
                    new TilemapCollider(t),
                    world);
            case ENEMY:
            	List<Sprite> enemySprite = new ArrayList<Sprite>();
            	AnimatedSprite en = new AnimatedSprite(TextureMap.get("entity_enemy"), 2, 1, 2, 10);
            	enemySprite.add(en);
            	Entity enemy = new Entity(
            			enemySprite,
            			new EnemyBehavior(),
            			new BoxCollider(),
            			world
            			);
            	return enemy;
            case HAT:
            	List<Sprite> hatSprites = new ArrayList<Sprite>();
            	StaticSprite h = new StaticSprite(TextureMap.get("entity_hat"));
            	hatSprites.add(h);
            	Entity hat = new Entity(
            			hatSprites,
            			new HatBehavior(),
            			new EmptyCollider(),
            			world
            			);
            	return hat;
            default:
                throw new IllegalArgumentException("Invalid entity type!");
        }
    }
}
