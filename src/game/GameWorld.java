package game;

import game.component.behavior.*;
import game.component.collider.*;
import game.component.sprite.*;
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

    private ProjectionMatrix projection;
    private ViewMatrix view;
    private List<Entity> entities;
    private MenuHandler menuHandler;
    private Entity player;

    private boolean gamePaused;

    public GameWorld() {
        projection = new ProjectionMatrix();
        view = new ViewMatrix();
        entities = new ArrayList<>();
        menuHandler = new MenuHandler();

        gamePaused = false;

        EntityFactory factory = new EntityFactory(this);
        entities.add(factory.getEntity(EntityFactory.EntityType.TILEMAP));
        entities.add(factory.getEntity(EntityFactory.EntityType.COIN));
        entities.add(factory.getEntity(EntityFactory.EntityType.PLAYER));
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

        view.translate((int)-player.posX()+320-(float)player.width()/2, 0);

        ShaderMap.use("texture");
        view.bind();
        ShaderMap.use("spritesheet");
        view.bind();

        for (Entity e : entities)
            e.render();

        view.setIdentity();
        if (gamePaused) {

            ShaderMap.use("texture");
            view.bind();
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
    public void setPlayer(Entity e) { player = e; }
}
