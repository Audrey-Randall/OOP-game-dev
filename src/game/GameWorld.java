package game;

import game.component.behavior.*;
import game.component.collider.*;
import game.component.sprite.*;
import main.Input;
import menu.MenuHandler;
import mote4.scenegraph.Scene;
import mote4.util.matrix.ProjectionMatrix;
import mote4.util.shader.ShaderMap;
import mote4.util.texture.TextureMap;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

public class GameWorld implements Scene {

    private ProjectionMatrix projection;
    private List<Entity> entities;
    private MenuHandler menuHandler;

    private boolean gamePaused;

    public GameWorld() {
        projection = new ProjectionMatrix();
        entities = new ArrayList<>();
        menuHandler = new MenuHandler();

        gamePaused = false;

        entities.add(new Entity(
                new Tilemap(TextureMap.get("tileset")),
                new EmptyBehavior(),
                new TilemapCollider(),
                this
        ));
        entities.add(new Entity(
                new StaticSprite(TextureMap.get("entity_rat")),
                new PlayerBehavior(),
                new BoxCollider(),
                this
        ));
        entities.add(new Entity(
                new AnimatedSprite(TextureMap.get("entity_coin"),16,2,21,3),
                new CoinBehavior(),
                new BoxCollider(),
                this
        ));
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

        for (Entity e : entities)
            e.render();
        
        if (gamePaused)
            menuHandler.render();
    }

    @Override
    public void framebufferResized(int w, int h) {
        double aspectRatio = w/(double)h;
        int right = (int)(480*aspectRatio);
        projection.setOrthographic(0,0,right,480,-1,1);

        ShaderMap.use("texture");
        projection.bind();
        ShaderMap.use("spritesheet");
        projection.bind();
    }

    @Override
    public void destroy() {

    }

    public List<Entity> getEntities() {
        return entities;
    }
}
