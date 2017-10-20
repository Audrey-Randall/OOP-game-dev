package main;

import mote4.scenegraph.Window;

import java.util.Arrays;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Centralized input management.  All input checks for the game should be done
 * through this class, as it provides and easy-to-edit abstraction.
 * @author Peter
 */
public class Input {
    
    public enum Keys {
        YES(0),
        NO(1),
        UP(2),
        DOWN(3),
        LEFT(4),
        RIGHT(5),
        SPRINT(6),
        BACKSPACE(7),
        ENTER(8),
        ESC(13);
        
        int index;
        Keys(int i) {
            index = i;
        }
    }
    
    private static boolean[] isNew, isDown;
    
    static {
        isNew  = new boolean[Keys.values().length];
        isDown = new boolean[Keys.values().length];
    }

    public static boolean isKeyDown(Keys k) {
        return isDown[k.index];
    }

    public static boolean isKeyNew(Keys k) {
        boolean b =  isNew[k.index];
        isNew[k.index] = false;
        return b;
    }

    public static void clearKeys() {
        Arrays.fill(isNew, false);
        Arrays.fill(isDown, false);
    }
    
    /**
     * Override the default key callback created by the engine.
     */
    public static void createKeyCallback() {
        glfwSetKeyCallback(Window.getWindowID(), (long window, int key, int scancode, int action, int mods) -> {
            // action is GLFW_PRESS, GLFW_REPEAT, or GLFW_RELEASE
            switch (key) {
                case GLFW_KEY_Z:
                    callbackAction(action, Keys.YES);
                    break;
                case GLFW_KEY_X:
                    callbackAction(action, Keys.NO);
                    break;
                case GLFW_KEY_W:
                case GLFW_KEY_UP:
                    callbackAction(action, Keys.UP);
                    break;
                case GLFW_KEY_S:
                case GLFW_KEY_DOWN:
                    callbackAction(action, Keys.DOWN);
                    break;
                case GLFW_KEY_A:
                case GLFW_KEY_LEFT:
                    callbackAction(action, Keys.LEFT);
                    break;
                case GLFW_KEY_D:
                case GLFW_KEY_RIGHT:
                    callbackAction(action, Keys.RIGHT);
                    break;
                case GLFW_KEY_LEFT_SHIFT:
                    callbackAction(action, Keys.SPRINT);
                    break;
                case GLFW_KEY_BACKSPACE:
                    callbackAction(action, Keys.BACKSPACE);
                    break;
                case GLFW_KEY_ENTER:
                    callbackAction(action, Keys.ENTER);
                    break;
                case GLFW_KEY_ESCAPE:
                    callbackAction(action, Keys.ESC);
                    break;
            }
        });
    }
    private static void callbackAction(int action, Keys key) {
        isNew[key.index] = (action == GLFW_PRESS);
        isDown[key.index] = (action != GLFW_RELEASE);
    }
}
