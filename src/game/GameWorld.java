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
import java.util.List;

public class GameWorld implements Scene {
	
	private static GameWorld instance = null;

    private ProjectionMatrix projection;
    private ViewMatrix view;
    private List<Entity> entities;
    private MenuHandler menuHandler;
    private GameHUD gameHUD;
    private Entity player;
    private ArrayList<Level> levels;
    private int levelWidth, screenWidth;

    
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

        levels = new ArrayList<>();
        EntityFactory factory = new EntityFactory(this);
        String[] levelNames = FileIO.getString("/res/files/index.txt").split("\n");
        for (String s : levelNames)
            levels.add(new Level(s, factory));

        loadLevel(levels.get(0));
    }
    
    public void loadLevel(Level level) {
        entities = level.getEntities();
        player = level.getPlayer();
        levelWidth = level.getLevelLength();
    }
    public ArrayList<Level> getLevels() { return levels; }

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
        screenWidth = width;
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
        float translateTo = (float)-player.posX()+screenWidth/2-(float)player.width()/2;
        if (translateTo > 0) translateTo = 0;
        else if (translateTo < -levelWidth +screenWidth) translateTo = -levelWidth +screenWidth;
        return translateTo;
    }
    
}
