package menu.behavior;

import game.GameWorld;
import game.Level;
import menu.MenuHandler;

import java.util.List;

public class LevelSelectBehavior extends MenuBehavior {

    private List<Level> levels;

    public LevelSelectBehavior(MenuHandler h) {
        super(h);
        title = "Level Select";
        levels = GameWorld.getInstance().getLevels();
        elements = new String[levels.size()+1];
        for (int i = 0; i < levels.size(); i++)
            elements[i] = levels.get(i).getLevelName();
        elements[elements.length-1] = "Exit";
    }

    @Override
    public void onAction() {
        switch (elements[cursorPos]) {
            case "Exit":
                handler.closeMenu();
                break;
            default:
                GameWorld.getInstance().loadLevel(levels.get(cursorPos));
        }
    }
}
