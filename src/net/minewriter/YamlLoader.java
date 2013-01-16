package net.minewriter;

import static org.bukkit.configuration.file.YamlConfiguration.loadConfiguration;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;

public abstract class YamlLoader {

	protected String fileName;
	protected File configFile;
	protected File dataFolder;
	protected MineReader plugin;
	protected FileConfiguration config;

	protected YamlLoader(final MineReader plugin, final String fileName) {
		this.plugin = plugin;
		this.fileName = fileName;
		this.dataFolder = plugin.getDataFolder();
		this.configFile = new File(this.dataFolder, File.separator + fileName);
		config = loadConfiguration(this.configFile);
	}

	protected void load() {
		if (!this.configFile.exists()) {
			this.dataFolder.mkdir();
			saveConfig();
		}
		addDefaults();
		loadKeys();
		this.saveIfNotExist();
	}

	protected void saveConfig() {
		try {
			config.save(this.configFile);
		} catch (IOException ex) {
		}
	}

	protected void saveIfNotExist() {
		if (!this.configFile.exists()) {
			if (this.plugin.getResource(this.fileName) != null) {
				this.plugin.saveResource(this.fileName, false);
			}
		}
		rereadFromDisk();
	}

	protected void rereadFromDisk() {
		config = loadConfiguration(this.configFile);
	}

	protected FileConfiguration getYaml() {
		return this.config;
	}

	protected void addDefaults() {
		config.options().copyDefaults(true);
		saveConfig();
	}

	protected abstract void loadKeys();

}