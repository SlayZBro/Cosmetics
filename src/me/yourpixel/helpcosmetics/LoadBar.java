package me.yourpixel.helpcosmetics;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class LoadBar {
	
	private Player player;
	private float time;
	private float count;
	private Plugin m;
	private int schdular;
	
	public LoadBar(Plugin m,Player p,float time) {
		this.player = p;
		this.time = time;
		this.count=time;
		this.m=m;
		launch();
	}
	
	public void launch() {
		player.setExp(1f);
		player.setLevel((int)count);
		
		schdular = Bukkit.getScheduler().scheduleSyncRepeatingTask(this.m, new Runnable() {
			
			@Override
			public void run() {
				count--;
				float exp = player.getExp();
				float remove = (float)1/time;
				float newExp = exp-remove;
				if(newExp <=0) {
					player.setExp(0f);
					player.setLevel(0);
					Bukkit.getScheduler().cancelTask(schdular);
				}
				player.setExp(newExp);
				player.setLevel((int)count);
				
				
				
			}
		}, 0, 20);
		
		player.setExp(0.99f);
	}

}
