package game;

import mote4.scenegraph.Scene;
import mote4.util.matrix.Transform;
import mote4.util.shader.ShaderMap;
import mote4.util.shader.Uniform;
import mote4.util.texture.TextureMap;
import mote4.util.vertex.mesh.MeshMap;

public class ScrollingBackground implements Scene {

    private Transform trans;

    public ScrollingBackground() {
        trans = new Transform();
    }

    @Override
    public void update(double time, double delta) {

    }

    @Override
    public void render(double time, double delta) {
        ShaderMap.use("background");
        TextureMap.bind("background"); // "background"
        float[] playerPos = new float[] {
                ((int)GameWorld.getInstance().getScrollPos())/1000f,
                0,//(float)GameWorld.getInstance().getPlayer().posY()/-10000f,
        };
        Uniform.vec("pos",playerPos);
        MeshMap.render("background");
    }

    @Override
    public void framebufferResized(int w, int h) {
        trans.projection.setPerspective(1,1, .1f, 100f, 65f);
        trans.view.setIdentity();
        trans.view.rotate((float)Math.PI/2, 0,0,1);
        trans.view.translate(0,0,-1f);
        trans.view.scale(1,1.3f,1);

        ShaderMap.use("background");
        Uniform.vec("aspectRatio", (float)w/h);
        trans.bind();
    }

    @Override
    public void destroy() {

    }
}
