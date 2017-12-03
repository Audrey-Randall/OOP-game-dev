package menu.behavior;

import menu.MenuHandler;
import java.sql.*;

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
    		String conUrl = "jdbc:sqlserver://localhost:60776; databaseName=players; user=sa; password=Password123;";
        	String username = "";
        	String password = "";
    		Connection dbCon = DriverManager.getConnection(conUrl);
    	} catch (SQLException e) {
    		System.out.println("?!!");
    		System.out.println(e.getMessage());
    	}
    	return new String[] {"Testing | testing | testing", "abcdef | ghijkl | asdfsdf"};
    }
}