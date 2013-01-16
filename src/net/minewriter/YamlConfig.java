package net.minewriter;

import java.util.List;

public class YamlConfig extends YamlLoader{

	public boolean cache, black;
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
		if(black) {
			ids = config.getIntegerList("Blacklist.IDs");
			authors = config.getStringList("Blacklist.Authors");
			titles = config.getStringList("Blacklist.Titles");
		}
	}
	
	public boolean isAllowed(Book b) {
		if(!black) {
			return true;
		}
		if(authors.contains(b.getAuthor())) {
			return false;
		}
		if(titles.contains(b.getTitle())) {
			return false;
		}
		if(ids.contains(b.getID())) {
			return false;
		}
		return true;
	}
}
