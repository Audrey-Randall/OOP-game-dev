package game.component.sprite;

import mote4.util.matrix.ModelMatrix;
import mote4.util.shader.ShaderMap;
import mote4.util.texture.Texture;
import mote4.util.vertex.mesh.MeshMap;

public class StaticSprite extends Sprite {

    private ModelMatrix model;
    private Texture texture;

    public StaticSprite(Texture t) {
        texture = t;

        model = new ModelMatrix();
    }

    @Override
    public void render() {
        model.push();
        {
            model.translate((int)entity.posX(), (int)entity.posY());
            model.scale((float)entity.width(), (float)entity.height(), 1);
            ShaderMap.use("texture");
            texture.bind();
            model.bind();
            MeshMap.render("quad");
        }
        model.pop();
    }
}
