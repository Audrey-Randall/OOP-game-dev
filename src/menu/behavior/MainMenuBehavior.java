package menu.behavior;

import menu.MenuHandler;
import mote4.scenegraph.Window;

public class MainMenuBehavior extends MenuBehavior {

    public MainMenuBehavior(MenuHandler h) {
        super(h);
        title = "Main Menu";
        elements = new String[] {"New game", "Options", "Leaderboards", "Quit"};
    }

    @Override
    public void onAction() {
        switch (elements[cursorPos]) {
            case "Options":
                handler.openMenu(new OptionsMenuBehavior(handler));
                break;
            case "Quit":
                Window.destroy();
                break;
            default:
                break;
        }
    }
}
