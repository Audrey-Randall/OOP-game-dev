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
    		/*String conUrl = "jdbc:sqlserver://localhost:60776; databaseName=players; user=TPD;";
        	String username = "";
        	String password = "";
    		Connection dbCon = DriverManager.getConnection(conUrl);*/
    		String userName = "Audacity";
    		String password = "12345";

    		String url = "jdbc:sqlserver://AUDREY-PC\\SQLEXPRESS:60776;databaseName=players;";

    		//Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    		Connection conn = DriverManager.getConnection(url, userName, password);
    	} catch (Exception e) {
    		System.out.println("?!!");
    		System.out.println(e.getMessage());
    	}
    	return new String[] {"Testing | testing | testing", "abcdef | ghijkl | asdfsdf"};
    }
}