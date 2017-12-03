package menu.behavior;

import menu.MenuHandler;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import oracle.jdbc.driver.*;

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
    	try {
    		String host = "Server=localhost\\SQLEXPRESS;Database=master;Trusted_Connection=True;";
        	String username = "";
        	String password = "";
    		Connection dbCon = DriverManager.getConnection( host, username, password );
    	} catch (SQLException e) {
    		System.out.println(e.getMessage());
    	}
    	return new String[] {"Testing | testing | testing", "abcdef | ghijkl | asdfsdf"};
    }
}