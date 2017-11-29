package game;

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
	
	private static GameWorld instance = null;

    private ProjectionMatrix projection;
    private ViewMatrix view;
    private List<Entity> entities;
    private MenuHandler menuHandler;
    private GameHUD gameHUD;
    private Entity player;
    private ArrayList<Level> l;
    private int maxLength;

    
    public void setPlayer(Entity e) { player = e; }
    public Entity getPlayer() { return player; }

    private boolean gamePaused;

    private GameWorld() {
        projection = new ProjectionMatrix();
        view = new ViewMatrix();
        entities = new ArrayList<>();
        menuHandler = new MenuHandler();
        gameHUD = new GameHUD();

        gamePaused = false;
        
        EntityFactory factory = new EntityFactory(this);
        String[] levelOne = FileIO.getString("/res/files/level1Objects.txt").split("\n");
        l = new ArrayList<Level>();
        l.add(new Level(levelOne, factory));
        loadLevel(0);
    }
    
    public void loadLevel(int lev){
    	if (lev < l.size()){
    		Level level = l.get(lev);
    		entities = level.getEntities();
    		player = level.getPlayer();
    		maxLength = level.getLevelLength();
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
        if (Input.getInstance().isKeyNew(Input.Key.ESC))
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
        //glClear(GL_COLOR_BUFFER_BIT);
        view.translate((int)getScrollPos(), 0);

        ShaderMap.use("spritesheet");
        view.bind();

        for (Entity e : entities)
            e.render();

        view.setIdentity();
        view.bind();
        if (player != null)
        	gameHUD.render();
        if (gamePaused) {
            menuHandler.render();
        }
    }

    @Override
    public void framebufferResized(int width, int height) {
        double aspectRatio = width/(double)height;
        int w = (int)(480*aspectRatio);
        projection.setOrthographic(0,0,w,480,-1,1);

        ShaderMap.use("spritesheet");
        projection.bind();
    }

    @Override
    public void destroy() {

    }

    public List<Entity> getEntities() {
        return entities;
    }

    public float getScrollPos() {
    	if (player == null) return 0;
        float translateTo = (float)-player.posX()+320-(float)player.width()/2;
        if (translateTo > 0) translateTo = 0;
        else if (translateTo < -maxLength+640) translateTo = -maxLength+640;
        return translateTo;
    }
    
}
