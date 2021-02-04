package me.yourpixel.cosmetics;

import java.util.Collection;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.yourpixel.sql.MySQLSetup;

public class Suits implements Listener {

	private Main m;

	public Suits(Main m) {
		this.m = m;
	}
	
	

	@EventHandler
	public void inventoryClick(InventoryClickEvent e) {
		if (e.getCurrentItem() != null && e.getCurrentItem().hasItemMeta()
				&& e.getCurrentItem().getType() != Material.STAINED_GLASS) {
			String item_name = e.getCurrentItem().getItemMeta().getDisplayName();
			Player p = (Player) e.getWhoClicked();
			
			//Astronaut
			if(item_name.equalsIgnoreCase(ChatColor.GRAY + "Astronaut")) {
				e.setCancelled(true);
				astronaut(p);
				new GUI(new MySQLSetup(m)).mainPage(p);
			}
			
			//sonic
			if(item_name.equalsIgnoreCase(ChatColor.BLUE+"Sonic")) {
				e.setCancelled(true);
				sonic(p);
				new GUI(new MySQLSetup(m)).mainPage(p);
			}
			//Ninja
			if(item_name.equalsIgnoreCase(ChatColor.BLACK + "Ninja")) {
				e.setCancelled(true);
				ninja(p);
				new GUI(new MySQLSetup(m)).mainPage(p);
			}
			
			
			
			if(item_name.equalsIgnoreCase(ChatColor.RED+"Remove suits")) {
				e.setCancelled(true);
				removeEffects(p);
				p.getInventory().setHelmet(null);
				p.getInventory().setChestplate(null);
				p.getInventory().setLeggings(null);
				p.getInventory().setBoots(null);
			}
			
		}
		
		
	}
	
	public void astronaut(Player p) {
		p.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
		p.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
		p.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
		p.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
		removeEffects(p);
		p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 2, true, false)); 
		p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 1, true, false)); 

	}
	
	public void sonic(Player p) {
		ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
		setColor(helmet,Color.BLUE);
		
		ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
		setColor(chestplate, Color.BLUE);
		
		ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
		setColor(leggings, Color.BLUE);
		
		ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
		setColor(boots, Color.BLUE);
		
		p.getInventory().setHelmet(helmet);
		p.getInventory().setChestplate(chestplate);
		p.getInventory().setLeggings(leggings);
		p.getInventory().setBoots(boots);
		removeEffects(p);
		p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 3, true, false)); 
	}
	
	public void ninja(Player p) {
		ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
		setColor(helmet,Color.BLACK);
		
		ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
		setColor(chestplate, Color.BLACK);
		
		ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
		setColor(leggings, Color.BLACK);
		
		ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
		setColor(boots, Color.BLACK);
		
		p.getInventory().setHelmet(helmet);
		p.getInventory().setChestplate(chestplate);
		p.getInventory().setLeggings(leggings);
		p.getInventory().setBoots(boots);
		removeEffects(p);
		p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 2, true, false)); 
		p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2, true, false)); 
		
	}
	
	public void setColor(ItemStack a,Color color) {
        LeatherArmorMeta legsmeta = (LeatherArmorMeta) a.getItemMeta();
        legsmeta.setColor(color);
        a.setItemMeta(legsmeta);
	}

	
	public void removeEffects(Player p) {
		Collection<PotionEffect> a = p.getActivePotionEffects();
		for(PotionEffect type : a) {
			p.removePotionEffect(type.getType());
		}
	}
}
