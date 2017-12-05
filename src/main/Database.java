package main;

import java.sql.*;

public class Database {

	private static Database db;
	private static String loginName;
	private static String loginPw;

    public static Database getInstance() {
        if (db == null)
            db = new Database();
        return db;
    }
    
    //We are real secure over here guys
    private Database() {
    	//TODO: Replace with something less hard-coded.
    	loginName = "Audacity";
    	loginPw = "12345";
    }

	private Connection connect() {
		Connection conn;
    	try {
    		String url = "jdbc:sqlserver://AUDREY-PC\\SQLEXPRESS:60776;databaseName=players;";
    		conn = DriverManager.getConnection(url, loginName, loginPw);
    	} catch (Exception e) {
    		System.out.println(e.getMessage());
    		return null;
    	}
    	return conn;
    }
	
	private String[] getScores() {
		return new String[] {"Testing | testing | testing", "abcdef | ghijkl | asdfsdf"};
	}
	
	private int getNewId() {
		String sql = "SELECT COUNT(*) FROM Scores";
		try (Connection conn = connect();
        		PreparedStatement ps = conn.prepareStatement(sql)) {
			ResultSet rs = ps.executeQuery();
			int count = 0;
			while(rs.next()) {
				count = rs.getInt(1);
			}
			return count;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return -1;
		}
	}
	
	public void addUser(String name) {
		String sqlExists = "IF NOT EXISTS(SELECT * FROM Scores WHERE Name = ?)\r\n" + 
				"INSERT INTO Scores (PlayerID,Name) VALUES(?, ?)";
        try (Connection conn = connect();
        		PreparedStatement ps = conn.prepareStatement(sqlExists)) {
        	ps.setString(1, name);
        	ps.setInt(2, getNewId()+1);
        	ps.setString(3, name);
        	ps.executeUpdate();
        } catch (SQLException e) {
        	System.out.println(e.getMessage());
        }
	}
}
