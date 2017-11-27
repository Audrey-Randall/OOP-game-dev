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
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_STRIP;

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
        final float SCALE = 16;

        PlayerBehavior b = (PlayerBehavior)GameWorld.getInstance().getPlayer().getBehavior();
        double[] playerInfo = b.getPlayerInfo();
        if (text != null)
            text.destroy();
        text = FontUtils.createString(
                "Rat: "+
                "\nRaccoon: "+
                "\nPossum: "+
                "\nScore: "+playerInfo[3],0,0,SCALE,SCALE);
        for (Mesh m : healthBars)
            if (m != null)
                m.destroy();
        healthBars[0] = createHealthBar(playerInfo[0],SCALE);
        healthBars[1] = createHealthBar(playerInfo[1],SCALE);
        healthBars[2] = createHealthBar(playerInfo[2],SCALE);

        model.push();
        {
            model.translate(4,2);
            model.bind();
            TextureMap.bind("font_1");
            text.render();

            model.translate(128,0);
            model.bind();
            TextureMap.bind("healthbar");
            for (Mesh m : healthBars) {
                m.render();
                model.translate(0,SCALE+4);
                model.bind();
            }
        }
        model.pop();
    }
    private Mesh createHealthBar(double health, float scale) {
        float h = (float)health;
        return StaticMeshBuilder.constructVAO(GL_TRIANGLE_STRIP,
                2, new float[] {0,0, h*scale,0, 0,scale, h*scale,scale},
                2, new float[] {0,0, h,0, 0,1, h,1},
                0, null, null);
    }

    public void destroy() {
        text.destroy();
    }
}
