package net.milkycraft;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.json.JSONObject;

public class Book extends ItemStack {
	public Book(String urlpath) throws Exception {
		super(Material.WRITTEN_BOOK);
		BookMeta meta = (BookMeta) this.getItemMeta();
		meta.setTitle("Downloaded book");
		meta.setAuthor("Anonymous author");
		List<String> pages = new ArrayList<String>();
		List<String> lore = new ArrayList<String>();
		String content = null;
			JSONObject json = JsonReader.read(urlpath);
			meta.setAuthor(json.getString("Author"));
			meta.setTitle(json.getString("Title"));
			content = json.getString("Content");
		for (String s : delimit(content, 256)) {
			pages.add(ChatColor.translateAlternateColorCodes('$', s));
		}
		meta.setPages(pages);
		meta.setLore(lore);
		this.setItemMeta(meta);
	}

	public Book(Material stack) {
		super(stack);
	}

	public String[] delimit(String str, int chunk) {
		int arraySize = (int) Math.ceil((double) str.length() / chunk);
		String[] returnArray = new String[arraySize];
		int index = 0;
		for (int i = 0; i < str.length(); i = i + chunk) {
			if (str.length() - i < chunk) {
				returnArray[index++] = str.substring(i);
			} else {
				returnArray[index++] = str.substring(i, i + chunk);
			}
		}
		return returnArray;
	}
}