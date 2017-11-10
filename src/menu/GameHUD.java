package menu;

import game.GameWorld;
import game.component.behavior.PlayerBehavior;
import mote4.util.matrix.ModelMatrix;
import mote4.util.shader.ShaderMap;
import mote4.util.shader.Uniform;
import mote4.util.texture.TextureMap;
import mote4.util.vertex.FontUtils;
import mote4.util.vertex.mesh.Mesh;

public class GameHUD {

    private Mesh text;

    //private Mesh[] strings;
    private ModelMatrix model;

    public GameHUD() {
        model = new ModelMatrix();
        //model.translate(64,64);
    }

    public void render() {
        ShaderMap.use("spritesheet");
        Uniform.vec("spriteInfo",1,1,0);
        TextureMap.bind("font_1");

        PlayerBehavior b = (PlayerBehavior)GameWorld.getInstance().getPlayer().getBehavior();
        double[] playerInfo = b.getPlayerInfo();
        if (text != null)
            text.destroy();
        text = FontUtils.createString(
                "Rat: "+playerInfo[0]+
                "\nRaccoon: "+playerInfo[1]+
                "\nPossum: "+playerInfo[2]+
                "\nScore: "+playerInfo[3],0,0,32,32);

        model.push();
        {
            //model.translate(0, 32 * (1 + index));
            model.bind();
            text.render();
        }
        model.pop();
    }

    public void destroy() {
        text.destroy();
    }
}
