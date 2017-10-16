package game;

import game.component.behavior.*;
import game.component.collider.*;
import game.component.sprite.*;
import mote4.scenegraph.Scene;
import mote4.util.matrix.ProjectionMatrix;
import mote4.util.texture.TextureMap;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

public class GameWorld implements Scene {

    private ProjectionMatrix projection;

    private List<Entity> entities;

    public GameWorld() {
        entities = new ArrayList<>();
        projection = new ProjectionMatrix();

        entities.add(new Entity(
                new StaticSprite(TextureMap.get("entity_rat"), 64, 64),
                new TestBehavior(),
                new EmptyCollider()
        ));
    }

    @Override
    public void update(double time, double delta) {
        for (Entity e : entities)
            e.update();
    }

    @Override
    public void render(double time, double delta) {
        glClear(GL_COLOR_BUFFER_BIT);

        projection.bind();
        for (Entity e : entities)
            e.render();
    }

    @Override
    public void framebufferResized(int w, int h) {
        double aspectRatio = w/(double)h;
        int right = (int)(480*aspectRatio);
        projection.setOrthographic(0,0,right,480,-1,1);
    }

    @Override
    public void destroy() {

    }
}
