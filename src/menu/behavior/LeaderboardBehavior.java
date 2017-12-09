package menu.behavior;

import menu.MenuHandler;
import java.sql.*;
import java.util.List;

import game.GameWorld;
import game.component.behavior.PlayerBehavior;

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
    	PlayerBehavior pb = (PlayerBehavior)game.GameWorld.getInstance().getPlayer().getBehavior();
    	int score = pb.getScore();
    	main.Database.getInstance().updateScore(score);
    	List<String> scoreList = main.Database.getInstance().getScores();
    	String[] scoreArr = scoreList.toArray(new String[scoreList.size()]);
    	return scoreArr;
    }
}