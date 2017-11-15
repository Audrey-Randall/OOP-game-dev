package game.component;

import mote4.scenegraph.Scene;
import mote4.util.shader.ShaderMap;
import mote4.util.shader.Uniform;
import mote4.util.texture.TextureMap;
import mote4.util.vertex.mesh.MeshMap;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

public class Postprocess implements Scene {

    public Postprocess() {
        ShaderMap.use("quad");
        Uniform.sampler("tex_scanlines", 1, "post_scanlines", true);
    }

    @Override
    public void update(double v, double v1) {

    }

    @Override
    public void render(double v, double v1) {
        glClear(GL_COLOR_BUFFER_BIT);
        ShaderMap.use("quad");
        TextureMap.bindFiltered("fbo_scene");
        MeshMap.render("normal_quad");
    }

    @Override
    public void framebufferResized(int width, int height) {
        ShaderMap.use("quad");
        double aspectRatio = width/(double)height;
        int w = (int)(480*aspectRatio);
        Uniform.vec("texSize",w,480);
    }

    @Override
    public void destroy() {

    }
}
