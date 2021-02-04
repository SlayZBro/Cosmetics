package me.yourpixel.cosmetics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import me.yourpixel.helpcosmetics.LoadBar;
import me.yourpixel.helpcosmetics.MathUtils;
import me.yourpixel.sql.MySQLSetup;

public class Weapons implements Listener {

	private Main m;
	private HashMap<String, Integer> cooldown;
	private List<String> names;
	private HashMap<Player,Integer> sechular;
	private HashMap<String,Boolean> fish;
	
	

	public Weapons(Main m) {
		this.m = m;
		cooldown = new HashMap<>();
		cooldown();
		names = new ArrayList<>();
		sechular = new HashMap<>();
		fish = new HashMap<>();
	}

	@EventHandler
	public void clickInventory(InventoryClickEvent e) {
		if (e.getCurrentItem() != null)
			if (e.getCurrentItem().getType() != Material.STAINED_GLASS && e.getCurrentItem().hasItemMeta()) {
				Player p = (Player) e.getWhoClicked();
				if (e.getCurrentItem().getItemMeta().getDisplayName()
						.equalsIgnoreCase(ChatColor.GOLD + "cannon flame trail")) {
					setItem(Material.GOLD_HOE, ChatColor.GOLD + "Cannon Flame Trail", p);
					e.setCancelled(true);
					new GUI(new MySQLSetup(m)).mainPage(p);
				}

				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(
						ChatColor.translateAlternateColorCodes('&', "&aP&ca&4i&bn&ft &ac&ca&4nn&co&4n"))) {
					setItem(Material.STICK,
							ChatColor.translateAlternateColorCodes('&', "&aP&ca&4i&bn&ft &ac&ca&4nn&co&4n"), p);
					e.setCancelled(true);
					new GUI(new MySQLSetup(m)).mainPage(p);
				}

				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.BLUE + "Water Cage")) {
					setItem(Material.IRON_BARDING, ChatColor.BLUE + "Water Cage", p);
					e.setCancelled(true);
					new GUI(new MySQLSetup(m)).mainPage(p);
				}
				
				if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GRAY + "Grappling Hook")) {
					setItem(Material.FISHING_ROD, ChatColor.GRAY + "Grappling Hook", p);
					e.setCancelled(true);
					new GUI(new MySQLSetup(m)).mainPage(p);
				}
				
				if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.LIGHT_PURPLE+"JetPack")) {
					setItem(Material.ELYTRA, ChatColor.LIGHT_PURPLE+"JetPack", p);
					e.setCancelled(true);
					new GUI(new MySQLSetup(m)).mainPage(p);
				}

			}
	}

	public void setItem(Material m, String name, Player p) {
		ItemStack item = new ItemStack(m);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		p.getInventory().setItem(2, item);
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void use(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (e.getItem() != null && e.getItem().getItemMeta().getDisplayName() != null) {

			if (p.getItemInHand().getItemMeta().getDisplayName()
					.equalsIgnoreCase(ChatColor.GOLD + "cannon flame trail")) {
				if (cooldown != null && cooldown.containsKey(p.getName())) {
					p.sendMessage(ChatColor.RED + "You can use a weapon in " + cooldown.get(p.getName()) + " seconds");
					return;
				}
				trail(p);
				cooldown.put(p.getName(), 10);
			}

			if (p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(
					ChatColor.translateAlternateColorCodes('&', "&aP&ca&4i&bn&ft &ac&ca&4nn&co&4n"))) {
				if (cooldown != null && cooldown.containsKey(p.getName())) {
					p.sendMessage(ChatColor.RED + "You can use a weapon in " + cooldown.get(p.getName()) + " seconds");
					return;
				}
				cooldown.put(p.getName(), 15);
				Snowball ball = p.getWorld().spawn(p.getEyeLocation(), Snowball.class);
				ball.setShooter(p);
				ball.setVelocity(p.getLocation().getDirection().multiply(1.5));

			}
			
			if(p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.LIGHT_PURPLE+"JetPack")) {
				e.setCancelled(true);
				if (cooldown != null && cooldown.containsKey(p.getName())) {
					p.sendMessage(ChatColor.RED + "You can use a weapon in " + cooldown.get(p.getName()) + " seconds");
					return;
				}
				cooldown.put(p.getName(), 11);

				new LoadBar(m, p, 4);
				sechular.put(p, Bukkit.getScheduler().scheduleSyncRepeatingTask(m, new Runnable() {
					int i=0;
					@Override
					public void run() {
						if(i<=28) {
							i++;
							p.setVelocity(p.getLocation().getDirection().multiply(0.7D).setY(0.3D));
							p.getWorld().playEffect(p.getLocation(), Effect.LAVA_POP, 1);
							p.getWorld().playEffect(p.getLocation(), Effect.SMOKE, 1);
						}else {
							Bukkit.getScheduler().cancelTask(sechular.get(p));
						}
					}
				}, 1L, 2L));
			}

		}

	}

	@EventHandler
	public void onBlockFromTo(BlockFromToEvent event) {
		@SuppressWarnings("deprecation")
		int id = event.getBlock().getTypeId();
		if (id == 8 || id == 9) {
			event.setCancelled(true);
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void userplayer(PlayerInteractEntityEvent e) {
		Player p = e.getPlayer();
		if (p.getItemInHand().getType() != Material.AIR)
			if (p.getItemInHand().getItemMeta().getDisplayName() != null)
				if (p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.BLUE + "Water Cage")) {
					if (cooldown != null && cooldown.containsKey(p.getName())) {
						p.sendMessage(
								ChatColor.RED + "You can use a weapon in " + cooldown.get(p.getName()) + " seconds");
						return;
					}
					if (e.getRightClicked() != null && e.getRightClicked() instanceof Player) {
						Player target = (Player) e.getRightClicked();
						names.add(target.getName());
						cooldown.put(p.getName(), 17);

						List<Block> a = new ArrayList<>();
						a.add(setWater(target.getLocation().add(0, 0, 1)));
						a.add(setWater(target.getLocation().add(0, 1, 1)));
						a.add(setWater(target.getLocation().add(0, 0, -1)));
						a.add(setWater(target.getLocation().add(0, 1, -1)));
						a.add(setWater(target.getLocation().add(1, 0, 0)));
						a.add(setWater(target.getLocation().add(1, 1, 0)));
						a.add(setWater(target.getLocation().add(-1, 0, 0)));
						a.add(setWater(target.getLocation().add(-1, 1, 0)));
						a.add(setWater(target.getLocation().add(0, 2, 0)));
						a.add(setWater(target.getLocation().add(0, -1, 0)));
						target.teleport(new Location(target.getWorld(), target.getLocation().getBlockX() + 0.5,
								target.getLocation().getBlockY(), target.getLocation().getBlockZ() + 0.5));
						new LoadBar(m, target, 5);

						new BukkitRunnable() {
							int i = 0;

							@Override
							public void run() {
								i++;
								if (i == 5) {
									names.remove(target.getName());
									for (Block b : a) {
										if (b != null)
											b.setType(Material.AIR);
									}

									this.cancel();
								}

							}
						}.runTaskTimer(m, 0, 20);
					}

				}
	}

	@EventHandler
	public void playerMove(PlayerMoveEvent e) {
		if (names.contains(e.getPlayer().getName()))
			e.setCancelled(true);
	}

	public Block setWater(Location loc) {
		if (loc.getBlock().getType() == Material.AIR) {
			loc.getBlock().setType(Material.WATER);
			return loc.getBlock();
		}
		return null;
	}

	@EventHandler
	public void onProjectileHit(ProjectileHitEvent e) {
		
		if (e.getEntity() instanceof Snowball) {
			Material[] cosmetics = { Material.STAINED_CLAY,Material.GLASS,Material.LAPIS_BLOCK,Material.REDSTONE_BLOCK,Material.PACKED_ICE,Material.RED_SANDSTONE };
			Location loc = e.getEntity().getLocation();
			List<Block> blocks = getNearbyBlocks(loc, 2);
			List<Material> da = new ArrayList<Material>();
			for (Block b : blocks) {
				da.add(b.getType());
			}
			for (Block b : blocks) {
				Random rnd = new Random();
				int i = rnd.nextInt(cosmetics.length);
				b.setType(cosmetics[i]);
			}
			new BukkitRunnable() {
				int i = 0;

				@Override
				public void run() {
					i++;
					if (i == 3) {
						for (int i = 0; i < blocks.size(); i++)
							blocks.get(i).setType(da.get(i));

						this.cancel();
					}

				}
			}.runTaskTimer(m, 0, 20);

			return;

		}
	}
	
	@EventHandler
	public void onProjectileLaunch(PlayerFishEvent e) {
		if(!fish.isEmpty() && fish.get(e.getPlayer().getName())!=null && fish.get(e.getPlayer().getName())) {

			final Player p = e.getPlayer();
			if (cooldown != null && cooldown.containsKey(p.getName())) {
				e.setCancelled(true);
				p.sendMessage(ChatColor.RED + "You can use a weapon in " + cooldown.get(p.getName()) + " seconds");
				return;
			}
			
			Location target =e.getHook().getLocation();
			final Vector v = new MathUtils().getVectorForPoints(p.getLocation(), target);			
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(m, new Runnable() {
				
				@Override
				public void run() {
					p.setVelocity(v);
					
				}
			},5);
				cooldown.put(p.getName(), 2);
				fish.remove(p.getName());
				
		}else {
			fish.put(e.getPlayer().getName(), true);
		}
	}

	public void cooldown() {
		new BukkitRunnable() {

			@Override
			public void run() {
				if (cooldown != null)
					for (Object x : cooldown.keySet().toArray()) {
						cooldown.put(x.toString(), cooldown.get(x) - 1);
						if (cooldown.get(x) == 0)
							cooldown.remove(x);

					}

			}

		}.runTaskTimerAsynchronously(m, 0, 20);
	}

	public void trail(Player p) {
		new BukkitRunnable() {
			double t = 0;

			@Override
			public void run() {
				t += 0.5;

				Location loc = p.getLocation();
				Vector direction = loc.getDirection().normalize();

				double x = direction.getX() * t;
				double y = direction.getY() * t + 1.5;
				double z = direction.getZ() * t;
				loc.add(x, y, z);
				p.getWorld().playEffect(loc, Effect.MOBSPAWNER_FLAMES, 1);
				loc.subtract(x, y, z);

				if (t > 30)
					this.cancel();
			}
		}.runTaskTimer(m, 0, 1);
	}

	public static List<Block> getNearbyBlocks(Location location, int radius) {
		List<Block> blocks = new ArrayList<Block>();
		for (int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
			for (int y = location.getBlockY() - radius; y <= location.getBlockY() + radius; y++) {
				for (int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
					Block a = location.getWorld().getBlockAt(x, y, z);
					if (a.getType() != Material.AIR)
						blocks.add(a);
				}
			}
		}
		return blocks;
	}

}
