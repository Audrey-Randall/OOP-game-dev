package main;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
    		String url = "jdbc:sqlserver://AUDREY-PC\\SQLEXPRESS:60776;databaseName=players; loginTimeout=2;";
    		conn = DriverManager.getConnection(url, loginName, loginPw);
    	} catch (Exception e) {
    		System.out.println(e.getMessage());
    		return null;
    	}
    	return conn;
    }
	
	public List<String> getScores() {
		String sql = "SELECT * FROM Scores";
		List<String> results = new ArrayList<String>();
		try {
			Connection conn = connect();
			if (conn == null) {
				return results;
			}
        	PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				String name = rs.getString(2);
				float score = rs.getFloat(3);
				results.add(name+" | "+score);
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		//return new String[] {"Testing | testing | testing", "abcdef | ghijkl | asdfsdf"};
		return results;
	}
	
	private int getNewId() {
		String sql = "SELECT COUNT(*) FROM Scores";
		try {
			Connection conn = connect();
			if (conn == null) {
				return 0;
			}
        	PreparedStatement ps = conn.prepareStatement(sql);
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
	
	private float getHighScore() {
		String sql = "SELECT HighScore FROM Scores WHERE Name = ?";
		try {
			Connection conn = connect();
			if (conn == null) {
				return 0;
			}
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, game.GameWorld.getInstance().getCurrentUser());
			ResultSet rs = ps.executeQuery();
			float hs = 0;
			while(rs.next()) {
				hs = rs.getInt(1);
			}
			return hs;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return -1;
		}
	}
	
	public void updateScore(float score) {
		if (score <= getHighScore()) {
			return;
		}
		String sql = "UPDATE Scores\r\n" + 
				"SET HighScore = ?\r\n" + 
				"WHERE Name = ?;";
		try {
			Connection conn = connect();
			if (conn == null) {
				return;
			}
        	PreparedStatement ps = conn.prepareStatement(sql);
			ps.setFloat(1, score);
        	ps.setString(2, game.GameWorld.getInstance().getCurrentUser());
        	ps.executeUpdate();
        } catch (SQLException e) {
        	System.out.println(e.getMessage());
        }
	}
	
	public void addUser(String name) {
		String sqlExists = "IF NOT EXISTS(SELECT * FROM Scores WHERE Name = ?)\r\n" + 
				"INSERT INTO Scores (PlayerID,Name) VALUES(?, ?)";
        try {
        	Connection conn = connect();
			if (conn == null) {
				return;
			}
			PreparedStatement ps = conn.prepareStatement(sqlExists);
        	ps.setString(1, name);
        	ps.setInt(2, getNewId()+1);
        	ps.setString(3, name);
        	ps.executeUpdate();
        	game.GameWorld.getInstance().setCurrentUsername(name);
        } catch (SQLException e) {
        	System.out.println(e.getMessage());
        }
	}
}
