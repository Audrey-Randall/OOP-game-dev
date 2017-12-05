package main;

import mote4.scenegraph.Window;

import java.util.Arrays;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Centralized input management.  All input checks for the game should be done
 * through this class, as it provides an easy-to-edit abstraction.
 * @author Peter
 */
public class Input {

    private static Input input;
    public static Input getInstance() {
        if (input == null)
            input = new Input();
        return input;
    }

    public enum Key {
        YES(0),
        NO(1),
        UP(2),
        DOWN(3),
        LEFT(4),
        RIGHT(5),
        SPRINT(6),
        BACKSPACE(7),
        ENTER(8),
        ESC(9);
        
        int index;
        Key(int i) {
            index = i;
        }
    }
    
    private boolean[] isNew, isDown;

    private Input() {
        createKeyCallback();
        isNew  = new boolean[Key.values().length];
        isDown = new boolean[Key.values().length];
    }

    public boolean isKeyDown(Key k) {
        return isDown[k.index];
    }

    public boolean isKeyNew(Key k) {
        boolean b =  isNew[k.index];
        isNew[k.index] = false; // TODO this may be unnecessary since isNew ignores GLFW_REPEAT
        return b;
    }

    public void clearKeys() {
        Arrays.fill(isNew, false);
        Arrays.fill(isDown, false);
    }
    
    /**
     * Override the default key callback created by the engine.
     */
    private void createKeyCallback() {
        glfwSetKeyCallback(Window.getWindowID(), (long window, int key, int scancode, int action, int mods) -> {
            // action is GLFW_PRESS, GLFW_REPEAT, or GLFW_RELEASE
            switch (key) {
                case GLFW_KEY_Z:
                    callbackAction(action, Key.YES);
                    break;
                case GLFW_KEY_X:
                    callbackAction(action, Key.NO);
                    break;
                case GLFW_KEY_W:
                case GLFW_KEY_UP:
                    callbackAction(action, Key.UP);
                    break;
                case GLFW_KEY_S:
                case GLFW_KEY_DOWN:
                    callbackAction(action, Key.DOWN);
                    break;
                case GLFW_KEY_A:
                case GLFW_KEY_LEFT:
                    callbackAction(action, Key.LEFT);
                    break;
                case GLFW_KEY_D:
                case GLFW_KEY_RIGHT:
                    callbackAction(action, Key.RIGHT);
                    break;
                case GLFW_KEY_LEFT_SHIFT:
                    callbackAction(action, Key.SPRINT);
                    break;
                case GLFW_KEY_BACKSPACE:
                    callbackAction(action, Key.BACKSPACE);
                    break;
                case GLFW_KEY_ENTER:
                    callbackAction(action, Key.ENTER);
                    break;
                case GLFW_KEY_ESCAPE:
                    callbackAction(action, Key.ESC);
                    break;
            }
        });
    }
    private void callbackAction(int action, Key key) {
        isNew[key.index] = (action == GLFW_PRESS);
        isDown[key.index] = (action != GLFW_RELEASE);
    }
}
