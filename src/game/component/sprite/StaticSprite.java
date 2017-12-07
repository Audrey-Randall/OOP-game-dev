package game.component.sprite;

import game.Entity;
import game.component.behavior.PlayerBehavior;
import mote4.util.matrix.ModelMatrix;
import mote4.util.shader.ShaderMap;
import mote4.util.shader.Uniform;
import mote4.util.texture.Texture;
import mote4.util.vertex.mesh.MeshMap;

public class StaticSprite extends Sprite {

    private ModelMatrix model;
    private Texture texture;
    private int tileX, tileY, frame;
    private float offsetX, offsetY, scaleX, scaleY;

    public StaticSprite(Texture t, int tileX, int tileY, int frame) {
        texture = t;
        this.tileX = tileX;
        this.tileY = tileY;
        this.frame = frame;
        this.offsetX = 0;
        this.offsetY = 0;
        this.scaleX = 1;
        this.scaleY = 1;

        model = new ModelMatrix();
    }

    @Override
    public void render() {
        model.push();
        {
        	float trueOffsetX = offsetX;
        	float trueOffsetY = offsetY;
        	if (scaleX < 0) trueOffsetX += entity.width();
        	if (scaleY < 0) trueOffsetY += entity.height();
            model.translate((int)(entity.posX()+trueOffsetX), (int)(entity.posY()+trueOffsetY));
            model.scale((float)entity.width()*scaleX, (float)entity.height()*scaleY, 1);
            ShaderMap.use("spritesheet");
            Uniform.vec("spriteInfo", tileX, tileY, frame);
            texture.bind();
            model.bind();
            MeshMap.render("quad");
        }
        model.pop();
    }

    
    public void setSprite(int newIndex) {
        frame = newIndex;
    }
    
    public void setOffset(float X, float Y){
    	offsetX = X;
    	offsetY = Y;
    }
    
    public void setScale(float sX, float sY){
    	scaleX = sX;
    	scaleY = sY;
    }
}
