package game.component.sprite;

import game.Entity;
import mote4.util.matrix.ModelMatrix;
import mote4.util.shader.ShaderMap;
import mote4.util.texture.Texture;
import mote4.util.vertex.mesh.MeshMap;

public class StaticSprite extends Sprite {

    private ModelMatrix model;
    private Texture texture;
    private int width, height;

    public StaticSprite(Texture t, int w, int h) {
        texture = t;
        width = w;
        height = h;

        model = new ModelMatrix();
    }

    @Override
    public void update(Entity e) {
        model.push();
        {
            model.translate((int)e.posX(), (int)e.posY());
            model.scale(width, height, 1);
            ShaderMap.use("texture");
            texture.bind();
            model.bind();
            MeshMap.render("quad");
        }
        model.pop();
    }
}
