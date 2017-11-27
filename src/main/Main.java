package main;

import game.GameWorld;
import game.ScrollingBackground;
import game.component.Postprocess;
import mote4.scenegraph.Window;
import mote4.util.ErrorUtils;
import mote4.util.shader.ShaderUtils;
import mote4.util.texture.TextureMap;
import mote4.util.vertex.FontUtils;
import mote4.util.vertex.builder.StaticMeshBuilder;
import mote4.util.vertex.mesh.Mesh;
import mote4.util.vertex.mesh.MeshMap;

import static org.lwjgl.glfw.GLFW.GLFW_DONT_CARE;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSizeLimits;
import static org.lwjgl.opengl.GL11.*;

public class Main {

    public static void main(String[] args) {
        ErrorUtils.debug(true);
        //Window.initWindowedPercent(.75, 16.0/9.0);
        Window.initWindowed(640,480);
        Window.setVsync(true);

        glfwSetWindowSizeLimits(Window.getWindowID(), 640, 480, GLFW_DONT_CARE, GLFW_DONT_CARE);

        glDisable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        //glEnable(GL_CULL_FACE);

        loadResources();

        FixedResolutionLayer l = new FixedResolutionLayer();
        l.addScene(new ScrollingBackground());
        l.addScene(GameWorld.getInstance());
        Window.addLayer(l);
        Window.addScene(new Postprocess());
        Window.loop();
    }

    private static void loadResources() {
        TextureMap.loadIndex("index.txt");
        ShaderUtils.loadIndex("index.txt");

        FontUtils.loadMetric("font/misterpixel/misterpixel_metric","font_1");
        FontUtils.useMetric("font_1");

        Mesh quad = StaticMeshBuilder.constructVAO(GL_TRIANGLE_STRIP,
                2,new float[] {0,0, 0,1, 1,0, 1,1},
                2,new float[] {0,0, 0,1, 1,0, 1,1},
                0,null,null);
        MeshMap.add(quad, "quad");
        MeshMap.add(StaticMeshBuilder.loadQuadMesh(), "normal_quad");
        MeshMap.add(StaticMeshBuilder.constructVAOFromOBJ("openCylinder",true), "background");

        ErrorUtils.checkGLError();
    }
}
