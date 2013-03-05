package net.minewriter;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.json.JSONObject;

public class Book extends ItemStack {

	private String title;
	private String url;
	private String author;
	private int id;
	private List<String> pages = new ArrayList<String>();
	private List<String> lore = new ArrayList<String>();
	private BookMeta meta;
	private JSONObject json;

	public Book(String urlpath) throws Exception {
		super(Material.WRITTEN_BOOK);
		url = urlpath;
		json = JsonReader.read(url);
		title = json.getString("Title");
		author = json.getString("Author");
		id = json.getInt("ID");
		meta = (BookMeta) getItemMeta();
		for (String s : delimit(json.getString("Content"), 256)) {
			pages.add(ChatColor.translateAlternateColorCodes('$', s));
		}
		meta.setAuthor(author);
		meta.setTitle(title);
		meta.setPages(pages);
		meta.setLore(lore);
		setItemMeta(meta);
	}

	private static String[] delimit(String str, int chunk) {
		int size = (int) Math.ceil((double) str.length() / chunk);
		String[] arr = new String[size];
		int dex = 0;
		for (int i = 0; i < str.length(); i = i + chunk) {
			if (str.length() - i < chunk) {
				arr[dex++] = str.substring(i);
			} else {
				arr[dex++] = str.substring(i, i + chunk);
			}
		}
		return arr;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public int getID() {
		return id;
	}

	public boolean isBanned(MineReader plugin) {
		return plugin.getConf().isBanned(this);
	}
}