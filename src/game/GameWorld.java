package game;

import game.component.behavior.EnemyBehavior;
import game.component.behavior.FoodBehavior;
import main.Input;
import menu.GameHUD;
import menu.MenuHandler;
import mote4.scenegraph.Scene;
import mote4.util.FileIO;
import mote4.util.matrix.ProjectionMatrix;
import mote4.util.matrix.ViewMatrix;
import mote4.util.shader.ShaderMap;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.print.attribute.standard.PrinterLocation;

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
    private GameHUD gameHUD;
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
    	EnemyPosition[5] = 300.;
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
        gameHUD = new GameHUD();

        gamePaused = false;
        
        EntityFactory factory = new EntityFactory(this);
        entities.add(factory.getEntity(EntityFactory.EntityType.TILEMAP));
        String file = FileIO.getString("/res/files/level1Objects.txt");
        createGameWorld(file.split("\n"), factory);
        /*
        entities.add(factory.getEntity(EntityFactory.EntityType.COIN));
        entities.add(factory.getEntity(EntityFactory.EntityType.PLAYER));
        entities.add(factory.getEntity(EntityFactory.EntityType.ENEMY));
        entities.add(factory.getEntity(EntityFactory.EntityType.ENEMY));
        entities.add(factory.getEntity(EntityFactory.EntityType.ENEMY));
        // entities.add(factory.getEntity(EntityFactory.EntityType.HAT));
        entities.add(factory.getEntity(EntityFactory.EntityType.FOOD));
        entities.add(factory.getEntity(EntityFactory.EntityType.FOOD));
        placeEnemies();
        placeFood();
        */
    }
    
    private void createGameWorld(String[] mapAndLegend, EntityFactory factory){
    	boolean getFullLegend = true;
    	Hashtable<String, EntityFactory.EntityType> legend = 
    			new Hashtable<String, EntityFactory.EntityType>(); // Definitely OVERKILL.
    	int yOffset = 0;
    	
    	for (int i = 0; i < mapAndLegend.length; i++){
    		if (getFullLegend){
    			if (mapAndLegend[i].length() > 2 && mapAndLegend[i].charAt(1) == ' '){
    				String key = mapAndLegend[i].substring(0, 1);
    				String rest = mapAndLegend[i].substring(2);
    				if (rest.indexOf(" ") == -1)
						legend.put(key, EntityFactory.EntityType.valueOf(rest));
    				else{
    					legend.put(key, EntityFactory.EntityType.valueOf(rest.substring(0, rest.indexOf(" "))));
    				}
    			} else{
    				yOffset = i+1;
    				getFullLegend = false;
    			}
    		} else {
    			for (int j = 0; j < mapAndLegend[i].length(); j++){
    				EntityFactory.EntityType type = legend.get(mapAndLegend[i].substring(j,j+1));
    				if (type != null){
    					Entity e = factory.getEntity(type);
    					// Hard coded for now; Please fix.
    					e.moveTo(j*32, (i-yOffset)*20);
    					entities.add(e);
    					
    				}
    			}
    		}
    	}
    	
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

            for (int i = 0; i < entities.size(); i++) {
                if (!entities.get(i).isAlive()) {
                    entities.remove(i);
                    i--;
                }
            }
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
        view.bind();
        gameHUD.render();
        if (gamePaused) {
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
