package game;

import java.util.ArrayList;
import java.util.List;

import game.component.behavior.*;
import game.component.behavior.FoodBehavior.foodType;
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
        COSMETIC, 
        FOOD,
        BOX;
    }

    private GameWorld world;

    public EntityFactory(GameWorld w) {
        world = w;
    }
    
    public Entity getEntity(EntityType type) {
    	return getEntity(type, "");
    }

    public Entity getEntity(EntityType type, String arg) {
    	
        switch (type) {
            case PLAYER:
                Entity e = new Entity(
                	new PlayerSprite(),
                    //new AnimatedSprite(TextureMap.get("entity_rat"),  2,1,2,15),
                    new PlayerBehavior(),
                    new BoxCollider(),
                    world
                );
                //world.setPlayer(e);
                return e;
            case COIN:
                return new Entity(
					new AnimatedSprite(TextureMap.get("entity_coin"),16,2,21,3),
                    new CoinBehavior(),
                    new BoxCollider(),
                    world
                );
            case TILEMAP:
                Tilemap t = new Tilemap(arg);
                return new Entity(
					 new TilemapSprite(TextureMap.get("tileset"), t),
                    new EmptyBehavior(),
                    new TilemapCollider(t),
                    world
				);
            case ENEMY:
            	Entity enemy = new Entity(
					new AnimatedSprite(TextureMap.get("entity_enemy"), 2, 1, 2, 10),
					new EnemyBehavior(),
					new BoxCollider(),
					world
				);
            	return enemy;
            case HAT:
            	Entity hat = new Entity(
					new StaticSprite(TextureMap.get("entity_santahat"),1,1,0),
					new HatBehavior(),
					new EmptyCollider(),
					world
				);
            	return hat;
            case FOOD:
            	FoodBehavior fBehavior = new FoodBehavior();
            	Entity food = new Entity(
					new StaticSprite(TextureMap.get("entity_food"),2,1,0),
					fBehavior,
					new BoxCollider(),
					world
				);
            	try {
            		fBehavior.setFoodType(FoodBehavior.foodType.valueOf(arg));
            	} catch (Exception ex) {
            		System.out.println(arg + " is not a valid foodType!");
            	}
            	
            	return food;
            case COSMETIC:
            	String sprite = CosmeticBehavior.chooseSpriteFile();
            	return new Entity(
					new StaticSprite(TextureMap.get(sprite),2,1,0),
					new CosmeticBehavior(sprite),
					new BoxCollider(),
					world
				);
            case BOX:
            	Entity en = new Entity(
            			new StaticSprite(TextureMap.get("tileset"),8,8,3),
            			new EmptyBehavior(),
            			new BoxCollider(true, true),
            			world
            			);
            	return en;
            	
            default:
                throw new IllegalArgumentException("Invalid entity type!");
        }
    }
}
