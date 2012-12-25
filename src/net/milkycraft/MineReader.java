package net.milkycraft;

import net.milkycraft.skullwall.commands.CommandHandler;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class MineReader extends JavaPlugin {

	private CommandHandler commands;

	@Override
	public void onEnable() {
		commands = new CommandHandler(this);
	}

	@Override
	public void onDisable() {

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
}
