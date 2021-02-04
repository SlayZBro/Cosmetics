package me.yourpixel.cosmetics;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import me.yourpixel.helpcosmetics.LoadBar;


public class DoubleJump implements Listener{
	private Plugin m;
	public DoubleJump(Plugin m) {
		this.m = m;
	}
	
	public void doubleJump(Player p,Player p2) {
		
		if(p.getGameMode() == GameMode.CREATIVE || p.getGameMode() == GameMode.SPECTATOR) return;
		Location loc = p.getLocation();
		Vector v = loc.getDirection().multiply(1.2f).setY(1.2);

		if(p2==null) {
		p.setFlying(false);
		p.setAllowFlight(false);
		p.setVelocity(v);
		}else {
			p2.setFlying(false);
			p2.setAllowFlight(false);
			p2.setVelocity(v);
		}
		loc.getWorld().playSound(loc, Sound.ENTITY_FIREWORK_BLAST, 1, 0.2f);
		p.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, loc,250);
	}
	
	@EventHandler
	public void togglefly(PlayerToggleFlightEvent e) {
		if(e.getPlayer().getGameMode()!=GameMode.CREATIVE) {
		doubleJump(e.getPlayer(),null);
		e.setCancelled(true);
		new LoadBar(m, e.getPlayer(), 5);
		}
		
	}

	@EventHandler
	public void move(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if(p.getGameMode() == GameMode.CREATIVE || p.getGameMode() == GameMode.SPECTATOR) return;
		
		if(!p.isOnGround() && !p.isInsideVehicle()) {
			if(!p.getAllowFlight() && p.getLevel()==0) {
				p.setAllowFlight(true);
			}
		}
	}
}
