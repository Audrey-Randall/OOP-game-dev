package menu;

import main.Input;
import menu.behavior.MainMenuBehavior;
import menu.behavior.MenuBehavior;

import java.util.Stack;

/**
 * Encapsulates operations needed to use menus.
 */
public class MenuHandler {

    private Stack<MenuBehavior> behaviors;
    private MenuRenderer renderer;

    public MenuHandler() {
        behaviors = new Stack<>();
        renderer = null;
        openMenu(new MainMenuBehavior(this));
    }

    public void openMenu(MenuBehavior behavior) {
        behaviors.push(behavior);
        updateRenderer();
    }

    public void update() {
        if (Input.isKeyNew(Input.Key.YES))
        {
            behaviors.peek().onAction();
        }
        else if (Input.isKeyNew(Input.Key.NO))
        {
            closeMenu();
        }
        else if (Input.isKeyNew(Input.Key.UP))
        {
            // cursor up
            behaviors.peek().moveCursor(-1);
        }
        else if (Input.isKeyNew(Input.Key.DOWN))
        {
            // cursor down
            behaviors.peek().moveCursor(1);
        }
    }

    public void render() {
        renderer.render(behaviors.peek().getCursorPos());
    }

    public void closeMenu() {
        if (behaviors.size() > 1) {
            behaviors.pop();
            updateRenderer();
        }
    }

    private void updateRenderer() {
        if (renderer != null)
            renderer.destroy(); // delete OpenGL buffers
        renderer = new MenuRenderer(behaviors.peek());
    }
}
