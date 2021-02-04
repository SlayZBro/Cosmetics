package me.yourpixel.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;


import me.yourpixel.cosmetics.Main;

public class MySQLSetup implements Listener {
	
	
	private Connection con =null;
	private String host, database, username, password;
	private String table;
	private int port;
	public Main m;
	
	public MySQLSetup(Main m) {
		this.m=m;
		table = m.getConfig().getString("MySQL.table");
		host = m.getConfig().getString("MySQL.host"); //"88.198.38.238";
		database = m.getConfig().getString("MySQL.database");//"mc_playtime";
		username= m.getConfig().getString("MySQL.username");//"slayzbro";
		password= m.getConfig().getString("MySQL.password");//"EupmbMo4hY8Xgigv";
		port = m.getConfig().getInt("MySQL.port");//3306
		

	}
	
	public void setupSQL() {
		try {
			
			synchronized(this) {
			
				if(getCon()!=null && !getCon().isClosed()) {
					return;
				}
				Class.forName("com.mysql.jdbc.Driver");
				setCon(DriverManager.getConnection("jdbc:mysql://"+this.host+":"+this.port+"/"+this.database, this.username, this.password));
				Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"SQL Connected for cosmetics");
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	public String getTable() {
		return table;
	}
	
	
	public Connection getCon() {
		return con;
	}
	
	public void setCon(Connection con) {
		this.con = con;
	}
	

	
	
	
}
