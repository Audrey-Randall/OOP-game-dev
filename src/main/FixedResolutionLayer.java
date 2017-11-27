package main;

import mote4.scenegraph.Layer;
import mote4.scenegraph.target.FBO;

public class FixedResolutionLayer extends Layer {

    private FBO sceneFBO;

    public FixedResolutionLayer() {
        super(null);
    }

    @Override
    public void framebufferResized(int width, int height) {
        super.framebufferResized(width,height);

        double aspectRatio = width/(double)height;
        int w = (int)(480*aspectRatio);

        if (sceneFBO != null)
            sceneFBO.destroy();
        sceneFBO = new FBO(w,480);
        sceneFBO.addToTextureMap("fbo_scene");
        target = sceneFBO;
    }
}