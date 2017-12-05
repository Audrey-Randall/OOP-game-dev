package menu.behavior;

import menu.MenuHandler;
import java.sql.*;
import java.util.List;

public class LeaderboardBehavior extends MenuBehavior {

    public LeaderboardBehavior(MenuHandler h) {
        super(h);
        title = "Main Menu";
        elements = getScores();
    }

    @Override
    public void onAction() {
        
    }
    
    private String[] getScores() {
    	List<String> scoreList = main.Database.getInstance().getScores();
    	String[] scoreArr = scoreList.toArray(new String[scoreList.size()]);
    	return scoreArr;
    }
}