package menu;

import menu.behavior.MenuBehavior;
import mote4.util.matrix.ModelMatrix;
import mote4.util.shader.ShaderMap;
import mote4.util.shader.Uniform;
import mote4.util.texture.TextureMap;
import mote4.util.vertex.FontUtils;
import mote4.util.vertex.mesh.Mesh;

/**
 * Renders the contents of a menu.
 */
public class MenuRenderer {

    private static Mesh cursor;
    static {
        cursor = FontUtils.createString(">",0,0,32,32);
    }

    private Mesh[] strings;
    private ModelMatrix model;

    protected MenuRenderer(MenuBehavior b) {
        model = new ModelMatrix();
        model.translate(64,64);

        strings = new Mesh[b.getNumElements()+1];
        strings[0] = FontUtils.createString(b.getTitle(), 0,0, 32,32);
        for (int i = 0; i < b.getNumElements(); i++) {
            strings[i+1] = FontUtils.createString(b.getElementName(i), 32,32*(i+1), 32,32);
        }
    }

    public void render(int index) {
        ShaderMap.use("spritesheet");
        Uniform.vec("spriteInfo",1,1,0);
        TextureMap.bind("font_1");

        model.bind();
        for (Mesh m : strings)
            m.render();

        model.push();
        {
            model.translate(0, 32 * (1 + index));
            model.bind();
            cursor.render();
        }
        model.pop();
    }

    public void destroy() {
        for (Mesh m : strings)
            m.destroy();
    }
}
