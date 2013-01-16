package net.minewriter;

import net.minewriter.commands.CommandHandler;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class MineReader extends JavaPlugin {

	private CommandHandler commands;
	private static Library lib = new Library();
	private YamlConfig conf;
	
	@Override
	public void onEnable() {
		commands = new CommandHandler(this);
		conf = new YamlConfig(this, "config.yml");
	}

	@Override
	public void onDisable() {
		lib = null;
	}

	public void info(String info) {
		this.getLogger().info(info);
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd,
			final String label, final String[] args) {
		try {
			this.commands.runCommand(sender, label, args);
		} catch (Exception ex) {
			sender.sendMessage(ChatColor.RED + "Error: " + ex.getMessage());
		}
		return true;
	}
	
	public static Library getLibrary() {
		return lib;
	}
	
	public YamlConfig getConf() {
		return conf;
	}
}
