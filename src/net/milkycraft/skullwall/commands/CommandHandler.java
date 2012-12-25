package net.milkycraft.skullwall.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.milkycraft.MineReader;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * @author krinsdeath
 */
public class CommandHandler {

	@SuppressWarnings("unused")
	private final MineReader plugin;
	private final Map<String, Command> commands = new HashMap<String, Command>();

	public CommandHandler(final MineReader plugin) {
		this.plugin = plugin;
		this.commands.put("download", new DownloadCommand(plugin));
	}

	public void runCommand(final CommandSender sender, final String label,
			final String[] args) {
		if (args.length == 0
				|| this.commands.get(args[0].toLowerCase()) == null) {
			sender.sendMessage(ChatColor.GREEN + "===" + ChatColor.GOLD
					+ " SkullWalls Help " + ChatColor.GREEN + "===");
			for (final Command cmd : this.commands.values()) {
				if (cmd.checkPermission(sender)) {
					cmd.showHelp(sender, label);
				}
			}
			return;
		}
		final List<String> arguments = new ArrayList<String>(
				Arrays.asList(args));
		final Command cmd = this.commands
				.get(arguments.remove(0).toLowerCase());
		if (arguments.size() < cmd.getRequiredArgs()) {
			cmd.showHelp(sender, label);
			return;
		}
		cmd.runCommand(sender, arguments);
	}

}