package me.yourpixel.cosmetics;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.yourpixel.sql.MySQLProperties;
import me.yourpixel.sql.MySQLSetup;

public class GUI implements Listener {
	MySQLProperties sql;

	public GUI(MySQLSetup m) {
		sql = new MySQLProperties(m);
	}

	@EventHandler
	public void onJoinEvent(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if (!sql.isUUIDExists(p.getUniqueId())) {
			sql.CreatePlayer(p, 100);
			Bukkit.getServer().getConsoleSender()
					.sendMessage("Player " + p.getName() + " inserted to SQL");
		}
		if (!sql.getName(p.getUniqueId() + "").equalsIgnoreCase(p.getName())) {
			sql.updateName(p.getUniqueId() + "", p.getName());
		}

		ItemStack a = new ItemStack(Material.CHEST);
		ItemMeta chest = a.getItemMeta();

		chest.setDisplayName(ChatColor.YELLOW + "Cosmetics");
		ArrayList<String> lore = new ArrayList<>();
		lore.add(ChatColor.BLUE + "Cosmetics");
		chest.setLore(lore);
		a.setItemMeta(chest);
		p.getInventory().setItem(0, a);

	}

	public ItemStack createItem(Inventory inv, Material mat, int ammount, int invslot, String displayName,
			String... lorelist) {
		ItemStack item;
		List<String> lore = new ArrayList<>();
		item = new ItemStack(mat, ammount);

		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(displayName);
		for (String a : lorelist)
			lore.add(a);
		meta.setLore(lore);
		item.setItemMeta(meta);
		inv.setItem(invslot - 1, item);

		return item;
	}

	public void mainPage(Player p) {

		Inventory inv = Bukkit.createInventory(null, 45, ChatColor.YELLOW + "Cosmetics");
		createItem(inv, Material.NETHER_STAR, 1, 10, ChatColor.YELLOW + "Particles", "Cool gadgets");
		createItem(inv, Material.DIAMOND_SWORD, 1, 14, ChatColor.BLUE + "Weapons", "SHOOT EVERYWHERE");
		createItem(inv, Material.MONSTER_EGG, 1, 18, ChatColor.DARK_PURPLE + "Morphs",
				"Have you ever wanted to be a mob?");
		createItem(inv, Material.DIAMOND_CHESTPLATE, 1, 30, ChatColor.AQUA + "Suits", "You can wear arrmor",
				"that gives you buffs");
		createItem(inv, Material.SADDLE, 1, 34, ChatColor.GREEN + "Mounts", "You can ride on a mount",
				"for amount of time");
		p.openInventory(inv);
		return;
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void click(PlayerInteractEvent e) {
		if(e.getItem()!=null && e.getItem().getItemMeta().getDisplayName() != null)
		if (e.getPlayer().getItemInHand().getItemMeta().getDisplayName()
				.equalsIgnoreCase(ChatColor.YELLOW + "Cosmetics")) {
			e.setCancelled(true);
			mainPage(e.getPlayer());
		}
	}

	public void particlePage(Player p) {
		Inventory inv = Bukkit.createInventory(null, 45, ChatColor.YELLOW + "Particles");
		createItem(inv, Material.BARRIER, 1, 45, ChatColor.RED + "Remove Particles", "Removes all particles");
		createItem(inv, Material.GLASS, 1, 37, ChatColor.DARK_RED + "Back", "Back to the main page");
		if (sql.getPoints(p.getUniqueId() + "") >= 100) {
			createItem(inv, Material.BLAZE_POWDER, 1, 12, ChatColor.GOLD + "Flame Trail", "Cool flame trail");
		} else
			createItem(inv, Material.STAINED_GLASS, 1, 12, ChatColor.GOLD + "Flame Trail", ChatColor.RED + "LOCKED");

		if (sql.getPoints(p.getUniqueId() + "") >= 200) {
			createItem(inv, Material.APPLE, 1, 13, ChatColor.RED + "Heart Trail", "Heart on top of your head");

		} else
			createItem(inv, Material.STAINED_GLASS, 1, 13, ChatColor.GOLD + "Heart Trail", ChatColor.RED + "LOCKED");

		if (sql.getPoints(p.getUniqueId() + "") >= 300) {
			createItem(inv, Material.NOTE_BLOCK, 1, 14, ChatColor.DARK_PURPLE + "Notes Trail", "Notes trail");
		} else
			createItem(inv, Material.STAINED_GLASS, 1, 14, ChatColor.DARK_PURPLE + "Notes Train",
					ChatColor.RED + "LOCKED");

		if (sql.getPoints(p.getUniqueId().toString()) >= 400) {
			createItem(inv, Material.LEASH, 1, 15, ChatColor.AQUA + "Heart Snake", "Heart snake follows you");
		} else
			createItem(inv, Material.STAINED_GLASS, 1, 15, ChatColor.AQUA + "Heart Snake", ChatColor.RED + "LOCKED");
		
		if(sql.getPoints(p.getUniqueId().toString()) >= 500) {
			createItem(inv, Material.WATER_BUCKET, 1, 16, ChatColor.BLUE+"Water Zone", ChatColor.BLUE+"Press sneak and release", ChatColor.BLUE+"To make a water zone around you!");
		} else 
			createItem(inv, Material.STAINED_GLASS, 1, 16, ChatColor.BLUE+"Water Zone", ChatColor.RED+"LOCKED");
		p.openInventory(inv);
		return;
	}
	
	public void weaponsPage(Player p) {
		Inventory inv = Bukkit.createInventory(null, 45, ChatColor.BLUE + "Weapons");
		createItem(inv, Material.BARRIER, 1, 45, ChatColor.GRAY + "Remove Particles", "Removes all particles");
		createItem(inv, Material.GLASS, 1, 37, ChatColor.DARK_RED + "Back", "Back to the main page");
		
		//cannon trail
		if (sql.getPoints(p.getUniqueId() + "") >= 100) {
			createItem(inv, Material.GOLD_HOE, 1, 12, ChatColor.GOLD + "Cannon Flame Trail", "Cool flame trail");
		} else
			createItem(inv, Material.STAINED_GLASS, 1, 12, ChatColor.GOLD + "Cannon Flame Trail", ChatColor.RED + "LOCKED");

		//paint brush
		if (sql.getPoints(p.getUniqueId() + "") >= 200) {
			createItem(inv, Material.STICK, 1, 13, ChatColor.translateAlternateColorCodes('&', "&aP&ca&4i&bn&ft &ac&ca&4nn&co&4n") , "Heart on top of your head");

		} else
			createItem(inv, Material.STAINED_GLASS, 1, 13, ChatColor.GOLD + "Paint cannon", ChatColor.RED + "LOCKED");

		
		//water cage
		if (sql.getPoints(p.getUniqueId() + "") >= 300) {
			createItem(inv, Material.IRON_BARDING, 1, 14, ChatColor.BLUE + "Water Cage", "If you hit a player","It puts him in water cage for 3 seconds");
		} else
			createItem(inv, Material.STAINED_GLASS, 1, 14, ChatColor.DARK_PURPLE + "Notes Train",
					ChatColor.RED + "LOCKED");

		
		//Grappling hook
		if (sql.getPoints(p.getUniqueId().toString()) >= 400) {
			createItem(inv, Material.FISHING_ROD, 1, 15, ChatColor.GRAY + "Grappling Hook", "Launch you to the hook direction");
		} else
			createItem(inv, Material.STAINED_GLASS, 1, 15, ChatColor.AQUA + "Heart Snake", ChatColor.RED + "LOCKED");
		
		
		//jetpack
		if(sql.getPoints(p.getUniqueId().toString()) >= 500) {
			createItem(inv, Material.ELYTRA, 1, 16, ChatColor.LIGHT_PURPLE+"JetPack", "Press to fly for 4 seconds");
		} else 
			createItem(inv, Material.STAINED_GLASS, 1, 16, ChatColor.LIGHT_PURPLE+"JetPack", ChatColor.RED+"LOCKED");
		
		
		p.openInventory(inv);
		return;
	}
	
	public void suitsPage(Player p) {
		Inventory inv = Bukkit.createInventory(null, 45, ChatColor.AQUA + "Suits");
		createItem(inv, Material.BARRIER, 1, 45, ChatColor.RED+"Remove suits", "Removes your suit");
		createItem(inv, Material.GLASS, 1, 37, ChatColor.DARK_RED + "Back", "Back to the main page");
		
		//Astronaut
		if (sql.getPoints(p.getUniqueId() + "") >= 100) {
			createItem(inv, Material.IRON_HELMET, 1, 12, ChatColor.GRAY + "Astronaut", "Anti-Gravitiy and slowness effect");
		} else
			createItem(inv, Material.STAINED_GLASS, 1, 12, ChatColor.GOLD + "Cannon Flame Trail", ChatColor.RED + "LOCKED");

		//Sonic
		if (sql.getPoints(p.getUniqueId() + "") >= 200) {
			//CHANGE TO SONIC HELMET
			createItem(inv, Material.LEATHER_HELMET, 1, 13, ChatColor.BLUE+"Sonic" , "Speed effect");

		} else
			createItem(inv, Material.STAINED_GLASS, 1, 13, ChatColor.BLUE+"Sonic", ChatColor.RED + "LOCKED");

		
		//Ninja
		if (sql.getPoints(p.getUniqueId() + "") >= 300) {
			//CHANGE TO BLACK HELMET
			createItem(inv, Material.LEATHER_HELMET, 1, 14, ChatColor.BLACK + "Ninja", "Speed and jump effect");
		} else
			createItem(inv, Material.STAINED_GLASS, 1, 14, ChatColor.BLACK + "Ninja", ChatColor.RED + "LOCKED");

		
		//TNT 
		if (sql.getPoints(p.getUniqueId().toString()) >= 400) {
			createItem(inv, Material.TNT, 1, 15, ChatColor.RED + "TNT", "Launch TNT");
		} else
			createItem(inv, Material.STAINED_GLASS, 1, 15,ChatColor.RED + "TNT", ChatColor.RED + "LOCKED");
		
		
		//jetpack
		if(sql.getPoints(p.getUniqueId().toString()) >= 500) {
			//REPLACE TO ELYTRA
			createItem(inv, Material.BARRIER, 1, 16, ChatColor.LIGHT_PURPLE+"JetPack", "Press to fly for 5 seconds");
		} else 
			createItem(inv, Material.STAINED_GLASS, 1, 16, ChatColor.LIGHT_PURPLE+"JetPack", ChatColor.RED+"LOCKED");
		
		
		p.openInventory(inv);
		return;
	}

	@EventHandler
	public void inventoryClick(InventoryClickEvent e) {
		if (e.getCurrentItem()!=null && e.getCurrentItem().hasItemMeta()) {
			if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.YELLOW + "particles")) {
				e.setCancelled(true);
				particlePage((Player) e.getWhoClicked());
			}
			else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.BLUE+"weapons")) {
				e.setCancelled(true);
				weaponsPage((Player)e.getWhoClicked());
			}
			else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.AQUA + "Suits")) {
				e.setCancelled(true);
				suitsPage((Player)e.getWhoClicked());
			}
			else if (e.getCurrentItem().getItemMeta().getLore().get(0).equalsIgnoreCase(ChatColor.RED + "LOCKED")) {
				e.setCancelled(true);
			}
		}

	}

}
