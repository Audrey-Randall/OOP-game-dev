package game.component.sprite;

import game.Entity;
import mote4.util.ErrorUtils;
import mote4.util.matrix.ModelMatrix;
import mote4.util.shader.ShaderMap;
import mote4.util.shader.Uniform;
import mote4.util.texture.Texture;
import mote4.util.vertex.mesh.MeshMap;

public class Tilemap extends Sprite {

    private ModelMatrix model;
    private Texture texture;

    private int[][] tiles;

    public Tilemap(Texture t) {
        texture = t;
        model = new ModelMatrix();
        model.scale(32, 32, 1);

        tiles = new int[20][15];
        for (int x = 0; x < tiles.length; x++)
            for (int y = 0; y < tiles[0].length; y++) {
                tiles[x][y] = (int)(Math.random()*4);
            }
    }

    @Override
    public void update(Entity e) {
        ShaderMap.use("spritesheet");
        texture.bind();
        for (int x = 0; x < tiles.length; x++)
            for (int y = 0; y < tiles[0].length; y++) {
                model.push();
                {
                    Uniform.vec("spriteInfo",8f,8f,(float)tiles[x][y]);
                    model.translate(x, y);
                    model.bind();
                    MeshMap.render("quad");
                }
                model.pop();
            }

    }
}
