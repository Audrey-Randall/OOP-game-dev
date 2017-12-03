package menu.behavior;

import menu.MenuHandler;
import mote4.scenegraph.Window;

public class MainMenuBehavior extends MenuBehavior {

    public MainMenuBehavior(MenuHandler h) {
        super(h);
        title = "Main Menu";
        elements = new String[] {"Level Select", "Options", "Leaderboards", "Quit"};
    }

    @Override
    public void onAction() {
        switch (elements[cursorPos]) {
            case "Level Select":
                handler.openMenu(new LevelSelectBehavior(handler));
                break;
            case "Options":
                handler.openMenu(new OptionsMenuBehavior(handler));
                break;
            case "Leaderboards":
            	handler.openMenu(new LeaderboardBehavior(handler));
                break;
            case "Quit":
                Window.destroy();
                break;
            default:
                break;
        }
    }
}
