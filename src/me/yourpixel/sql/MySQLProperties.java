package me.yourpixel.sql;

import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MySQLProperties {
	
	public MySQLSetup m;
		
	
	public MySQLProperties(MySQLSetup m) {
	this.m=m;
	}
	
	
	
	public void CreatePlayer(Player p,int points) {
		PreparedStatement posted;
		try {
			posted = m.getCon().prepareStatement("INSERT INTO cosmetics (PLAYER, UUID, POINTS) VALUES ('"+p.getName()+"', '"+p.getUniqueId()+"', '"+points+"')");
			posted.executeUpdate();
			posted.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public boolean isUUIDExists(UUID uuid) {
		try {
			PreparedStatement posted = m.getCon().prepareStatement("SELECT * FROM cosmetics WHERE UUID = '"+uuid+"'");
			ResultSet result = posted.executeQuery();
			if(result.next())
				return true;
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	public int getPoints(String UUID) {
		int a=0;
		try {
			PreparedStatement poster = m.getCon().prepareStatement("SELECT * FROM cosmetics WHERE UUID = '"+UUID+"'");
			ResultSet result = poster.executeQuery();
			result.next();
			a=result.getInt("POINTS");
			return a;
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return a;
	}
	
	public String getName(String UUID) {
		String a=null;
		try {
			PreparedStatement poster = m.getCon().prepareStatement("SELECT * FROM cosmetics WHERE UUID = '"+UUID+"'");
			ResultSet result = poster.executeQuery();
			result.next();
			a=result.getString("PLAYER");
			return a;
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return a;
	}
	
	public void updatePoints(String UUID, int points ) {
		PreparedStatement statement;
		try {
			statement = m.getCon().prepareStatement("UPDATE cosmetics SET POINTS = '"+points+"' WHERE UUID = '"+UUID+"'");
			statement.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateName(String UUID, String name) {
		PreparedStatement statement;
		try {
			statement = m.getCon().prepareStatement("UPDATE cosmetics SET PLAYER = '"+name+"' WHERE UUID = '"+UUID+"'");
			statement.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
}
