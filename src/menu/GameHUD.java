package menu;

import game.GameWorld;
import game.component.behavior.PlayerBehavior;
import mote4.util.matrix.ModelMatrix;
import mote4.util.shader.ShaderMap;
import mote4.util.shader.Uniform;
import mote4.util.texture.TextureMap;
import mote4.util.vertex.FontUtils;
import mote4.util.vertex.builder.StaticMeshBuilder;
import mote4.util.vertex.mesh.Mesh;

import static org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN;

public class GameHUD {

    private Mesh text;
    private Mesh[] healthBars;

    //private Mesh[] strings;
    private ModelMatrix model;

    public GameHUD() {
        model = new ModelMatrix();
        //model.translate(64,64);
        healthBars = new Mesh[3];
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
        for (Mesh m : healthBars)
            if (m != null)
                m.destroy();
        healthBars[0] = createHealthBar(playerInfo[0]);
        healthBars[1] = createHealthBar(playerInfo[1]);
        healthBars[2] = createHealthBar(playerInfo[2]);

        model.push();
        {
            //model.translate(0, 32 * (1 + index));
            model.bind();
            text.render();
        }
        model.pop();
    }
    private Mesh createHealthBar(double health) {
        return StaticMeshBuilder.constructVAO(GL_TRIANGLE_FAN,
                2, new float[] {},
                2, new float[] {},
                0, null, null);
    }

    public void destroy() {
        text.destroy();
    }
}
