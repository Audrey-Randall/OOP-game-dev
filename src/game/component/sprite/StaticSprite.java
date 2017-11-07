package game.component.sprite;

import mote4.util.matrix.ModelMatrix;
import mote4.util.shader.ShaderMap;
import mote4.util.shader.Uniform;
import mote4.util.texture.Texture;
import mote4.util.vertex.mesh.MeshMap;

public class StaticSprite extends Sprite {

    private ModelMatrix model;
    private Texture texture;
    private int tileX, tileY, frame;

    public StaticSprite(Texture t, int tileX, int tileY, int frame) {
        texture = t;
        this.tileX = tileX;
        this.tileY = tileY;
        this.frame = frame;

        model = new ModelMatrix();
    }

    @Override
    public void render() {
        model.push();
        {
            model.translate((int)entity.posX(), (int)entity.posY());
            model.scale((float)entity.width(), (float)entity.height(), 1);
            ShaderMap.use("spritesheet");
            Uniform.vec("spriteInfo", tileX, tileY, frame);
            texture.bind();
            model.bind();
            MeshMap.render("quad");
        }
        model.pop();
    }
}
