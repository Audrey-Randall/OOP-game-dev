package menu.behavior;

import menu.MenuHandler;

public class OptionsMenuBehavior extends MenuBehavior {

    public OptionsMenuBehavior(MenuHandler h) {
        super(h);
        title = "Options";
        elements = new String[] {"Exit"};
    }

    @Override
    public void onAction() {
        switch (elements[cursorPos]) {
            case "Exit":
                handler.closeMenu();
                break;
            default:
                break;
        }
    }
}
