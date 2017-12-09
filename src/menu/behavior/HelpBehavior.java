package menu.behavior;

import java.util.List;

import menu.MenuHandler;

public class HelpBehavior extends MenuBehavior{
	public HelpBehavior(MenuHandler h) {
        super(h);
        title = "Main Menu";
        String[] help = {"Arrow keys move!\nWASD moves!\nESC gives you the menu!\nand Z and X select from the menu!\nK thx bye!"};
        elements = help;
    }

    @Override
    public void onAction() {
        
    }

}
