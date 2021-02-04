package me.yourpixel.cosmetics;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.util.Vector;

import me.yourpixel.helpcosmetics.MathUtils;
import me.yourpixel.sql.MySQLSetup;

public class Particles implements Listener {
	private Main m;

	private HashMap<String, String> map;
	private HashMap<Player, Integer> schedular;
	private MathUtils math;

	public Particles(Main m) {
		this.m = m;
		map = new HashMap<>();
		schedular = new HashMap<>();

		math = new MathUtils();

	}

	public Particles() {

	}

	@EventHandler
	public void joinParticle(PlayerJoinEvent e) {
		if (m.getConfig().contains("c."+e.getPlayer().getUniqueId().toString())) {
			map.put(e.getPlayer().getUniqueId().toString(),
					m.getConfig().getString("c."+e.getPlayer().getUniqueId().toString()));
		}
	}

	@EventHandler
	public void leave(PlayerQuitEvent e) {
		if (schedular != null && schedular.get(e.getPlayer()) != null)
			Bukkit.getScheduler().cancelTask(schedular.get(e.getPlayer()));
	}

	@EventHandler
	public void inventoryClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (e.getCurrentItem() != null)
			if (e.getCurrentItem().getType() != Material.STAINED_GLASS && e.getCurrentItem().hasItemMeta()) {
				if (e.getCurrentItem().getItemMeta().getDisplayName()
						.equalsIgnoreCase(ChatColor.GOLD + "flame trail")) {
					e.setCancelled(true);

//			ItemMeta meta = e.getCurrentItem().getItemMeta();
//			meta.addEnchant(Enchantment.KNOCKBACK, 1, true);
//			e.getCurrentItem().setItemMeta(meta);
//			
					map.put(e.getWhoClicked().getUniqueId().toString(), "c.flame");

					m.getConfig().set("c."+e.getWhoClicked().getUniqueId().toString(), "c.flame");
					m.saveConfig();

					new GUI(new MySQLSetup(m)).mainPage((Player) e.getWhoClicked());
					if (schedular != null && schedular.get(p) != null)
						Bukkit.getScheduler().cancelTask(schedular.get((Player) e.getWhoClicked()));

				}

				else if (e.getCurrentItem().getItemMeta().getDisplayName()
						.equalsIgnoreCase(ChatColor.RED + "heart trail")) {
					e.setCancelled(true);

//			ItemMeta meta = e.getCurrentItem().getItemMeta();
//			meta.addEnchant(Enchantment.KNOCKBACK, 1, true);
//			e.getCurrentItem().setItemMeta(meta);

					map.put(e.getWhoClicked().getUniqueId().toString(), "c.heart");

					m.getConfig().set("c."+e.getWhoClicked().getUniqueId().toString(), "c.heart");
					m.saveConfig();

					new GUI(new MySQLSetup(m)).mainPage((Player) e.getWhoClicked());
					if (schedular != null && schedular.get(p) != null)
						Bukkit.getScheduler().cancelTask(schedular.get((Player) e.getWhoClicked()));

				}

				else if (e.getCurrentItem().getItemMeta().getDisplayName()
						.equalsIgnoreCase(ChatColor.DARK_PURPLE + "notes trail")) {
					map.put(e.getWhoClicked().getUniqueId().toString(), "c.notes");

					m.getConfig().set("c."+e.getWhoClicked().getUniqueId().toString(), "c.notes");
					m.saveConfig();

					new GUI(new MySQLSetup(m)).mainPage((Player) e.getWhoClicked());
					if (schedular != null && schedular.get(p) != null)
						Bukkit.getScheduler().cancelTask(schedular.get((Player) e.getWhoClicked()));

				}

				else if (e.getCurrentItem().getItemMeta().getDisplayName()
						.equalsIgnoreCase(ChatColor.AQUA + "Heart Snake")) {

					map.put(e.getWhoClicked().getUniqueId().toString(), "c.snakeheart");

					m.getConfig().set("c."+e.getWhoClicked().getUniqueId().toString(), "c.snakeheart");
					m.saveConfig();

					new GUI(new MySQLSetup(m)).mainPage((Player) e.getWhoClicked());
					if (schedular != null && schedular.get(p) != null)
						Bukkit.getScheduler().cancelTask(schedular.get((Player) e.getWhoClicked()));

				}

				else if (e.getCurrentItem().getItemMeta().getDisplayName()
						.equalsIgnoreCase(ChatColor.BLUE + "water zone")) {

					map.put(e.getWhoClicked().getUniqueId().toString(), "c.waterzone");

					m.getConfig().set("c."+e.getWhoClicked().getUniqueId().toString(), "c.waterzone");
					m.saveConfig();

					new GUI(new MySQLSetup(m)).mainPage((Player) e.getWhoClicked());
					if (schedular != null && schedular.get(p) != null)
						Bukkit.getScheduler().cancelTask(schedular.get((Player) e.getWhoClicked()));

				}

				else if (e.getCurrentItem().getItemMeta().getDisplayName()
						.equalsIgnoreCase(ChatColor.DARK_RED + "back")) {
					new GUI(new MySQLSetup(m)).mainPage((Player) e.getWhoClicked());
					e.setCancelled(true);
				}

				else if (e.getCurrentItem().getItemMeta().getDisplayName()
						.equalsIgnoreCase(ChatColor.RED + "Remove Particles")) {
					e.setCancelled(true);
					m.getConfig().set("c."+e.getWhoClicked().getUniqueId().toString(), null);
					if (map.containsKey(e.getWhoClicked().getUniqueId() + ""))
						map.remove(e.getWhoClicked().getUniqueId() + "");
					new GUI(new MySQLSetup(m)).mainPage((Player) e.getWhoClicked());
					if (schedular != null && schedular.get(p) != null)
						Bukkit.getScheduler().cancelTask(schedular.get((Player) e.getWhoClicked()));

				}

			}
	}

	@EventHandler
	public void move(PlayerMoveEvent e) {
		if (check(e.getPlayer(), "c.flame"))
			playFlame(e.getPlayer());
		else if (check(e.getPlayer(), "c.heart"))
			playHeart(e.getPlayer());
		else if (check(e.getPlayer(), "c.notes"))
			playNotes(e.getPlayer());
		else if (check(e.getPlayer(), "c.water"))
			playWater(e.getPlayer());
		else if (check(e.getPlayer(), "c.snakeheart")) {
			fireSerpent(e.getPlayer());
			map.put(e.getPlayer().getUniqueId().toString(), "false");
		} else if (check(e.getPlayer(), "c.waterzone")) {
			sneak(e.getPlayer());
			map.put(e.getPlayer().getUniqueId().toString(), "false");
		}

	}

	public void playFlame(Player p) {
		p.getWorld().playEffect(p.getLocation(), Effect.MOBSPAWNER_FLAMES, 2);
	}

	@SuppressWarnings("deprecation")
	public void playHeart(Player p) {
		Location loc = p.getLocation();
		loc.setY(loc.getY() + 2.2);
		p.getWorld().playEffect(loc, Effect.HEART, 2);
	}

	@SuppressWarnings("deprecation")
	public void playNotes(Player p) {
		Location loc = p.getLocation();
		loc.setY(loc.getY() + 2.0);
		p.getWorld().playEffect(loc, Effect.NOTE, 2);
	}

	@SuppressWarnings("deprecation")
	public void playWater(Player p) {
		for (double y = 0; y <= 30; y++) {
			double adjustx = Math.cos(y);
			double adjustz = Math.sin(y);
			Location loc = new Location(p.getWorld(), adjustx, y, adjustz);
			p.getWorld().playEffect(loc, Effect.WATERDRIP, 2);

		}
	}

	public boolean check(Player p, String par) {
		if (map.containsKey(p.getUniqueId() + "")) {
			return map.get(p.getUniqueId() + "").equalsIgnoreCase(par);
		}
		return false;
	}

	public void fireSerpent(final Player p) {

		schedular.put(p, Bukkit.getScheduler().scheduleSyncRepeatingTask(m, new Runnable() {
			double t = 6.0;

			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				t = t + Math.PI / 230;
				final Location loc = p.getLocation();
				double x, y, z;
				double x1, y1, z1;
				double x2, y2, z2;
				double r = 0.3;

				x2 = math.sin(2 * t);
				y2 = 2 * math.cos(t);
				z2 = math.sin(3 * t);

				t -= Math.PI / 230;

				x1 = math.sin(2 * t);
				y1 = 2 * math.cos(t);
				z1 = math.sin(3 * t);
				t += Math.PI / 230;
				Vector dir = new Vector(x2 - x1, y2 - y1, z2 - z1);
				Location loc2 = new Location(p.getWorld(), 0, 0, 0).setDirection(dir.normalize());

				loc2.setDirection(dir.normalize()); // usless line

				for (double i = 0; i <= 2 * Math.PI; i = i + Math.PI / 8) {
					x = 0.2 * t;
					y = r * math.sin(i) + 2 * math.sin(10 * t) + 2.8;
					z = r * math.cos(i);

					Vector v = new Vector(x, y, z);
					v = math.rotateFunction(v, loc2);
					loc.add(v.getX(), v.getY(), v.getZ());
					p.getWorld().playEffect(loc, Effect.HEART, 1);
					if (i == 0) {
						p.getWorld().playEffect(loc, Effect.LAVA_POP, 1);
					}
					loc.subtract(v.getX(), v.getY(), v.getZ());

				}
				if (t > 12 * Math.PI) {
					map.put(p.getUniqueId().toString(), "c.snakeheart");
					Bukkit.getScheduler().cancelTask(schedular.get(p));
				}
			}
		}, 0, 1));

	}

	public void sneak(Player p) {

		schedular.put(p, Bukkit.getScheduler().scheduleSyncRepeatingTask(m, new Runnable() {

			double phi = 0;

			@SuppressWarnings("deprecation")
			@Override
			public void run() {

				final Location loc = p.getLocation();

				phi += Math.PI / 10;
				for (double theta = 0; theta <= 2 * Math.PI; theta += Math.PI / 40) {
					double r = 1.5;
					double x = r * math.cos(theta) * math.sin(phi);
					double y = r * math.cos(phi) + 1.5;
					double z = r * math.sin(theta) * math.sin(phi);
					loc.add(x, y, z);
					p.getWorld().playEffect(loc, Effect.WATERDRIP, 2);
					loc.subtract(x, y, z);
				}

				if (phi > Math.PI) {
					phi = Math.PI / 10;
//					map.put(p.getUniqueId().toString(), "waterzone");
//					Bukkit.getScheduler().cancelTask(schedular.get(p));
				}

			}
		}, 0, 2));

	}

}
