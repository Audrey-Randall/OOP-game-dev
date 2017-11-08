package game;

import game.component.behavior.*;
import game.component.collider.*;
import game.component.sprite.*;
import javafx.util.Pair;
import main.Input;
import main.Tilemap;
import menu.MenuHandler;
import mote4.scenegraph.Scene;
import mote4.util.matrix.ProjectionMatrix;
import mote4.util.matrix.ViewMatrix;
import mote4.util.shader.ShaderMap;
import mote4.util.texture.TextureMap;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

public class GameWorld implements Scene {
	private static int NUMBER_OF_ENEMIES = 3;
	private static int NUMBER_OF_FOODS = 2;
	
	private static GameWorld instance = null;

    private ProjectionMatrix projection;
    private ViewMatrix view;
    private List<Entity> entities;
    private MenuHandler menuHandler;
    private Entity player;

    
    private double[] EnemyPosition = new double[NUMBER_OF_ENEMIES * 2];
    private double[] FoodPosition = new double[NUMBER_OF_FOODS * 2];
    private FoodBehavior.foodType[] FoodType = new FoodBehavior.foodType[NUMBER_OF_FOODS];
    
    public void setPlayer(Entity e) { player = e; }
    public Entity getPlayer() { return player; }

    private boolean gamePaused;

    private GameWorld() {
    	EnemyPosition[0] = 20.;
    	EnemyPosition[1] = 30.;
    	EnemyPosition[2] = 300.;
    	EnemyPosition[3] = 70.;
    	EnemyPosition[4] = 400.;
    	EnemyPosition[5] = 100.;
    	FoodPosition[0] = 50.;
    	FoodPosition[1] = 150.;
    	FoodPosition[2] = 500.;
    	FoodPosition[3] = 100.;
    	FoodType[0] = FoodBehavior.foodType.TRASH;
    	FoodType[1] = FoodBehavior.foodType.CHEESE;
        projection = new ProjectionMatrix();
        view = new ViewMatrix();
        entities = new ArrayList<>();
        menuHandler = new MenuHandler();

        gamePaused = false;

        EntityFactory factory = new EntityFactory(this);
        entities.add(factory.getEntity(EntityFactory.EntityType.TILEMAP));
        entities.add(factory.getEntity(EntityFactory.EntityType.COIN));
        entities.add(factory.getEntity(EntityFactory.EntityType.PLAYER));
        entities.add(factory.getEntity(EntityFactory.EntityType.ENEMY));
        entities.add(factory.getEntity(EntityFactory.EntityType.ENEMY));
        entities.add(factory.getEntity(EntityFactory.EntityType.ENEMY));
        entities.add(factory.getEntity(EntityFactory.EntityType.HAT));
        entities.add(factory.getEntity(EntityFactory.EntityType.FOOD));
        entities.add(factory.getEntity(EntityFactory.EntityType.FOOD));
        placeEnemies();
        placeFood();
    }

    public static GameWorld getInstance() {
        if(instance == null) {
           instance = new GameWorld();
        }
        return instance;
     }
    
    @Override
    public void update(double time, double delta) {
        if (Input.isKeyNew(Input.Key.ESC))
            gamePaused = !gamePaused;

        if (gamePaused) {
            menuHandler.update();
        } else {
            for (Entity e : entities)
                e.getCollider().act(entities);

            for (Entity e : entities)
                e.update();
        }
    }

    @Override
    public void render(double time, double delta) {
        glClear(GL_COLOR_BUFFER_BIT);
        float translateTo = (float)-player.posX()+320-(float)player.width()/2;
        if (translateTo > 0) translateTo = 0;
        // else if (translateTo < ???) translateTo = ???;
        view.translate(translateTo, 0);

        ShaderMap.use("spritesheet");
        view.bind();

        for (Entity e : entities)
            e.render();

        view.setIdentity();
        if (gamePaused) {

            ShaderMap.use("spritesheet");
            view.bind();

            menuHandler.render();
        }
    }

    @Override
    public void framebufferResized(int w, int h) {
        double aspectRatio = w/(double)h;
        int right = (int)(480*aspectRatio);
        projection.setOrthographic(0,0,right,480,-1,1);

        ShaderMap.use("spritesheet");
        projection.bind();
    }

    @Override
    public void destroy() {

    }

    public List<Entity> getEntities() {
        return entities;
    }
    
    public void placeEnemies() {
    	int i = 0;
    	for (Entity e : entities) {
    		if(e.getBehavior() instanceof EnemyBehavior) {
    			e.moveTo(EnemyPosition[i], EnemyPosition[i+1]);
    			i+=2;
    		}
    	}
    }
    
    public void placeFood() {
    	int i = 0;
    	for (Entity e : entities) {
    		if(e.getBehavior() instanceof FoodBehavior) {
    			e.moveTo(FoodPosition[i], FoodPosition[i+1]);
    			FoodBehavior foodBehavior = (FoodBehavior)e.getBehavior();
    			foodBehavior.setFoodType(FoodType[i/2]);
    			i+=2;
    		}
    	}
    }
}
