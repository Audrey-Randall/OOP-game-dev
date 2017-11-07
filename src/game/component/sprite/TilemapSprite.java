package game.component.sprite;

import main.Tilemap;
import mote4.util.FileIO;
import mote4.util.matrix.ModelMatrix;
import mote4.util.shader.ShaderMap;
import mote4.util.shader.Uniform;
import mote4.util.texture.Texture;
import mote4.util.vertex.mesh.MeshMap;

public class TilemapSprite extends Sprite {

    private ModelMatrix model;
    private Texture texture;

    private Tilemap tilemap;

    public TilemapSprite(Texture t, Tilemap tm) {
        texture = t;
        tilemap = tm;
        model = new ModelMatrix();
        model.scale(32, 32, 1);
    }

    @Override
    public void render() {
        ShaderMap.use("spritesheet");
        texture.bind();
        for (int x = 0; x < tilemap.tiles.length; x++)
            for (int y = 0; y < tilemap.tiles[0].length; y++) {
                model.push();
                {
                    Uniform.vec("spriteInfo",8f,8f,(float)tilemap.tiles[x][y]);
                    model.translate(x, y);
                    model.bind();
                    MeshMap.render("quad");
                }
                model.pop();
            }

    }
}
