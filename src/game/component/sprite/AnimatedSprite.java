package game.component.sprite;

import mote4.util.matrix.ModelMatrix;
import mote4.util.shader.ShaderMap;
import mote4.util.shader.Uniform;
import mote4.util.texture.Texture;
import mote4.util.vertex.mesh.MeshMap;

public class AnimatedSprite extends Sprite {

    private ModelMatrix model;
    private Texture texture;
    private int tileX, tileY, frames, currentFrame, maxDelay, delay;

    public AnimatedSprite(Texture t, int tileX, int tileY, int frames, int delay) {
        texture = t;
        this.tileX = tileX;
        this.tileY = tileY;
        this.frames = frames;
        this.delay = maxDelay = delay;
        currentFrame = 0;

        model = new ModelMatrix();
    }

    @Override
    public void render() {

        if (delay <= 0) {
            currentFrame++;
            currentFrame %= frames;
            delay = maxDelay;
        } else
            delay--;

        model.push();
        {
            model.translate((int)entity.posX(), (int)entity.posY());
            model.scale((float)entity.width(), (float)entity.height(), 1);
            ShaderMap.use("spritesheet");
            Uniform.vec("spriteInfo", tileX, tileY, currentFrame);
            texture.bind();
            model.bind();
            MeshMap.render("quad");
        }
        model.pop();
    }
}
