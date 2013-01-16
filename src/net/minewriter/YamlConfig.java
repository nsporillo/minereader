package net.minewriter;

import java.util.List;

public class YamlConfig extends YamlLoader{

	public boolean cache, black, debug;
	public List<String> authors, titles;
	public List<Integer> ids;
	public YamlConfig(MineReader plugin, String fileName) {
		super(plugin, fileName);
		super.saveIfNotExist();
		super.load();
	}

	@Override
	protected void loadKeys() {
		cache = config.getBoolean("General.Cache-books");
		black = config.getBoolean("General.Use-Blacklist");
		debug = config.getBoolean("General.Debug");
		if(black) {
			ids = config.getIntegerList("Blacklist.IDs");
			authors = config.getStringList("Blacklist.Authors");
			titles = config.getStringList("Blacklist.Titles");
		}
	}
	
	public boolean isBanned(Book b) {
		if(!black) {
			return false;
		}
		if(authors.contains(b.getAuthor())) {
			return true;
		}
		if(titles.contains(b.getTitle())) {
			return true;
		}
		if(ids.contains(b.getID())) {
			return true;
		}
		return false;
	}
}
