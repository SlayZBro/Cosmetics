package me.yourpixel.cosmetics;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

public class Ride implements Listener {

	private Plugin m;

	public Ride(Plugin m) {
		this.m = m;
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void ride(PlayerInteractEntityEvent e) {
		if (e.getRightClicked() instanceof Player && e.getPlayer().hasPermission("hub.donator")
				&& (e.getPlayer().getItemInHand().getType() == Material.AIR))
			e.getRightClicked().setPassenger(e.getPlayer());

	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void kick(PlayerInteractEvent e) {
		Player p = e.getPlayer();

		if (p.getPassenger() != null && e.getAction() == Action.LEFT_CLICK_AIR) {
			Player rider = (Player) p.getPassenger();
			p.eject();

			DoubleJump d = new DoubleJump(m);
			d.doubleJump(p, rider);
		}
	}

}
