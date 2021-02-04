package me.yourpixel.cosmetics;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import me.yourpixel.sql.MySQLProperties;
import me.yourpixel.sql.MySQLSetup;



public class Main extends JavaPlugin implements Listener{
	private MySQLSetup sql;
	
	@Override
	public void onEnable() {
		sql = new MySQLSetup(this);
		sql.setupSQL();
		getServer().getConsoleSender().sendMessage("Cosmetics plugin has been enabled");
		
		getServer().getPluginManager().registerEvents(this, this);
		getServer().getPluginManager().registerEvents(new DoubleJump(this),this);
		getServer().getPluginManager().registerEvents(new Ride(this), this);
		getServer().getPluginManager().registerEvents(new Particles(this), this);
		getServer().getPluginManager().registerEvents(new GUI(sql), this);
		getServer().getPluginManager().registerEvents(new Weapons(this), this);
		getServer().getPluginManager().registerEvents(new Suits(this), this);
		
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		

	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(label.equalsIgnoreCase("setpoints") && ((Player)sender).isOp()) {
			if(args.length==2) {
				Player target = Bukkit.getPlayer(args[0]);
				new MySQLProperties(sql).updatePoints(target.getUniqueId()+"", Integer.parseInt(args[1]));
				sender.sendMessage("Done");
				return true;
			}
		}
		
		return true;
	}
	
	
	
	
	
	
	

}
